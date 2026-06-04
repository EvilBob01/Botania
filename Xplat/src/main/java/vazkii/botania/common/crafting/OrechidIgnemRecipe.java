/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.StateIngredient;

import java.util.Optional;

public class OrechidIgnemRecipe extends OrechidRecipe {
	public OrechidIgnemRecipe(StateIngredient input, StateIngredient output, int weight, Optional<ResourceLocation> successFunction) {
		super(input, output, weight, successFunction);
	}

	private OrechidIgnemRecipe(OrechidRecipe recipe) {
		this(recipe.getInput(), recipe.getOutput(), recipe.getWeight(), recipe.getSuccessFunction());
	}

	@NotNull
	@Override
	public RecipeType<? extends OrechidIgnemRecipe> getType() {
		return BotaniaRecipeTypes.ORECHID_IGNEM_TYPE;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.ORECHID_IGNEM_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<OrechidIgnemRecipe> {
		public static final MapCodec<OrechidIgnemRecipe> CODEC =
				OrechidRecipe.Serializer.CODEC.xmap(
						OrechidIgnemRecipe::new,
						r -> r
				);

		public static final StreamCodec<RegistryFriendlyByteBuf, OrechidIgnemRecipe> STREAM_CODEC =
				OrechidRecipe.Serializer.STREAM_CODEC.map(
						OrechidIgnemRecipe::new,
						r -> r
				);

		@NotNull
		@Override
		public MapCodec<OrechidIgnemRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, OrechidIgnemRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
