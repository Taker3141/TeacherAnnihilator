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
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import gui.element.Slot;
import gui.handler.MouseHandler;
import gui.item.Inventory;

public class MenuInventory extends Menu
{
	private final int X = W / 2 - 256;
	private final int Y = H / 2 - 256;
	
	public void doMenu(Inventory inventory)
	{
		gRenderer = new OverlayRenderer(loader);
		
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/inventory/background"), new Vector2f(X, Y), new Vector2f(512, 512), (Menu)this));
		guiElements.add(new Slot(new Vector2f(X + 100, Y + 100), new Vector2f(64, 64), this));
		new GUIText("inventory.schoolBag", 2, font, new Vector2f(X + 50, Y + 450), 1, false);
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		TextMaster.init(loader);
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
	public final void doMenu()
	{
		
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
