package com.hk.artm.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerARTM extends Container
{
	private final IInventory inv;

	public ContainerARTM(InventoryPlayer playerInv, IInventory inv)
	{
		this.inv = inv;
	}

	public void addPlayerSlots(InventoryPlayer playerInv, int x, int y)
	{
		for (int l = 0; l < 3; ++l)
		{
			for (int j1 = 0; j1 < 9; ++j1)
			{
				this.addSlotToContainer(new Slot(playerInv, j1 + l * 9 + 9, x + j1 * 18, y + l * 18));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1)
		{
			this.addSlotToContainer(new Slot(playerInv, i1, x + i1 * 18, y + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return inv.isUsableByPlayer(playerIn);
	}
}
