/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.api.recipe.StateIngredient;

import java.util.Optional;

public class PureDaisyRecipe implements vazkii.botania.api.recipe.PureDaisyRecipe {

	public static final int DEFAULT_TIME = 150;

	protected final StateIngredient input;
	protected final BlockState outputState;
	private final int time;
	private final Optional<ResourceLocation> function;

	public PureDaisyRecipe(StateIngredient input, BlockState state, int time, Optional<ResourceLocation> function) {
		Preconditions.checkArgument(time >= 0, "Time must be nonnegative");
		this.input = input;
		this.outputState = state;
		this.time = time;
		this.function = function;
	}

	@Override
	public boolean matches(Level world, BlockPos pos, SpecialFlowerBlockEntity pureDaisy, BlockState state) {
		return input.test(state) && outputState != state;
	}

	@Override
	public boolean set(Level world, BlockPos pos, SpecialFlowerBlockEntity pureDaisy) {
		if (!world.isClientSide) {
			boolean success = world.setBlockAndUpdate(pos, outputState);
			if (success) {
				var serverLevel = (ServerLevel) world;
				var server = serverLevel.getServer();
				this.function.ifPresent(funcId -> {
					server.getFunctions().get(funcId).ifPresent(command -> {
						var context = server.getFunctions().getGameLoopSender()
								.withLevel((ServerLevel) world)
								.withPosition(Vec3.atBottomCenterOf(pos));
						server.getFunctions().execute(command, context);
					});
				});
			}
			return success;
		}
		return true;
	}

	@Override
	public StateIngredient getInput() {
		return input;
	}

	@Override
	public BlockState getOutputState() {
		return outputState;
	}

	@Override
	public Optional<ResourceLocation> getSuccessFunction() {
		return this.function;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.PURE_DAISY_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<PureDaisyRecipe> {
		// Codec for BlockState using block ID + properties
		private static final Codec<BlockState> BLOCK_STATE_CODEC = BuiltInRegistries.BLOCK.byNameCodec()
				.xmap(Block::defaultBlockState, BlockState::getBlock);

		public static final MapCodec<PureDaisyRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				StateIngredientHelper.CODEC.fieldOf("input").forGetter(r -> r.input),
				BLOCK_STATE_CODEC.fieldOf("output").forGetter(r -> r.outputState),
				Codec.INT.optionalFieldOf("time", DEFAULT_TIME).forGetter(r -> r.time),
				ResourceLocation.CODEC.optionalFieldOf("success_function").forGetter(r -> r.function)
		).apply(inst, (input, output, time, funcId) ->
				new PureDaisyRecipe(input, output, time, funcId)));

		public static final StreamCodec<RegistryFriendlyByteBuf, PureDaisyRecipe> STREAM_CODEC =
				StreamCodec.composite(
						StateIngredientHelper.STREAM_CODEC, r -> r.input,
						ByteBufCodecs.VAR_INT.map(Block::stateById, Block::getId), r -> r.outputState,
						ByteBufCodecs.VAR_INT, r -> r.time,
						(input, output, time) -> new PureDaisyRecipe(input, output, time, Optional.empty()));

		@NotNull
		@Override
		public MapCodec<PureDaisyRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PureDaisyRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
