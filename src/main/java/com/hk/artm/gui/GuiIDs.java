package com.hk.artm.gui;

import com.hk.artm.ARTM;
import net.minecraft.util.ResourceLocation;

public interface GuiIDs
{
	public static final int GUI_LIBRARY = 1 << 0;               // 0000000000000001
	public static final int GUI_CONSTRUCTING_TABLE = 1 << 1;    // 0000000000000010
	public static final ResourceLocation BUTTON_LOC = new ResourceLocation(ARTM.MODID + ":textures/gui/artm_widgets.png");
}
