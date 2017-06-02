package com.hk.artm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler
{
	@SubscribeEvent
	public void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		if (event.player != null)
		{
			event.player.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).sync();
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
	{
		if (!event.player.getEntityWorld().isRemote)
		{
			event.player.getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null).sync();
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event)
	{
		if (event.isWasDeath())
		{
			if (event.getOriginal().hasCapability(ARTM.ARTM_PLAYER_PROPERTIES, null))
			{
				ARTMPlayerProperties cap = event.getOriginal().getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null);
				ARTMPlayerProperties newCap = event.getEntityPlayer().getCapability(ARTM.ARTM_PLAYER_PROPERTIES, null);
				NBTTagCompound tag = cap.serializeNBT();
				newCap.deserializeNBT(tag);
			}
		}
	}

	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			ARTMPlayerProperties artmPlayerProperties = new ARTMPlayerProperties();
			event.addCapability(new ResourceLocation(ARTMPlayerProperties.CAP_ID), artmPlayerProperties);
			artmPlayerProperties.setPlayer((EntityPlayer) event.getEntity());
		}
	}
}
