package com.hk.artm.gui;

import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.container.ContainerLibrary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ARTMGuiHandler implements IGuiHandler, GuiIDs
{
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (ID == GUI_LIBRARY && tile instanceof TileLibrary)
		{
			return new ContainerLibrary(player.inventory, (TileLibrary) tile);
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (ID == GUI_LIBRARY && tile instanceof TileLibrary)
		{
			return new GuiLibrary(player.inventory, (TileLibrary) tile);
		}
		return null;
	}
}
