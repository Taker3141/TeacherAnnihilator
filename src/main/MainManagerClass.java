package main;

import localize.Localizer;
import renderer.DisplayManager;
import renderer.Loader;
import toolbox.LogStream;

public class MainManagerClass
{
	public static Localizer localizer = new Localizer("de_DE");
	
	public static void main(String[] args)
	{
		System.setOut(new LogStream());
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		MainMenu.doMainMenu(loader);
		//MainGameLoop.doGame(loader);
	}
}
