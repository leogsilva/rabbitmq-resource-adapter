/*
 * IronJacamar, a Java EE Connector Architecture implementation
 * Copyright 2013, Red Hat Inc, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.connector.rabbitmq.inflow;

import java.util.ArrayList;
import java.util.List;

import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;

import org.wildfly.connector.rabbitmq.RabbitmqResourceAdapter;

import com.rabbitmq.client.Channel;

/**
 * RabbitmqActivation
 *
 * @version $Revision: $
 */
public class RabbitmqActivation extends RabbitmqExceptionHandler {

	public RabbitmqActivation(RabbitmqResourceAdapter ra,
			RabbitmqActivationSpec spec, MessageEndpointFactory endpointFactory)
			throws ResourceException {
		super(ra, spec, endpointFactory);
		this.ra = ra;
		this.spec = spec;
		this.endpointFactory = endpointFactory;
	}

	private final List<RabbitmqMessageHandler> _handlers = new ArrayList<RabbitmqMessageHandler>();
	/** The resource adapter */
	private RabbitmqResourceAdapter ra;

	/** Activation spec */
	private RabbitmqActivationSpec spec;

	/** The message endpoint factory */
	private MessageEndpointFactory endpointFactory;

	/**
	 * Get activation spec class
	 * 
	 * @return Activation spec
	 */
	public RabbitmqActivationSpec getActivationSpec() {
		return spec;
	}

	/**
	 * Get message endpoint factory
	 * 
	 * @return Message endpoint factory
	 */
	public MessageEndpointFactory getMessageEndpointFactory() {
		return endpointFactory;
	}

	/**
	 * Start the activation
	 * 
	 * @throws ResourceException
	 *             Thrown if an error occurs
	 */
	public void start() throws ResourceException {
		deliveryActive.set(true);
		new Thread(new SetupActivation()).start();
	}

	/**
	 * Stop the activation
	 */
	public void stop() {
		for(RabbitmqMessageHandler handler : _handlers) {
			handler.stop();
		}
	}

	/**
	 * Handles the setup
	 */
	private class SetupActivation implements Work {

		public SetupActivation() {
		}

		public void run() {
			try {
				setup();
			} catch (Throwable t) {
				handleFailure(t);
			}
		}

		public void release() {
		}
	}

	@Override
	public void setup() throws Exception {
		log.debug("Setting up " + spec);

		connection = this.ra.internalConnectionFactory().newConnection();
		Channel channel = connection.createChannel();
		_activated.set(true);

		for (int i = 0; i < spec.getMaxSession(); i++) {
			try {
				RabbitmqMessageHandler handler = null;

				if (spec.isUseConnectionPerHandler()) {
					handler = new RabbitmqMessageHandler(this.ra, this.spec,
							this.endpointFactory, channel);
				}

				handler.start();
				_handlers.add(handler);
			} catch (Exception e) {
				try {
					if (connection != null) {
						this.connection.close();
					}
				} catch (Exception e2) {
					log.trace("Ignored error closing connection", e2);
				}

				throw e;

			}

		}

		log.debug("Setup complete " + this);
	}

}
