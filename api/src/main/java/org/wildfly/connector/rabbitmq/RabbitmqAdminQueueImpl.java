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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.spi.AdministeredObject;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

/**
 * RabbitmqAdminObjectImpl
 *
 * @version $Revision: $
 */
@AdministeredObject(adminObjectInterfaces = { AdminRabbitmqQueue.class })
public class RabbitmqAdminQueueImpl implements AdminRabbitmqQueue,
		Externalizable, ResourceAdapterAssociation, Referenceable, Serializable, javax.jms.Queue {

	private String url;

	/** Serial version uid */
	private static final long serialVersionUID = 1L;

	/** The resource adapter */
	private ResourceAdapter ra;

	/** Reference */
	private Reference reference;

	/**
	 * Default constructor
	 */
	public RabbitmqAdminQueueImpl() {

	}

	/**
	 * Get the resource adapter
	 *
	 * @return The handle
	 */
	public ResourceAdapter getResourceAdapter() {
		return ra;
	}

	/**
	 * Set the resource adapter
	 *
	 * @param ra
	 *            The handle
	 */
	public void setResourceAdapter(ResourceAdapter ra) {
		this.ra = ra;
	}

	/**
	 * Get the Reference instance.
	 *
	 * @return Reference instance
	 * @exception NamingException
	 *                Thrown if a reference can't be obtained
	 */
	@Override
	public Reference getReference() throws NamingException {
		return reference;
	}

	/**
	 * Set the Reference instance.
	 *
	 * @param reference
	 *            A Reference instance
	 */
	@Override
	public void setReference(Reference reference) {
		this.reference = reference;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		return result;
	}

	/**
	 * Indicates whether some other object is equal to this one.
	 * 
	 * @param other
	 *            The reference object with which to compare.
	 * @return true if this object is the same as the obj argument, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof RabbitmqAdminQueueImpl))
			return false;
		boolean result = true;
		return result;
	}

	@Override
	public void setDestinationAddress(String destinationAddress)
			throws Exception {
		this.url = destinationAddress;
	}

	@Override
	public String getDestinationAddress() {
		return this.url;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.url);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		this.url = (String) in.readObject();
	}

	@Override
	public String getQueueName() throws JMSException {
		return getDestinationAddress();
	}
}
