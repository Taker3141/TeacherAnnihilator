package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import font.fontMeshCreator.FontType;
import font.fontRendering.TextMaster;
import gui.element.Button;
import gui.element.GuiElement;
import renderer.GuiRenderer;
import renderer.Loader;

public abstract class Menu
{
	protected Loader loader = new Loader();
	protected FontType font = new FontType(loader.loadTexture("font/roboto"), new File("res/font/roboto.fnt"));
	protected List<GuiElement> guiElements = new ArrayList<GuiElement>();
	protected List<GuiElement> guiElementsBackground = new ArrayList<GuiElement>();
	protected List<GuiElement> guiElementsForeground = new ArrayList<GuiElement>();
	protected GuiRenderer gRenderer = new GuiRenderer(loader);
	
	public Menu()
	{
		TextMaster.init(loader);
		Button.loadAllTextures(loader);
	}
	
	public abstract void doMenu();
	
	protected void render()
	{
		List<GuiElement> renderList = new ArrayList<GuiElement>();
		renderList.addAll(guiElementsForeground);
		renderList.addAll(guiElements);
		renderList.addAll(guiElementsBackground);
		gRenderer.render(renderList);
		TextMaster.render();
		Display.update();
	}
	
	protected void cleanUp()
	{
		TextMaster.cleanUp();
		gRenderer.cleanUp();
	}
}
