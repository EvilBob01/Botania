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

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class LokiPlaceTrigger extends SimpleCriterionTrigger<LokiPlaceTrigger.Instance> {
	public static final ResourceLocation ID = prefix("loki_placed_blocks");
	public static final LokiPlaceTrigger INSTANCE = new LokiPlaceTrigger();

	private LokiPlaceTrigger() {}

	@Override
	public Codec<LokiPlaceTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, ItemStack ring, int blocksPlaced) {
		trigger(player, instance -> instance.test(ring, blocksPlaced));
	}

	public static class Instance implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemPredicate.CODEC.optionalFieldOf("ring").forGetter(Instance::getRing),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("blocks_placed").forGetter(Instance::getBlocksPlaced)
		).apply(instance, Instance::new));

		private final Optional<ItemPredicate> ring;
		private final Optional<MinMaxBounds.Ints> blocksPlaced;

		public Instance(Optional<ItemPredicate> ring, Optional<MinMaxBounds.Ints> blocksPlaced) {
			this.ring = ring;
			this.blocksPlaced = blocksPlaced;
		}

		boolean test(ItemStack ring, int blocksPlaced) {
			return this.ring.map(p -> p.test(ring)).orElse(true)
					&& this.blocksPlaced.map(b -> b.matches(blocksPlaced)).orElse(true);
		}

		public Optional<ItemPredicate> getRing() {
			return this.ring;
		}

		public Optional<MinMaxBounds.Ints> getBlocksPlaced() {
			return this.blocksPlaced;
		}

		@Override
		public Optional<ContextAwarePredicate> player() {
			return Optional.empty();
		}
	}
}
