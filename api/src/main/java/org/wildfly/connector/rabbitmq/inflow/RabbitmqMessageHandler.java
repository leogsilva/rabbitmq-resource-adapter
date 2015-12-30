package org.wildfly.connector.rabbitmq.inflow;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;

import org.jboss.logging.Logger;
import org.wildfly.connector.rabbitmq.RabbitmqAdminQueueImpl;
import org.wildfly.connector.rabbitmq.RabbitmqResourceAdapter;
import org.wildfly.connector.rabbitmq.Util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitmqMessageHandler extends RabbitmqExceptionHandler {

	/** The logger */
	private static Logger log = Logger.getLogger(RabbitmqMessageHandler.class
			.getName());

	private DefaultConsumer consumer;
	private Channel channel;

	public RabbitmqMessageHandler(final RabbitmqResourceAdapter ra,
			final RabbitmqActivationSpec spec,
			final MessageEndpointFactory endpointFactory,
			final Channel channel) throws ResourceException {
		super(ra, spec, endpointFactory);
		this.channel = channel;
		this.spec = spec;
		this.ra = ra;
	}
	
	public void setup() throws Exception {
		if (log.isTraceEnabled()) {
			log.trace("setup()");
		}
		this.consumer = new DefaultConsumer(this.channel) {
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				MessageEndpoint localEndpoint;
				try {
					localEndpoint = endpointFactory.createEndpoint(null);
					RabbitmqBytesMessage m = new RabbitmqBytesMessage(consumerTag,envelope,properties,body);
					onMessage(localEndpoint, m);
				} catch (UnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		if ("javax.jms.Queue".equals(this.spec.getDestinationType())) {
			RabbitmqAdminQueueImpl queue = Util.lookup(new InitialContext(), this.spec.getDestination(), RabbitmqAdminQueueImpl.class);
			this.channel.basicConsume(queue.getDestinationAddress(),true, consumer);
		}
	}

	public void onMessage(MessageEndpoint localEndpoint, Message message) {
		if (log.isTraceEnabled()) {
			log.trace("onMessage(" + Util.asString(message) + ")");
		}

		boolean beforeDelivery = false;

		try {

			localEndpoint.beforeDelivery(RabbitmqActivation.ONMESSAGE);
			beforeDelivery = true;

			((MessageListener) localEndpoint).onMessage(message);
			message.acknowledge();

			try {
				localEndpoint.afterDelivery();
			} catch (ResourceException e) {
				log.warn("Unable to call after delivery", e);
				return;
			}
		} catch (Throwable e) {
			log.error("Failed to deliver message", e);
			// we need to call before/afterDelivery as a pair
			if (beforeDelivery) {
				try {
					localEndpoint.afterDelivery();
				} catch (ResourceException e1) {
					log.warn("Unable to call after delivery", e);
				}
			}
		}
	}

	@Override
	public void start() throws Exception {
		deliveryActive.set(true);
		setup();
	}

	@Override
	public void stop() {
		deliveryActive.set(false);
		teardown();
	}

	@Override
	protected synchronized void teardown() {
		if (this.channel != null) {
			try {
				this.channel.close();
			} catch (IOException | TimeoutException e) {
				log.debug("Error stopping channel " + this.channel, e);
			}
		}
		super.teardown();
	}
}
