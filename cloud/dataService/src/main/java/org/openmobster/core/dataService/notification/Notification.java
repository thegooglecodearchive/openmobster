/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openmobster.core.dataService.notification;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

import org.openmobster.core.security.device.Device;
import org.openmobster.core.dataService.Constants;

/**
 * Encapsulates the Notification meta data to be processed by the Notifier. Notification Object is used by
 * System components that would like to send Notifications to devices via the Notifier Service
 * 
 * @author openmobster@gmail.com
 */
public class Notification implements Serializable 
{
	private static final long serialVersionUID = -8754021735988478805L;
	
	/**
	 * Type of Notification 
	 */
	private NotificationType type;
	
	private Map<String, Object> metaData;
	
	public Notification(NotificationType type)
	{
		this.type = type;
		this.metaData = new HashMap<String, Object>();
	}
	
	public NotificationType getType()
	{
		return this.type;
	}
	
	public void setMetaData(String name, Object value)
	{
		if(name == null || name.trim().length() == 0)
		{
			throw new IllegalArgumentException("Name cannot be empty");
		}
		if(value == null)
		{
			throw new IllegalArgumentException("Value cannot be Null");
		}
		
		this.metaData.put(name, value);
	}
	
	public Object getMetaData(String name)
	{
		return this.metaData.get(name);
	}
	
	public String getMetaDataAsString(String name)
	{
		return (String)this.metaData.get(name);
	}
	
	public static Notification createSyncNotification(Device device, String service)
	{
		Notification syncNotification = new Notification(NotificationType.SYNC);
		
		syncNotification.setMetaData(Constants.device, device.getIdentifier());
		syncNotification.setMetaData(Constants.service, service);		
		
		return syncNotification;
	}
	
	public static Notification createSyncNotification(String deviceIdentifier, String service)
	{
		Notification syncNotification = new Notification(NotificationType.SYNC);
		
		syncNotification.setMetaData(Constants.device, deviceIdentifier);
		syncNotification.setMetaData(Constants.service, service);		
		
		return syncNotification;
	}
	
	public static Notification createPushRPCNotification(Device device, String service)
	{
		Notification notification = new Notification(NotificationType.RPC);
		
		notification.setMetaData(Constants.device, device.getIdentifier());	
		notification.setMetaData(Constants.service, service);
		
		return notification;
	}
	
	public static Notification createPushNotification(Device device, String message, Map<String,String> extras)
	{
		if(extras == null || extras.isEmpty())
		{
			new IllegalArgumentException("Extras cannot be null");
		}
		Notification notification = new Notification(NotificationType.PUSH);
		
		notification.setMetaData(Constants.device, device.getIdentifier());
		notification.setMetaData(Constants.message,message);
		notification.setMetaData(Constants.extras,extras);
		
		return notification;
	}
}
