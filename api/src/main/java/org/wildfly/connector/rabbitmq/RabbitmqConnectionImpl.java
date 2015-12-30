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
import java.net.InetAddress;
import java.util.Map;

import org.jboss.logging.Logger;

import com.rabbitmq.client.BlockedListener;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ExceptionHandler;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * RabbitmqConnectionImpl
 *
 * @version $Revision: $
 */
public class RabbitmqConnectionImpl implements RabbitmqConnection
{
   /** The logger */
   private static Logger log = Logger.getLogger(RabbitmqConnectionImpl.class.getName());

   /** ManagedConnection */
   private RabbitmqManagedConnection mc;

   /** ManagedConnectionFactory */
   private RabbitmqManagedConnectionFactory mcf;

   /**
    * Default constructor
    * @param mc RabbitmqManagedConnection
    * @param mcf RabbitmqManagedConnectionFactory
    */
   public RabbitmqConnectionImpl(RabbitmqManagedConnection mc, RabbitmqManagedConnectionFactory mcf)
   {
      this.mc = mc;
      this.mcf = mcf;
   }


   /**
    * Close
    */
   public void close()
   {
      mc.closeHandle(this);
   }

  @Override
  public void abort() {
    this.mc.getUnderlyingConnection().abort();
  }


  @Override
  public void abort(int v) {
   this.mc.getUnderlyingConnection().abort(v); 
  }


  @Override
  public void abort(int x, String s) {
    this.mc.getUnderlyingConnection().abort(x,s);
  }


  @Override
  public void abort(int i, String s, int v) {
    this.mc.getUnderlyingConnection().abort(i,s,v);
  }


  @Override
  public void addBlockedListener(BlockedListener listener) {
    this.mc.getUnderlyingConnection().addBlockedListener(listener);
  }


  @Override
  public void clearBlockedListeners() {
    this.mc.getUnderlyingConnection().clearBlockedListeners();
  }


  @Override
  public void close(int v) throws IOException {
    this.mc.getUnderlyingConnection().close(v);
  }


  @Override
  public void close(int i, String s) throws IOException {
    this.mc.getUnderlyingConnection().close(i,s);
  }


  @Override
  public void close(int i, String s, int v) throws IOException {
    this.mc.getUnderlyingConnection().close(i,s,v);
  }


  @Override
  public Channel createChannel() throws IOException {
    return this.mc.getUnderlyingConnection().createChannel();
  }


  @Override
  public Channel createChannel(int c) throws IOException {
    return this.mc.getUnderlyingConnection().createChannel(c);
  }


  @Override
  public InetAddress getAddress() {
    return this.mc.getUnderlyingConnection().getAddress();
  }


  @Override
  public int getChannelMax() {
    return this.mc.getUnderlyingConnection().getChannelMax();
  }


  @Override
  public Map<String, Object> getClientProperties() {
    return this.mc.getUnderlyingConnection().getClientProperties();
  }


  @Override
  public ExceptionHandler getExceptionHandler() {
    return this.mc.getUnderlyingConnection().getExceptionHandler();
  }


  @Override
  public int getFrameMax() {
    return this.mc.getUnderlyingConnection().getFrameMax();
  }


  @Override
  public int getHeartbeat() {
    return this.mc.getUnderlyingConnection().getHeartbeat();
  }


  @Override
  public int getPort() {
    return this.mc.getUnderlyingConnection().getPort();
  }


  @Override
  public Map<String, Object> getServerProperties() {
    return this.mc.getUnderlyingConnection().getServerProperties();
  }


  @Override
  public boolean removeBlockedListener(BlockedListener listener) {
    return this.mc.getUnderlyingConnection().removeBlockedListener(listener);
  }


  @Override
  public void addShutdownListener(ShutdownListener listener) {
    this.mc.getUnderlyingConnection().addShutdownListener(listener);
  }


  @Override
  public ShutdownSignalException getCloseReason() {
    return this.mc.getUnderlyingConnection().getCloseReason();
  }


  @Override
  public boolean isOpen() {
    return this.mc.getUnderlyingConnection().isOpen();
  }


  @Override
  public void notifyListeners() {
    this.mc.getUnderlyingConnection().notifyListeners();
  }


  @Override
  public void removeShutdownListener(ShutdownListener listener) {
    this.mc.getUnderlyingConnection().removeShutdownListener(listener);
  }

}
