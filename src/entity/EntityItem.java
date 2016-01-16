package entity;

import terrain.Terrain;
import gui.item.Item;

public class EntityItem extends Movable
{
	protected BodyPart arm;
	public EntityItem(Item i, BodyPart arm)
	{
		super(i.getModel(), arm.position, arm.rotX, arm.rotY, arm.rotZ, 0.1F, arm.entityList);
		this.arm = arm;
	}
	
	@Override
	public void update(Terrain t)
	{
		position = arm.position;
		position.y = arm.position.y - 0.15F;
		rotX = arm.rotX;
		rotY = arm.rotY;
		rotZ = arm.rotZ;
	}
}
