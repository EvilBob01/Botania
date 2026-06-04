/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.bauble;

import com.google.common.collect.Multimap;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.common.handler.PixieHandler;

public class GreatFairyRingItem extends BaubleItem {
	public GreatFairyRingItem(Properties props) {
		super(props);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
		Multimap<Attribute, AttributeModifier> ret = super.getEquippedAttributeModifiers(stack);
		ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, new AttributeModifier(ResourceLocation.fromNamespaceAndPath("botania", "great_fairy_ring_pixie_chance"), 0.25, AttributeModifier.Operation.ADD_VALUE));
		return ret;
	}
}
