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
package org.wildfly.connector.rabbitmq;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.jboss.logging.Logger;
import org.wildfly.connector.rabbitmq.inflow.RabbitmqActivation;
import org.wildfly.connector.rabbitmq.inflow.RabbitmqActivationSpec;

import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitmqResourceAdapter
 *
 * @version $Revision: $
 */
@Connector(reauthenticationSupport = false, transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction)
public class RabbitmqResourceAdapter implements ResourceAdapter,
		java.io.Serializable {

	/** The serial version UID */
	private static final long serialVersionUID = 1L;

	/** The logger */
	private static Logger log = Logger.getLogger(RabbitmqResourceAdapter.class
			.getName());

	/** The activations by activation spec */
	private ConcurrentHashMap<RabbitmqActivationSpec, RabbitmqActivation> activations;

	@ConfigProperty(defaultValue = "")
	private String rarabbit;
	
	/** uri */
	@ConfigProperty(defaultValue = "")
	private String uri;

	@ConfigProperty(defaultValue = "")
	private int connectionTimeout;

	@ConfigProperty(defaultValue = "")
	private int requestedHeartbeat;

	private BootstrapContext bootstrapContext;

	private ConnectionFactory rabbitCF;

	/**
	 * Default constructor
	 */
	public RabbitmqResourceAdapter() {
		this.activations = new ConcurrentHashMap<RabbitmqActivationSpec, RabbitmqActivation>();

	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	public void setRequestedHeartbeat(int requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}

	/**
	 * This is called during the activation of a message endpoint.
	 *
	 * @param endpointFactory
	 *            A message endpoint factory instance.
	 * @param spec
	 *            An activation spec JavaBean instance.
	 * @throws ResourceException
	 *             generic exception
	 */
	public void endpointActivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) throws ResourceException {

		if (!equals(spec.getResourceAdapter())) {
			throw new ResourceException(
					"Activation spec not initialized with this ResourceAdapter instance ("
							+ spec.getResourceAdapter() + " != " + this + ")");
		}

		if (!(spec instanceof RabbitmqActivationSpec)) {
			throw new NotSupportedException(
					"That type of ActivationSpec not supported: "
							+ spec.getClass());
		}

		RabbitmqActivation activation = new RabbitmqActivation(this,
				(RabbitmqActivationSpec) spec, endpointFactory);
		activations.put((RabbitmqActivationSpec) spec, activation);
		activation.start();

		log.tracef("endpointActivation(%s, %s)", endpointFactory, spec);

	}

	/**
	 * This is called when a message endpoint is deactivated.
	 *
	 * @param endpointFactory
	 *            A message endpoint factory instance.
	 * @param spec
	 *            An activation spec JavaBean instance.
	 */
	public void endpointDeactivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) {
		RabbitmqActivation activation = activations.remove(spec);
		if (activation != null)
			activation.stop();

		log.tracef("endpointDeactivation(%s)", endpointFactory);

	}

	/**
	 * This is called when a resource adapter instance is bootstrapped.
	 *
	 * @param ctx
	 *            A bootstrap context containing references
	 * @throws ResourceAdapterInternalException
	 *             indicates bootstrap failure.
	 */
	public void start(BootstrapContext ctx)
			throws ResourceAdapterInternalException {
		log.tracef("start(%s)", ctx);
		this.bootstrapContext = ctx;
		rabbitCF = new ConnectionFactory();
		try {
			rabbitCF.setUri(uri);
			rabbitCF.setConnectionTimeout(getConnectionTimeout());
			rabbitCF.setRequestedHeartbeat(getRequestedHeartbeat());
		} catch (KeyManagementException | NoSuchAlgorithmException
				| URISyntaxException e) {
			throw new ResourceAdapterInternalException(e);
		}

	}

	/**
	 * This is called when a resource adapter instance is undeployed or during
	 * application server shutdown.
	 */
	public void stop() {
		log.trace("stop()");
		this.bootstrapContext = null;
	}

	/**
	 * This method is called by the application server during crash recovery.
	 *
	 * @param specs
	 *            An array of ActivationSpec JavaBeans
	 * @throws ResourceException
	 *             generic exception
	 * @return An array of XAResource objects
	 */
	public XAResource[] getXAResources(ActivationSpec[] specs)
			throws ResourceException {
		log.tracef("getXAResources(%s)", specs.toString());
		return null;
	}

	@Override
	public String toString() {
		return "RabbitmqResourceAdapter [activations=" + activations + ", uri="
				+ uri + ", connectionTimeout=" + connectionTimeout
				+ ", requestedHeartbeat=" + requestedHeartbeat
				+ ", bootstrapContext=" + bootstrapContext + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + connectionTimeout;
		result = prime * result + requestedHeartbeat;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RabbitmqResourceAdapter other = (RabbitmqResourceAdapter) obj;
		if (connectionTimeout != other.connectionTimeout)
			return false;
		if (requestedHeartbeat != other.requestedHeartbeat)
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	/**
	 * Return internal connection factory from rabbitmq for future use on inflow feature
	 * @return
	 */
	public ConnectionFactory internalConnectionFactory() {
		return this.rabbitCF;
	}
}
