package entity.animation;

import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;

public class Animation
{
	private float startTime = 0;
	private Vector3f[] turningSpeeds;
	private float[] durations;
	private int pointer = 0;
	private boolean isRunning = false;
	private boolean resetFlag = false;
	private boolean loop;
	
	public Animation(Vector3f[] turningSpeeds, float[] durations)
	{
		this(turningSpeeds, durations, true);
	}
	
	public Animation(Vector3f[] turningSpeeds, float[] durations, boolean loop)
	{
		this.turningSpeeds = turningSpeeds;
		this.durations = durations;
		this.loop = loop;
	}
	
	public void start()
	{
		isRunning = true;
		startTime = DisplayManager.getTime();
		pointer = 0;
	}
	
	public Vector3f getTurn()
	{
		Vector3f ret = new Vector3f();
		if (isRunning)
		{
			ret =  (Vector3f)new Vector3f(turningSpeeds[pointer]).scale(DisplayManager.getFrameTimeSeconds() / durations[pointer]);
		}
		if(DisplayManager.getTime() - startTime > durations[pointer]) next();
		return ret;
	}
	
	public boolean getReset()
	{
		if(resetFlag)
		{
			resetFlag = false;
			return true;
		}
		return false;
	}
	
	private void next()
	{
		startTime = DisplayManager.getTime();
		pointer++;
		if (pointer == turningSpeeds.length)
		{
			pointer = 0;
			resetFlag = true;
			if(!loop) 
			{
				isRunning = false;
				pointer = turningSpeeds.length - 1;
				resetFlag = false;
			}
		}	
	}
}
