package com.hk.artm.util;

import com.google.common.collect.Iterables;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Array;
import java.util.*;

public class ARTMUtil
{
	public static List<ItemStack> setAmoutFor(List<ItemStack> lst, int amt)
	{
		for (ItemStack stack : lst)
		{
			if (!stack.isEmpty())
			{
				stack.setCount(amt);
			}
		}
		return lst;
	}

	public static List<ItemStack> toStackList(Object... objs)
	{
		List<ItemStack> lst = new ArrayList<>();
		for (Object obj : objs)
		{
			if (obj == null)
			{
				continue;
			}
			else if (obj instanceof ItemStack)
			{
				lst.add((ItemStack) obj);
			}
			else if (obj instanceof Item)
			{
				lst.add(new ItemStack((Item) obj));
			}
			else if (obj instanceof Block)
			{
				lst.add(new ItemStack((Block) obj));
			}
			else if (obj instanceof String)
			{
				lst.addAll(OreDictionary.getOres((String) obj));
			}
			else if (obj instanceof ItemStack[])
			{
				Collections.addAll(lst, (ItemStack[]) obj);
			}
			else if (obj.getClass().isArray())
			{
				for (int i = 0; i < Array.getLength(obj); i++)
				{
					lst.addAll(toStackList(Array.get(obj, i)));
				}
			}
			else if (obj instanceof Collection<?>)
			{
				lst.addAll(toStackList(((Collection<?>) obj).toArray()));
			}
		}
		return lst;
	}

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
