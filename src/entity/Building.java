package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.BuildingBox;
import raycasting.Floor;
import raycasting.IHitBox;
import raycasting.Wall;
import renderer.models.TexturedModel;

public class Building extends Entity
{
	protected Floor floor;
	protected Wall[] walls;
	protected AABB bigBox = new AABB(new Vector3f(), new Vector3f(), new Vector3f());
	protected BuildingBox b;
	
	public Building(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, Vector3f size, Floor floor)
	{
		super(model, position, rotX, rotY, rotZ, scale, list, floor);
		bigBox.size = size;
		this.floor = floor;
		b = new BuildingBox(floor, walls, bigBox);
	}
	
	public Building(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f size, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		bigBox.size = size;
		b = new BuildingBox(floor, walls, bigBox);
	}
	
	@Override
	public boolean isInsideHitBox(Vector3f point)
	{
		return b.isInside(point);
	}
	
	@Override
	public boolean isInsideHitBox(IHitBox box)
	{
		return b.isInside(box);
	}
	
	@Override
	public IHitBox getHitBox()
	{
		return b;
	}
	
	public void setWalls(Wall[] array)
	{
		walls = array;
		for(Wall w : walls)
		{
			w.location = Vector3f.add(position, w.location, null);
		}
		b = new BuildingBox(floor, walls, bigBox);
	}
	
	@Override
	public void setHitBox(IHitBox box)
	{
		super.setHitBox(box);
		if(box instanceof Floor) 
		{
			floor = (Floor)box;
			bigBox.location = floor.location;
			bigBox.offset = floor.offset;
			b = new BuildingBox(floor, walls, bigBox);
		}
	}
}
