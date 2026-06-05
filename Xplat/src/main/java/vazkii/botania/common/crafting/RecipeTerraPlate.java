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

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.BotaniaContainer;
import vazkii.botania.api.recipe.TerrestrialAgglomerationRecipe;
import vazkii.botania.common.crafting.recipe.RecipeUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeTerraPlate implements TerrestrialAgglomerationRecipe {
	private final int mana;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;

	public RecipeTerraPlate(int mana, List<Ingredient> inputs, ItemStack output) {
		this.mana = mana;
		this.inputs = NonNullList.of(Ingredient.EMPTY, inputs.toArray(new Ingredient[0]));
		this.output = output;
	}

	@Override
	public int getMana() {
		return mana;
	}

	@Override
	public boolean matches(BotaniaContainer inv, @NotNull Level world) {
		int nonEmptySlots = 0;
		for (int i = 0; i < inv.size(); i++) {
			if (!inv.getItem(i).isEmpty()) {
				if (inv.getItem(i).getCount() > 1) {
					return false;
				}
				nonEmptySlots++;
			}
		}

		IntOpenHashSet usedSlots = new IntOpenHashSet(inv.size());
		return RecipeUtils.matches(inputs, inv.getContainer(), usedSlots) && usedSlots.size() == nonEmptySlots;
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull BotaniaContainer inv, @NotNull HolderLookup.Provider registries) {
		return output.copy();
	}

	@NotNull
	@Override
	public ItemStack getResultItem(@NotNull HolderLookup.Provider registries) {
		return output;
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@NotNull
	@Override
	public RecipeSerializer<RecipeTerraPlate> getSerializer() {
		return BotaniaRecipeTypes.TERRA_PLATE_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<RecipeTerraPlate> {
		public static final MapCodec<RecipeTerraPlate> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				Codec.INT.fieldOf("mana").forGetter(r -> r.mana),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs),
				ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.output)
		).apply(inst, RecipeTerraPlate::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, RecipeTerraPlate> STREAM_CODEC =
				StreamCodec.composite(
						ByteBufCodecs.VAR_INT, r -> r.mana,
						Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> new ArrayList<>(r.inputs),
						ItemStack.STREAM_CODEC, r -> r.output,
						RecipeTerraPlate::new);

		@NotNull
		@Override
		public MapCodec<RecipeTerraPlate> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, RecipeTerraPlate> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
