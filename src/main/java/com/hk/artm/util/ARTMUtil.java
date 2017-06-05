package com.hk.artm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ARTMUtil
{
	public static void sendMessage(EntityPlayer player, String message)
	{
		if (isServer())
		{
			player.sendMessage(new TextComponentTranslation(message));
		}
	}

	public static boolean isClient()
	{
		return getSide().isClient();
	}

	public static boolean isServer()
	{
		return getSide().isServer();
	}

	public static Side getSide()
	{
		return FMLCommonHandler.instance().getEffectiveSide();
	}
}
