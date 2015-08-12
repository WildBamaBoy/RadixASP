package com.radixshock.asp.server;

import java.io.IOException;

import com.radixshock.asp.util.In;
import com.radixshock.asp.util.Sys;

public class Main 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		In.initialize();
		Sys.clear();
		ASPServer.startASP();
	}
}
