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

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.StateIngredient;

import java.util.Optional;

public class MarimorphosisRecipe extends OrechidRecipe {
	private final int weightBonus;
	private final TagKey<Biome> biomes;

	public MarimorphosisRecipe(StateIngredient input, StateIngredient output, int weight,
			Optional<ResourceLocation> successFunction,
			int weightBonus, TagKey<Biome> biomes) {
		super(input, output, weight, successFunction);
		this.weightBonus = weightBonus;
		this.biomes = biomes;
	}

	@Override
	public int getWeight(@NotNull Level level, @NotNull BlockPos pos) {
		if (level.getBiome(pos).is(this.biomes)) {
			return getWeight() + weightBonus;
		}
		return getWeight();
	}

	@NotNull
	@Override
	public RecipeType<? extends MarimorphosisRecipe> getType() {
		return BotaniaRecipeTypes.MARIMORPHOSIS_TYPE;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.MARIMORPHOSIS_SERIALIZER;
	}

	public int getWeightBonus() {
		return weightBonus;
	}

	public TagKey<Biome> getBiomes() {
		return biomes;
	}

	public static class Serializer implements RecipeSerializer<MarimorphosisRecipe> {
		public static final MapCodec<MarimorphosisRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				StateIngredientHelper.CODEC.fieldOf("input").forGetter(r -> r.getInput()),
				StateIngredientHelper.CODEC.fieldOf("output").forGetter(r -> r.getOutput()),
				Codec.INT.fieldOf("weight").forGetter(r -> r.getWeight()),
				ResourceLocation.CODEC.optionalFieldOf("success_function").forGetter(r -> Optional.empty()),
				ResourceLocation.CODEC.fieldOf("biome_bonus_tag").xmap(
						id -> TagKey.create(Registries.BIOME, id),
						TagKey::location
				).forGetter(r -> r.biomes),
				Codec.INT.optionalFieldOf("biome_bonus", 0).forGetter(r -> r.weightBonus)
		).apply(inst, (input, output, weight, funcId, biomes, bonus) ->
				new MarimorphosisRecipe(input, output, weight, funcId, bonus, biomes)));

		public static final StreamCodec<RegistryFriendlyByteBuf, MarimorphosisRecipe> STREAM_CODEC =
				StreamCodec.composite(
						OrechidRecipe.Serializer.STREAM_CODEC, r -> r,
						ResourceLocation.STREAM_CODEC.map(
								id -> TagKey.<Biome>create(Registries.BIOME, id),
								TagKey::location),
						r -> r.biomes,
						ByteBufCodecs.VAR_INT, r -> r.weightBonus,
						(base, biomes, bonus) -> new MarimorphosisRecipe(
								base.getInput(), base.getOutput(), base.getWeight(),
								base.getSuccessFunction(), bonus, biomes));

		@NotNull
		@Override
		public MapCodec<MarimorphosisRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, MarimorphosisRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
