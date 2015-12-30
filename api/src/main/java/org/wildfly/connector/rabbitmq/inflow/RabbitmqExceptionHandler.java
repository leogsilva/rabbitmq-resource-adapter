package org.wildfly.connector.rabbitmq.inflow;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpointFactory;

import org.jboss.logging.Logger;
import org.wildfly.connector.rabbitmq.RabbitmqResourceAdapter;

import com.rabbitmq.client.Connection;

public abstract class RabbitmqExceptionHandler implements ExceptionListener {

	protected static Logger log = Logger
			.getLogger(RabbitmqExceptionHandler.class.getName());

	public static final Method ONMESSAGE;

	static {
		try {
			ONMESSAGE = MessageListener.class.getMethod("onMessage",
					new Class[] { Message.class });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Whether or not we have completed activating
	protected AtomicBoolean _activated = new AtomicBoolean(false);

	// Whether we are in the failure recovery loop
	protected AtomicBoolean _inFailure = new AtomicBoolean(false);

	private boolean isDeliveryTransacted;

	protected Connection connection;
	protected RabbitmqResourceAdapter ra;
	protected RabbitmqActivationSpec spec;
	protected MessageEndpointFactory endpointFactory;
	protected final AtomicBoolean deliveryActive = new AtomicBoolean(false);

	private boolean _isTopic;

	public abstract void setup() throws Exception;

	public abstract void start() throws Exception;

	public abstract void stop();

	public RabbitmqExceptionHandler(RabbitmqResourceAdapter ra,
			RabbitmqActivationSpec spec, MessageEndpointFactory endpointFactory)
			throws ResourceException {
		this.ra = ra;
		this.spec = spec;
		this.endpointFactory = endpointFactory;

		try {
			isDeliveryTransacted = endpointFactory
					.isDeliveryTransacted(ONMESSAGE);
		} catch (Exception e) {
			throw new ResourceException(e);
		}
	}

	public void onException(JMSException e) {
		if (_activated.get()) {
			handleFailure(e);
		} else {
			log.warn("Received JMSException: " + e
					+ " while endpoint was not activated.");
		}
	}

	/**
	 * Handles any failure by trying to reconnect
	 *
	 * @param failure
	 *            the reason for the failure
	 */
	public void handleFailure(Throwable failure) {
		int reconnectCount = 0;
		int setupAttempts = spec.getSetupAttempts();
		long setupInterval = spec.getSetupInterval();

		// Only enter the failure loop once
		if (_inFailure.getAndSet(true))
			return;
		try {
			while (deliveryActive.get()
					&& (setupAttempts == -1 || reconnectCount < setupAttempts)) {
				teardown();

				try {
					Thread.sleep(setupInterval);
				} catch (InterruptedException e) {
					log.debug("Interrupted trying to reconnect " + spec, e);
					break;
				}

				log.info("Attempting to reconnect " + spec);
				try {
					setup();
					log.info("Reconnected with rabbitmq");
					break;
				} catch (Throwable t) {
					log.error("Error on setuping connection", t);
				}
				++reconnectCount;
			}
		} finally {
			// Leaving failure recovery loop
			_inFailure.set(false);
		}
	}

	protected synchronized void teardown() {
		log.debug("Tearing down " + spec);

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Throwable t) {
			log.debug("Error stopping connection " + connection, t);
		}

		log.debug("Tearing down complete " + this);
	}

}
