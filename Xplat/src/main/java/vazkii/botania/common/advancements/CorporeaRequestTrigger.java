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
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class CorporeaRequestTrigger extends SimpleCriterionTrigger<CorporeaRequestTrigger.Instance> {
	public static final ResourceLocation ID = prefix("corporea_index_request");
	public static final CorporeaRequestTrigger INSTANCE = new CorporeaRequestTrigger();

	private CorporeaRequestTrigger() {}

	@Override
	public Codec<CorporeaRequestTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, ServerLevel world, BlockPos pos, int count) {
		this.trigger(player, instance -> instance.test(world, pos, count));
	}

	public static class Instance implements CriterionTriggerInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				MinMaxBounds.Ints.CODEC.optionalFieldOf("extracted").forGetter(Instance::getCount),
				LocationPredicate.CODEC.optionalFieldOf("location").forGetter(Instance::getIndexPos)
		).apply(instance, Instance::new));

		private final Optional<MinMaxBounds.Ints> count;
		private final Optional<LocationPredicate> indexPos;

		public Instance(Optional<MinMaxBounds.Ints> count, Optional<LocationPredicate> indexPos) {
			this.count = count;
			this.indexPos = indexPos;
		}

		boolean test(ServerLevel world, BlockPos pos, int count) {
			return this.count.map(c -> c.matches(count)).orElse(true)
					&& this.indexPos.map(p -> p.matches(world, pos.getX(), pos.getY(), pos.getZ())).orElse(true);
		}

		public Optional<MinMaxBounds.Ints> getCount() {
			return this.count;
		}

		public Optional<LocationPredicate> getIndexPos() {
			return this.indexPos;
		}
	}
}
