package entity.medical;

import renderer.DisplayManager;

public class Heart
{
	protected float heartRate;
	protected final float calmPulse;
	protected final float heartStrength;
	
	public Heart(float calmPulse, float heartStrength)
	{
		this.heartRate = calmPulse;
		this.calmPulse = calmPulse;
		this.heartStrength = heartStrength;
	}
	
	public void updateHeartRate(float labor)
	{
		heartRate += (getTargetPulse(labor) - heartRate) * 0.05 * DisplayManager.getFrameTimeSeconds();
	}
	
	protected float getTargetPulse(float labor)
	{
		return calmPulse;
	}
	
	public float getHeartRate()
	{
		return heartRate;
	}
}
