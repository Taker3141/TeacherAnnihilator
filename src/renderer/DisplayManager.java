package renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
	private static final int standardWidth = 1280;
	private static final int standardHeight = 720;
	private static final int maxFPS = 60;
	
	private static long lastFrameTime;
	private static float delta;
	private static int frameCounter = 0;
	
	public static void createDisplay(int width, int height, boolean fullscreen)
	{
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		
		try
		{
			recreateDisplay(width, height, fullscreen);
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Teacher Annihilator");
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
	}
	
	public static void recreateDisplay(int width, int height, boolean fullscreen)
	{
		try
		{
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			DisplayMode mode = null;
			for (int i = 0; i < modes.length; i++)
			{
				if(modes[i].getWidth() == width && modes[i].getHeight() == height)
				{
					mode = modes[i];
					break;
				}
			}
			if (mode != null) Display.setDisplayMode(mode);
			else Display.setDisplayMode(new DisplayMode(standardWidth, standardHeight));
			Display.setFullscreen(fullscreen);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateDisplay()
	{
		Display.sync(maxFPS);
		Display.update();
		frameCounter++;
		long currentFrameTime = getCurrentTime();
		delta = ((float) (currentFrameTime - lastFrameTime)) / 1000F;
		if (frameCounter % 10 == 0) Display.setTitle("Teacher Annihilator (" + (int) (1 / delta) + " FPS)");
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds()
	{
		return delta;
	}
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
}
