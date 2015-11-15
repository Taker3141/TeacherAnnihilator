package renderer;

import java.awt.Toolkit;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
	private static final int width;
	private static final int height;
	private static final int maxFPS = 60;
	
	private static final boolean fullscreen = false;
	
	private static long lastFrameTime;
	private static float delta;
	private static int frameCounter = 0;
	
	public static void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		
		try
		{
			
			if (fullscreen)
			{
				DisplayMode displayMode = null;
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				for (int i = 0; i < modes.length; i++)
				{
					if (modes[i].getWidth() == width && modes[i].getHeight() == height && modes[i].isFullscreenCapable())
					{
						displayMode = modes[i];
					}
				}
				Display.setFullscreen(true);
				Display.setDisplayMode(displayMode);
			}
			else
			{
				Display.setDisplayMode(new DisplayMode(width, height));
			}
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
	
	public static void updateDisplay()
	{
		Display.sync(maxFPS);
		Display.update();
		frameCounter++;
		long currentFrameTime = getCurrentTime();
		delta = ((float) (currentFrameTime - lastFrameTime)) / 1000F;
		if (frameCounter % maxFPS == 0) Display.setTitle("Teacher Annihilator (" + (int) (1 / delta) + " FPS)");
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
	
	static
	{
		if (fullscreen)
		{
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		else
		{
			width = 1280;
			height = 720;
		}
	}
}
