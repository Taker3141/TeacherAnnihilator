package gui.element;

import gui.Menu;
import org.lwjgl.util.vector.Vector2f;

public class GuiElement
{
	protected int texture;
	public Vector2f position;
	public Vector2f size;
	public boolean isEnabled = true;
	public Menu parent;
	
	public GuiElement(int texture, Vector2f position, Vector2f size, Menu parent)
	{
		super();
		this.texture = texture;
		this.position = position;
		this.size = size;
		this.parent = parent;
	}
	
	public int getTextureID()
	{
		return texture;
	}
}
