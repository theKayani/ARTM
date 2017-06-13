package com.hk.artm.gui.guide;

import net.minecraft.util.text.TextFormatting;

public class InfoPage extends GuidePage
{
	private final String[] txt;

	public InfoPage(GuiARTMGuide screen, String... txt)
	{
		super(screen);
		this.txt = txt;
	}

	@Override
	public void drawPage(int mouseX, int mouseY)
	{
		int y = 3;
		for (int i = 0; i < txt.length; i++)
		{
			parent.getFROBJ().drawSplitString(txt[i], 3, y, width - 3, 0xFF000000);
			y += parent.getFROBJ().getWordWrappedHeight(txt[i], width - 3) + 10;
		}
	}
}
