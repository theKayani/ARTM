package com.hk.artm.gui.guide;

import net.minecraft.util.text.TextFormatting;

public class IntroPage extends GuidePage
{
	public IntroPage(GuiARTMGuide screen)
	{
		super(screen);
	}

	@Override
	public void drawPage(int mouseX, int mouseY)
	{
		parent.getFROBJ().drawSplitString("To begin with ARTM, you must collect three different materials. These materials are " +
				"" + TextFormatting.GOLD + "Copper" + TextFormatting.RESET + ", " + TextFormatting.GRAY + "Tin" + TextFormatting.RESET +
				", and " + TextFormatting.DARK_GREEN + "Nickel (or Ferrous)" + TextFormatting.RESET + ". These are the main metals of the mod " +
				"that'll be used in most recipes. Including alloys of these metals as well.", 3, 3, width - 3, 0xFF000000);
	}
}
