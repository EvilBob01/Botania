/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.mixin;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.crafting.RecipeManager;

import org.spongepowered.asm.mixin.Mixin;

/**
 * Previously used to access internal RecipeManager methods; no longer needed in 1.21
 * since getAllRecipesFor is public API. Kept as an empty mixin to avoid Mixin config errors.
 */
@Mixin(RecipeManager.class)
public interface RecipeManagerAccessor {
}
