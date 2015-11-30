package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.models.TexturedModel;
import terrain.Terrain;

public class Player extends Person
{
	private static final float RUN_SPEED = 0.5F;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 10;
	
	private float currentTurnSpeed = 0;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	@Override
	public void update(Terrain terrain)
	{
        checkInputs();
		rotY += currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
		super.update(terrain);
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
			v.x += (float) (RUN_SPEED * Math.sin(Math.toRadians(rotY)));
			v.z += (float) (RUN_SPEED * Math.cos(Math.toRadians(rotY)));
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			v.x += (float) (-RUN_SPEED * Math.sin(Math.toRadians(rotY)));
			v.z += (float) (-RUN_SPEED * Math.cos(Math.toRadians(rotY)));
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;
		else currentTurnSpeed = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) 
		{
			jump();
		}
	}
}
