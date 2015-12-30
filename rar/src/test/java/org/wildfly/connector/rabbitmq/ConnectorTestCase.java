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

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * ConnectorTestCase
 *
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class ConnectorTestCase {
	private static Logger log = Logger.getLogger(ConnectorTestCase.class
			.getName());

	private static String deploymentName = "ConnectorTestCase";

	/**
	 * Define the deployment
	 *
	 * @return The deployment archive
	 */
	@Deployment(name="ConnectorTestCase", order = 1)
	public static ResourceAdapterArchive createDeployment() {
		ResourceAdapterArchive raa = ShrinkWrap.create(
				ResourceAdapterArchive.class, deploymentName + ".rar");
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID()
				.toString() + ".jar");
		ja.addPackages(true,
				Package.getPackage("org.wildfly.connector.rabbitmq"));
		raa.addAsLibrary(ja);

		raa.addAsManifestResource("ironjacamar-test.xml", "ironjacamar.xml");
		System.out.println(raa.toString(true));
		return raa;
	}

//	@Deployment(name="ejb", order = 2)
//	public static JavaArchive createArchive() {
//		JavaArchive ejbJar = ShrinkWrap
//				.create(JavaArchive.class, "test-ejb.jar")
//				.addClass(MessageListener.class)
//				.addClass(MyMDB.class)
//				.addClass(org.jboss.shrinkwrap.descriptor.api.Descriptor.class);
//
//		System.out.println(ejbJar.toString(true));
//
//		return ejbJar;
//	}

	/** Resource */
	@Resource(mappedName = "java:/eis/RabbitmqConnectionFactory")
	private RabbitmqConnectionFactory connectionFactory1;
	
	@Resource(mappedName = "java:/eis/RabbitQueue")
	private javax.jms.Queue queue;
	

	@Test
	public void testConnectionFactory() throws Exception {
		Assert.assertNotNull(connectionFactory1);
		Assert.assertNotNull(queue);
		
		RabbitmqConnection connection = connectionFactory1.getConnection();
		Assert.assertNotNull(connection);
		String queueName = "testing";
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
		String message = "Hello World!";

		final CountDownLatch counter = new CountDownLatch(1);
		Consumer consume = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				Assert.assertEquals("Hello World!", new String(body));
				counter.countDown();
			}
		};

		channel.basicConsume(queueName, true, consume);
		channel.basicPublish("", queueName, null, message.getBytes());
		counter.await(10, TimeUnit.SECONDS);
		Assert.assertEquals(0, counter.getCount());
		channel.close();
		
	}

}
