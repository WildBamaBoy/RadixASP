package com.radixshock.asp.server.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.radixshock.asp.server.ConnectionHandler;

public abstract class AbstractMessage 
{
	protected ConnectionHandler connectionHandler;
	
	public AbstractMessage()
	{
		
	}
	
	public final void setConnectionHandler(ConnectionHandler connectionHandler)
	{
		this.connectionHandler = connectionHandler;
	}
	
	public abstract byte getId();
	
	public abstract void writeData(DataOutputStream out) throws IOException;
	
	public abstract void readData(DataInputStream in) throws IOException;
	
	public abstract void process() throws IOException;
}