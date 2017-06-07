package com.hk.artm.util.recipes;

import com.google.common.collect.ImmutableList;
import com.hk.artm.ARTM;
import com.hk.artm.container.ICraftingContainer;
import com.hk.artm.network.PacketPlayerPropSync;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
		recipes.add(new LibraryRecipe(Items.EMERALD, Items.DIAMOND, new ItemStack(Items.IRON_INGOT, 3), null, null));
		recipes.add(new LibraryRecipe(Items.IRON_INGOT, Items.COAL, Blocks.IRON_ORE, null, null));
		recipes.add(new LibraryRecipe(Items.DIAMOND, Items.APPLE, Items.CARROT, null, null));
		recipes.add(new LibraryRecipe(Items.APPLE, Items.BEEF, Items.CARROT, Blocks.LOG, Blocks.OAK_FENCE));
		recipes.add(new LibraryRecipe(Items.CARROT, new ItemStack(Items.COAL, 64), null, null, null));
		recipes.add(new LibraryRecipe(Items.COAL, Items.FEATHER, null, null, null));
		recipes.add(new LibraryRecipe(Items.FEATHER, Blocks.STONE, null, null, null));
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

	public static class LibraryRecipe
	{
		public final Item itemLearnt;
		public final ItemStack[] stacks = new ItemStack[4];

		public LibraryRecipe(Item itemLearnt, ItemStack a, ItemStack b, ItemStack c, ItemStack d)
		{
			this.itemLearnt = itemLearnt;
			stacks[0] = a;
			stacks[1] = b;
			stacks[2] = c;
			stacks[3] = d;
			checkAtLeastOne();
		}

		public LibraryRecipe(Item itemLearnt, ItemStack[] stacks)
		{
			this.itemLearnt = itemLearnt;
			System.arraycopy(stacks, 0, this.stacks, 0, 4);
			checkAtLeastOne();
		}

		public LibraryRecipe(Item itemLearnt, List<ItemStack> stacks)
		{
			this(itemLearnt, stacks, 0);
		}

		public LibraryRecipe(Item itemLearnt, List<ItemStack> stacks, int offset)
		{
			this.itemLearnt = itemLearnt;
			for (int i = offset; i < offset + Math.min(4, stacks.size()); i++)
			{
				this.stacks[i - offset] = stacks.get(i);
			}
			checkAtLeastOne();
		}

		public LibraryRecipe(Item itemLearnt, Object a, Object b, Object c, Object d)
		{
			this.itemLearnt = itemLearnt;
			this.stacks[0] = a == null ? null : (a instanceof Block ? new ItemStack((Block) a) : (a instanceof ItemStack ? (ItemStack) a : new ItemStack((Item) a)));
			this.stacks[1] = b == null ? null : (b instanceof Block ? new ItemStack((Block) b) : (b instanceof ItemStack ? (ItemStack) b : new ItemStack((Item) b)));
			this.stacks[2] = c == null ? null : (c instanceof Block ? new ItemStack((Block) c) : (c instanceof ItemStack ? (ItemStack) c : new ItemStack((Item) c)));
			this.stacks[3] = d == null ? null : (d instanceof Block ? new ItemStack((Block) d) : (d instanceof ItemStack ? (ItemStack) d : new ItemStack((Item) d)));
			checkAtLeastOne();
		}

		private void checkAtLeastOne()
		{
			for (int i = 0; i < 4; i++)
			{
				if (stacks[i] == null)
				{
					stacks[i] = ItemStack.EMPTY;
				}
			}
			for (int i = 0; i < 4; i++)
			{
				if (!stacks[i].isEmpty())
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
					ItemStack a = stacks[i];
					ItemStack b = lst.get(j);
					if (ItemStack.areItemStacksEqual(a, b))
					{
						lst.remove(j);
						continue label1;
					}
				}
			}
			return lst.isEmpty();
		}
	}
}
