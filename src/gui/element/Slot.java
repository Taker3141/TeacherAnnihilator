package gui.element;

import gui.item.Item;
import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

public class Slot extends GuiElement
{
	protected static final Vector2f standardSize = new Vector2f(64, 64);
	private static int textureSlot;
	protected Item item;
	
	public static void loadAllTextures(Loader l)
	{
		textureSlot = l.loadTexture("texture/gui/inventory/slot");
	}
	
	public Slot(Vector2f position, Vector2f size, Menu parent)
	{
		super(textureSlot, position, size, parent);
	}
	
	public Slot(Vector2f position, Menu parent)
	{
		this(position, standardSize, parent);
	}
}
