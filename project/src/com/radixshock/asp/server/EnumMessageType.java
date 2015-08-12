package com.radixshock.asp.server;

import com.radixshock.asp.server.messages.AbstractMessage;
import com.radixshock.asp.server.messages.MessageCrashReport;
import com.radixshock.asp.server.messages.MessageDebugReport;
import com.radixshock.asp.server.messages.MessageStatisticsQuery;
import com.radixshock.asp.server.messages.MessageVersionQuery;
import com.radixshock.asp.util.Out;

public enum EnumMessageType 
{
	VersionQuery (1, MessageVersionQuery.class),
	CrashReport (2, MessageCrashReport.class),
	DebugReport (3, MessageDebugReport.class),
	StatisticsQuery(5, MessageStatisticsQuery.class);
	
	private int id;
	private Class<?> messageClass;

	EnumMessageType(int id, Class<?> messageClass)
	{
		this.id = id;
		this.messageClass = messageClass;
	}

	public int getId()
	{
		return id;
	}

	public Class<?> getMessageClass()
	{
		return messageClass;
	}

	public static AbstractMessage getMessageInstance(byte id)
	{
		try
		{
			for (EnumMessageType messageType : EnumMessageType.values())
			{
				if (messageType.id == id)
				{
					return (AbstractMessage) messageType.getMessageClass().newInstance();
				}
			}
		}

		catch (Throwable e)
		{
			Out.writeTS("Error getting next message for provided id: " + id + "!");
			e.printStackTrace();
		}
		
		return null;
	}
}
