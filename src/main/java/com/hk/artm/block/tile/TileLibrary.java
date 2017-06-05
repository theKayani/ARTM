package com.hk.artm.block.tile;

import com.hk.artm.ARTM;
import com.hk.artm.block.BlockLibrary;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class TileLibrary extends TileInventory
{
	private boolean isUsed = false;

	public TileLibrary()
	{
		super(4);
	}

	public void markDirty()
	{
		super.markDirty();
		ARTM.log.info("MARKED DIRTY");
	}

	@Override
	public String getName()
	{
		return "container.library.name";
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return super.isUsableByPlayer(player) && !isUsed && world.getBlockState(getPos()).getValue(BlockLibrary.MODE) != BlockLibrary.EnumType.DEFAULT;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		isUsed = true;
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		isUsed = false;
	}

	@Override
	public List<Slot> getSlots()
	{
		List<Slot> slots = new ArrayList<Slot>();

		addSlotSquare(this, slots, 0, 8, 18, 2, 2);

		return slots;
	}
}
