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

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import vazkii.botania.api.recipe.BotaniaContainer;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.BrewContainer;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BotanicalBreweryRecipe implements vazkii.botania.api.recipe.BotanicalBreweryRecipe {
	private final Brew brew;
	private final NonNullList<Ingredient> inputs;

	public BotanicalBreweryRecipe(Brew brew, List<Ingredient> inputs) {
		this.brew = brew;
		this.inputs = NonNullList.of(Ingredient.EMPTY, inputs.toArray(new Ingredient[0]));
	}

	public BotanicalBreweryRecipe(Brew brew, Ingredient... inputs) {
		this(brew, List.of(inputs));
	}

	@Override
	public boolean matches(BotaniaContainer inv, @NotNull Level world) {
		List<Ingredient> inputsMissing = new ArrayList<>(inputs);

		for (int i = 0; i < inv.size(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.isEmpty()) {
				break;
			}

			if (stack.getItem() instanceof BrewContainer) {
				continue;
			}

			boolean matchedOne = false;

			Iterator<Ingredient> iter = inputsMissing.iterator();
			while (iter.hasNext()) {
				Ingredient input = iter.next();
				if (input.test(stack)) {
					iter.remove();
					matchedOne = true;
					break;
				}
			}

			if (!matchedOne) {
				return false;
			}
		}

		return inputsMissing.isEmpty();
	}

	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@NotNull
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(BotaniaBlocks.brewery);
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return BotaniaRecipeTypes.BREW_SERIALIZER;
	}

	@Override
	public Brew getBrew() {
		return brew;
	}

	@Override
	public int getManaUsage() {
		return brew.getManaCost();
	}

	@Override
	public ItemStack getOutput(ItemStack stack) {
		if (stack.isEmpty() || !(stack.getItem() instanceof BrewContainer container)) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}

		return container.getItemForBrew(brew, stack);
	}

	@Override
	public int hashCode() {
		return 31 * brew.hashCode() ^ inputs.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof BotanicalBreweryRecipe brewRecipe
				&& brew == brewRecipe.brew
				&& inputs.equals(brewRecipe.inputs);
	}

	public static class Serializer implements RecipeSerializer<BotanicalBreweryRecipe> {
		private static final Codec<Brew> BREW_CODEC = ResourceLocation.CODEC.xmap(
				id -> BotaniaAPI.instance().getBrewRegistry().getOptional(id)
						.orElseThrow(() -> new IllegalStateException("Unknown brew: " + id)),
				brew -> BotaniaAPI.instance().getBrewRegistry().getKey(brew)
		);

		public static final MapCodec<BotanicalBreweryRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
				BREW_CODEC.fieldOf("brew").forGetter(r -> r.brew),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(r -> r.inputs)
		).apply(inst, BotanicalBreweryRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, BotanicalBreweryRecipe> STREAM_CODEC =
				StreamCodec.composite(
						ResourceLocation.STREAM_CODEC.map(
								id -> BotaniaAPI.instance().getBrewRegistry().get(id),
								brew -> BotaniaAPI.instance().getBrewRegistry().getKey(brew)),
						r -> r.brew,
						Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> new ArrayList<>(r.inputs),
						BotanicalBreweryRecipe::new);

		@NotNull
		@Override
		public MapCodec<BotanicalBreweryRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, BotanicalBreweryRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
