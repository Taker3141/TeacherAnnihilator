package entity;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import raycasting.Floor;
import raycasting.IHitBox;
import renderer.models.TexturedModel;

public class Building extends Entity
{
	Floor floor;
	
	public Building(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list, Floor floor)
	{
		super(model, position, rotX, rotY, rotZ, scale, list, floor);
		this.floor = floor;
	}
	
	public Building(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}
	
	@Override
	public boolean isInsideHitBox(Vector3f point)
	{
		return floor.isInside(point);
	}
	
	@Override
	public boolean isInsideHitBox(IHitBox box)
	{
		return floor.isInside(box);
	}
	
	@Override
	public void setHitBox(IHitBox box)
	{
		super.setHitBox(box);
		if(box instanceof Floor) floor = (Floor)box;
	}
}
