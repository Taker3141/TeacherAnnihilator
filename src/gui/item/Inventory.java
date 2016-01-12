package gui.item;

import org.lwjgl.util.vector.Vector2f;
import main.MainManagerClass;

public class Inventory
{
	private static final String path = "texture/gui/items/";
	public String name;
	protected Item[] items;
	public final int size;
	
	public Inventory(int size)
	{
		this.size = size;
		items = new Item[size];
		items[0] = new Item(MainManagerClass.loader.loadTexture(path + "ruler"), new Vector2f(), null);
		items[1] = new Item(MainManagerClass.loader.loadTexture(path + "book"), new Vector2f(), null);
		items[2] = new Item(MainManagerClass.loader.loadTexture(path + "sheet"), new Vector2f(), null);
		items[3] = new Item(MainManagerClass.loader.loadTexture(path + "pencil"), new Vector2f(), null);
		items[4] = new Item(MainManagerClass.loader.loadTexture(path + "triangle"), new Vector2f(), null);
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
