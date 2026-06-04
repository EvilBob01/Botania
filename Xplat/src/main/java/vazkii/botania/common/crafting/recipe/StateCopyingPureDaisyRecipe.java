/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.PureDaisyRecipe;
import vazkii.botania.common.crafting.StateIngredientHelper;

import java.util.Optional;

/**
 * Recipe that copies state properties to the new block on crafting.
 */
public class StateCopyingPureDaisyRecipe extends PureDaisyRecipe {
	public StateCopyingPureDaisyRecipe(StateIngredient input, Block block, int time) {
		super(input, block.defaultBlockState(), time, Optional.empty());
	}

	@Override
	public boolean matches(Level world, BlockPos pos, SpecialFlowerBlockEntity pureDaisy, BlockState state) {
		return input.test(state) && outputState.getBlock().withPropertiesOf(state) != state;
	}

	@Override
	public boolean set(Level world, BlockPos pos, SpecialFlowerBlockEntity pureDaisy) {
		if (!world.isClientSide) {
			Block block = getOutputState().getBlock();
			world.setBlockAndUpdate(pos, block.withPropertiesOf(world.getBlockState(pos)));
		}
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.COPYING_PURE_DAISY_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<StateCopyingPureDaisyRecipe> {
		public static final MapCodec<StateCopyingPureDaisyRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				StateIngredientHelper.CODEC.fieldOf("input").forGetter(r -> r.input),
				BuiltInRegistries.BLOCK.byNameCodec().fieldOf("output").forGetter(r -> r.outputState.getBlock()),
				Codec.INT.optionalFieldOf("time", DEFAULT_TIME).forGetter(r -> r.getTime())
		).apply(inst, StateCopyingPureDaisyRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, StateCopyingPureDaisyRecipe> STREAM_CODEC =
				StreamCodec.composite(
						StateIngredientHelper.STREAM_CODEC, r -> r.input,
						ByteBufCodecs.VAR_INT.map(BuiltInRegistries.BLOCK::byId, BuiltInRegistries.BLOCK::getId),
						r -> r.outputState.getBlock(),
						ByteBufCodecs.VAR_INT, r -> r.getTime(),
						StateCopyingPureDaisyRecipe::new);

		@NotNull
		@Override
		public MapCodec<StateCopyingPureDaisyRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, StateCopyingPureDaisyRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
