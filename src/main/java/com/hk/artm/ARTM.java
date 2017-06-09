package com.hk.artm;

import com.hk.artm.gui.ARTMGuiHandler;
import com.hk.artm.network.ARTMNetwork;
import com.hk.artm.player.ARTMPlayerProperties;
import com.hk.artm.util.ARTMUtil;
import com.hk.artm.util.recipes.ConstructingTableRecipes;
import com.hk.artm.util.recipes.LibraryRecipes;
import mezz.jei.JeiHelpers;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.server.ServerEula;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
		GameRegistry.registerWorldGenerator(new ARTMWorldGen(), 5);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();

		LibraryRecipes.init();
		ConstructingTableRecipes.init();
	}

	public static final Logger log = LogManager.getLogger("ARTM");
	public static final Random rand = new Random();
	public static final DecimalFormat thousandFormat = new DecimalFormat("#,###");
}
