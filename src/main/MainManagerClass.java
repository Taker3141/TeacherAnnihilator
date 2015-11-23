package main;

import localize.Localizer;
import renderer.DisplayManager;
import toolbox.LogStream;

public class MainManagerClass
{
	public static Localizer localizer;
	public static final String workingPath;
	public static SettingsFile settings;
	
	public static void main(String[] args)
	{
		settings = new SettingsFile(workingPath + "/save/settings.txt");
		localizer = new Localizer(settings.language);
		DisplayManager.createDisplay(settings.resolutionX, settings.resolutionY, settings.fullscreen);
		
		MainMenu mainMenu = new MainMenu();
		mainMenu.doMenu();
		//MainGameLoop.doGame();
	}
	
	static 
	{
		System.setOut(new LogStream());
		workingPath = System.getProperty("user.dir");
		System.out.println("Working path is: " + workingPath);
	}
}
