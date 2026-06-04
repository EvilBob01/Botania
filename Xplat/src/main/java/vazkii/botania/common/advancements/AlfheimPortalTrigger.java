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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class AlfheimPortalTrigger extends SimpleCriterionTrigger<AlfheimPortalTrigger.Instance> {
	public static final ResourceLocation ID = prefix("open_elf_portal");
	public static final AlfheimPortalTrigger INSTANCE = new AlfheimPortalTrigger();

	private AlfheimPortalTrigger() {}

	@Override
	public Codec<AlfheimPortalTrigger.Instance> codec() {
		return Instance.CODEC;
	}

	public void trigger(ServerPlayer player, ServerLevel world, BlockPos pos, ItemStack wand) {
		trigger(player, instance -> instance.test(world, pos, wand));
	}

	public static class Instance implements CriterionTriggerInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemPredicate.CODEC.optionalFieldOf("wand").forGetter(Instance::getWand),
				LocationPredicate.CODEC.optionalFieldOf("location").forGetter(Instance::getPos)
		).apply(instance, Instance::new));

		private final Optional<ItemPredicate> wand;
		private final Optional<LocationPredicate> pos;

		public Instance(Optional<ItemPredicate> wand, Optional<LocationPredicate> pos) {
			this.wand = wand;
			this.pos = pos;
		}

		boolean test(ServerLevel world, BlockPos pos, ItemStack wand) {
			return this.wand.map(p -> p.test(wand)).orElse(true)
					&& this.pos.map(p -> p.matches(world, pos.getX(), pos.getY(), pos.getZ())).orElse(true);
		}

		public Optional<ItemPredicate> getWand() {
			return this.wand;
		}

		public Optional<LocationPredicate> getPos() {
			return this.pos;
		}
	}
}
