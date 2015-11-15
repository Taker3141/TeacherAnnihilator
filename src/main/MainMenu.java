package main;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import gui.Menu;
import gui.element.Button;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.HandlerStartGame;
import gui.handler.MouseHandler;

public class MainMenu extends Menu
{
	private final int W = Display.getWidth();
	private final int H = Display.getHeight();
	
	@Override
	public void doMenu()
	{
		{
			final int indention = W / 4;
			final Vector2f size = new Vector2f(256, 32);
			guiElements.add(new Button(new Vector2f(indention + 200, H - 200), size, this).setText("menu.walk_around", font, 1).setIcon(loader.loadTexture("texture/gui/icon_walk_around"), guiElementsForeground).setClickHandler(new HandlerStartGame()));
			guiElements.add(new Button(new Vector2f(indention, H - 250), size, this).setText("menu.missions", font, 1).setIcon(loader.loadTexture("texture/gui/icon_book"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 300), size, this).setText("menu.shop", font, 1).setIcon(loader.loadTexture("texture/gui/icon_shopping_cart"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention, H - 350), size, this).setText("menu.settings", font, 1).setIcon(loader.loadTexture("texture/gui/icon_gear"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 400), size, this).setText("menu.updates", font, 1).setIcon(loader.loadTexture("texture/gui/icon_arrow"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention, H - 450), size, this).setText("menu.credits", font, 1).setIcon(loader.loadTexture("texture/gui/icon_book"), guiElementsForeground));
			guiElements.add(new Button(new Vector2f(indention + 200, H - 500), size, this).setText("menu.exit", font, 1).setIcon(loader.loadTexture("texture/gui/icon_door"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(null)));
		}
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner"), new Vector2f(0, H - 150), new Vector2f(W, 128), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/background"), new Vector2f(0, 0), new Vector2f(W, H), this));
		
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
}
