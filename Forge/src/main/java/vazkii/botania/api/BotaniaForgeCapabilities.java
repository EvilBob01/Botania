package vazkii.botania.api;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block.*;
import vazkii.botania.api.item.*;
import vazkii.botania.api.mana.ManaCollisionGhost;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.api.mana.ManaTrigger;
import vazkii.botania.api.mana.spark.SparkAttachable;

public final class BotaniaForgeCapabilities {
	private static final String MODID = BotaniaAPI.MODID;

	public static final ItemCapability<AvatarWieldable, @Nullable Void> AVATAR_WIELDABLE =
			ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "avatar_wieldable"), AvatarWieldable.class);
	public static final ItemCapability<BlockProvider, @Nullable Void> BLOCK_PROVIDER =
			ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "block_provider"), BlockProvider.class);
	public static final ItemCapability<CoordBoundItem, @Nullable Void> COORD_BOUND_ITEM =
			ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "coord_bound_item"), CoordBoundItem.class);
	public static final ItemCapability<ManaItem, @Nullable Void> MANA_ITEM =
			ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "mana_item"), ManaItem.class);
	public static final ItemCapability<Relic, @Nullable Void> RELIC =
			ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "relic"), Relic.class);

	public static final BlockCapability<ExoflameHeatable, @Nullable Direction> EXOFLAME_HEATABLE =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "exoflame_heatable"), ExoflameHeatable.class);
	public static final BlockCapability<HornHarvestable, @Nullable Direction> HORN_HARVEST =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "horn_harvest"), HornHarvestable.class);
	public static final BlockCapability<HourglassTrigger, @Nullable Direction> HOURGLASS_TRIGGER =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "hourglass_trigger"), HourglassTrigger.class);
	public static final BlockCapability<ManaCollisionGhost, @Nullable Direction> MANA_GHOST =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "mana_ghost"), ManaCollisionGhost.class);
	public static final BlockCapability<ManaReceiver, @Nullable Direction> MANA_RECEIVER =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "mana_receiver"), ManaReceiver.class);
	public static final BlockCapability<SparkAttachable, @Nullable Direction> SPARK_ATTACHABLE =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "spark_attachable"), SparkAttachable.class);
	public static final BlockCapability<ManaTrigger, @Nullable Direction> MANA_TRIGGER =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "mana_trigger"), ManaTrigger.class);
	public static final BlockCapability<Wandable, @Nullable Direction> WANDABLE =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "wandable"), Wandable.class);
	public static final BlockCapability<PhantomInkableBlock, @Nullable Direction> PHANTOM_INKABLE =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "phantom_inkable"), PhantomInkableBlock.class);

	private BotaniaForgeCapabilities() {}
}
