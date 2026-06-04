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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Consumer;

/**
 * In 1.21, ItemStack codec preserves NBT natively. This class is kept for
 * API compatibility but simply passes recipes through unchanged — the NBT
 * should already be set on the output ItemStack before calling save().
 */
public class NbtOutputResult {
	public static RecipeOutput with(RecipeOutput parent, Consumer<CompoundTag> tagSetup) {
		// NBT must be applied to the ItemStack before calling save(). This wrapper is a no-op passthrough.
		return parent;
	}
}
