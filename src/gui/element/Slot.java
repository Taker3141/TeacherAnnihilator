package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

public class Slot extends GuiElement
{
	private static int textureSlot;
	
	public static void loadAllTextures(Loader l)
	{
		textureSlot = l.loadTexture("texture/gui/inventory/slot");
	}
	
	public Slot(Vector2f position, Vector2f size, Menu parent)
	{
		super(textureSlot, position, size, parent);
	}
}
