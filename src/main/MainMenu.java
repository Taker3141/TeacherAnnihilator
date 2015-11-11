package main;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Input;
import renderer.GuiRenderer;
import renderer.Loader;
import font.fontMeshCreator.FontType;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.Button;
import gui.element.GuiElement;
import gui.handler.MouseHandler;

public class MainMenu
{	
	public static void doMainMenu(Loader loader)
	{
		TextMaster.init(loader);
		FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
		GUIText text = new GUIText("label.isengart", 2, font, new Vector2f(200, 720), 1F, false);
		text.setColour(0, 1, 0);
		Button.loadAllTextures(loader);
		
		List<GuiElement> guiElements = new ArrayList<GuiElement>();
		guiElements.add(new Button(new Vector2f(200, 500), new Vector2f(256, 128)).setText("button.fuck", font, 1.5F, 1, 0, 0));
		guiElements.add(new Button(new Vector2f(700, 200), new Vector2f(256, 128)).setText("button.button", font, 1, 1, 0.7F, 0));
		guiElements.add(new Button(new Vector2f(20, 20), new Vector2f(64, 64)));
		GuiRenderer gRenderer = new GuiRenderer(loader);
		Input input = new Input(Display.getHeight());
		MouseHandler mouse = new MouseHandler(guiElements);
		input.addMouseListener(mouse);
		mouse.setInput(input);
		
		while(!Display.isCloseRequested())
		{
			input.poll(Display.getWidth(), Display.getHeight());
			gRenderer.render(guiElements);
			TextMaster.render();
			Display.update();
		}
		
		TextMaster.cleanUp();
		gRenderer.cleanUp();
	}
}
