package main;

import localize.Localizer;
import renderer.DisplayManager;
import toolbox.LogStream;

public class MainManagerClass
{
	public static Localizer localizer = new Localizer("en_US");
	public static final String workingPath;
	public static SettingsFile settings;
	
	public static void main(String[] args)
	{
		System.setOut(new LogStream());
		DisplayManager.createDisplay();
		
		MainMenu mainMenu = new MainMenu();
		mainMenu.doMenu();
		//MainGameLoop.doGame();
	}
	
	static 
	{
		workingPath = System.getProperty("user.dir");
		System.out.println("Working path is: " + workingPath);
		settings = new SettingsFile(workingPath + "/save/settings.txt");
	}
}
