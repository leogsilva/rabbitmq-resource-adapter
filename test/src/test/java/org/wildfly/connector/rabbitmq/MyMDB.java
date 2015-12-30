package org.wildfly.connector.rabbitmq;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/eis/RabbitQueue") }, name = "ActiveMQMDB")
@ResourceAdapter(value = "rabbitmq-rar-0.0.1-SNAPSHOT.rar")
public class MyMDB implements MessageListener {


	public void onMessage(Message message) {
		System.err.println("Message is ok !" + message);
	}

}
