package com.hk.artm.gui;

import com.hk.artm.Init;
import com.hk.artm.block.tile.InventoryConstructing;
import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.container.ContainerARTM;
import com.hk.artm.container.ContainerConstructing;
import com.hk.artm.container.ContainerLibrary;
import net.minecraft.block.Block;
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
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (ID == GUI_LIBRARY && tile instanceof TileLibrary)
		{
			return new ContainerLibrary(player.inventory, (TileLibrary) tile);
		}
		else if (ID == GUI_CONSTRUCTING_TABLE && block == Init.BLOCK_CONSTRUCTING_TABLE)
		{
			return new ContainerConstructing(player.inventory, pos);
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		if (ID == GUI_LIBRARY && tile instanceof TileLibrary)
		{
			return new GuiLibrary(player.inventory, (TileLibrary) tile);
		}
		else if (ID == GUI_CONSTRUCTING_TABLE && block == Init.BLOCK_CONSTRUCTING_TABLE)
		{
			return new GuiConstructing(player.inventory, pos);
		}
		return null;
	}
}
