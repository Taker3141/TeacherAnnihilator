package gui.handler;

import gui.Menu;

public class HandlerChangeMenu implements ClickHandler
{
	private Menu nextMenu;
	
	public HandlerChangeMenu(Menu next)
	{
		nextMenu = next;
	}
	
	@Override
	public void click(Menu parent)
	{
		parent.requestClose(nextMenu);
	}
}