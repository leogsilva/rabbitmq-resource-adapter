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

import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;

import org.jboss.logging.Logger;

import com.rabbitmq.client.ConnectionFactory;

import javax.resource.ResourceException;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;

/**
 * RabbitmqManagedConnectionFactory
 *
 * @version $Revision: $
 */
@ConnectionDefinition(connectionFactory = RabbitmqConnectionFactory.class, connectionFactoryImpl = RabbitmqConnectionFactoryImpl.class, connection = RabbitmqConnection.class, connectionImpl = RabbitmqConnectionImpl.class)
public class RabbitmqManagedConnectionFactory implements
    ManagedConnectionFactory, ResourceAdapterAssociation {

  /** The serial version UID */
  private static final long serialVersionUID = 1L;

  /** The logger */
  private static Logger log = Logger
      .getLogger(RabbitmqManagedConnectionFactory.class.getName());

  /** The resource adapter */
  private ResourceAdapter ra;

  /** The logwriter */
  private PrintWriter logwriter;

  /** host */
  @ConfigProperty(defaultValue = "uri")
  private String uri;
  
  @ConfigProperty(defaultValue = "10")
  private int connectionTimeout;

  private int timeout;

  @ConfigProperty(defaultValue = "5")
  private int requestedHeartbeat;

  private ConnectionFactory rabbitCF;

  
  
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  @Override
  public String toString() {
    return "RabbitmqManagedConnectionFactory [uri=" + uri
        + ", connectionTimeout=" + connectionTimeout + ", requestedHeartbeat="
        + requestedHeartbeat + "]";
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
    RabbitmqManagedConnectionFactory other = (RabbitmqManagedConnectionFactory) obj;
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
   * Default constructor
   */
  public RabbitmqManagedConnectionFactory() {

  }

  /**
   * Creates a Connection Factory instance.
   *
   * @param cxManager
   *          ConnectionManager to be associated with created EIS connection
   *          factory instance
   * @return EIS-specific Connection Factory instance or
   *         javax.resource.cci.ConnectionFactory instance
   * @throws ResourceException
   *           Generic exception
   */
  public Object createConnectionFactory(ConnectionManager cxManager)
      throws ResourceException {
    log.tracef("createConnectionFactory(%s)", cxManager);
    rabbitCF = new ConnectionFactory();
    try {
      rabbitCF.setUri(uri);
      rabbitCF.setConnectionTimeout(timeout);
      rabbitCF.setRequestedHeartbeat(requestedHeartbeat);
    } catch (KeyManagementException | NoSuchAlgorithmException
        | URISyntaxException e) {
      throw new ResourceException(e);
    }
    return new RabbitmqConnectionFactoryImpl(this, cxManager);
  }

  /**
   * Creates a Connection Factory instance.
   *
   * @return EIS-specific Connection Factory instance or
   *         javax.resource.cci.ConnectionFactory instance
   * @throws ResourceException
   *           Generic exception
   */
  public Object createConnectionFactory() throws ResourceException {
    throw new ResourceException(
        "This resource adapter doesn't support non-managed environments");
  }

  /**
   * Creates a new physical connection to the underlying EIS resource manager.
   *
   * @param subject
   *          Caller's security information
   * @param cxRequestInfo
   *          Additional resource adapter specific connection request
   *          information
   * @throws ResourceException
   *           generic exception
   * @return ManagedConnection instance
   */
  public ManagedConnection createManagedConnection(Subject subject,
      ConnectionRequestInfo cxRequestInfo) throws ResourceException {
    log.tracef("createManagedConnection(%s, %s)", subject, cxRequestInfo);
    return new RabbitmqManagedConnection(this);
  }

  /**
   * Returns a matched connection from the candidate set of connections.
   *
   * @param connectionSet
   *          Candidate connection set
   * @param subject
   *          Caller's security information
   * @param cxRequestInfo
   *          Additional resource adapter specific connection request
   *          information
   * @throws ResourceException
   *           generic exception
   * @return ManagedConnection if resource adapter finds an acceptable match
   *         otherwise null
   */
  public ManagedConnection matchManagedConnections(Set connectionSet,
      Subject subject, ConnectionRequestInfo cxRequestInfo)
      throws ResourceException {
    log.tracef("matchManagedConnections(%s, %s, %s)", connectionSet, subject,
        cxRequestInfo);
    ManagedConnection result = null;
    Iterator it = connectionSet.iterator();
    while (result == null && it.hasNext()) {
      ManagedConnection mc = (ManagedConnection) it.next();
      if (mc instanceof RabbitmqManagedConnection) {
        result = mc;
      }

    }
    return result;
  }

  /**
   * Get the log writer for this ManagedConnectionFactory instance.
   *
   * @return PrintWriter
   * @throws ResourceException
   *           generic exception
   */
  public PrintWriter getLogWriter() throws ResourceException {
    log.trace("getLogWriter()");
    return logwriter;
  }

  /**
   * Set the log writer for this ManagedConnectionFactory instance.
   *
   * @param out
   *          PrintWriter - an out stream for error logging and tracing
   * @throws ResourceException
   *           generic exception
   */
  public void setLogWriter(PrintWriter out) throws ResourceException {
    log.tracef("setLogWriter(%s)", out);
    logwriter = out;
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
   *          The handle
   */
  public void setResourceAdapter(ResourceAdapter ra) {
    log.tracef("setResourceAdapter(%s)", ra);
    this.ra = ra;
  }

  public ConnectionFactory getRabbitConnectionFactory() {
    return this.rabbitCF;
  }
}
