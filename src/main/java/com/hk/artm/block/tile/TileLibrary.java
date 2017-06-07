package com.hk.artm.block.tile;

import com.hk.artm.ARTM;
import com.hk.artm.block.BlockLibrary;
import com.hk.artm.container.ICraftingContainer;
import com.hk.artm.container.SlotCrafted;
import com.hk.artm.item.ItemBlueprint;
import com.hk.artm.util.ARTMUtil;
import com.hk.artm.util.recipes.LibraryRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TileLibrary extends TileInventory implements ICraftingContainer, ITickable
{
	public boolean recheck = true;
	private EntityPlayer playerUsing = null;
	private LibraryRecipes.LibraryRecipe currRecipe;
	public int selectedRecipe = -1;

	public TileLibrary()
	{
		super(6);
	}

	@Override
	public void update()
	{
		if (recheck && playerUsing != null)
		{
			recheck = false;
			checkRecipe();
		}
	}

	private void checkRecipe()
	{
		this.currRecipe = null;
		boolean allEmpty = true;
		ItemStack[] stacks = new ItemStack[4];
		for (int i = 0; i < 4; i++)
		{
			stacks[i] = getStackInSlot(i).copy();
			if (!stacks[i].isEmpty())
			{
				allEmpty = false;
			}
		}
		if (!allEmpty)
		{
			this.currRecipe = LibraryRecipes.INSTANCE.getMatchingRecipe(stacks);
		}
		else if (selectedRecipe != -1)
		{
			this.currRecipe = LibraryRecipes.INSTANCE.getRecipeResult(playerUsing.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).learntItems.get(selectedRecipe));
		}

		if (currRecipe != null && hasPaper())
		{
			ItemStack stack = ItemBlueprint.getStackFor(currRecipe, playerUsing);
			if (!ItemStack.areItemStacksEqual(stack, getStackInSlot(5)))
			{
				setInventorySlotContents(5, stack);
			}
		}
		else
		{
			setInventorySlotContents(5, ItemStack.EMPTY);
		}
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		recheck = true;
	}

	public void tookCraft()
	{
		for (int i = 0; i < 4; i++)
		{
			setInventorySlotContents(i, ItemStack.EMPTY);
		}
		ItemStack stack = getStackInSlot(4);
		stack.setCount(stack.getCount() - 1);
		if (stack.getCount() == 0)
		{
			setInventorySlotContents(4, ItemStack.EMPTY);
		}
		playerUsing.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).addLearntItem(currRecipe.itemLearnt);
	}

	public boolean hasPaper()
	{
		ItemStack stack = getStackInSlot(4);
		if (stack.isEmpty())
		{
			return false;
		}
		return stack.getItem() == Items.PAPER;
	}

	@Override
	public String getName()
	{
		return "container.library.name";
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return super.isUsableByPlayer(player) && (playerUsing == null || playerUsing == player) && world.getBlockState(getPos()).getValue(BlockLibrary.MODE) != BlockLibrary.EnumType.DEFAULT;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		playerUsing = player;
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		playerUsing = null;
	}

	@Override
	public List<Slot> getSlots()
	{
		List<Slot> slots = new ArrayList<Slot>();

		addSlotSquare(this, slots, 0, 8, 18, 2, 2);
		slots.add(new Slot(this, 4, 8, 55));
		slots.add(new SlotCrafted(this, 5, 26, 55));

		return slots;
	}
}
