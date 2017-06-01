package com.hk.artm;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeARTMTabs extends CreativeTabs
{
	public CreativeARTMTabs()
	{
		super(ARTM.MODID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return new ItemStack(Init.ITEM_ARTM_GUIDE);
	}
}
