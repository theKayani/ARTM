package com.hk.artm.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public abstract class TileInventory extends TileEntity implements IInventory
{
	protected final List<ItemStack> items;

	public TileInventory(int size)
	{
		items = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getSizeInventory()
	{
		return items.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < items.size(); i++)
		{
			if (!items.get(i).isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		items.set(index, stack);
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
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
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

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, (NonNullList<ItemStack>) items);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound tag = super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, (NonNullList<ItemStack>) items);
		return tag;
	}

	@Override
	public abstract String getName();

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

	public abstract List<Slot> getSlots();

	public static void addSlotSquare(IInventory inv, List<Slot> slots, int idStart, int x, int y, int rows, int cols)
	{
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				slots.add(new Slot(inv, idStart + j + i * cols, x + j * 18, y + i * 18));
			}
		}
	}
}
