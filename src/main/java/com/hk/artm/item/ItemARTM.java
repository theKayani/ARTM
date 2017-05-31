package com.hk.artm.item;

import com.hk.artm.Init;
import net.minecraft.item.Item;

public class ItemARTM extends Item
{
	public ItemARTM(String name)
	{
		setName(this, name);
		setCreativeTab(Init.artmTab);
	}

	private static void setName(Item item, String name)
	{
		item.setRegistryName(name);
		item.setUnlocalizedName(name);
	}
}
