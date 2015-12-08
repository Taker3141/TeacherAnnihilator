package entity.animation;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;

public class Animation
{
	private float currentDuration = 0;
	private Vector3f[] turningSpeeds;
	private float[] durations;
	private int pointer = 0;
	private boolean isRunning = false;
	
	public Animation(Vector3f[] turningSpeeds, float[] durations)
	{
		this.turningSpeeds = turningSpeeds;
		this.durations = durations;
	}
	
	public void setPointer(int newPointer)
	{
		pointer = newPointer;
	}
	
	public void start()
	{
		isRunning = true;
		currentDuration = durations[pointer] / 2;
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	public Vector3f getTurn()
	{
		if (isRunning)
		{
			currentDuration += DisplayManager.getFrameTimeSeconds();
			if(currentDuration > durations[pointer]) 
			{
				next();
				return new Vector3f();
			}
			return (Vector3f)new Vector3f(turningSpeeds[pointer]).scale(DisplayManager.getFrameTimeSeconds());
		}
		else return new Vector3f();
	}
	
	private void next()
	{
		currentDuration = 0;
		pointer++;
		if(pointer >= turningSpeeds.length) pointer = 0;
	}
}
