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

import org.jboss.logging.Logger;

import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

/**
 * RabbitmqActivationSpec
 *
 * @version $Revision: $
 */
@Activation(messageListeners = { javax.jms.MessageListener.class })
public class RabbitmqActivationSpec implements ActivationSpec {

	/** The logger */
	private static Logger log = Logger.getLogger(RabbitmqActivationSpec.class
			.getName());

	/** The resource adapter */
	private ResourceAdapter ra;

	private String destination;
	private String destinationType;
	private int setupAttempts;
	private int setupInterval;
	private boolean useJndi;
	private boolean useConnectionPerHandler = true;
	private int maxSession = 1;

	/**
	 * Default constructor
	 */
	public RabbitmqActivationSpec() {

	}

	/**
	 * This method may be called by a deployment tool to validate the overall
	 * activation configuration information provided by the endpoint deployer.
	 *
	 * @throws InvalidPropertyException
	 *             indicates invalid configuration property settings.
	 */
	public void validate() throws InvalidPropertyException {
		log.trace("validate()");

	}

	/**
	 * Get the resource adapter
	 *
	 * @return The handle
	 */
	public ResourceAdapter getResourceAdapter() {
		log.trace("getResourceAdapter()");
		return ra;
	}

	/**
	 * Set the resource adapter
	 *
	 * @param ra
	 *            The handle
	 */
	public void setResourceAdapter(ResourceAdapter ra) {
		log.tracef("setResourceAdapter(%s)", ra);
		this.ra = ra;
	}

	public int getSetupAttempts() {
		return setupAttempts;
	}

	public void setSetupAttempts(int setupAttempts) {
		this.setupAttempts = setupAttempts;
	}

	public int getSetupInterval() {
		return setupInterval;
	}

	public void setSetupInterval(int setupInterval) {
		this.setupInterval = setupInterval;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	public boolean isUseJndi() {
		return useJndi;
	}

	public void setUseJndi(boolean useJndi) {
		this.useJndi = useJndi;
	}

	public boolean isUseConnectionPerHandler() {
		return useConnectionPerHandler;
	}

	public void setUseConnectionPerHandler(boolean useConnectionPerHandler) {
		this.useConnectionPerHandler = useConnectionPerHandler;
	}

	public int getMaxSession() {
		return maxSession;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}
	
}
