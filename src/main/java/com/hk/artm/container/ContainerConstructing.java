package com.hk.artm.container;

import com.hk.artm.Init;
import com.hk.artm.block.tile.InventoryConstructing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ContainerConstructing extends Container
{
	public final InventoryConstructing tile;
	public final InventoryPlayer playerInv;

	public ContainerConstructing(InventoryPlayer playerInv, BlockPos pos)
	{
		this.playerInv = playerInv;
		tile = new InventoryConstructing(this, pos);
		tile.openInventory(playerInv.player);

		addSlotToContainer(new Slot(tile, 0, 20, 35));
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				addSlotToContainer(new Slot(tile, 1 + j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}
		addSlotToContainer(new SlotCrafted(tile, 10, 143, 34));

		for (int l = 0; l < 3; ++l)
		{
			for (int j1 = 0; j1 < 9; ++j1)
			{
				this.addSlotToContainer(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1)
		{
			this.addSlotToContainer(new Slot(playerInv, i1, 8 + i1 * 18, 142));
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		super.onCraftMatrixChanged(inventoryIn);
		inventoryIn.markDirty();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tile.isUsableByPlayer(playerIn);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 10)
			{
				if (!this.mergeItemStack(itemstack1, 11, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index == 10)
			{
				while (true)
				{
					tile.tookCraft();
					if (!this.mergeItemStack(itemstack1, 11, inventorySlots.size(), true))
					{
						if (!tile.getStackInSlot(10).isEmpty())
						{
							continue;
						}
						return ItemStack.EMPTY;
					}
				}
			}
			else if (itemstack.getItem() == Init.ITEM_BLUEPRINT)
			{
				if (!this.mergeItemStack(itemstack1, 0, 1, true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 11 && index < 38)
			{
				if (!this.mergeItemStack(itemstack1, 38, 47, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 11, 38, true))
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
}
