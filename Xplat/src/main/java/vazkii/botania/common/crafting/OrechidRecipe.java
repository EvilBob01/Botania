/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.StateIngredient;

import java.util.Optional;

public class OrechidRecipe implements vazkii.botania.api.recipe.OrechidRecipe {
	private final StateIngredient input;
	private final StateIngredient output;
	private final int weight;
	private final Optional<ResourceLocation> successFunction;

	public OrechidRecipe(StateIngredient input, StateIngredient output, int weight, Optional<ResourceLocation> successFunction) {
		this.input = input;
		this.output = output;
		this.weight = weight;
		this.successFunction = successFunction;
	}

	@Override
	public StateIngredient getInput() {
		return input;
	}

	@Override
	public StateIngredient getOutput() {
		return output;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public Optional<ResourceLocation> getSuccessFunction() {
		return this.successFunction;
	}

	@NotNull
	@Override
	public RecipeType<? extends OrechidRecipe> getType() {
		return BotaniaRecipeTypes.ORECHID_TYPE;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.ORECHID_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<OrechidRecipe> {
		public static final MapCodec<OrechidRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				StateIngredientHelper.CODEC.fieldOf("input").forGetter(r -> r.input),
				StateIngredientHelper.CODEC.fieldOf("output").forGetter(r -> r.output),
				Codec.INT.fieldOf("weight").forGetter(r -> r.weight),
				ResourceLocation.CODEC.optionalFieldOf("success_function").forGetter(r -> r.successFunction)
		).apply(inst, (input, output, weight, funcId) ->
				new OrechidRecipe(input, output, weight, funcId)));

		public static final StreamCodec<RegistryFriendlyByteBuf, OrechidRecipe> STREAM_CODEC =
				StreamCodec.composite(
						StateIngredientHelper.STREAM_CODEC, r -> r.input,
						StateIngredientHelper.STREAM_CODEC, r -> r.output,
						ByteBufCodecs.VAR_INT, r -> r.weight,
						(input, output, weight) -> new OrechidRecipe(input, output, weight, Optional.empty()));

		@NotNull
		@Override
		public MapCodec<OrechidRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, OrechidRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
