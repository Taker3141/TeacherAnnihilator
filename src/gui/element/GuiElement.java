package gui.element;

import org.lwjgl.util.vector.Vector2f;

public class GuiElement
{
	protected int texture;
	public Vector2f position;
	public Vector2f size;
	public boolean isEnabled = true;
	
	public GuiElement(int texture, Vector2f position, Vector2f size)
	{
		super();
		this.texture = texture;
		this.position = position;
		this.size = size;
	}
	
	public int getTextureID()
	{
		return texture;
	}
}
