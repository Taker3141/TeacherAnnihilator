package gui.menu;

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

public class MenuSettings extends Menu
{
	private CycleButton langButton;
	private CycleButton screenButton;
	private CycleButton fullscreenButton;
	
	@Override
	public void doMenu()
	{
		final int buttonIndention = W / 4;
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner"), new Vector2f(0, H - 128), new Vector2f(W, 128), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/background"), new Vector2f(0, 0), new Vector2f(W, H), this));
		{
			langButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 200), buttonSize, this).setTextList(new String[]{"lang.german", "lang.english"}, font, 1);
			new GUIText("lang.lang", 1, font, new Vector2f(langButton.position.x - 100, langButton.position.y + (langButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(langButton);
		}
		{
			screenButton = (CycleButton)new CycleButton(new Vector2f(buttonIndention, H - 250), buttonSize, this).setTextList(getDisplayModes(), font, 1);
			new GUIText("screen.resolution", 1, font, new Vector2f(screenButton.position.x - 100, screenButton.position.y + (screenButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(screenButton);
		}
		{
			fullscreenButton = (CycleButton) new CycleButton(new Vector2f(buttonIndention, H - 300), buttonSize, this).setTextList(new String[]{"menu.yes",  "menu.no"}, font, 1);
			new GUIText("screen.fullscreen", 1, font, new Vector2f(fullscreenButton.position.x - 100, fullscreenButton.position.y + (fullscreenButton.size.y / 2) + 10), buttonIndention, false);
			guiElements.add(fullscreenButton);
			
		}
		
		guiElements.add(new Button(new Vector2f(200, 100), buttonSize, this).setText("menu.back", font, 1).setIcon(loader.loadTexture("texture/gui/icon_back"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(new MainMenu())));
		
		
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
			nextMenu.doMenu();
		}
		if(shouldStartGame)
		{
			MainGameLoop.doGame();
		}
	}
	
	@Override
	public void requestClose(Menu next)
	{
		super.requestClose(next);
		
		switch (langButton.list[langButton.index])
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
	}
	
	private String[] getDisplayModes()
	{
		String[] ret = null;
		try
		{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			ret = new String[modes.length];
			for(int i = 0; i < ret.length; i++)
			{
				ret[i] = "o!" + modes[i].getWidth() + "x" + modes[i].getHeight();
			}
		}
		catch (LWJGLException e)
		{
			System.out.println("Couldn't get Display Modes!");
			e.printStackTrace();
		}
		return ret;
	}
}
