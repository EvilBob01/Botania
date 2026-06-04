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

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.NotNull;

/**
 * In 1.21, ItemStack codec preserves NBT natively. NbtOutputResult in data-gen
 * passes recipes through unchanged. This serializer is kept for registration
 * compatibility but is never actually invoked — NBT is included in the recipe's
 * output ItemStack codec directly.
 */
public class NbtOutputRecipe {
	public static final RecipeSerializer<Recipe<?>> SERIALIZER = new NbtOutputRecipe.Serializer();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static class Serializer implements RecipeSerializer<Recipe<?>> {
		private final MapCodec<Recipe<?>> codec = (MapCodec) MapCodec.unit(() -> {
			throw new IllegalStateException("NbtOutputRecipe codec should not be invoked directly");
		});
		private final StreamCodec<RegistryFriendlyByteBuf, Recipe<?>> streamCodec = StreamCodec.of(
				(buf, recipe) -> {
					throw new IllegalStateException("NbtOutputRecipe should not be sent over network");
				},
				buf -> {
					throw new IllegalStateException("NbtOutputRecipe should not be sent over network");
				}
		);

		@NotNull
		@Override
		public MapCodec<Recipe<?>> codec() {
			return codec;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, Recipe<?>> streamCodec() {
			return streamCodec;
		}
	}
}
