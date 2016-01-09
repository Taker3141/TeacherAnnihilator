package gui.item;

public class Inventory
{
	public String name;
	protected Item[] items;
	protected int size;
	
	public Item getItemAt(int index)
	{
		return items[index];
	}
	
	public void setItem(int index, Item item)
	{
		items[index] = item;
	}
	
	public int getSize()
	{
		return size;
	}
}
