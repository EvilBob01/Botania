/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.configdata.ConfigDataManager;
import vazkii.botania.api.corporea.CorporeaNodeDetector;
import vazkii.botania.api.internal.ManaNetwork;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.common.block.flower.functional.SolegnoliaBlockEntity;
import vazkii.botania.common.config.ConfigDataManagerImpl;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.handler.ManaNetworkHandler;
import vazkii.botania.common.integration.corporea.CorporeaNodeDetectors;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.relic.RingOfLokiItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class BotaniaAPIImpl implements BotaniaAPI {

	private static final Holder<net.minecraft.world.item.ArmorMaterial> MANASTEEL = Holder.direct(new net.minecraft.world.item.ArmorMaterial(
			Map.of(ArmorItem.Type.BOOTS, 2, ArmorItem.Type.LEGGINGS, 5, ArmorItem.Type.CHESTPLATE, 6, ArmorItem.Type.HELMET, 2),
			18, SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(BotaniaItems.manaSteel),
			List.of(new net.minecraft.world.item.ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("botania", "manasteel"))),
			0f, 0f
	));

	private static final Holder<net.minecraft.world.item.ArmorMaterial> MANAWEAVE = Holder.direct(new net.minecraft.world.item.ArmorMaterial(
			Map.of(ArmorItem.Type.BOOTS, 1, ArmorItem.Type.LEGGINGS, 2, ArmorItem.Type.CHESTPLATE, 3, ArmorItem.Type.HELMET, 1),
			18, SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(BotaniaItems.manaweaveCloth),
			List.of(new net.minecraft.world.item.ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("botania", "manaweave"))),
			0f, 0f
	));

	private static final Holder<net.minecraft.world.item.ArmorMaterial> ELEMENTIUM = Holder.direct(new net.minecraft.world.item.ArmorMaterial(
			Map.of(ArmorItem.Type.BOOTS, 2, ArmorItem.Type.LEGGINGS, 5, ArmorItem.Type.CHESTPLATE, 6, ArmorItem.Type.HELMET, 2),
			18, SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(BotaniaItems.elementium),
			List.of(new net.minecraft.world.item.ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("botania", "elementium"))),
			0f, 0f
	));

	private static final Holder<net.minecraft.world.item.ArmorMaterial> TERRASTEEL = Holder.direct(new net.minecraft.world.item.ArmorMaterial(
			Map.of(ArmorItem.Type.BOOTS, 3, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.HELMET, 3),
			26, SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(BotaniaItems.terrasteel),
			List.of(new net.minecraft.world.item.ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("botania", "terrasteel"))),
			3f, 0f
	));

	private enum ItemTier implements Tier {
		MANASTEEL(300, 6.2F, 2, 20, () -> BotaniaItems.manaSteel),
		ELEMENTIUM(720, 6.2F, 2, 20, () -> BotaniaItems.elementium),
		TERRASTEEL(2300, 9, 4, 26, () -> BotaniaItems.terrasteel);

		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final int enchantability;
		private final Supplier<Item> repairItem;

		ItemTier(int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Item> repairItem) {
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = attackDamage;
			this.enchantability = enchantability;
			this.repairItem = repairItem;
		}

		@Override
		public int getUses() {
			return maxUses;
		}

		@Override
		public float getSpeed() {
			return efficiency;
		}

		@Override
		public float getAttackDamageBonus() {
			return attackDamage;
		}

		@Override
		@Nullable
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return null;
		}

		@Override
		public int getEnchantmentValue() {
			return enchantability;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(repairItem.get());
		}
	}

	private ConfigDataManager configDataManager = new ConfigDataManagerImpl();

	@Override
	public int apiVersion() {
		return 2;
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public Registry<Brew> getBrewRegistry() {
		return (Registry<Brew>) BuiltInRegistries.REGISTRY.get(BotaniaRegistries.BREWS.location());
	}

	@Override
	public Holder<net.minecraft.world.item.ArmorMaterial> getManasteelArmorMaterial() {
		return MANASTEEL;
	}

	@Override
	public Holder<net.minecraft.world.item.ArmorMaterial> getElementiumArmorMaterial() {
		return ELEMENTIUM;
	}

	@Override
	public Holder<net.minecraft.world.item.ArmorMaterial> getManaweaveArmorMaterial() {
		return MANAWEAVE;
	}

	@Override
	public Holder<net.minecraft.world.item.ArmorMaterial> getTerrasteelArmorMaterial() {
		return TERRASTEEL;
	}

	@Override
	public Tier getManasteelItemTier() {
		return ItemTier.MANASTEEL;
	}

	@Override
	public Tier getElementiumItemTier() {
		return ItemTier.ELEMENTIUM;
	}

	@Override
	public Tier getTerrasteelItemTier() {
		return ItemTier.TERRASTEEL;
	}

	@Override
	public ManaNetwork getManaNetworkInstance() {
		return ManaNetworkHandler.instance;
	}

	@Override
	public Container getAccessoriesInventory(Player player) {
		return EquipmentHandler.getAllWorn(player);
	}

	@Override
	public void breakOnAllCursors(Player player, ItemStack stack, BlockPos pos, Direction side) {
		RingOfLokiItem.breakOnAllCursors(player, stack, pos, side);
	}

	@Override
	public boolean hasSolegnoliaAround(Entity e) {
		return SolegnoliaBlockEntity.hasSolegnoliaAround(e);
	}

	@Override
	public void sparkleFX(Level world, double x, double y, double z, float r, float g, float b, float size, int m) {
		SparkleParticleData data = SparkleParticleData.sparkle(size, r, g, b, m);
		world.addParticle(data, x, y, z, 0, 0, 0);
	}

	private final Map<ResourceLocation, Function<DyeColor, Block>> paintableBlocks = new ConcurrentHashMap<>();

	@Override
	public Map<ResourceLocation, Function<DyeColor, Block>> getPaintableBlocks() {
		return Collections.unmodifiableMap(paintableBlocks);
	}

	@Override
	public void registerPaintableBlock(ResourceLocation block, Function<DyeColor, Block> transformer) {
		paintableBlocks.put(block, transformer);
	}

	@Override
	public void registerCorporeaNodeDetector(CorporeaNodeDetector detector) {
		CorporeaNodeDetectors.register(detector);
	}

	@Override
	public ConfigDataManager getConfigData() {
		return configDataManager;
	}

	@Override
	public void setConfigData(ConfigDataManager configDataManager) {
		this.configDataManager = configDataManager;
	}
}
