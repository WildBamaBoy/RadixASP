package com.radixshock.asp.server.messages;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.radixshock.asp.server.ASPServer;
import com.radixshock.asp.util.Out;

public class MessageVersionQuery extends AbstractMessage
{
	private String validationString;
	private String modId;
	private String modVersion;
	
	public MessageVersionQuery()
	{
		super();
	}

	public MessageVersionQuery(String modId, String modVersion)
	{
		this.modId = modId;
		this.modVersion = modVersion;
	}

	@Override
	public byte getId() 
	{
		return 1;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException 
	{
		out.writeUTF(modId);
		out.writeUTF(modVersion);
	}

	@Override
	public void readData(DataInputStream in) throws IOException 
	{
		validationString = in.readUTF();
		
		if (validationString.equals("@Validate@"))
		{
			modId = in.readUTF();
			modVersion = in.readUTF();
		}
		
		else
		{
			throw new IOException("Rejecting request without validation string.");
		}
	}

	@Override
	public void process() throws IOException 
	{
		try
		{
			InputStream fileInput = new FileInputStream("./slugs/" + modId + ".sig");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput, Charset.forName("UTF-8")));
			
			String response = reader.readLine();
			connectionHandler.dataOut.writeUTF(response);
			
			reader.close();

			Integer currentUpdateNumber = ASPServer.displayUpdatesMap.get(modId + " " + modVersion);
			
			if (currentUpdateNumber == null)
			{
				currentUpdateNumber = 0;
			}
			
			ASPServer.displayUpdatesMap.put(modId + " " + modVersion, currentUpdateNumber + 1);
		}

		catch (Throwable e)
		{
			e.printStackTrace();
			Out.writeTS("Unexpected error when responding to version query!");
		}
	}
}
