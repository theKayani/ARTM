package com.hk.artm;

import com.hk.artm.gui.ARTMGuiHandler;
import com.hk.artm.network.ARTMNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = ARTM.MODID, version = ARTM.VERSION)
public class ARTM
{
	public static final String MODID = "artm";
	public static final String VERSION = "1.0.0";
	@SidedProxy(modId = MODID, serverSide = "com.hk.artm.CommonARTMProxy", clientSide = "com.hk.artm.ClientARTMProxy")
	public static CommonARTMProxy proxy;
	@Mod.Instance
	public static ARTM instance;
	@CapabilityInject(ARTMPlayerProperties.class)
	public static Capability<ARTMPlayerProperties> ARTM_PLAYER_PROPERTIES = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ARTMNetwork.INSTANCE.preInit();
		proxy.preInit();
		Init.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new ARTMGuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		Init.addRecipes();
		ARTMPlayerProperties.register();
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
}
