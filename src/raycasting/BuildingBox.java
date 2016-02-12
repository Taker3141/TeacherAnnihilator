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
			for(Wall w : walls)
			{
				System.out.println(w.isInside(point));
				if(w.isInside(point)) return true;
			}
			if(floor.isInside(point)) return true;
		}
		return false;
	}

	@Override
	public boolean isInside(IHitBox box)
	{
		if(bigBox.isInside(box))
		{
			if(floor.isInside(box)) return true;
			for(Wall w : walls)
			{
				if(w.isInside(box)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPlatform()
	{
		return true;
	}

	@Override
	public Vector3f getCenter(Vector3f point)
	{
		return walls[0].location;
		//return floor.getCenter(point);
	}
}
