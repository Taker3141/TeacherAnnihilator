package gui.menu;

import entity.Player;
import font.fontMeshCreator.GUIText;
import font.fontRendering.TextMaster;
import gui.element.GuiBar;
import gui.element.GuiElement;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;
import renderer.OverlayRenderer;

public class MenuOrgans extends Menu
{
	private final int X = 0;
	private final int Y = 0;
	private final int BAR_HEIGHT = 117;
	private final int BAR_WIDTH = 58;
	private float heart = 0;
	
	private Player p;
	
	private final GuiElement heart0 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_heart_0"), new Vector2f(0, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement heart1 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_heart_1"), new Vector2f(0, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement lung0 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_lung_0"), new Vector2f(80, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiElement lung1 = new GuiElement(loader.loadTexture("texture/gui/organs/icon_lung_1"), new Vector2f(80, 0), new Vector2f(64, 64), (Menu)this);
	private final GuiBar blood = new GuiBar(loader.loadTexture("texture/gui/organs/blood"), new Vector2f(3, 67), new Vector2f(58, 64), (Menu)this);
	private final GuiBar air = new GuiBar(loader.loadTexture("texture/gui/organs/air"), new Vector2f(83, 67), new Vector2f(58, 64), (Menu)this);
	
	@Override
	public final void doMenu()
	{
		
	}
	
	public final void doMenu(Player player)
	{
		p = player;
		gRenderer = new OverlayRenderer(loader);
		guiElementsBackground.add(new GuiElement(loader.loadTexture("texture/gui/organs/background"), new Vector2f(X, Y), new Vector2f(256, 256), (Menu)this));
		guiElementsForeground.add(new GuiElement(loader.loadTexture("texture/gui/organs/overlay"), new Vector2f(X, Y), new Vector2f(256, 256), (Menu)this));
	}
	
	@Override
	public void render()
	{
		List<GuiElement> renderList = new ArrayList<GuiElement>();
		heart += p.getHeartRate() / 60 * DisplayManager.getFrameTimeSeconds();
		System.out.println(heart);
		guiElements.clear();
		guiElements.add(heart % 1 < 0.8 ? heart0 : heart1);
		guiElements.add(DisplayManager.getTime() % p.getBreathTime() > p.getBreathTime() / 2 ? lung0 : lung1);
		blood.size.y = BAR_HEIGHT * p.getHeartRate() / 200;
		blood.height = blood.size.y / BAR_WIDTH;
		blood.offset += p.getHeartRate() / 200 * DisplayManager.getFrameTimeSeconds();
		air.size.y = 0.3F * BAR_HEIGHT / p.getBreathTime();
		air.height = air.size.y / BAR_WIDTH;
		air.offset = air.height * DisplayManager.getTime() % 1;
		guiElements.add(blood);
		guiElements.add(air);
		renderList.addAll(guiElementsForeground);
		renderList.addAll(guiElements);
		renderList.addAll(guiElementsBackground);
		gRenderer.render(renderList);
		TextMaster.clear();
		new GUIText("o!" + Float.toString(p.getHeartRate()).substring(0, 4) + " bpm", 0.8F, font, new Vector2f(2, 210), 1F, false);
		new GUIText("o!" + p.getBreathTime() + "s", 0.8F, font, new Vector2f(100, 210), 1F, false);
		TextMaster.render();
	}
}
