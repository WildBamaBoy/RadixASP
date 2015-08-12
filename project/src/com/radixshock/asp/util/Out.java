package com.radixshock.asp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Out 
{
	private static final int CONSOLE_WIDTH = 80;
	
	private Out()
	{
		
	}
	
	/**
	 * Write object to system.out.
	 */
	public static void write(Object obj)
	{
		StringBuilder sb = new StringBuilder();
		
		if (obj == null)
		{
			sb.append("null");
		}
		
		else
		{
			sb.append(obj.toString());
		}
		
		System.out.println(sb.toString());
	}
	
	private static void writeC(Object obj, char c)
	{
		StringBuilder sb = new StringBuilder();
		String string = obj.toString();
		
		int padding = (CONSOLE_WIDTH - string.length()) / 2;
		int counter = 0;
		
		while (counter != padding)
		{
			sb.append(c);
			counter++;
		}
		
		counter = 0;
		sb.append(string);
		
		while (counter != padding)
		{
			sb.append(c);
			counter++;
		}
		
		System.out.println(sb.toString());
	}
	
	/**
	 * Write centered with dashes.
	 */
	public static void writeCD(Object obj)
	{
		writeC(obj, '-');
	}
	
	/**
	 * Write centered with spaces.
	 */
	public static void writeCS(Object obj)
	{
		writeC(obj, ' ');
	}
	
	/**
	 * Write with time stamp.
	 */
	public static void writeTS(Object obj)
	{
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		sb.append("[" + format.format(timestamp) + "]: ");
		sb.append(obj);
		
		write(sb.toString());
	}

	/**
	 * Write a newline to the screen.
	 */
	public static void writeNL() 
	{
		System.out.println("");
	}
}