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
 * In 1.21, GogAlternation is handled entirely in data generation (see GogAlternationResult).
 * This class is kept for serializer registration compatibility; the serializer is never actually invoked
 * because the GOG selection is made at data-gen time.
 */
public class GogAlternationRecipe {
	public static final RecipeSerializer<Recipe<?>> SERIALIZER = new Serializer();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static class Serializer implements RecipeSerializer<Recipe<?>> {
		private final MapCodec<Recipe<?>> codec = (MapCodec) MapCodec.unit(() -> {
			throw new IllegalStateException("GogAlternationRecipe codec should not be invoked at runtime");
		});
		private final StreamCodec<RegistryFriendlyByteBuf, Recipe<?>> streamCodec = StreamCodec.of(
				(buf, recipe) -> {
					throw new IllegalStateException("GogAlternationRecipe should not be sent over network");
				},
				buf -> {
					throw new IllegalStateException("GogAlternationRecipe should not be sent over network");
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
