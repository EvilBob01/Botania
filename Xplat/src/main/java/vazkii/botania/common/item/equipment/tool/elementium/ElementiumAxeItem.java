/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.tool.elementium;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.annotations.SoftImplement;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelAxeItem;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class ElementiumAxeItem extends ManasteelAxeItem {
	public static final ResourceLocation BEHEADING_LOOT_TABLE = prefix("elementium_axe_beheading");

	public ElementiumAxeItem(Properties props) {
		super(BotaniaAPI.instance().getElementiumItemTier(), 6F, -3.1F, props);
	}

	@SoftImplement("IForgeItem")
	public boolean canApplyAtEnchantingTable(ItemStack stack, Holder<Enchantment> enchantment) {
		if (enchantment.is(Enchantments.LOOTING)) {
			return true;
		} else {
			// Copy the default impl
			return enchantment.value().canEnchant(stack);
		}

	}

	// [VanillaCopy] modified from DiggerItem::hurtEnemy, actually same as SwordItem::hurtEnemy
	@Override
	public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
		// only do 1 durability damage, since this is primarily a weapon
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
		return true;
	}

}
