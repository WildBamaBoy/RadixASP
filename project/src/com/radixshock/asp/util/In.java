package com.radixshock.asp.util;

import java.util.Scanner;

public final class In 
{
	private static Scanner inScanner;
	
	private In()
	{
	}
	
	public static void initialize()
	{
		inScanner = new Scanner(System.in);
	}
	
	public static String getLine()
	{
		System.out.print(">>> ");
		return inScanner.nextLine();
	}
	
	public static int getInt()
	{
		while (true)
		{
			System.out.print(">>> ");
			String input = inScanner.nextLine();
			
			try
			{
				int intInput = Integer.parseInt(input);
				return intInput;
			}
			
			catch (NumberFormatException e)
			{
				System.out.println("*** Keyed data is invalid ***");
			}
		}
	}
	
	public static int getInt(int min, int max)
	{
		while (true)
		{
			int input = getInt();
			
			if (input >= min && input <= max)
			{
				return input;
			}
			
			else
			{
				System.out.println("*** Keyed data out of range ***");
			}
		}
	}
}
