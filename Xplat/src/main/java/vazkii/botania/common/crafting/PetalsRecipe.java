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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.BotaniaContainer;
import vazkii.botania.api.recipe.PetalApothecaryRecipe;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.ArrayList;
import java.util.List;

public class PetalsRecipe implements PetalApothecaryRecipe {
	private final ItemStack output;
	private final Ingredient reagent;
	private final NonNullList<Ingredient> inputs;

	public PetalsRecipe(ItemStack output, Ingredient reagent, List<Ingredient> inputs) {
		Preconditions.checkArgument(inputs.size() <= 16, "Cannot have more than 16 ingredients");
		this.output = output;
		this.reagent = reagent;
		this.inputs = NonNullList.of(Ingredient.EMPTY, inputs.toArray(new Ingredient[0]));
	}

	public PetalsRecipe(ItemStack output, Ingredient reagent, Ingredient... inputs) {
		this(output, reagent, List.of(inputs));
	}

	@Override
	public Ingredient getReagent() {
		return reagent;
	}

	@Override
	public boolean matches(BotaniaContainer inv, @NotNull Level world) {
		List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

		for (int i = 0; i < inv.size(); i++) {
			ItemStack input = inv.getItem(i);
			if (input.isEmpty()) {
				break;
			}

			int stackIndex = -1;

			for (int j = 0; j < ingredientsMissing.size(); j++) {
				Ingredient ingr = ingredientsMissing.get(j);
				if (ingr.test(input)) {
					stackIndex = j;
					break;
				}
			}

			if (stackIndex != -1) {
				ingredientsMissing.remove(stackIndex);
			} else {
				return false;
			}
		}

		return ingredientsMissing.isEmpty();
	}

	@NotNull
	@Override
	public final ItemStack getResultItem(@NotNull HolderLookup.Provider registries) {
		return output;
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull BotaniaContainer inv, @NotNull HolderLookup.Provider registries) {
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
		return new ItemStack(BotaniaBlocks.defaultAltar);
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.PETAL_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<PetalsRecipe> {
		public static final MapCodec<PetalsRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				ItemStack.STRICT_CODEC.fieldOf("output").forGetter(r -> r.output),
				Ingredient.CODEC_NONEMPTY.fieldOf("reagent").forGetter(r -> r.reagent),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
		).apply(inst, PetalsRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, PetalsRecipe> STREAM_CODEC =
				StreamCodec.composite(
						ItemStack.STREAM_CODEC, r -> r.output,
						Ingredient.CONTENTS_STREAM_CODEC, r -> r.reagent,
						Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> new ArrayList<>(r.inputs),
						PetalsRecipe::new);

		@NotNull
		@Override
		public MapCodec<PetalsRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PetalsRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
