package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsFile
{
	private File file;
	public String language = "";
	public String resolution = "";
	public String fullscreen = "";
	
	public SettingsFile(String path)
	{
		file = new File(path);
		if(!file.exists())
		{
			createFile();
		}
	}
	
	public void createFile()
	{
		try
		{
			file.getParentFile().mkdirs(); 
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
			writer.write("language=en_US\n");
			writer.write("resolution=1280x720\n");
			writer.write("fullscreen=false\n");
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readFile()
	{
		
	}
}
