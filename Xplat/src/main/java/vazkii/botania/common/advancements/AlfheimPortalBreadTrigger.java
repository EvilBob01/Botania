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
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class AlfheimPortalBreadTrigger extends SimpleCriterionTrigger<AlfheimPortalBreadTrigger.Instance> {
	public static final ResourceLocation ID = prefix("alf_portal_bread");
	public static final AlfheimPortalBreadTrigger INSTANCE = new AlfheimPortalBreadTrigger();

	private AlfheimPortalBreadTrigger() {}

	@Override
	public Codec<AlfheimPortalBreadTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, BlockPos portal) {
		this.trigger(player, instance -> instance.test(player.serverLevel(), portal));
	}

	public static class Instance implements CriterionTriggerInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				LocationPredicate.CODEC.optionalFieldOf("portal_location").forGetter(Instance::getPortal)
		).apply(instance, Instance::new));

		private final Optional<LocationPredicate> portal;

		public Instance(Optional<LocationPredicate> portal) {
			this.portal = portal;
		}

		boolean test(ServerLevel world, BlockPos portal) {
			return this.portal.map(p -> p.matches(world, portal.getX(), portal.getY(), portal.getZ())).orElse(true);
		}

		public Optional<LocationPredicate> getPortal() {
			return this.portal;
		}
	}
}
