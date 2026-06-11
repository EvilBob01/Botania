/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

public class WaterBottleMatchingRecipe extends ShapedRecipe {
	public static final RecipeSerializer<WaterBottleMatchingRecipe> SERIALIZER = new Serializer();

	public WaterBottleMatchingRecipe(String group, CraftingBookCategory category, net.minecraft.world.item.crafting.ShapedRecipePattern pattern, ItemStack result) {
		super(group, category, pattern, result);
	}

	public WaterBottleMatchingRecipe(ShapedRecipe recipe) {
		this(recipe.getGroup(), recipe.category(), recipe.pattern,
				// XXX: Hacky, but compose should always be a vanilla shaped recipe which doesn't do anything with the
				// RegistryAccess
				recipe.getResultItem(RegistryAccess.EMPTY));
	}

	@Override
	public boolean matches(@NotNull CraftingInput craftingContainer, @NotNull Level level) {
		if (!super.matches(craftingContainer, level)) {
			return false;
		}
		for (int i = 0; i < craftingContainer.size(); i++) {
			var item = craftingContainer.getItem(i);
			if (item.is(Items.POTION)) {
				var contents = item.get(net.minecraft.core.component.DataComponents.POTION_CONTENTS);
				if (contents != null && !contents.is(Potions.WATER)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	private static class Serializer implements RecipeSerializer<WaterBottleMatchingRecipe> {
		private static final MapCodec<WaterBottleMatchingRecipe> CODEC =
				ShapedRecipe.Serializer.CODEC.xmap(
						WaterBottleMatchingRecipe::new,
						r -> r
				);

		private static final StreamCodec<RegistryFriendlyByteBuf, WaterBottleMatchingRecipe> STREAM_CODEC =
				ShapedRecipe.Serializer.STREAM_CODEC.map(
						WaterBottleMatchingRecipe::new,
						r -> r
				);

		@Override
		public MapCodec<WaterBottleMatchingRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, WaterBottleMatchingRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
