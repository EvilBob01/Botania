/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.api.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * Adapter to allow Botania's Container-based custom recipes to work with the 1.21.1
 * RecipeInput system. Botania's custom recipes don't use the RecipeInput slot system
 * directly (they have their own matching logic), but the type bound requires RecipeInput.
 */
public class BotaniaContainer implements RecipeInput {
	private final Container container;

	public BotaniaContainer(Container container) {
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	@Override
	public ItemStack getItem(int slot) {
		return container.getItem(slot);
	}

	@Override
	public int size() {
		return container.getContainerSize();
	}
}
