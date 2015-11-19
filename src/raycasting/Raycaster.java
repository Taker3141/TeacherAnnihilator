package raycasting;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;
import renderer.MasterRenderer;
import toolbox.Maths;
import entity.*;

public class Raycaster
{
	public List<Collidable> list = new ArrayList<Collidable>();
	private static final float step = 1F;
	
	public void castRay(int MouseX, int MouseY, MasterRenderer master, Camera c)
	{
		Vector4f device = new Vector4f((((float)MouseX) / Display.getWidth() * 2) - 1, (((float)MouseY) / Display.getHeight() * 2) - 1, -1, 1);
		Matrix4f inverseProjectionMatrix = Matrix4f.invert(master.getProjectionMatrix(), null);
		Vector4f rayEye = Matrix4f.transform(inverseProjectionMatrix, device, null); rayEye.z = -1; rayEye.w = 0;
		Matrix4f inverseViewMatrix = Matrix4f.invert(Maths.createViewMatrix(c), null);
		Vector4f rayWorld4 = Matrix4f.transform(inverseViewMatrix, rayEye, null);
		Vector3f rayWorld = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z);
		rayWorld = rayWorld.normalise(rayWorld);
		
		Vector3f rayStep = new Vector3f(rayWorld.x * step, rayWorld.y * step, rayWorld.z * step);
		Vector3f vec = new Vector3f(c.getPosition());
		for(int i = 0; i < 100; i++)
		{
			vec = Vector3f.add(vec, rayStep, vec);
			for(Collidable e : list)
			{
				if(e.isInsideHitBox(vec)) e.hover();
			}
		}
	}
}
