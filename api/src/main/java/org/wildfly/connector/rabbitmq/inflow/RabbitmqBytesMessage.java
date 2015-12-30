package org.wildfly.connector.rabbitmq.inflow;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageFormatException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;

/**
 * Bytes message for rabbitmq
 *
 */
public class RabbitmqBytesMessage implements javax.jms.BytesMessage {

	
	private String consumerTag;
	private Envelope envelope;
	private BasicProperties properties;
	private byte[] body;
	private DataInputStream dis;

	RabbitmqBytesMessage(String consumerTag, Envelope envelope,
			BasicProperties properties, byte[] body) {
		if (body == null)
			throw new IllegalArgumentException("Message body cannot be null");
		this.consumerTag = consumerTag;
		this.envelope = envelope;
		this.properties = properties;
		this.body = body;
		ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
		dis = new DataInputStream(bais);
	}
	
	@Override
	public void acknowledge() throws JMSException {
		
	}

	@Override
	public void clearBody() throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearProperties() throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBooleanProperty(String name) throws JMSException {
		return false;
	}

	@Override
	public byte getByteProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloatProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntProperty(String name) throws JMSException {
		Object value = this.properties.getHeaders().get(name);
		if (value == null) {
			throw new MessageFormatException("Invalid conversion for property " + name );
		}
		if (Number.class.isInstance(value)) {
			Number n = Number.class.cast(value);
			return n.intValue();
		} else {
			throw new MessageFormatException("Invalid conversion for property " + name );
		}
	}

	@Override
	public String getJMSCorrelationID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
		return null;
	}

	@Override
	public int getJMSDeliveryMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Destination getJMSDestination() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSExpiration() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJMSMessageID() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getJMSPriority() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getJMSRedelivered() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Destination getJMSReplyTo() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getJMSTimestamp() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJMSType() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLongProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getObjectProperty(String name) throws JMSException {
		return this.properties.getHeaders().get(name);
	}

	@Override
	public Enumeration getPropertyNames() throws JMSException {
		Map<String, Object> headers = this.properties.getHeaders();
		Vector<String> v = new Vector<String>();
		v.addAll(headers.keySet());
		return v.elements();
	}

	@Override
	public short getShortProperty(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringProperty(String name) throws JMSException {
		Object value = this.properties.getHeaders().get(name);
		if (value == null) {
			throw new MessageFormatException("Invalid conversion for property " + name );
		}
		if (CharSequence.class.isInstance(value)) {
			CharSequence n = CharSequence.class.cast(value);
			return n.toString();
		} else {
			throw new MessageFormatException("Invalid conversion for property " + name );
		}
	}

	@Override
	public boolean propertyExists(String name) throws JMSException {
		return properties.getHeaders().containsKey(name);
	}

	@Override
	public void setBooleanProperty(String arg0, boolean arg1)
			throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setByteProperty(String arg0, byte arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDoubleProperty(String arg0, double arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFloatProperty(String arg0, float arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIntProperty(String arg0, int arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSCorrelationID(String arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSDeliveryMode(int arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSDestination(Destination arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSExpiration(long arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSMessageID(String arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSPriority(int arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSRedelivered(boolean arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSReplyTo(Destination arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSTimestamp(long arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJMSType(String arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLongProperty(String arg0, long arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setObjectProperty(String arg0, Object arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setShortProperty(String arg0, short arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStringProperty(String arg0, String arg1) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getBodyLength() throws JMSException {
		return this.body.length;
	}

	@Override
	public boolean readBoolean() throws JMSException {
		return false;
	}

	@Override
	public byte readByte() throws JMSException {
		try {
			return dis.readByte();
		} catch (IOException e) {
			return -1;
		}
	}

	@Override
	public int readBytes(byte[] arr) throws JMSException {
		return readBytes(arr,arr.length);
	}

	@Override
	public int readBytes(byte[] arr, int length) throws JMSException {
		if (length > body.length) {
			throw new JMSException("length is greater than body length");
		}
		if (arr.length > this.body.length) {
			System.arraycopy(this.body, 0, arr, 0, body.length);
			return body.length;
		} else {
			System.arraycopy(this.body, 0, arr, 0, arr.length);
			return arr.length;
		}
	}

	@Override
	public char readChar() throws JMSException {
		try {
			return dis.readChar();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public double readDouble() throws JMSException {
		try {
			return dis.readDouble();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public float readFloat() throws JMSException {
		try {
			return dis.readFloat();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public int readInt() throws JMSException {
		try {
			return dis.readInt();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public long readLong() throws JMSException {
		try {
			return dis.readLong();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public short readShort() throws JMSException {
		try {
			return dis.readShort();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public String readUTF() throws JMSException {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public int readUnsignedByte() throws JMSException {
		try {
			return dis.readUnsignedByte();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public int readUnsignedShort() throws JMSException {
		try {
			return dis.readUnsignedShort();
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}

	@Override
	public void reset() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeBoolean(boolean arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeByte(byte arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(byte[] arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(byte[] arg0, int arg1, int arg2) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeChar(char arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeDouble(double arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeFloat(float arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeInt(int arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeLong(long arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeObject(Object arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeShort(short arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeUTF(String arg0) throws JMSException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Supported types so far
	 * @see String
	 * @see InputStream
	 */
	@Override
	public <T> T getBody(Class<T> type) throws JMSException {
		if (String.class.isAssignableFrom(type)) {
			return (T)new String(this.body);
		} else if (InputStream.class.isAssignableFrom(type)) {
			ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
			return (T) bais;
		}
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
