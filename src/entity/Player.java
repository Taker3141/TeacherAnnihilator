package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.ICollidable;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Player extends Person
{
	private static final float RUN_SPEED = 5;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 10;
	private static final float PUNCH_POWER = 100;
	
	private float currentTurnSpeed = 0;
	private float speed = RUN_SPEED;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	@Override
	public void update(Terrain terrain)
	{		
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs();
		rotY += currentTurnSpeed * delta;
		if(!canMove(v.x * delta, v.z * delta, terrain))
		{
			v.x = 0;
			v.y = 0;
			v.z = 0;
		}
		super.update(terrain);
	}
	
	public void clickAt(ICollidable e)
	{
		if (e instanceof Person)
		{
			Person p = (Person) e;
			Vector3f force = Vector3f.sub(p.position, position, null);
			force = force.normalise(force);
			force.x = force.x * PUNCH_POWER;
			force.y = force.y * PUNCH_POWER;
			force.z = force.z * PUNCH_POWER;
			p.forces.add(force);
			p.damage(1);
		}
	}
	
	private void jump()
	{
		v.y = JUMP_POWER;
		isInAir = true;
	}
	
	private void checkInputs()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			v.x = (float) (speed * Math.sin(Math.toRadians(rotY)));
			v.z = (float) (speed * Math.cos(Math.toRadians(rotY)));
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			v.x = (float) (-speed * Math.sin(Math.toRadians(rotY)));
			v.z = (float) (-speed * Math.cos(Math.toRadians(rotY)));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
		else currentTurnSpeed = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) jump();
		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) speed = 2 * RUN_SPEED;
		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) speed = RUN_SPEED;
	}
}
