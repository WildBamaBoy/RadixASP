package com.radixshock.asp.server.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.radixshock.asp.server.ASPServer;

public class MessageStatisticsQuery extends AbstractMessage
{
	private String modId;
	
	@Override
	public byte getId() 
	{
		return 5;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException 
	{
		out.writeUTF(ASPServer.getStatistics(modId));
	}

	@Override
	public void readData(DataInputStream in) throws IOException 
	{
		String validationString = in.readUTF();

		if (validationString.equals("@Validate@"))
		{
			modId = in.readUTF();
		}

		else
		{
			throw new IOException("Rejecting request without validation string.");
		}
	}

	@Override
	public void process() throws IOException 
	{
		writeData(connectionHandler.dataOut);
	}
	
}
