package com.radixshock.asp.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.radixshock.asp.util.Out;
import com.radixshock.asp.util.Sys;

public class ASPServer 
{
	public static Map<String, Integer> displayUpdatesMap = new HashMap<String, Integer>();
	public static Map<String, Integer> displayCrashesMap = new HashMap<String, Integer>();
	public static List<String> dataBlacklist;

	private static ServerSocket serverSocket;
	private static boolean stopRunning;

	public static void startASP()
	{
		Sys.clear();
		Out.writeTS("Loading blacklist...");

		try
		{
			Path blacklistPath = Paths.get("./blacklist.txt");
			File blacklistFile = blacklistPath.toFile();

			if (!blacklistFile.exists())
			{
				Files.write(blacklistPath, "<mod id>:<mod version>".getBytes(), StandardOpenOption.CREATE_NEW);
			}

			dataBlacklist = Files.readAllLines(blacklistPath);
		}

		catch (Exception e)
		{
			Out.writeTS("Unexpected exception while loading blacklist.");
			Out.writeTS(e.getMessage());
		}

		Out.writeTS("Starting ASP...");

		try
		{
			serverSocket = new ServerSocket(3577);
			serverSocket.setReuseAddress(true);
		}

		catch (SocketException e)
		{
			Out.writeTS("Unexpected socket exception while starting server! " + e.getMessage());
			return;
		}

		catch (IOException e)
		{
			Out.writeTS("Unexpected IO exception while starting server! " + e.getMessage());
			return;
		}

		Out.writeTS("Server started and listening.");
		runServerLoop();
	}

	private static void runServerLoop()
	{
		while (!stopRunning)
		{
			try 
			{
				Socket clientSocket = serverSocket.accept();
				final Runnable connectionHandler = new ConnectionHandler(clientSocket);
				new Thread(connectionHandler).start();
			}

			catch (Throwable e)
			{
				Out.writeTS("[EXCEPTION] " + e.getMessage());
				break;
			}
		}

		Out.writeTS("Server stopped.");
	}

	public static String getStatistics(String modId)
	{
		StringBuilder sb = new StringBuilder();

		sb.append("---Updates---|");
		for (String s : displayUpdatesMap.keySet())
		{
			if (s.contains(modId))
			{
				sb.append(s + ": " + displayUpdatesMap.get(s) + "|");
			}
		}

		sb.append("---Crashes---" + "|");
		for (String s : displayCrashesMap.keySet())
		{
			if (s.contains(modId))
			{
				sb.append(s + ": " + displayCrashesMap.get(s) + "|");
			}
		}

		Out.writeTS("Statistics: " + sb.toString());
		return sb.toString();
	}
}
