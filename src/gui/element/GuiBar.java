package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class GuiBar extends GuiElement
{
	public float offset = 0, height = 1;
	
	public GuiBar(int texture, Vector2f position, Vector2f size, Menu parent)
	{
		super(texture, position, size, parent);
	}
}
