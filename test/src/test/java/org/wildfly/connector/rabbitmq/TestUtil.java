package org.wildfly.connector.rabbitmq;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

public class TestUtil {

    Properties loadUserProperties() throws IOException {
    	Properties p = new Properties();
    	InputStream is = getClass().getResourceAsStream("/profiles/"  + System.getProperty("user.name") + ".properties");
    	p.load(is);
    	return p;
    }
 
    public static String toString(Element node) throws Exception {
    	TransformerFactory transFactory = TransformerFactory.newInstance();
    	Transformer transformer = transFactory.newTransformer();
    	StringWriter buffer = new StringWriter();
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    	transformer.transform(new DOMSource(node),
    	      new StreamResult(buffer));
    	return buffer.toString();
    }
}
