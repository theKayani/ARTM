package com.hk.artm.util.recipes;

import com.google.common.collect.ImmutableList;
import com.hk.artm.ARTM;
import com.hk.artm.container.ICraftingContainer;
import com.hk.artm.item.ItemBlueprint;
import com.hk.artm.network.PacketPlayerPropSync;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Array;
import java.rmi.activation.ActivationGroup_Stub;
import java.util.*;

public class LibraryRecipes
{
	public static final LibraryRecipes INSTANCE = new LibraryRecipes();
	private final ImmutableList<LibraryRecipe> recipes;

	private LibraryRecipes()
	{
		List<LibraryRecipe> lst = new ArrayList<LibraryRecipe>();
		ARTM.log.info("Registering Library Recipes...");
		initializeRecipes(lst);
		ARTM.log.info("Registered " + lst.size() + " Library Recipes");
		recipes = ImmutableList.copyOf(lst);
	}

	private void initializeRecipes(List<LibraryRecipe> recipes)
	{
		recipes.add(new LibraryRecipe(Items.EMERALD, "gemDiamond", ARTMUtil.setAmoutFor(ARTMUtil.toStackList("ingotIron"), 3), null, null));
		recipes.add(new LibraryRecipe(Items.IRON_INGOT, new Object[]{new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE), "coal"}, "oreIron", null, null));
		recipes.add(new LibraryRecipe(Items.DIAMOND, Items.APPLE, "cropCarrot", null, null));
		recipes.add(new LibraryRecipe(Items.APPLE, Items.BEEF, "cropCarrot", "logWood", Blocks.OAK_FENCE));
		recipes.add(new LibraryRecipe(Items.CARROT, new Object[]{new ItemStack(Items.COAL, 64, OreDictionary.WILDCARD_VALUE), "coal"}, null, null, null));
		recipes.add(new LibraryRecipe(Items.COAL, "feather", null, null, null));
		recipes.add(new LibraryRecipe(Items.FEATHER, "stone", null, null, null));
	}

	public LibraryRecipe getMatchingRecipe(ItemStack[] stacks)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			LibraryRecipe recipe = recipes.get(i);
			if (recipe.matches(stacks))
			{
				return recipe;
			}
		}
		return null;
	}

	public LibraryRecipe getRecipeResult(Item item)
	{
		for (int i = 0; i < recipes.size(); i++)
		{
			LibraryRecipe recipe = recipes.get(i);
			if (recipe.itemLearnt == item)
			{
				return recipe;
			}
		}
		return null;
	}

	public List<LibraryRecipe> getRecipes()
	{
		return recipes;
	}

	public static void init()
	{
	}

	public static class LibraryRecipe implements BasicRecipe
	{
		public final Item itemLearnt;
		public final List<List<ItemStack>> stacks = NonNullList.withSize(4, (List<ItemStack>) new ArrayList<ItemStack>());
		public LibraryRecipe(Item itemLearnt, Object a, Object b, Object c, Object d)
		{
			this.itemLearnt = itemLearnt;
			this.stacks.set(0, ARTMUtil.toStackList(a));
			this.stacks.set(1, ARTMUtil.toStackList(b));
			this.stacks.set(2, ARTMUtil.toStackList(c));
			this.stacks.set(3, ARTMUtil.toStackList(d));
			checkAtLeastOne();
		}

		private void checkAtLeastOne()
		{
			for (int i = 0; i < 4; i++)
			{
				List<ItemStack> lst = stacks.get(i);
				if (!lst.isEmpty())
				{
					return;
				}
			}
			throw new IllegalArgumentException("No valid itemstacks");
		}

		public boolean matches(ItemStack[] items)
		{
			List<ItemStack> lst = new ArrayList<ItemStack>(Arrays.asList(items));
			label1:
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < Math.min(lst.size(), 4); j++)
				{
					ItemStack b = lst.get(j);
					if (contains(b, i))
					{
						lst.remove(j);
						continue label1;
					}
				}
			}
			return lst.isEmpty();
		}

		private boolean contains(ItemStack s, int indx)
		{
			List<ItemStack> lst = stacks.get(indx);
			if (s.isEmpty())
			{
				return lst.isEmpty();
			}
			for (int i = 0; i < lst.size(); i++)
			{
				ItemStack s1 = lst.get(i);
				if (s1.getCount() != s.getCount())
				{
					continue;
				}
				if (s1.getItemDamage() == OreDictionary.WILDCARD_VALUE && ItemStack.areItemsEqual(s1, s))
				{
					return true;
				}
				if (OreDictionary.itemMatches(s1, s, false))
				{
					return true;
				}
			}
			return false;
		}

		@Override
		public List<List<ItemStack>> getAllValidInputs()
		{
			return stacks;
		}

		@Override
		public List<ItemStack> getAllOutputs()
		{
			return Collections.singletonList(ItemBlueprint.getStackFor(this, null));
		}
	}
}
