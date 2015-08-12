package com.radixshock.asp.server.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import com.radixshock.asp.server.ASPServer;

public class MessageCrashReport extends AbstractMessage
{
	private String validationString;
	private String modId;
	private String modVersion;
	private boolean isServer;
	private String crashReport;

	public MessageCrashReport()
	{
		super();
	}

	public MessageCrashReport(String modId)
	{
		this.modId = modId;
	}

	@Override
	public byte getId() 
	{
		return 2;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException 
	{
		//No response.
	}

	@Override
	public void readData(DataInputStream in) throws IOException 
	{
		validationString = in.readUTF();

		if (validationString.equals("@Validate@"))
		{
			modId = in.readUTF();
			modVersion = in.readUTF();
			isServer = in.readBoolean();
			crashReport = in.readUTF();
		}

		else
		{
			throw new IOException("Rejecting request without validation string.");
		}
	}

	@Override
	public void process() throws IOException 
	{
		String fileName = new Timestamp(new Date().getTime()).toString();
		fileName = fileName.replace(' ', '-');
		fileName = fileName.trim();
		fileName = isServer ? fileName + "-server" : fileName + "-client";
		fileName = fileName + ".txt";

		File crashFolder = new File("./crash-reports/" + modId + "/" + modVersion + "/");
		crashFolder.mkdirs();

		File crashFile = new File("./crash-reports/" + modId + "/" + modVersion + "/" + fileName);
		FileWriter filewriter = new FileWriter(crashFile);
		filewriter.write(crashReport);
		filewriter.close();

		String blacklistId = modId + ":" + modVersion;
		if (!ASPServer.dataBlacklist.contains(blacklistId))
		{
			Integer currentCrashNumber = ASPServer.displayCrashesMap.get(modId + " " + modVersion);

			if (currentCrashNumber == null)
			{
				currentCrashNumber = 0;
			}

			ASPServer.displayCrashesMap.put(modId + " " + modVersion, currentCrashNumber + 1);
		}
	}
}
