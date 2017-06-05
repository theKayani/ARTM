package com.hk.artm.player;

import com.hk.artm.ARTM;
import com.hk.artm.network.ARTMNetwork;
import com.hk.artm.network.PacketPlayerPropSync;
import com.hk.artm.util.ARTMUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class ARTMPlayerProperties implements ICapabilitySerializable<NBTTagCompound>
{
	public static final String CAP_ID = ARTM.MODID + ":player_cap";
	public int randomNum;
	private EntityPlayer player;

	public ARTMPlayerProperties()
	{
		randomNum = (int) (Math.random() * 1000);
	}

	public void setPlayer(EntityPlayer player)
	{
		if (this.player == null)
		{
			this.player = player;
		}
	}

	public void sync()
	{
		if (ARTMUtil.isServer())
		{
//			ARTMNetwork.INSTANCE.sendTo(new PacketPlayerPropSync(this), (EntityPlayerMP) player);
			ARTMNetwork.INSTANCE.sendToAll(new PacketPlayerPropSync(this));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability != null && ARTM.ARTM_PLAYER_PROPERTIES == capability;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (hasCapability(capability, facing))
		{
			return (T) this;
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("Schtuff", randomNum);

		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag)
	{
		randomNum = tag.getInteger("Schtuff");
	}

	public static void register()
	{
		CapabilityManager.INSTANCE.register(ARTMPlayerProperties.class, new PPStorage(), ARTMPlayerProperties.class);
	}

	private static class PPStorage implements Capability.IStorage<ARTMPlayerProperties>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ARTMPlayerProperties> capability, ARTMPlayerProperties instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<ARTMPlayerProperties> capability, ARTMPlayerProperties instance, EnumFacing side, NBTBase nbt)
		{
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}
}
