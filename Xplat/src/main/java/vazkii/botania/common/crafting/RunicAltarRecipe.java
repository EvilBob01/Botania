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

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.recipe.RecipeUtils;

import java.util.ArrayList;
import java.util.List;

public class RunicAltarRecipe implements vazkii.botania.api.recipe.RunicAltarRecipe {
	private final ItemStack output;
	private final NonNullList<Ingredient> inputs;
	private final int mana;

	public RunicAltarRecipe(ItemStack output, int mana, List<Ingredient> inputs) {
		Preconditions.checkArgument(inputs.size() <= 16, "Cannot have more than 16 ingredients");
		this.output = output;
		this.inputs = NonNullList.of(Ingredient.EMPTY, inputs.toArray(new Ingredient[0]));
		this.mana = mana;
	}

	public RunicAltarRecipe(ItemStack output, int mana, Ingredient... inputs) {
		this(output, mana, List.of(inputs));
	}

	@Override
	public boolean matches(Container inv, @NotNull Level world) {
		return RecipeUtils.matches(inputs, inv, null);
	}

	@NotNull
	@Override
	public final ItemStack getResultItem(@NotNull RegistryAccess registries) {
		return output;
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull Container inv, @NotNull RegistryAccess registries) {
		return getResultItem(registries).copy();
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@NotNull
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BotaniaBlocks.runeAltar);
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.RUNE_SERIALIZER;
	}

	@Override
	public int getManaUsage() {
		return mana;
	}

	public static class Serializer implements RecipeSerializer<RunicAltarRecipe> {
		public static final MapCodec<RunicAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				ItemStack.STRICT_CODEC.fieldOf("output").forGetter(r -> r.output),
				Codec.INT.fieldOf("mana").forGetter(r -> r.mana),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
		).apply(inst, RunicAltarRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, RunicAltarRecipe> STREAM_CODEC =
				StreamCodec.composite(
						ItemStack.STREAM_CODEC, r -> r.output,
						ByteBufCodecs.VAR_INT, r -> r.mana,
						Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> new ArrayList<>(r.inputs),
						RunicAltarRecipe::new);

		@NotNull
		@Override
		public MapCodec<RunicAltarRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, RunicAltarRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
