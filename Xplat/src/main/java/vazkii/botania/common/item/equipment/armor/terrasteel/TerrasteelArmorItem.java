/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.armor.terrasteel;

import com.google.common.base.Suppliers;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.client.lib.ResourcesLib;
import vazkii.botania.common.annotations.SoftImplement;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.armor.manasteel.ManasteelArmorItem;

import java.util.List;
import java.util.function.Supplier;

public class TerrasteelArmorItem extends ManasteelArmorItem {

	public TerrasteelArmorItem(Type type, Properties props) {
		super(type, BotaniaAPI.instance().getTerrasteelArmorMaterial(), props);
	}

	@Override
	public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
		return ResourcesLib.MODEL_TERRASTEEL_NEW;
	}

	@NotNull
	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
		ItemAttributeModifiers parent = super.getDefaultAttributeModifiers(stack);
		var builder = ItemAttributeModifiers.builder();
		parent.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
		EquipmentSlot slot = getType().getSlot();
		int reduction = getType().getDefense();
		String slotName = slot.getName();
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath("botania", "terrasteel_knockback_resistance_" + slotName);
		EquipmentSlotGroup slotGroup = switch (slot) {
			case HEAD -> EquipmentSlotGroup.HEAD;
			case CHEST -> EquipmentSlotGroup.CHEST;
			case LEGS -> EquipmentSlotGroup.LEGS;
			case FEET -> EquipmentSlotGroup.FEET;
			default -> EquipmentSlotGroup.ANY;
		};
		builder.add(Attributes.KNOCKBACK_RESISTANCE,
				new AttributeModifier(id, (double) reduction / 20, AttributeModifier.Operation.ADD_VALUE),
				slotGroup);
		return builder.build();
	}

	private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[] {
			new ItemStack(BotaniaItems.terrasteelHelm),
			new ItemStack(BotaniaItems.terrasteelChest),
			new ItemStack(BotaniaItems.terrasteelLegs),
			new ItemStack(BotaniaItems.terrasteelBoots)
	});

	@Override
	public ItemStack[] getArmorSetStacks() {
		return armorSet.get();
	}

	@Override
	public boolean hasArmorSetItem(Player player, EquipmentSlot slot) {
		if (player == null) {
			return false;
		}

		ItemStack stack = player.getItemBySlot(slot);
		if (stack.isEmpty()) {
			return false;
		}

		return switch (slot) {
			case HEAD -> stack.is(BotaniaItems.terrasteelHelm);
			case CHEST -> stack.is(BotaniaItems.terrasteelChest);
			case LEGS -> stack.is(BotaniaItems.terrasteelLegs);
			case FEET -> stack.is(BotaniaItems.terrasteelBoots);
			default -> false;
		};

	}

	@Override
	public MutableComponent getArmorSetName() {
		return Component.translatable("botania.armorset.terrasteel.name");
	}

	@Override
	public void addArmorSetDescription(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("botania.armorset.terrasteel.desc0").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("botania.armorset.terrasteel.desc1").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("botania.armorset.terrasteel.desc2").withStyle(ChatFormatting.GRAY));
	}

	@SoftImplement("IForgeItem")
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}
}
