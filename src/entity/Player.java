package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Player extends Entity
{
	private static final float RUN_SPEED = 10;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 10;
	
	private float terrainHeight = 0;
	private float currentTurnSpeed = 0;
	private Vector3f move = new Vector3f();
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	@Override
	public void update(Terrain terrain)
	{
		checkInputs();
		move.y += GRAVITY * DisplayManager.getFrameTimeSeconds();
		rotY += currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
		position.x += move.x * DisplayManager.getFrameTimeSeconds();
		position.y += move.y * DisplayManager.getFrameTimeSeconds();
		position.z += move.z * DisplayManager.getFrameTimeSeconds();
		terrainHeight = terrain.getHeight(position.x, position.z);
		if(position.y < terrainHeight) 
		{
			move.y = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	private void jump()
	{	
		move.y = JUMP_POWER;
		isInAir = true;
	}
	
	private void checkInputs()
	{
		move.x = 0;
		move.z = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) 
		{
			move.x += (float) (RUN_SPEED * Math.sin(Math.toRadians(rotY)));
			move.z += (float) (RUN_SPEED * Math.cos(Math.toRadians(rotY)));
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			move.x += (float) (-RUN_SPEED * Math.sin(Math.toRadians(rotY)));
			move.z += (float) (-RUN_SPEED * Math.cos(Math.toRadians(rotY)));
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
		else currentTurnSpeed = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) jump();
	}
}
