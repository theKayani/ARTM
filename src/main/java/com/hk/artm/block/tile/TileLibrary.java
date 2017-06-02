package com.hk.artm.block.tile;

import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class TileLibrary extends TileInventory
{
	public TileLibrary()
	{
		super(1);
	}

	@Override
	public String getName()
	{
		return "container.library.name";
	}

	@Override
	public List<Slot> getSlots()
	{
		List<Slot> slots = new ArrayList<Slot>();

		slots.add(new Slot(this, 0, 8, 31));

		return slots;
	}
}
