package main;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import font.fontRendering.TextMaster;
import gui.element.Button;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.HandlerStartGame;
import gui.handler.MouseHandler;
import gui.menu.Menu;
import gui.menu.MenuCredits;
import gui.menu.MenuSettings;

public class MainMenu extends Menu
{
	@Override
	public void doMenu()
	{	
		{
			final int indention = W / 4;
			guiElements.add(new Button(new Vector2f(indention + 200, H - 200), buttonSize, this).setText("menu.walk_around", font, 1).setIcon(loader.loadTexture("texture/gui/icon_walk_around"), guiElementsForeground).setClickHandler(new HandlerStartGame()));
			guiElements.add(new Button(new Vector2f(indention, H - 250), buttonSize, this).setText("menu.missions", font, 1).setIcon(loader.loadTexture("texture/gui/icon_book"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 300), buttonSize, this).setText("menu.shop", font, 1).setIcon(loader.loadTexture("texture/gui/icon_shopping_cart"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention, H - 350), buttonSize, this).setText("menu.settings", font, 1).setIcon(loader.loadTexture("texture/gui/icon_gear"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(MenuSettings.class)));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 400), buttonSize, this).setText("menu.updates", font, 1).setIcon(loader.loadTexture("texture/gui/icon_arrow"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention, H - 450), buttonSize, this).setText("menu.credits", font, 1).setIcon(loader.loadTexture("texture/gui/icon_book"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(MenuCredits.class)));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 500), buttonSize, this).setText("menu.exit", font, 1).setIcon(loader.loadTexture("texture/gui/icon_door"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(null)));
		}
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner"), new Vector2f(0, H - 128), new Vector2f(W, 128), this));
		guiElements.add(new GuiElement(loader.loadTexture("texture/drawings/drawing_1"), new Vector2f(W * 0.7F, H - 500), new Vector2f(256, 256), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/background"), new Vector2f(0, 0), new Vector2f(W, H), this));
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		boolean loopFlag = true;
		
		TextMaster.init(loader);
		while (loopFlag)
		{
			loopFlag = false;
			while (!Display.isCloseRequested() && !isCloseRequested)
			{
				render();
				input.poll(W, H);
			}
			isCloseRequested = false;
			if (shouldStartGame)
			{
				shouldStartGame = false;
				MainGameLoop.doGame();
				MainManagerClass.nextMenu = MainMenu.class;
			}
		}
		cleanUp();
	}
}
