/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.annotations.SoftImplement;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelPickaxeItem;
import vazkii.botania.xplat.XplatAbstractions;

public class VitreousPickaxeItem extends ManasteelPickaxeItem {
	private static final String TAG_SILK_HACK = "botania:silk_hack";
	private static final int MANA_PER_DAMAGE = 160;
	private static final Tier MATERIAL = new Tier() {
		@Override
		public int getUses() {
			return 125;
		}

		@Override
		public float getSpeed() {
			return 4.8F;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Nullable
		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return null;
		}

		@Override
		public int getEnchantmentValue() {
			return 10;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(Blocks.GLASS);
		}
	};

	public VitreousPickaxeItem(Properties props) {
		super(MATERIAL, props, -1);
	}

	/*
	* No way to modify the loot context so we're gonna go braindead hack workaround here:
	* - When block starting to break, if the tool doesn't have silktouch already, add it and add a "temp silk touch" flag
	* - Every tick, if the "temp silk touch" flag is present, remove it and remove any silk touch enchants from the stack
	*/

	@SoftImplement("IForgeItem")
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
		BlockState state = player.level().getBlockState(pos);
		Holder<Enchantment> silkTouch = player.level().registryAccess()
				.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
		boolean hasSilk = EnchantmentHelper.getItemEnchantmentLevel(silkTouch, itemstack) > 0;
		if (hasSilk || !isGlass(state)) {
			return false;
		}

		itemstack.enchant(silkTouch, 1);
		// Set silk hack flag in custom data
		CompoundTag silkHackTag = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		silkHackTag.putBoolean(TAG_SILK_HACK, true);
		itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(silkHackTag));

		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity player, int slot, boolean selected) {
		super.inventoryTick(stack, world, player, slot, selected);
		CompoundTag customTag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		if (customTag.getBoolean(TAG_SILK_HACK)) {
			customTag.remove(TAG_SILK_HACK);
			if (customTag.isEmpty()) {
				stack.remove(DataComponents.CUSTOM_DATA);
			} else {
				stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
			}
			Holder<Enchantment> silkTouch = world.registryAccess()
					.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			ItemEnchantments.Mutable mutableEnch = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantments(stack));
			mutableEnch.set(silkTouch, 0);
			EnchantmentHelper.setEnchantments(stack, mutableEnch.toImmutable());
		}
	}

	private boolean isGlass(BlockState state) {
		return XplatAbstractions.INSTANCE.isInGlassTag(state);
	}

	@Override
	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}

	@Override
	public int getSortingPriority(ItemStack stack, BlockState state) {
		return isGlass(state) ? Integer.MAX_VALUE : 0;
	}

}
