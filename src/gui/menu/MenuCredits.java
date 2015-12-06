package gui.menu;

import font.fontMeshCreator.GUIText;
import gui.element.Button;
import gui.element.GuiElement;
import gui.handler.HandlerChangeMenu;
import gui.handler.MouseHandler;
import main.MainMenu;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;

public class MenuCredits extends Menu
{	
	@Override
	public void doMenu()
	{
		guiElements.add(new GuiElement(loader.loadTexture("texture/gui/banner_credits"), new Vector2f(0, H - 128), new Vector2f(W, 128), this));
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/background"), new Vector2f(0, 0), new Vector2f(W, H), this));
		guiElements.add(new Button(new Vector2f(200, 100), buttonSize, this).setText("menu.back", font, 1).setIcon(loader.loadTexture("texture/gui/icon_back"), guiElementsForeground).setClickHandler(new HandlerChangeMenu(MainMenu.class)));
		
		new GUIText("o!Taker (minecrafty512@gmail.com)", 2, font, new Vector2f(50, H - 150), 1, false).setColour(0, 0.5F, 1);
		new GUIText("o!KlötzchenTV", 2, font, new Vector2f(50, H - 200), 1, false).setColour(1, 0.5F, 0);
		
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
			doNextMenu();
		}
	}
}
