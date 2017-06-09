package com.hk.artm.block.tile;

import com.hk.artm.Init;
import com.hk.artm.container.ContainerConstructing;
import com.hk.artm.container.ICraftingContainer;
import com.hk.artm.util.recipes.ConstructingTableRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class InventoryConstructing implements ICraftingContainer
{
	public final ContainerConstructing container;
	public final BlockPos pos;
	private final List<ItemStack> stacks = NonNullList.withSize(11, ItemStack.EMPTY);
	private final ItemStack[] grid = new ItemStack[9];

	public InventoryConstructing(ContainerConstructing container, BlockPos pos)
	{
		this.container = container;
		this.pos = pos;
	}

	@Override
	public int getSizeInventory()
	{
		return stacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (!getStackInSlot(i).isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(stacks, index, count);

		if (!itemstack.isEmpty())
		{
			container.onCraftMatrixChanged(this);
		}
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack itemstack = ItemStackHelper.getAndRemove(stacks, index);
		if (!itemstack.isEmpty())
		{
			container.onCraftMatrixChanged(this);
		}
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index > 0 && index < 10)
		{
			container.onCraftMatrixChanged(this);
		}
		stacks.set(index, stack);
		if (stack.getCount() > getInventoryStackLimit())
		{
			stack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		for (int i = 1; i < 10; i++)
		{
			grid[i - 1] = getStackInSlot(i);
		}
		setInventorySlotContents(10, ItemStack.EMPTY);

		ConstructingTableRecipes.TableRecipe recipe = ConstructingTableRecipes.INSTANCE.getMatchingRecipe(container.playerInv.player.world, grid);
		if (recipe != null)
		{
			ItemStack output = recipe.getRecipeOutput().copy();
			if (recipe.isVanilla)
			{
				setInventorySlotContents(10, output);
			}
			else
			{
				int itemID = itemID();
				if (itemID != -1 && !output.isEmpty() && Item.getIdFromItem(output.getItem()) == itemID)
				{
					setInventorySlotContents(10, output);
				}
			}
		}
	}

	private int itemID()
	{
		ItemStack stack = getStackInSlot(0);
		if (!stack.isEmpty() && stack.getItem() == Init.ITEM_BLUEPRINT)
		{
			return stack.getItemDamage();
		}
		return -1;
	}

	@Override
	public void tookCraft()
	{
		for (int i = 1; i < 10; i++)
		{
			getStackInSlot(i).setCount(getStackInSlot(i).getCount() - 1);
		}

		markDirty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return player.world.getBlockState(pos).getBlock() == Init.BLOCK_CONSTRUCTING_TABLE && player.getDistanceSqToCenter(pos) <= 16F;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		InventoryHelper.dropInventoryItems(player.getEntityWorld(), player, this);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index != 10;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{

	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < getSizeInventory(); i++)
		{
			setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	@Override
	public String getName()
	{
		return "container.constructing.name";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(getName());
	}
}
