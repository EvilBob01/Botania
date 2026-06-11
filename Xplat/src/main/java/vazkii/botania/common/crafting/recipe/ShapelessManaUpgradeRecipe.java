/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.crafting.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import org.jetbrains.annotations.NotNull;

public class ShapelessManaUpgradeRecipe extends ShapelessRecipe {
	public ShapelessManaUpgradeRecipe(ShapelessRecipe compose) {
		super(compose.getGroup(), CraftingBookCategory.EQUIPMENT,
				// XXX: Hacky, but compose should always be a vanilla shapeless recipe which doesn't do anything with the
				// RegistryAccess
				compose.getResultItem(RegistryAccess.EMPTY),
				compose.getIngredients());
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull CraftingInput inv, @NotNull HolderLookup.Provider registries) {
		return ManaUpgradeRecipe.output(super.assemble(inv, registries), inv);
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static final RecipeSerializer<ShapelessManaUpgradeRecipe> SERIALIZER = new Serializer();

	private static class Serializer implements RecipeSerializer<ShapelessManaUpgradeRecipe> {
		private static final MapCodec<ShapelessManaUpgradeRecipe> CODEC =
				RecipeSerializer.SHAPELESS_RECIPE.codec().xmap(
						ShapelessManaUpgradeRecipe::new,
						r -> r
				);

		private static final StreamCodec<RegistryFriendlyByteBuf, ShapelessManaUpgradeRecipe> STREAM_CODEC =
				RecipeSerializer.SHAPELESS_RECIPE.streamCodec().map(
						ShapelessManaUpgradeRecipe::new,
						r -> r
				);

		@NotNull
		@Override
		public MapCodec<ShapelessManaUpgradeRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapelessManaUpgradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
