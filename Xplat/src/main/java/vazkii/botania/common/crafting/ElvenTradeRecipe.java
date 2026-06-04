/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.block.BotaniaBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ElvenTradeRecipe implements vazkii.botania.api.recipe.ElvenTradeRecipe {
	private final ImmutableList<ItemStack> outputs;
	private final NonNullList<Ingredient> inputs;

	public ElvenTradeRecipe(List<ItemStack> outputs, List<Ingredient> inputs) {
		this.outputs = ImmutableList.copyOf(outputs);
		this.inputs = NonNullList.create();
		this.inputs.addAll(inputs);
	}

	public ElvenTradeRecipe(ItemStack[] outputs, Ingredient... inputs) {
		this(Arrays.asList(outputs), Arrays.asList(inputs));
	}

	@Override
	public Optional<List<ItemStack>> match(List<ItemStack> stacks) {
		List<Ingredient> inputsMissing = new ArrayList<>(inputs);
		List<ItemStack> stacksToRemove = new ArrayList<>();

		for (ItemStack stack : stacks) {
			if (stack.isEmpty()) {
				continue;
			}
			if (inputsMissing.isEmpty()) {
				break;
			}

			int stackIndex = -1;

			for (int i = 0; i < inputsMissing.size(); i++) {
				Ingredient ingr = inputsMissing.get(i);
				if (ingr.test(stack)) {
					if (!stacksToRemove.contains(stack)) {
						stacksToRemove.add(stack);
					}
					stackIndex = i;
					break;
				}
			}

			if (stackIndex != -1) {
				inputsMissing.remove(stackIndex);
			}
		}

		return inputsMissing.isEmpty() ? Optional.of(stacksToRemove) : Optional.empty();
	}

	@Override
	public boolean containsItem(ItemStack stack) {
		for (Ingredient input : inputs) {
			if (input.test(stack)) {
				return true;
			}
		}
		return false;
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.ELVEN_TRADE_SERIALIZER;
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@NotNull
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BotaniaBlocks.alfPortal);
	}

	@Override
	public List<ItemStack> getOutputs() {
		return outputs;
	}

	@Override
	public List<ItemStack> getOutputs(List<ItemStack> inputs) {
		return getOutputs();
	}

	public static class Serializer implements RecipeSerializer<ElvenTradeRecipe> {
		public static final MapCodec<ElvenTradeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				ItemStack.STRICT_CODEC.listOf().fieldOf("output").forGetter(r -> r.outputs),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
		).apply(inst, ElvenTradeRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, ElvenTradeRecipe> STREAM_CODEC =
				StreamCodec.composite(
						ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), r -> r.outputs,
						Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> new ArrayList<>(r.inputs),
						ElvenTradeRecipe::new);

		@NotNull
		@Override
		public MapCodec<ElvenTradeRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ElvenTradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
