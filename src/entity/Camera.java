package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private float distanceFromPlayer = 20;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 10, 0);
	private float pitch = 20;
	private float yaw;
	private float roll;
	private Player player;
	private boolean isFirstPerson = false;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	public void update()
	{
		calculatePitch();
		if (!isFirstPerson)
		{
			calculateZoom();
			calculateAngleAroundPlayer();
			calculateCameraPosition((float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch))), (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch))));
			yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
		else
		{
			position = new Vector3f(player.position.x, player.position.y + 0.5F, player.position.z);
			yaw = -player.rotY + 180;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) && !isFirstPerson)
		{
			pitch = 20;
			angleAroundPlayer = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) isFirstPerson = true;
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))
		{
			isFirstPerson = false;
			pitch = 20;
			angleAroundPlayer = 0;
		}
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public float getPitch()
	{
		return pitch;
	}
	
	public float getYaw()
	{
		return yaw;
	}
	
	public float getRoll()
	{
		return roll;
	}
	
	private void calculateCameraPosition(float hDistance, float vDistance)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		position.x = player.getPosition().x - (float) (hDistance * Math.sin(Math.toRadians(theta)));
		position.y = player.getPosition().y + vDistance;
		position.z = player.getPosition().z - (float) (hDistance * Math.cos(Math.toRadians(theta)));
	}
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.05F;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer < 3) distanceFromPlayer = 3;
		if (distanceFromPlayer > 30) distanceFromPlayer = 30;
	}
	
	private void calculatePitch()
	{
		if (Mouse.isButtonDown(2))
		{
			float pitchChange = Mouse.getDY() * 0.1F;
			pitch -= pitchChange;
			if (pitch < -90) pitch = -90;
			if (pitch > 90) pitch = 90;
		}
	}
	
	private void calculateAngleAroundPlayer()
	{
		if (Mouse.isButtonDown(2))
		{
			float angleChange = Mouse.getDX() * 0.3F;
			angleAroundPlayer -= angleChange;
		}
	}
}
