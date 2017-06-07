package com.hk.artm.container;

import com.hk.artm.ARTM;
import com.hk.artm.block.tile.TileLibrary;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class ContainerLibrary extends ContainerARTM
{
	private final TileLibrary tile;

	public ContainerLibrary(InventoryPlayer playerInv, TileLibrary tile)
	{
		super(playerInv, tile);
		tile.openInventory(playerInv.player);
		this.tile = tile;

		for (Slot slot : tile.getSlots())
		{
			addSlotToContainer(slot);
		}

		addPlayerSlots(playerInv, 8, 84);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (itemstack1.getItem() == Items.PAPER)
			{
				if (!this.mergeItemStack(itemstack1, 4, 5, true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index < 6)
			{
				if (!this.mergeItemStack(itemstack1, 6, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 4, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		tile.markDirty();

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		tile.closeInventory(playerIn);
	}
}
