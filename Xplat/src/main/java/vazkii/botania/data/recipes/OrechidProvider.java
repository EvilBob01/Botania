/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.MarimorphosisRecipe;
import vazkii.botania.common.crafting.OrechidIgnemRecipe;
import vazkii.botania.common.crafting.OrechidRecipe;
import vazkii.botania.common.crafting.StateIngredientHelper;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class OrechidProvider extends BotaniaRecipeProvider {

	public OrechidProvider(PackOutput packOutput) {
		super(packOutput);
	}

	// TODO: We had an enormous amount of ores defined for mod compat.
	//       The old data needs to be completely revised.
	@Override
	public void buildRecipes(RecipeOutput output) {
		stone(output, Blocks.COAL_ORE, 67415);
		stone(output, Blocks.IRON_ORE, 29371);
		stone(output, Blocks.REDSTONE_ORE, 7654);
		stone(output, Blocks.COPPER_ORE, 7000);
		stone(output, Blocks.GOLD_ORE, 2647);
		stone(output, Blocks.EMERALD_ORE, 1239);
		stone(output, Blocks.LAPIS_ORE, 1079);
		stone(output, Blocks.DIAMOND_ORE, 883);

		deepslate(output, Blocks.DEEPSLATE_COAL_ORE, 75);
		deepslate(output, Blocks.DEEPSLATE_IRON_ORE, 250);
		deepslate(output, Blocks.DEEPSLATE_REDSTONE_ORE, 150);
		deepslate(output, Blocks.DEEPSLATE_COPPER_ORE, 75);
		deepslate(output, Blocks.DEEPSLATE_GOLD_ORE, 125);
		deepslate(output, Blocks.DEEPSLATE_EMERALD_ORE, 50);
		deepslate(output, Blocks.DEEPSLATE_LAPIS_ORE, 175);
		deepslate(output, Blocks.DEEPSLATE_DIAMOND_ORE, 100);

		netherrack(output, Blocks.NETHER_QUARTZ_ORE, 19600);
		netherrack(output, Blocks.NETHER_GOLD_ORE, 3635);
		netherrack(output, Blocks.ANCIENT_DEBRIS, 148);

		biomeStone(output, BotaniaBlocks.biomeStoneForest, BotaniaTags.Biomes.MARIMORPHOSIS_FOREST_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStonePlains, BotaniaTags.Biomes.MARIMORPHOSIS_PLAINS_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneMountain, BotaniaTags.Biomes.MARIMORPHOSIS_MOUNTAIN_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneFungal, BotaniaTags.Biomes.MARIMORPHOSIS_FUNGAL_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneSwamp, BotaniaTags.Biomes.MARIMORPHOSIS_SWAMP_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneDesert, BotaniaTags.Biomes.MARIMORPHOSIS_DESERT_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneTaiga, BotaniaTags.Biomes.MARIMORPHOSIS_TAIGA_BONUS);
		biomeStone(output, BotaniaBlocks.biomeStoneMesa, BotaniaTags.Biomes.MARIMORPHOSIS_MESA_BONUS);
	}

	protected ResourceLocation orechidId(Block b) {
		return prefix("orechid/" + BuiltInRegistries.BLOCK.getKey(b).getPath());
	}

	protected ResourceLocation ignemId(Block b) {
		return prefix("orechid_ignem/" + BuiltInRegistries.BLOCK.getKey(b).getPath());
	}

	protected ResourceLocation marimorphosisId(Block b) {
		return prefix("marimorphosis/" + BuiltInRegistries.BLOCK.getKey(b).getPath());
	}

	protected void stone(RecipeOutput output, Block b, int weight) {
		output.accept(orechidId(b), new OrechidRecipe(forBlock(Blocks.STONE), forBlock(b), weight, Optional.empty()), null);
	}

	protected void deepslate(RecipeOutput output, Block b, int weight) {
		output.accept(orechidId(b), new OrechidRecipe(forBlock(Blocks.DEEPSLATE), forBlock(b), weight, Optional.empty()), null);
	}

	protected void netherrack(RecipeOutput output, Block b, int weight) {
		output.accept(ignemId(b), new OrechidIgnemRecipe(forBlock(Blocks.NETHERRACK), forBlock(b), weight, Optional.empty()), null);
	}

	protected void biomeStone(RecipeOutput output, Block b, TagKey<Biome> biome) {
		output.accept(marimorphosisId(b), new MarimorphosisRecipe(forTag(BotaniaTags.Blocks.MARIMORPHOSIS_CONVERTABLE), forBlock(b), 1, Optional.empty(), 11, biome), null);
	}

	protected static StateIngredient forBlock(Block block) {
		return StateIngredientHelper.of(block);
	}

	protected static StateIngredient forTag(TagKey<Block> tag) {
		return StateIngredientHelper.of(tag);
	}

}

