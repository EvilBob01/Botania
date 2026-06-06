/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.handler;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;

import vazkii.botania.common.entity.PixieEntity;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.armor.elementium.ElementiumHelmItem;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public final class PixieHandler {

	private PixieHandler() {}

	public static final Attribute PIXIE_SPAWN_CHANCE = new RangedAttribute("attribute.name.botania.pixieSpawnChance", 0, 0, 1);
	public static final Holder<Attribute> PIXIE_SPAWN_CHANCE_HOLDER = Holder.direct(PIXIE_SPAWN_CHANCE);
	private static final Map<EquipmentSlot, ResourceLocation> DEFAULT_MODIFIER_IDS = Util.make(new EnumMap<>(EquipmentSlot.class), m -> {
		m.put(EquipmentSlot.HEAD, prefix("pixie_spawn_chance_head"));
		m.put(EquipmentSlot.CHEST, prefix("pixie_spawn_chance_chest"));
		m.put(EquipmentSlot.LEGS, prefix("pixie_spawn_chance_legs"));
		m.put(EquipmentSlot.FEET, prefix("pixie_spawn_chance_feet"));
		m.put(EquipmentSlot.MAINHAND, prefix("pixie_spawn_chance_mainhand"));
		m.put(EquipmentSlot.OFFHAND, prefix("pixie_spawn_chance_offhand"));
	});

	private static final List<Supplier<MobEffectInstance>> effectSuppliers = List.of(
			() -> new MobEffectInstance(MobEffects.BLINDNESS, 40, 0),
			() -> new MobEffectInstance(MobEffects.WITHER, 50, 0),
			() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0),
			() -> new MobEffectInstance(MobEffects.WEAKNESS, 40, 0)
	);

	public static void registerAttribute(BiConsumer<Attribute, ResourceLocation> r) {
		r.accept(PIXIE_SPAWN_CHANCE, prefix("pixie_spawn_chance"));
	}

	public static AttributeModifier makeModifier(EquipmentSlot slot, String name, double amount) {
		return new AttributeModifier(DEFAULT_MODIFIER_IDS.get(slot), amount, AttributeModifier.Operation.ADD_VALUE);
	}

	public static void onDamageTaken(Player player, DamageSource source) {
		if (!player.level().isClientSide && source.getEntity() instanceof LivingEntity livingSource) {
			// Sometimes the player doesn't have the attribute, not sure why.
			// Could be badly-written mixins on Fabric.
			double chance = player.getAttributes().hasAttribute(PIXIE_SPAWN_CHANCE_HOLDER)
					? player.getAttributeValue(PIXIE_SPAWN_CHANCE_HOLDER) : 0;
			ItemStack sword = PlayerHelper.getFirstHeldItem(player, s -> s.is(BotaniaItems.elementiumSword));

			if (Math.random() < chance) {
				PixieEntity pixie = new PixieEntity(player.level());
				pixie.setPos(player.getX(), player.getY() + 2, player.getZ());

				if (((ElementiumHelmItem) BotaniaItems.elementiumHelm).hasArmorSet(player)) {
					pixie.setApplyPotionEffect(effectSuppliers.get(player.level().random.nextInt(effectSuppliers.size())).get());
				}

				float dmg = 4;
				if (!sword.isEmpty()) {
					dmg += 2;
				}

				pixie.setProps(livingSource, player, 0, dmg);
				pixie.finalizeSpawn((ServerLevelAccessor) player.level(), player.level().getCurrentDifficultyAt(pixie.blockPosition()),
						MobSpawnType.EVENT, null);
				player.level().addFreshEntity(pixie);
			}
		}
	}
}
