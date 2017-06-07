package com.hk.artm.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotCrafted extends Slot
{
	private final ICraftingContainer inv;

	public SlotCrafted(ICraftingContainer inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.inv = inventoryIn;
	}

	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
	{
		inv.tookCraft();
		return super.onTake(thePlayer, stack);
	}

	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
