package com.hk.artm.network;

import com.hk.artm.ARTM;
import com.hk.artm.block.tile.TileLibrary;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class PacketLibraryRecipePicked extends ARTMNetwork.ARTMPacket
{
	private BlockPos pos;
	private int selectedRecipe;

	public PacketLibraryRecipePicked()
	{
	}

	public PacketLibraryRecipePicked(BlockPos pos, int selectedRecipe)
	{
		this.pos = pos;
		this.selectedRecipe = selectedRecipe;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setLong("Pos", pos.toLong());
		tag.setInteger("SelectedRecipe", selectedRecipe);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		pos = BlockPos.fromLong(tag.getLong("Pos"));
		selectedRecipe = tag.getInteger("SelectedRecipe");
	}

	@Override
	public void onServerReceive(EntityPlayerMP player)
	{
		super.onServerReceive(player);
		TileEntity tile = player.getEntityWorld().getTileEntity(pos);
		if (tile instanceof TileLibrary)
		{
			((TileLibrary) tile).selectedRecipe = selectedRecipe;
			((TileLibrary) tile).recheck = true;
		}
		else
		{
			ARTM.log.error("TileLibrary expected, not actually there...");
		}
	}
}
