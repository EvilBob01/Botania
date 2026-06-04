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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

// Catch-all "used an item and it succeeded" trigger for Botania items, because making a separate
// trigger for each one is dumb.
public class UseItemSuccessTrigger extends SimpleCriterionTrigger<UseItemSuccessTrigger.Instance> {
	public static final ResourceLocation ID = prefix("use_item_success");
	public static final UseItemSuccessTrigger INSTANCE = new UseItemSuccessTrigger();

	private UseItemSuccessTrigger() {}

	@Override
	public Codec<UseItemSuccessTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, ItemStack stack, ServerLevel world, double x, double y, double z) {
		trigger(player, instance -> instance.test(stack, world, x, y, z));
	}

	public static class Instance implements CriterionTriggerInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::getItem),
				LocationPredicate.CODEC.optionalFieldOf("location").forGetter(Instance::getLocation)
		).apply(instance, Instance::new));

		private final Optional<ItemPredicate> item;
		private final Optional<LocationPredicate> location;

		public Instance(Optional<ItemPredicate> item, Optional<LocationPredicate> location) {
			this.item = item;
			this.location = location;
		}

		boolean test(ItemStack stack, ServerLevel world, double x, double y, double z) {
			return this.item.map(p -> p.test(stack)).orElse(true)
					&& this.location.map(p -> p.matches(world, x, y, z)).orElse(true);
		}

		public Optional<ItemPredicate> getItem() {
			return this.item;
		}

		public Optional<LocationPredicate> getLocation() {
			return this.location;
		}
	}
}
