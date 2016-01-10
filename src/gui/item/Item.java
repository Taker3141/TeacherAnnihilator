package gui.item;

import gui.element.Icon;
import gui.element.Slot;

public class Item
{
	protected Icon icon;
	
	public Item(int texture, Slot s)
	{
		icon = new Icon(texture, s.position, Icon.Size.x64, s.parent);
	}
	
	public Icon getIcon()
	{
		return icon;
	}
}
