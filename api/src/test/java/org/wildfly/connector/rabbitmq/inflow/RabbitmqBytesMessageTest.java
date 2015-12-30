package org.wildfly.connector.rabbitmq.inflow;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageFormatException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitmqBytesMessageTest {
	
	public static final String HEADER_STRING = "headerString";
	public static final String HEADER_INT = "headerInt";

	@Test
	public void testMessageBody() throws Exception {
		byte[] message = "This is a test".getBytes();
		RabbitmqBytesMessage m = new RabbitmqBytesMessage(null, null, null, message);
    	int i = 0;
    	StringBuilder sb = new StringBuilder();
    	try {
			while ((i = m.readByte()) != -1) {
				sb.append((char) i);
			}
		} catch (JMSException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
    	System.out.println(sb.toString());
		Assert.assertEquals("This is a test", sb.toString());
		Assert.assertEquals("This is a test", m.getBody(String.class));
	}

	@Test
	public void testIntMessageHeaders() throws Exception {
		BasicProperties basicProperties = Mockito.mock(BasicProperties.class);
		Map<String,Object> headers = createHeaders();
		
		when(basicProperties.getHeaders()).thenReturn(headers);
		RabbitmqBytesMessage m = new RabbitmqBytesMessage(null, null, basicProperties, new byte[0]);
		try {
			int intProperty = m.getIntProperty(null);
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}

		try {
			int intProperty = m.getIntProperty("test");
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}
		
		try {
			int intProperty = m.getIntProperty(HEADER_STRING);
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}
		
		int intProperty = m.getIntProperty(HEADER_INT);
		Assert.assertEquals(10,intProperty);
		
	}
	
	@Test
	public void testStringMessageHeaders() throws Exception {
		BasicProperties basicProperties = Mockito.mock(BasicProperties.class);
		Map<String,Object> headers = createHeaders();
		
		when(basicProperties.getHeaders()).thenReturn(headers);
		RabbitmqBytesMessage m = new RabbitmqBytesMessage(null, null, basicProperties, new byte[0]);
		try {
			String sProperty = m.getStringProperty(null);
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}

		try {
			String sProperty = m.getStringProperty("test");
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}
		
		try {
			String sProperty = m.getStringProperty(HEADER_INT);
			Assert.assertTrue(false);
		} catch (MessageFormatException e) {
			Assert.assertTrue(true);
		}
		
		String stProperty = m.getStringProperty(HEADER_STRING);
		Assert.assertEquals("h1",stProperty);
		
	}
	
	protected Map<String,Object> createHeaders() {
		Map<String,Object> headers = new HashMap<String,Object>();
		headers.put(HEADER_STRING, "h1");
		headers.put(HEADER_INT, 10);
		return headers;
	}
}
