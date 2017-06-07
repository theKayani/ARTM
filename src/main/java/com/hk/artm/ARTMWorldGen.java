package com.hk.artm;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ARTMWorldGen implements IWorldGenerator
{
	private World world;
	private Random random;
	private int chunkX, chunkZ;
	private IChunkProvider chunkProvider;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		this.world = world;
		this.random = random;
		this.chunkX = chunkX * 16;
		this.chunkZ = chunkZ * 16;
		this.chunkProvider = chunkProvider;
		if (!(chunkProvider instanceof ChunkProviderEnd || chunkProvider instanceof ChunkProviderHell))
		{
			addOreSpawn(Init.BLOCK_TIN_ORE, 8, 9, 20, 55);
			addOreSpawn(Init.BLOCK_COPPER_ORE, 8, 12, 40, 75);
			addOreSpawn(Init.BLOCK_NICKEL_ORE, 4, 2, 0, 20);
		}
	}

	public void addOreSpawn(Block ore, int maxVeinSize, int spawnsPerChunks, int minY, int maxY)
	{
		if (spawnsPerChunks > 0)
		{
			for (int i = 0; i < spawnsPerChunks; i++)
			{
				int posX = chunkX + random.nextInt(16);
				int posY = minY + random.nextInt(maxY - minY);
				int posZ = chunkZ + random.nextInt(16);
				new WorldGenMinable(ore.getDefaultState(), maxVeinSize).generate(world, random, new BlockPos(posX, posY, posZ));
			}
		}
	}
}
