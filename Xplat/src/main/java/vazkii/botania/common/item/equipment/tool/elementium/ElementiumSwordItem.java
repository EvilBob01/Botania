/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.tool.elementium;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelSwordItem;

public class ElementiumSwordItem extends ManasteelSwordItem {

	public ElementiumSwordItem(Properties props) {
		super(BotaniaAPI.instance().getElementiumItemTier(), props);
	}

	@NotNull
	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
		ItemAttributeModifiers parent = super.getDefaultAttributeModifiers(stack);
		var builder = ItemAttributeModifiers.builder();
		parent.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
		builder.add(PixieHandler.PIXIE_SPAWN_CHANCE_HOLDER,
				PixieHandler.makeModifier(net.minecraft.world.entity.EquipmentSlot.MAINHAND, "Sword modifier", 0.05),
				EquipmentSlotGroup.MAINHAND);
		return builder.build();
	}

}
