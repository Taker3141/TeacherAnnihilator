package raycasting;

import org.lwjgl.util.vector.Vector3f;

public class BuildingBox implements IHitBox
{
	public Floor floor;
	public Wall[] walls;
	public AABB bigBox;
	
	public BuildingBox(Floor floor, Wall[] walls, AABB bigBox)
	{
		this.floor = floor;
		this.walls = walls;
		this.bigBox = bigBox;
	}

	@Override
	public boolean isInside(Vector3f point)
	{
		if(bigBox.isInside(point))
		{
			return floor.isInside(point);
		}
		return false;
	}

	@Override
	public boolean isInside(IHitBox box)
	{
		if(bigBox.isInside(box))
		{
			return floor.isInside(box);
		}
		return false;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}

	@Override
	public Vector3f getCenter()
	{
		return floor.getCenter();
	}
}
