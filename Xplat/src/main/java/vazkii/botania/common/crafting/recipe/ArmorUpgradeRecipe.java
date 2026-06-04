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

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import org.jetbrains.annotations.NotNull;

public class ArmorUpgradeRecipe extends ShapedRecipe {
	public ArmorUpgradeRecipe(ShapedRecipe compose) {
		super(compose.getGroup(), compose.category(), compose.pattern(),
				// XXX: Hacky, but compose should always be a vanilla shaped recipe which doesn't do anything with the
				// RegistryAccess
				compose.getResultItem(RegistryAccess.EMPTY));
	}

	@NotNull
	@Override
	public ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess registries) {
		ItemStack out = super.assemble(inv, registries);
		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.hasTag() && stack.getItem() instanceof ArmorItem) {
				out.setTag(stack.getTag());
				break;
			}
		}
		return out;
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static final RecipeSerializer<ArmorUpgradeRecipe> SERIALIZER = new Serializer();

	private static class Serializer implements RecipeSerializer<ArmorUpgradeRecipe> {
		private static final MapCodec<ArmorUpgradeRecipe> CODEC =
				ShapedRecipe.Serializer.CODEC.xmap(
						ArmorUpgradeRecipe::new,
						r -> r
				);

		private static final StreamCodec<RegistryFriendlyByteBuf, ArmorUpgradeRecipe> STREAM_CODEC =
				ShapedRecipe.Serializer.STREAM_CODEC.map(
						ArmorUpgradeRecipe::new,
						r -> r
				);

		@Override
		public MapCodec<ArmorUpgradeRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ArmorUpgradeRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
