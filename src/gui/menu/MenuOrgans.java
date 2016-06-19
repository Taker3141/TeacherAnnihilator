package gui.menu;

import font.fontRendering.TextMaster;
import gui.element.GuiElement;
import gui.element.Icon;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import renderer.OverlayRenderer;

public class MenuOrgans extends Menu
{
	private final int X = 0;
	private final int Y = 0;
	private float heartTime = 1;
	private float breathTime = 5;
	
	private final GuiElement heart0 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_heart_0"), new Vector2f(0, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement heart1 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_heart_1"), new Vector2f(0, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement lung0 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_lung_0"), new Vector2f(80, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement lung1 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_lung_1"), new Vector2f(80, 0), new Vector2f(64, 64), (Menu)this);
	
	@Override
	public final void doMenu()
	{
		gRenderer = new OverlayRenderer(loader);
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/organs/background"), new Vector2f(X, Y), new Vector2f(256, 256), (Menu)this));
	}
	
	@Override
	public void render()
	{
		List<GuiElement> renderList = new ArrayList<GuiElement>();
		guiElements.clear();
		guiElements.add(DisplayManager.getTime() % heartTime > heartTime / 5 ? heart0 : heart1);
		guiElements.add(DisplayManager.getTime() % breathTime > breathTime / 2 ? lung0 : lung1);
		renderList.addAll(guiElementsForeground);
		renderList.addAll(guiElements);
		renderList.addAll(guiElementsBackground);
		gRenderer.render(renderList);
		TextMaster.render();
	}
}
