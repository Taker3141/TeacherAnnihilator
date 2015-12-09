package entity;

import java.util.List;
import main.MainManagerClass;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.ICollidable;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;

public class Player extends Person
{
	private static final float RUN_SPEED = 5;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 10;
	private static final float PUNCH_POWER = 10;
	
	private float currentTurnSpeed = 0;
	private float speed = RUN_SPEED;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
	}
	
	public Player(String texture, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(new TexturedModel(body, new ModelTexture(MainManagerClass.loader.loadTexture(texture))), position, rotX, rotY, rotZ, scale, list);
	}
	
	@Override
	public void update(Terrain terrain)
	{		
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs();

		if(Math.abs(v.x) > 1.5 * RUN_SPEED || Math.abs(v.z) > 1.5 * RUN_SPEED)
		{
			if(state != State.RUNNING) stateChanged(State.RUNNING);
			state = State.RUNNING;
		}		
		else if(v.x != 0 || v.z != 0) 
		{
			if(state != State.WALKING) stateChanged(State.WALKING);
			state = State.WALKING;
		}
		else 
		{
			if(state != State.IDLE) stateChanged(State.IDLE);
			state = State.IDLE;
		}
		rotY += currentTurnSpeed * delta;
		super.update(terrain);
	}
	
	public void clickAt(ICollidable e, Vector3f point)
	{
		if (e instanceof Movable)
		{
			Movable m = (Movable)e;
			Vector3f force = Vector3f.sub(m.position, position, null);
			force = force.normalise(force);
			if(force.x != force.x) force = new Vector3f();
			force.x = force.x * PUNCH_POWER;
			force.y = force.y * PUNCH_POWER;
			force.z = force.z * PUNCH_POWER;
			m.click();
			m.forces.add(force);
			m.isInAir = true;
			//m.damage(1);
			
			entityList.add(new Particle("blood", point, entityList));
			
			stateChanged(State.PUNCHING);
			state = State.PUNCHING;
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
