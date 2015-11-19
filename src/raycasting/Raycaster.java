package raycasting;

import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;
import renderer.MasterRenderer;
import toolbox.Maths;
import entity.*;

public class Raycaster
{
	public List<Collidable> list;
	
	public Entity getCurrentEntity(int MouseX, int MouseY, MasterRenderer master, Camera c)
	{
		Vector4f device = new Vector4f((((float)MouseX) / Display.getWidth() * 2) - 1, (((float)MouseY) / Display.getHeight() * 2) - 1, -1, 1);
		Matrix4f inverseProjectionMatrix = null;
		inverseProjectionMatrix = Matrix4f.invert(master.getProjectionMatrix(), inverseProjectionMatrix);
		Vector4f rayEye = Matrix4f.transform(inverseProjectionMatrix, device, null);
		rayEye.z = -1;
		rayEye.w = 0;
		Matrix4f inverseViewMatrix = Matrix4f.invert(Maths.createViewMatrix(c), null);
		Vector4f rayWorld4 = Matrix4f.transform(inverseViewMatrix, rayEye, null);
		Vector3f rayWorld = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z);
		rayWorld = rayWorld.normalise(rayWorld);
		
		return null;
	}
}
