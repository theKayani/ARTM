package com.hk.artm;

import com.hk.artm.block.BlockARTM;
import com.hk.artm.block.BlockLibrary;
import com.hk.artm.block.tile.TileLibrary;
import com.hk.artm.item.ItemARTM;
import com.hk.artm.item.ItemARTMGuide;
import com.hk.artm.item.ItemBlueprint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("unused")
public class Init
{
	public static final CreativeTabs artmTab = new CreativeARTMTabs();

	// ITEMS
	public static final ItemARTMGuide ITEM_ARTM_GUIDE = new ItemARTMGuide();
	public static final ItemBlueprint ITEM_BLUEPRINT = new ItemBlueprint();
	public static final ItemARTM ITEM_TIN_INGOT = new ItemARTM("tin_ingot");
	public static final ItemARTM ITEM_COPPER_INGOT = new ItemARTM("copper_ingot");
	public static final ItemARTM ITEM_NICKEL_INGOT = new ItemARTM("nickel_ingot");
	public static final ItemARTM ITEM_TIN_DUST = new ItemARTM("tin_dust");
	public static final ItemARTM ITEM_COPPER_DUST = new ItemARTM("copper_dust");
	public static final ItemARTM ITEM_NICKEL_DUST = new ItemARTM("nickel_dust");

	// BLOCKS
	public static final BlockLibrary BLOCK_LIBRARY = new BlockLibrary();
	public static final BlockARTM BLOCK_TIN_ORE = new BlockARTM("tin_ore", Material.ROCK, 2.0F);
	public static final BlockARTM BLOCK_COPPER_ORE = new BlockARTM("copper_ore", Material.ROCK, 3.0F);
	public static final BlockARTM BLOCK_NICKEL_ORE = new BlockARTM("nickel_ore", Material.ROCK, 3.0F);

	public static void register()
	{
		register(ITEM_ARTM_GUIDE);
		register(ITEM_BLUEPRINT);
		register(ITEM_TIN_INGOT, "ingotTin");
		register(ITEM_COPPER_INGOT, "ingotCopper");
		register(ITEM_NICKEL_INGOT, "ingotNickel", "ingotFerrous");
		register(ITEM_TIN_DUST, "ingotTin");
		register(ITEM_COPPER_DUST, "ingotCopper");
		register(ITEM_NICKEL_DUST, "ingotNickel", "ingotFerrous");

		register(BLOCK_LIBRARY);
		register(BLOCK_TIN_ORE, "oreTin");
		register(BLOCK_COPPER_ORE, "oreCopper");
		register(BLOCK_NICKEL_ORE, "oreNickel", "oreFerrous");

		registerTile(TileLibrary.class);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRender(ITEM_ARTM_GUIDE);
		registerRender(ITEM_BLUEPRINT);
		registerRender(ITEM_TIN_INGOT);
		registerRender(ITEM_COPPER_INGOT);
		registerRender(ITEM_NICKEL_INGOT);
		registerRender(ITEM_TIN_DUST);
		registerRender(ITEM_COPPER_DUST);
		registerRender(ITEM_NICKEL_DUST);

		registerRender(BLOCK_LIBRARY);
		registerRender(BLOCK_TIN_ORE);
		registerRender(BLOCK_COPPER_ORE);
		registerRender(BLOCK_NICKEL_ORE);
	}

	public static void addRecipes()
	{
		GameRegistry.addSmelting(BLOCK_TIN_ORE, new ItemStack(ITEM_TIN_INGOT), 0.6F);
		GameRegistry.addSmelting(BLOCK_COPPER_ORE, new ItemStack(ITEM_COPPER_INGOT), 0.5F);
		GameRegistry.addSmelting(BLOCK_NICKEL_ORE, new ItemStack(ITEM_NICKEL_INGOT), 0.7F);

		registerRecipe(BLOCK_LIBRARY, "#B#", "#C#", "###", '#', Blocks.PLANKS, 'B', Items.BOOK, 'C', ITEM_COPPER_INGOT);
		registerRecipe(ITEM_ARTM_GUIDE, " S ", "SPS", " S ", 'S', Blocks.STONE, 'P', Items.PAPER);
	}

	private static void registerRecipe(Item item, Object... objects)
	{
		registerRecipe(new ItemStack(item), objects);
	}

	private static void registerRecipe(Block block, Object... objects)
	{
		registerRecipe(new ItemStack(block), objects);
	}

	private static void registerRecipe(ItemStack itemStack, Object... objects)
	{
		GameRegistry.addShapedRecipe(itemStack, objects);
	}

	private static void registerTile(Class<? extends TileEntity> cls)
	{
		GameRegistry.registerTileEntity(cls, cls.getName());
	}

	private static void register(Object obj, String... oreDictNames)
	{
		if (obj instanceof Block)
		{
			Block block = (Block) obj;
			GameRegistry.register(block);
			GameRegistry.register(new ItemBlock(block), block.getRegistryName());
			for (int i = 0; i < oreDictNames.length; i++)
			{
				OreDictionary.registerOre(oreDictNames[i], block);
			}
		}
		else if (obj instanceof Item)
		{
			GameRegistry.register((Item) obj);
			for (int i = 0; i < oreDictNames.length; i++)
			{
				OreDictionary.registerOre(oreDictNames[i], (Item) obj);
			}
		}
		else
		{
			throw new IllegalArgumentException(obj + " isn't a block or item");
		}
	}

	private static void register(Block block, ItemBlock itemBlock, String... oreDictNames)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock, block.getRegistryName());
		for (int i = 0; i < oreDictNames.length; i++)
		{
			OreDictionary.registerOre(oreDictNames[i], block);
		}
	}

	private static void registerRender(Object obj)
	{
		registerRender(obj, 1);
	}

	private static void registerRender(Object obj, int subtypes)
	{
		if (obj instanceof Block)
		{
			Block block = (Block) obj;
			final Item item = Item.getItemFromBlock(block);
			for (int i = 0; i < subtypes; i++)
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		}
		else if (obj instanceof Item)
		{
			final Item item = (Item) obj;
			for (int i = 0; i < subtypes; i++)
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		}
		else
		{
			throw new IllegalArgumentException(obj + " isn't a block or item");
		}
	}
}
