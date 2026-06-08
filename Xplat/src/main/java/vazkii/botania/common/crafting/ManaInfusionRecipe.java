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

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.Optional;

public class ManaInfusionRecipe implements vazkii.botania.api.recipe.ManaInfusionRecipe {
	private final ItemStack output;
	private final Ingredient input;
	private final int mana;
	@Nullable
	private final StateIngredient catalyst;
	private final String group;

	public ManaInfusionRecipe(ItemStack output, Ingredient input, int mana,
			@Nullable String group, @Nullable StateIngredient catalyst) {
		Preconditions.checkArgument(mana > 0, "Mana cost must be positive");
		Preconditions.checkArgument(mana <= 1_000_001, "Mana cost must be at most a pool");
		this.output = output;
		this.input = input;
		this.mana = mana;
		this.group = group == null ? "" : group;
		this.catalyst = catalyst;
	}

	@NotNull
	@Override
	public RecipeSerializer<ManaInfusionRecipe> getSerializer() {
		return BotaniaRecipeTypes.MANA_INFUSION_SERIALIZER;
	}

	@Override
	public boolean matches(ItemStack stack) {
		return input.test(stack);
	}

	@Override
	public StateIngredient getRecipeCatalyst() {
		return catalyst;
	}

	@Override
	public int getManaToConsume() {
		return mana;
	}

	@NotNull
	@Override
	public ItemStack getResultItem(@NotNull HolderLookup.Provider registries) {
		return output;
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, input);
	}

	@NotNull
	@Override
	public String getGroup() {
		return group;
	}

	@NotNull
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BotaniaBlocks.manaPool);
	}

	public static class Serializer implements RecipeSerializer<ManaInfusionRecipe> {
		public static final MapCodec<ManaInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				ItemStack.STRICT_CODEC.fieldOf("output").forGetter(r -> r.output),
				Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(r -> r.input),
				Codec.INT.fieldOf("mana").forGetter(r -> r.mana),
				Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
				StateIngredientHelper.CODEC.optionalFieldOf("catalyst").forGetter(r -> Optional.ofNullable(r.catalyst))
		).apply(inst, (output, input, mana, group, catalyst) ->
				new ManaInfusionRecipe(output, input, mana, group, catalyst.orElse(null))));

		public static final StreamCodec<RegistryFriendlyByteBuf, ManaInfusionRecipe> STREAM_CODEC =
				StreamCodec.composite(
						ItemStack.STREAM_CODEC, r -> r.output,
						Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
						ByteBufCodecs.VAR_INT, r -> r.mana,
						ByteBufCodecs.STRING_UTF8, r -> r.group,
						StateIngredientHelper.OPTIONAL_STREAM_CODEC, r -> Optional.ofNullable(r.catalyst),
						(output, input, mana, group, catalyst) ->
								new ManaInfusionRecipe(output, input, mana, group, catalyst.orElse(null)));

		@NotNull
		@Override
		public MapCodec<ManaInfusionRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ManaInfusionRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
