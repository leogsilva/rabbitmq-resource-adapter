package org.wildfly.connector.rabbitmq.inflow;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.jboss.logging.Logger;
import org.wildfly.connector.rabbitmq.Util;

/**
 * A wrapper for a message
 */
public class RabbitmqMessage implements Message {
	/** The logger */
	private static Logger log = Logger.getLogger(RabbitmqMessage.class
			.getName());

	/** The message */
	protected Message _message;


	/**
	 * Create a new wrapper
	 * 
	 * @param message
	 *            the message
	 * @param session
	 *            the session
	 */
	public RabbitmqMessage(final Message message) {
		if (log.isTraceEnabled()) {
			log.trace("constructor(" + Util.asString(message) + ")");
		}

		this._message = message;
	}

	/**
	 * Acknowledge
	 * 
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void acknowledge() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("acknowledge()");
		}

		_message.acknowledge();
	}

	/**
	 * Clear body
	 * 
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void clearBody() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("clearBody()");
		}

		_message.clearBody();
	}

	/**
	 * Clear properties
	 * 
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void clearProperties() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("clearProperties()");
		}

		_message.clearProperties();
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public boolean getBooleanProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getBooleanProperty(" + name + ")");
		}

		return _message.getBooleanProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public byte getByteProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getByteProperty(" + name + ")");
		}

		return _message.getByteProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public double getDoubleProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getDoubleProperty(" + name + ")");
		}

		return _message.getDoubleProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public float getFloatProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getFloatProperty(" + name + ")");
		}

		return _message.getFloatProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public int getIntProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getIntProperty(" + name + ")");
		}

		return _message.getIntProperty(name);
	}

	/**
	 * Get correlation id
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public String getJMSCorrelationID() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSCorrelationID()");
		}

		return _message.getJMSCorrelationID();
	}

	/**
	 * Get correlation id
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSCorrelationIDAsBytes()");
		}

		return _message.getJMSCorrelationIDAsBytes();
	}

	/**
	 * Get delivery mode
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public int getJMSDeliveryMode() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSDeliveryMode()");
		}

		return _message.getJMSDeliveryMode();
	}

	/**
	 * Get destination
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public Destination getJMSDestination() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSDestination()");
		}

		return _message.getJMSDestination();
	}

	/**
	 * Get expiration
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public long getJMSExpiration() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSExpiration()");
		}

		return _message.getJMSExpiration();
	}

	/**
	 * Get message id
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public String getJMSMessageID() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSMessageID()");
		}

		return _message.getJMSMessageID();
	}

	/**
	 * Get priority
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public int getJMSPriority() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSPriority()");
		}

		return _message.getJMSPriority();
	}

	/**
	 * Get redelivered status
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public boolean getJMSRedelivered() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSRedelivered()");
		}

		return _message.getJMSRedelivered();
	}

	/**
	 * Get reply to destination
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public Destination getJMSReplyTo() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSReplyTo()");
		}

		return _message.getJMSReplyTo();
	}

	/**
	 * Get timestamp
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public long getJMSTimestamp() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSTimestamp()");
		}

		return _message.getJMSTimestamp();
	}

	/**
	 * Get type
	 * 
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public String getJMSType() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getJMSType()");
		}

		return _message.getJMSType();
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public long getLongProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getLongProperty(" + name + ")");
		}

		return _message.getLongProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public Object getObjectProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getObjectProperty(" + name + ")");
		}

		return _message.getObjectProperty(name);
	}

	/**
	 * Get property names
	 * 
	 * @return The values
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public Enumeration<?> getPropertyNames() throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getPropertyNames()");
		}

		return _message.getPropertyNames();
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public short getShortProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getShortProperty(" + name + ")");
		}

		return _message.getShortProperty(name);
	}

	/**
	 * Get property
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public String getStringProperty(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("getStringProperty(" + name + ")");
		}

		return _message.getStringProperty(name);
	}

	/**
	 * Do property exist
	 * 
	 * @param name
	 *            The name
	 * @return The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public boolean propertyExists(final String name) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("propertyExists(" + name + ")");
		}

		return _message.propertyExists(name);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setBooleanProperty(final String name, final boolean value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setBooleanProperty(" + name + ", " + value + ")");
		}

		_message.setBooleanProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setByteProperty(final String name, final byte value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setByteProperty(" + name + ", " + value + ")");
		}

		_message.setByteProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setDoubleProperty(final String name, final double value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setDoubleProperty(" + name + ", " + value + ")");
		}

		_message.setDoubleProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setFloatProperty(final String name, final float value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setFloatProperty(" + name + ", " + value + ")");
		}

		_message.setFloatProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setIntProperty(final String name, final int value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setIntProperty(" + name + ", " + value + ")");
		}

		_message.setIntProperty(name, value);
	}

	/**
	 * Set correlation id
	 * 
	 * @param correlationID
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSCorrelationID(final String correlationID)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSCorrelationID(" + correlationID + ")");
		}

		_message.setJMSCorrelationID(correlationID);
	}

	/**
	 * Set correlation id
	 * 
	 * @param correlationID
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSCorrelationIDAsBytes(final byte[] correlationID)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSCorrelationIDAsBytes(" + correlationID + ")");
		}

		_message.setJMSCorrelationIDAsBytes(correlationID);
	}

	/**
	 * Set delivery mode
	 * 
	 * @param deliveryMode
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSDeliveryMode(final int deliveryMode) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSDeliveryMode(" + deliveryMode + ")");
		}

		_message.setJMSDeliveryMode(deliveryMode);
	}

	/**
	 * Set destination
	 * 
	 * @param destination
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSDestination(final Destination destination)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSDestination(" + destination + ")");
		}

		_message.setJMSDestination(destination);
	}

	/**
	 * Set expiration
	 * 
	 * @param expiration
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSExpiration(final long expiration) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSExpiration(" + expiration + ")");
		}

		_message.setJMSExpiration(expiration);
	}

	/**
	 * Set message id
	 * 
	 * @param id
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSMessageID(final String id) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSMessageID(" + id + ")");
		}

		_message.setJMSMessageID(id);
	}

	/**
	 * Set priority
	 * 
	 * @param priority
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSPriority(final int priority) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSPriority(" + priority + ")");
		}

		_message.setJMSPriority(priority);
	}

	/**
	 * Set redelivered status
	 * 
	 * @param redelivered
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSRedelivered(final boolean redelivered)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSRedelivered(" + redelivered + ")");
		}

		_message.setJMSRedelivered(redelivered);
	}

	/**
	 * Set reply to
	 * 
	 * @param replyTo
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSReplyTo(final Destination replyTo) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSReplyTo(" + replyTo + ")");
		}

		_message.setJMSReplyTo(replyTo);
	}

	/**
	 * Set timestamp
	 * 
	 * @param timestamp
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSTimestamp(final long timestamp) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSTimestamp(" + timestamp + ")");
		}

		_message.setJMSTimestamp(timestamp);
	}

	/**
	 * Set type
	 * 
	 * @param type
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setJMSType(final String type) throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setJMSType(" + type + ")");
		}

		_message.setJMSType(type);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setLongProperty(final String name, final long value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setLongProperty(" + name + ", " + value + ")");
		}

		_message.setLongProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setObjectProperty(final String name, final Object value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setObjectProperty(" + name + ", " + value + ")");
		}

		_message.setObjectProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setShortProperty(final String name, final short value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setShortProperty(" + name + ", " + value + ")");
		}

		_message.setShortProperty(name, value);
	}

	/**
	 * Set property
	 * 
	 * @param name
	 *            The name
	 * @param value
	 *            The value
	 * @exception JMSException
	 *                Thrown if an error occurs
	 */
	public void setStringProperty(final String name, final String value)
			throws JMSException {
		if (log.isTraceEnabled()) {
			log.trace("setStringProperty(" + name + ", " + value + ")");
		}

		_message.setStringProperty(name, value);
	}

	/**
	 * Return the hash code
	 * 
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		return _message.hashCode();
	}

	/**
	 * Check for equality
	 * 
	 * @param object
	 *            The other object
	 * @return True / false
	 */
	@Override
	public boolean equals(final Object object) {
		if (object != null && object instanceof RabbitmqMessage) {
			return _message.equals(((RabbitmqMessage) object)._message);
		} else {
			return _message.equals(object);
		}
	}

	@Override
	public <T> T getBody(Class<T> arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSDeliveryTime() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBodyAssignableTo(Class arg0) throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setJMSDeliveryTime(long arg0) throws JMSException {
		// TODO Auto-generated method stub
		
	}
}
