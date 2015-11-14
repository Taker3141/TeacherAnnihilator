package main;

import localize.Localizer;
import renderer.DisplayManager;
import toolbox.LogStream;

public class MainManagerClass
{
	public static Localizer localizer = new Localizer("en_US");
	
	public static void main(String[] args)
	{
		System.setOut(new LogStream());
		DisplayManager.createDisplay();
		
		MainMenu mainMenu = new MainMenu();
		mainMenu.doMenu();
		//MainGameLoop.doGame();
	}
}
