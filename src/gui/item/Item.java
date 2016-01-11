package gui.item;

import org.lwjgl.util.vector.Vector2f;
import gui.element.Icon;
import gui.element.Slot;
import gui.menu.MenuInventory;

public class Item
{
	protected Icon icon;
	
	public Item(int texture, Slot s)
	{
		icon = new Icon(texture, s.position, Icon.Size.x64, s.parent);
	}
	
	public Item(int texture, Vector2f position, MenuInventory parent)
	{
		icon = new Icon(texture, position, Icon.Size.x64, parent);
	}
	
	public void setPosition(Vector2f position)
	{
		icon.position = position;
	}
	
	public Vector2f getPosition()
	{
		return icon.position;
	}
	
	public Icon getIcon()
	{
		return icon;
	}
}
