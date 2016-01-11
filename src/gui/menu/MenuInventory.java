package gui.menu;

import java.util.ArrayList;
import java.util.List;
import main.MainGameLoop;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import renderer.DisplayManager;
import renderer.OverlayRenderer;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import gui.element.Slot;
import gui.handler.HandlerSwapItems;
import gui.handler.MouseHandler;
import gui.item.Inventory;
import gui.item.Item;

public class MenuInventory extends Menu
{
	private final int X = W / 2 - 256;
	private final int Y = H / 2 - 256;
	
	public Item mouseItem = null;
	
	public void doMenu(Inventory inventory)
	{
		Slot.setIconList(guiElementsForeground);
		gRenderer = new OverlayRenderer(loader);
		
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/inventory/background"), new Vector2f(X, Y), new Vector2f(512, 512), (Menu)this));
		new GUIText("inventory.schoolBag", 2, font, new Vector2f(X + 50, Y + 450), 1, false);
		Slot[] slots = new Slot[inventory.size];
		for(int i = 0; i < inventory.size; i++)
		{
			final int d = 85;
			slots[i] = new Slot(new Vector2f(40 + X + d * (i % 5), 330 + Y - d * (i / 5)), this);
			slots[i].setClickHandler(new HandlerSwapItems(slots[i]));
			guiElements.add(slots[i]);
		}
		slots[0].setItem(new Item(loader.loadTexture("texture/gui/icon_ruler_selected"), slots[0]));
		
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		TextMaster.init(loader);
		while(!Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			if(mouseItem != null) mouseItem.getIcon().position = new Vector2f(Mouse.getX() - 32, Mouse.getY() - 32);
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
