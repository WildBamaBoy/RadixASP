package com.radixshock.asp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.radixshock.asp.server.messages.AbstractMessage;
import com.radixshock.asp.util.Out;

public class ConnectionHandler implements Runnable
{
	public DataInputStream dataIn;
	public DataOutputStream dataOut;
	private Socket clientSocket;
	
	public ConnectionHandler(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() 
	{
		primeDataStreams();
		handleNextMessage();

		try 
		{
			clientSocket.close();
		} 
		
		catch (IOException e) 
		{
			Out.writeTS("[ERROR] Unable to close client socket! " + e.getMessage());
		}
	}
	
	public void primeDataStreams() 
	{
		try
		{
			dataIn = new DataInputStream(clientSocket.getInputStream());
			dataOut = new DataOutputStream(clientSocket.getOutputStream());		
		}

		catch (IOException e)
		{
			Out.writeTS("[ERROR] Unexpected exception while priming data streams! " + e.getMessage());
		}
	}
	
	public void handleNextMessage()
	{
		try
		{
			byte messageId = dataIn.readByte();
			
			AbstractMessage message = EnumMessageType.getMessageInstance(messageId);
			message.setConnectionHandler(this);
			message.readData(dataIn);
			message.process();
		}

		catch (IOException e)
		{
			e.printStackTrace();
			Out.writeTS("[ERROR] Unexpected exception while handling next message! " + e.getMessage());
		}
	}
}
