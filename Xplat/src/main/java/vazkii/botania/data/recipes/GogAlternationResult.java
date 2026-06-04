/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import vazkii.botania.xplat.XplatAbstractions;

/**
 * Saves either the gog or base recipe variant depending on whether the
 * Garden of Glass is loaded. Both recipes must share the same ID (the base
 * recipe's ID is used as the canonical one).
 */
public class GogAlternationResult {
	private final Recipe<?> gogRecipe;
	private final Recipe<?> baseRecipe;
	private final ResourceLocation id;
	private final AdvancementHolder gogAdvancement;
	private final AdvancementHolder baseAdvancement;

	public GogAlternationResult(RecipeCapture gogCapture, RecipeCapture baseCapture) {
		this.gogRecipe = gogCapture.recipe;
		this.baseRecipe = baseCapture.recipe;
		this.id = baseCapture.id;
		this.gogAdvancement = gogCapture.advancement;
		this.baseAdvancement = baseCapture.advancement;
	}

	/** Save the appropriate variant to the output. */
	public void save(RecipeOutput output) {
		if (XplatAbstractions.INSTANCE.gogLoaded()) {
			output.accept(id, gogRecipe, gogAdvancement);
		} else {
			output.accept(id, baseRecipe, baseAdvancement);
		}
	}

	/**
	 * Helper to capture a recipe save. Use as:
	 * {@code RecipeCapture gog = new RecipeCapture(); builder.save(gog); }
	 */
	public static class RecipeCapture implements RecipeOutput {
		Recipe<?> recipe;
		ResourceLocation id;
		AdvancementHolder advancement;

		@Override
		public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement) {
			this.id = id;
			this.recipe = recipe;
			this.advancement = advancement;
		}

		@Override
		public net.minecraft.advancements.Advancement.Builder advancement() {
			return net.minecraft.advancements.Advancement.Builder.recipeAdvancement();
		}
	}
}
