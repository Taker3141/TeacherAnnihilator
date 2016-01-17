package entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;
import gui.item.Item;

public class EntityItem extends Movable
{
	protected BodyPart arm;
	protected Vector3f rotOffset = new Vector3f(0, 90, 0);
	protected Vector3f offset = new Vector3f(0, -1.5F, 0);
	public EntityItem(Item i, BodyPart arm)
	{
		super(i.getModel(), arm.position, arm.rotX, arm.rotY, arm.rotZ, 0.1F, arm.entityList, 0.1F);
		this.arm = arm;
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		return Matrix4f.mul(arm.getTransformationMatrix(), Maths.createTransformationMatrix(offset, rotOffset, 1), null);
	}
}
