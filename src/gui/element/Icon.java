package gui.element;

import org.lwjgl.util.vector.Vector2f;

public class Icon extends GuiElement
{
	public Icon(int texture, Vector2f position)
	{
		super(texture, position, new Vector2f(32, 32));
	}	
}
