/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.armor.elementium;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.mana.ManaDiscountArmor;
import vazkii.botania.common.handler.PixieHandler;

public class ElementiumHelmItem extends ElementiumArmorItem implements ManaDiscountArmor {
	public ElementiumHelmItem(Properties props) {
		super(Type.HELMET, props);
	}

	@NotNull
	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
		ItemAttributeModifiers parent = super.getDefaultAttributeModifiers(stack);
		var builder = ItemAttributeModifiers.builder();
		parent.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
		builder.add(PixieHandler.PIXIE_SPAWN_CHANCE_HOLDER,
				PixieHandler.makeModifier(getType().getSlot(), "Armor modifier", 0.11),
				EquipmentSlotGroup.HEAD);
		return builder.build();
	}

	@Override
	public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.1F : 0F;
	}

}
