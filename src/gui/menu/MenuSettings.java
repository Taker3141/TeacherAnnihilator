package gui.menu;

import java.util.ArrayList;
import java.util.List;
import font.fontMeshCreator.GUIText;
import gui.element.Button;
import gui.element.CycleButton;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.MouseHandler;
import localize.Localizer;
import main.MainGameLoop;
import main.MainManagerClass;
import main.MainMenu;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import renderer.DisplayManager;

public class MenuSettings extends Menu
{
	private CycleButton langButton;
	private CycleButton screenButton;
	private CycleButton fullscreenButton;
	
	@Override
	public void doMenu()
	{
		final int buttonIndention = W / 4;
		new GUIText("settings.settings", 3, font, new Vector2f(0, H - 32), 1, true).setColour(1, 1, 1);
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner_settings"), new Vector2f(0, H - 128), new Vector2f(W, 128), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/background"), new Vector2f(0, 0), new Vector2f(W, H), this));
		{
			langButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 200), buttonSize, this).setTextList(new String[]{"lang.german", "lang.english"}, font, 1);
			new GUIText("lang.lang", 1, font, new Vector2f(langButton.position.x - 100, langButton.position.y + (langButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(langButton);
		}
		{
			fullscreenButton = (CycleButton) new CycleButton(new Vector2f(buttonIndention, H - 300), buttonSize, this).setTextList(new String[]{"menu.yes",  "menu.no"}, font, 1);
			new GUIText("screen.fullscreen", 1, font, new Vector2f(fullscreenButton.position.x - 100, fullscreenButton.position.y + (fullscreenButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(fullscreenButton);
		}
		{
			screenButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 250), buttonSize, this).setTextList(getDisplayModes(), font, 1);
			new GUIText("screen.resolution", 1, font, new Vector2f(screenButton.position.x - 100, screenButton.position.y + (screenButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(screenButton);
		}
		
		guiElements.add(new Button(new Vector2f(200, 100), buttonSize, this).setText("menu.back", font, 1).setIcon(loader.loadTexture("texture/gui/icon_back"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(MainMenu.class)));
		
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		while(!Display.isCloseRequested() && !isCloseRequested)
		{
			render();
			input.poll(W, H);
		}
		cleanUp();
		if (nextMenu != null)
		{
			super.doNextMenu();
		}
		if(shouldStartGame)
		{
			MainGameLoop.doGame();
		}
	}
	
	@Override
	public void requestClose(Class<? extends Menu> next)
	{
		super.requestClose(next);
		
		switch (langButton.getCurrent())
		{
			case "lang.german":
			{
				MainManagerClass.localizer = new Localizer("de_DE");
				break;
			}
			case "lang.english":
			{
				MainManagerClass.localizer = new Localizer("en_US");
				break;
			}
		}
		
		String[] resolution = screenButton.getCurrent().substring(2).split("x");
		DisplayManager.recreateDisplay(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]), fullscreenButton.list[fullscreenButton.index] == "menu.yes");
	}
	
	public String[] getDisplayModes()
	{
		List<String> ret = new ArrayList<String>();
		try
		{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for(int i = 0; i < modes.length; i++)
			{
				if (modes[i].getBitsPerPixel() == 32 && modes[i].getHeight() >= 600)
				{
					ret.add("o!" + modes[i].getWidth() + "x" + modes[i].getHeight());
				}
			}
			String[] array = new String[ret.size()];
			for(int i = 0; i < array.length; i++)
			{
				array[i] = ret.get(i);
			}
			return array;
		}
		catch (LWJGLException e)
		{
			System.out.println("Couldn't get Display Modes!");
			e.printStackTrace();
		}
		return null;
	}
}
