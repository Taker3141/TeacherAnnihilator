package entity;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import entity.animation.Animation;
import renderer.models.TexturedModel;
import terrain.Terrain;
import toolbox.Maths;
import static entity.State.*;

public class BodyPart extends Movable
{
	protected boolean isAttatched = true;
	protected Person p;
	protected final Vector3f offset;
	protected Map<State, Animation> animations = new HashMap<>();
	protected Vector3f standardRotation = new Vector3f();

	protected State state;
	
	BodyPart(TexturedModel model, Person parent, Vector3f offset)
	{
		super(model, Vector3f.add(parent.position, offset, null), 0, 0, 0, parent.scale, parent.entityList);
		p = parent;
		this.offset = offset;
		position = calculatePosition();
		state = p.state;
	}
	
	BodyPart(TexturedModel model, Person parent, Vector3f offset, Vector3f hitboxSize, Vector3f hitboxOffset)
	{
		this(model, parent, offset);
		hitBox.location = p.position;
		hitBox.size = hitboxSize;
		hitBox.offset = hitboxOffset;
	}
	
	public BodyPart setAnimations(Map<State, Animation> a)
	{
		animations = a;
		animations.get(IDLE).start();
		return this;
	}
	
	@Override
	public void update(Terrain terrain)
	{
		if(!isAttatched) super.update(terrain);
		else position = calculatePosition();
		hitBox.location = position;
		if (animations.containsKey(state) && isAttatched)
		{
			Vector3f rotation = animations.get(state).getTurn();
			rotX += rotation.x;
			rotY += rotation.y;
			rotZ += rotation.z;
			
			if (state == IDLE || animations.get(state).getReset()) 
			{
				rotX = standardRotation.x; rotY = standardRotation.y; rotZ = standardRotation.z;
				if(state == PUNCHING || state == KICKING || state == ARM_DOWN) state = p.state;
			}
		}
	}
	
	@Override
	public Matrix4f getTransformationMatrix()
	{
		if(isAttatched) return Matrix4f.mul(p.getTransformationMatrix(), Maths.createTransformationMatrix(offset, rotX, rotY, rotZ, 1), null);
		else return Maths.createTransformationMatrix(position, rotX, rotY, rotZ, p.scale);
	}
	
	void stateChanged(State newState)
	{
		if (animations.containsKey(newState) && state != PUNCHING && state != KICKING && (state != ARM_UP || newState == ARM_DOWN)) 
		{
			animations.get(newState).start();
			state = newState;
		}
	}
	
	private Vector3f calculatePosition()
	{
		Vector4f vec = Matrix4f.transform(p.getTransformationMatrix().translate(offset), new Vector4f(0, 0, 0, 1), null);
		return new Vector3f(vec.x, vec.y, vec.z);
	}
	
	public void ripOff()
	{
		isAttatched = false;
		position = new Vector3f(position.x, position.y - hitBox.offset.y, position.z);
	}
	
	@Override
	public void click()
	{
		if(isAttatched) ripOff();
	}
	
	public void setStandardRotation(Vector3f standardRotation)
	{
		this.standardRotation = standardRotation;
		rotX = standardRotation.x;
		rotY = standardRotation.y;
		rotZ = standardRotation.z;
	}
}
