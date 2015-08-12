package com.radixshock.asp.util;


public final class Sys 
{
	private Sys()
	{
		
	}
	
	public static void clear()
	{
		System.out.print("\u001b[2J"); //ANSI_CLS
		System.out.print("\u001b[H");  //ANSI_HOME
		System.out.flush();
	}
}
