package gui.element;

import gui.menu.Menu;
import org.lwjgl.util.vector.Vector2f;

public class Icon extends GuiElement
{
	public Icon(int texture, Vector2f position, Menu parent)
	{
		super(texture, position, new Vector2f(32, 32), parent);
	}	
}
