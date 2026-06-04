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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.block.decor.BotaniaMushroomBlock;
import vazkii.botania.common.item.WandOfTheForestItem;
import vazkii.botania.common.item.material.MysticalPetalItem;

public class WandOfTheForestRecipe extends ShapedRecipe {
	public static final RecipeSerializer<WandOfTheForestRecipe> SERIALIZER = new Serializer();

	public WandOfTheForestRecipe(ShapedRecipe compose) {
		super(compose.getGroup(), compose.category(), compose.pattern(),
				// XXX: Hacky, but compose should always be a vanilla shaped recipe which doesn't do anything with the
				// RegistryAccess
				compose.getResultItem(RegistryAccess.EMPTY));
	}

	@NotNull
	@Override
	public ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess registries) {
		int first = -1;
		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			Item item = stack.getItem();

			int colorId;
			if (item instanceof MysticalPetalItem petal) {
				colorId = petal.color.getId();
			} else if (item instanceof BlockItem block && block.getBlock() instanceof BotaniaMushroomBlock mushroom) {
				colorId = mushroom.color.getId();
			} else {
				continue;
			}
			if (first == -1) {
				first = colorId;
			} else {
				return WandOfTheForestItem.setColors(getResultItem(registries).copy(), first, colorId);
			}
		}
		return WandOfTheForestItem.setColors(getResultItem(registries).copy(), first != -1 ? first : 0, 0);
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	private static class Serializer implements RecipeSerializer<WandOfTheForestRecipe> {
		private static final MapCodec<WandOfTheForestRecipe> CODEC =
				ShapedRecipe.Serializer.CODEC.xmap(
						WandOfTheForestRecipe::new,
						r -> r
				);

		private static final StreamCodec<RegistryFriendlyByteBuf, WandOfTheForestRecipe> STREAM_CODEC =
				ShapedRecipe.Serializer.STREAM_CODEC.map(
						WandOfTheForestRecipe::new,
						r -> r
				);

		@NotNull
		@Override
		public MapCodec<WandOfTheForestRecipe> codec() {
			return CODEC;
		}

		@NotNull
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, WandOfTheForestRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
