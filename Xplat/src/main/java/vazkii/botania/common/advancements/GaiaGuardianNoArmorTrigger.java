/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.critereon.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import vazkii.botania.common.entity.GaiaGuardianEntity;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class GaiaGuardianNoArmorTrigger extends SimpleCriterionTrigger<GaiaGuardianNoArmorTrigger.Instance> {
	public static final ResourceLocation ID = prefix("gaia_guardian_no_armor");
	public static final GaiaGuardianNoArmorTrigger INSTANCE = new GaiaGuardianNoArmorTrigger();

	private GaiaGuardianNoArmorTrigger() {}

	@Override
	public Codec<GaiaGuardianNoArmorTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, GaiaGuardianEntity guardian, DamageSource src) {
		trigger(player, instance -> instance.test(player, guardian, src));
	}

	public static class Instance implements CriterionTriggerInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.CODEC.optionalFieldOf("guardian").forGetter(Instance::getGuardian),
				DamageSourcePredicate.CODEC.optionalFieldOf("killing_blow").forGetter(Instance::getKillingBlow)
		).apply(instance, Instance::new));

		private final Optional<EntityPredicate> guardian;
		private final Optional<DamageSourcePredicate> killingBlow;

		public Instance(Optional<EntityPredicate> guardian, Optional<DamageSourcePredicate> killingBlow) {
			this.guardian = guardian;
			this.killingBlow = killingBlow;
		}

		boolean test(ServerPlayer player, GaiaGuardianEntity guardian, DamageSource src) {
			return this.guardian.map(p -> p.matches(player.serverLevel(), player.position(), guardian)).orElse(true)
					&& this.killingBlow.map(p -> p.matches(player.serverLevel(), player.position(), src)).orElse(true);
		}

		public Optional<EntityPredicate> getGuardian() {
			return this.guardian;
		}

		public Optional<DamageSourcePredicate> getKillingBlow() {
			return this.killingBlow;
		}
	}
}
