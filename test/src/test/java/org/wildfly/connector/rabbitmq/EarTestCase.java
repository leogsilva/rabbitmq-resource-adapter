package org.wildfly.connector.rabbitmq;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EarTestCase {

	private static String deploymentName = "EarTestCase";
	
	
	@Deployment
	public static EnterpriseArchive deploy() throws Exception {
		JavaArchive ejbJar = ShrinkWrap
				.create(JavaArchive.class, "test-ejb.jar")
				.addClass(MyMDB.class)
				.addClass(org.jboss.shrinkwrap.descriptor.api.Descriptor.class);

        WebArchive war = ShrinkWrap.create(WebArchive.class,"test.war")
                .addClass(EarTestCase.class);
		
	    EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
	    	.addAsModule(war)	
            .addAsModule(ejbJar);
	    System.out.println(ear.toString(true));
        return ear;
	}
	
	@Test
	public void testMessageDrivenBean() throws Exception {
		System.out.println("A");
	}
}
