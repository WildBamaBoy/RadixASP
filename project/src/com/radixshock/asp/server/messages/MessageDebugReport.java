package com.radixshock.asp.server.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageDebugReport extends AbstractMessage
{
	private String validationString;
	private String modId;
	private String modVersion;
	private String debugReport;

	public MessageDebugReport()
	{
		super();
	}

	public MessageDebugReport(String modId)
	{
		this.modId = modId;
	}

	@Override
	public byte getId() 
	{
		return 3;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException 
	{
		//No response.
	}

	@Override
	public void readData(DataInputStream in) throws IOException 
	{
		//Deprecated. Accept the data and do nothing.
		validationString = in.readUTF();

		if (validationString.equals("@Validate@"))
		{
			modId = in.readUTF();
			modVersion = in.readUTF();
			debugReport = in.readUTF();
		}

		else
		{
			throw new IOException("Rejecting request without validation string.");
		}
	}

	@Override
	public void process() throws IOException 
	{
	}
}
