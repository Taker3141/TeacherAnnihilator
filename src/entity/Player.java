package entity;

import gui.item.Inventory;
import java.util.List;
import main.MainManagerClass;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import raycasting.AABB;
import raycasting.ICollidable;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import renderer.textures.ModelTexture;
import terrain.Terrain;
import static entity.State.*;

public class Player extends Person
{
	private static final float RUN_SPEED = 5;
	private static final float TURN_SPEED = 80;
	private static final float JUMP_POWER = 10;
	private static final float PUNCH_POWER = 10;
	
	private float currentTurnSpeed = 0;
	private float speed = RUN_SPEED;
	private float punchTimer = 0;
	private boolean punchTimerStarted = false;
	private float kickTimer = 0;
	private boolean kickTimerStarted = false;
	private ICollidable toPunch;
	private Vector3f punchPoint;
	private ICollidable toKick;
	private Vector3f kickPoint;
	private boolean isArmUp = false;
	private Inventory inventory;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		super(model, position, rotX, rotY, rotZ, scale, list);
		hitBox = new AABB(position, new Vector3f(0.2F, 0.3F, 0.2F), new Vector3f(-0.1F, 0.15F, -0.1F));
	}
	
	public Player(String texture, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Entity> list)
	{
		this(new TexturedModel(body, new ModelTexture(MainManagerClass.loader.loadTexture(texture))), position, rotX, rotY, rotZ, scale, list);
	}
	
	@Override
	public void update(Terrain terrain)
	{		
		boolean armFlag = isArmUp;
		float delta = DisplayManager.getFrameTimeSeconds();
		checkInputs();
		boolean idleFlag = true;
		
		if(Math.abs(v.x) > 1.5 * RUN_SPEED || Math.abs(v.z) > 1.5 * RUN_SPEED)
		{
			if(state != RUNNING) stateChanged(RUNNING);
			state = RUNNING;
			idleFlag = false;
		}
		else if(v.x != 0 || v.z != 0) 
		{
			if(state != WALKING) stateChanged(WALKING);
			state = WALKING;
			idleFlag = false;
		}
		if(idleFlag) 
		{
			if(state != IDLE) stateChanged(IDLE);
			state = IDLE;
		}
		if(!armFlag && isArmUp)
		{
			stateChanged(ARM_UP);
		}
		if(armFlag && !isArmUp)
		{
			stateChanged(ARM_DOWN);
		}
		rotY += currentTurnSpeed * delta;
		if(punchTimerStarted) punchTimer += DisplayManager.getFrameTimeSeconds();
		if(punchTimer > 1.1F)
		{
			punchTimer = 0;
			punchTimerStarted = false;
			punch(toPunch, punchPoint);
		}
		if(kickTimerStarted) kickTimer += DisplayManager.getFrameTimeSeconds();
		if(kickTimer > 0.4F)
		{
			kickTimer = 0;
			kickTimerStarted = false;
			kick(toKick, kickPoint);
		}
		super.update(terrain);
	}
	
	public void clickAt(ICollidable e, Vector3f point)
	{
		if (bodyParts.get("rightArm").isAttatched)
		{
			toPunch = e;
			punchPoint = point;
			stateChanged(PUNCHING);
			state = PUNCHING;
			punchTimerStarted = true;
		}
	}

	public void kickAt(ICollidable e, Vector3f point)
	{
		if (bodyParts.get("rightLeg").isAttatched)
		{
			toKick = e;
			kickPoint = point;
			stateChanged(KICKING);
			state = KICKING;
			kickTimerStarted = true;
		}
	}
	
	private void kick(ICollidable e, Vector3f point)
	{
		punch(e, point);
	}
	
	private void punch(ICollidable e, Vector3f point)
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
		if (Keyboard.isKeyDown(Keyboard.KEY_F3)) System.out.println(position);
		isArmUp = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
}