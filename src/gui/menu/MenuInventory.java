package gui.menu;

import java.util.ArrayList;
import java.util.List;
import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import renderer.DisplayManager;
import renderer.OverlayRenderer;
import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import gui.handler.MouseHandler;

public class MenuInventory extends Menu
{
	@Override
	public void doMenu()
	{
		gRenderer = new OverlayRenderer(loader);
		
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/inventory/background"), new Vector2f(W / 2 - 256, H / 2 - 256), new Vector2f(512, 512), (Menu)this));
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			MainGameLoop.render();
			render();
			DisplayManager.updateDisplay();
			input.poll(W, H);
		}
		cleanUp();
	}
	
	@Override
	protected void render()
	{
		List<GuiElement> renderList = new ArrayList<GuiElement>();
		renderList.addAll(guiElementsForeground);
		renderList.addAll(guiElements);
		renderList.addAll(guiElementsBackground);
		gRenderer.render(renderList);
		TextMaster.render();
	}
}
