/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.PureDaisyRecipe;
import vazkii.botania.common.crafting.StateIngredientHelper;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class PureDaisyProvider extends BotaniaRecipeProvider {
	public PureDaisyProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {

		output.accept(id("livingrock"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.STONE), BotaniaBlocks.livingrock.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("livingwood"), new PureDaisyRecipe(StateIngredientHelper.of(BlockTags.LOGS), BotaniaBlocks.livingwoodLog.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()) {
			@Override
			public RecipeSerializer<?> getSerializer() {
				return BotaniaRecipeTypes.COPYING_PURE_DAISY_SERIALIZER;
			}
		}, null);

		output.accept(id("cobblestone"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.NETHERRACK), Blocks.COBBLESTONE.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("end_stone_to_cobbled_deepslate"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.END_STONE), Blocks.COBBLED_DEEPSLATE.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.of(prefix("ender_air_release"))), null);
		output.accept(id("sand"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.SOUL_SAND), Blocks.SAND.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("packed_ice"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.ICE), Blocks.PACKED_ICE.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("blue_ice"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.PACKED_ICE), Blocks.BLUE_ICE.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("obsidian"), new PureDaisyRecipe(StateIngredientHelper.of(BotaniaBlocks.blazeBlock), Blocks.OBSIDIAN.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
		output.accept(id("snow_block"), new PureDaisyRecipe(StateIngredientHelper.of(Blocks.WATER), Blocks.SNOW_BLOCK.defaultBlockState(), PureDaisyRecipe.DEFAULT_TIME, Optional.empty()), null);
	}

	@Override
	public String getName() {
		return "Botania Pure Daisy recipes";
	}

	private static ResourceLocation id(String path) {
		return prefix("pure_daisy/" + path);
	}
}
