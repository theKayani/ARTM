package com.hk.artm;

import com.hk.artm.gui.guide.GuiARTMGuide;
import net.minecraft.client.Minecraft;

@SuppressWarnings("unused")
public class ClientARTMProxy extends CommonARTMProxy
{
	public void preInit()
	{
	}

	public void init()
	{
		Init.registerRenders();
	}

	public void postInit()
	{
	}

	public void openARTMGuide()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiARTMGuide(Minecraft.getMinecraft().player));
	}
}
