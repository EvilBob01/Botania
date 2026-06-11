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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.function.Function;

public class WrapperResult {
	/**
	 * Wraps a RecipeOutput so that any ShapedRecipe saved through it is
	 * replaced by a custom recipe constructed via the given wrapper function.
	 */
	public static RecipeOutput ofType(Function<ShapedRecipe, ? extends Recipe<?>> wrapper, RecipeOutput parent) {
		return new RecipeOutput() {
			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement) {
				if (recipe instanceof ShapedRecipe sr) {
					parent.accept(id, wrapper.apply(sr), advancement);
				} else {
					parent.accept(id, recipe, advancement);
				}
			}

			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
				if (recipe instanceof ShapedRecipe sr) {
					parent.accept(id, wrapper.apply(sr), advancement, conditions);
				} else {
					parent.accept(id, recipe, advancement, conditions);
				}
			}

			@Override
			public net.minecraft.advancements.Advancement.Builder advancement() {
				return parent.advancement();
			}
		};
	}

	/**
	 * Wraps a RecipeOutput so that any ShapelessRecipe saved through it is
	 * replaced by a custom recipe constructed via the given wrapper function.
	 */
	public static RecipeOutput ofShapelessType(Function<ShapelessRecipe, ? extends Recipe<?>> wrapper, RecipeOutput parent) {
		return new RecipeOutput() {
			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement) {
				if (recipe instanceof ShapelessRecipe sr) {
					parent.accept(id, wrapper.apply(sr), advancement);
				} else {
					parent.accept(id, recipe, advancement);
				}
			}

			@Override
			public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
				if (recipe instanceof ShapelessRecipe sr) {
					parent.accept(id, wrapper.apply(sr), advancement, conditions);
				} else {
					parent.accept(id, recipe, advancement, conditions);
				}
			}

			@Override
			public net.minecraft.advancements.Advancement.Builder advancement() {
				return parent.advancement();
			}
		};
	}
}
