package gui.item;

import org.lwjgl.util.vector.Vector2f;
import main.MainManagerClass;

public class Inventory
{
	public String name;
	protected Item[] items;
	public final int size;
	
	public Inventory(int size)
	{
		this.size = size;
		items = new Item[size];
		items[0] = new Item(MainManagerClass.loader.loadTexture("texture/gui/icon_ruler_selected"), new Vector2f(), null);
	}
	
	public Item getItemAt(int index)
	{
		return items[index];
	}
	
	public void setItem(int index, Item item)
	{
		items[index] = item;
	}
}
