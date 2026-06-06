/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public abstract class BotaniaRecipeProvider extends RecipeProvider {
	public BotaniaRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(packOutput, registries);
	}

	/** Convenience constructor for providers that don't need registry lookup */
	public BotaniaRecipeProvider(PackOutput packOutput) {
		this(packOutput, CompletableFuture.completedFuture(null));
	}

	@Override
	protected final void buildRecipes(HolderLookup.Provider pRegistries, RecipeOutput output) {
		buildRecipes(output);
	}

	public abstract void buildRecipes(RecipeOutput output);
}
