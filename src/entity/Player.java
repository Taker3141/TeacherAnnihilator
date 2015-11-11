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
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	@Override
	public void update(Terrain terrain)
	{
		checkInputs();
		rotY += currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
		double distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
		position.x += dx;
		position.z += dz;
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		position.y += upwardsSpeed * DisplayManager.getFrameTimeSeconds();
		terrainHeight = terrain.getHeight(position.x, position.z);
		if(position.y < terrainHeight) 
		{
			upwardsSpeed = 0;
			position.y = terrainHeight;
			isInAir = false;
		}
	}
	
	private void jump()
	{	
		upwardsSpeed = JUMP_POWER;
		isInAir = true;
	}
	
	private void checkInputs()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) currentSpeed = RUN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) currentSpeed = -RUN_SPEED;
		else currentSpeed = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
		else currentTurnSpeed = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) jump();
	}
}
