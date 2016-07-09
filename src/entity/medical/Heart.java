package entity.medical;

import entity.Player;
import renderer.DisplayManager;

public class Heart
{
	protected float heartRate;
	protected final float calmPulse;
	protected final float heartStrength;
	protected final Player p;
	
	public Heart(float calmPulse, float heartStrength, Player player)
	{
		this.heartRate = calmPulse;
		this.calmPulse = calmPulse;
		this.heartStrength = heartStrength;
		this.p = player;
	}
	
	public void updateHeartRate()
	{
		heartRate += (getTargetPulse() - heartRate) * 0.01 * DisplayManager.getFrameTimeSeconds();
	}
	
	protected float getTargetPulse()
	{
		float speed = p.v.length();
		float labor = (1 / heartStrength) * speed * speed;
		return calmPulse + labor;
	}
	
	public float getHeartRate()
	{
		return heartRate;
	}
}
