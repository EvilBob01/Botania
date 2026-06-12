package vazkii.botania.forge.internal_caps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.internal_caps.*;
import vazkii.botania.common.lib.LibMisc;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public final class ForgeInternalEntityCapabilities {
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
			DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, LibMisc.MOD_ID);

	// Mob-type capabilities stored as attachments (work on any entity/mob subtype)
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<LooniumComponent>> LOONIUM_DROP =
			ATTACHMENT_TYPES.register("loonium_drop", () -> AttachmentType.builder(LooniumComponent::new).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<NarslimmusComponent>> NARSLIMMUS =
			ATTACHMENT_TYPES.register("narslimmus", () -> AttachmentType.builder(NarslimmusComponent::new).build());
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<TigerseyeComponent>> TIGERSEYE =
			ATTACHMENT_TYPES.register("tigerseye", () -> AttachmentType.builder(TigerseyeComponent::new).build());

	// Specific-type capabilities stored as EntityCapabilities
	public static final EntityCapability<EthicalComponent, @Nullable Void> TNT_ETHICAL =
			EntityCapability.createVoid(prefix("tnt_ethical"), EthicalComponent.class);
	public static final EntityCapability<SpectralRailComponent, @Nullable Void> GHOST_RAIL =
			EntityCapability.createVoid(prefix("ghost_rail"), SpectralRailComponent.class);
	public static final EntityCapability<ItemFlagsComponent, @Nullable Void> INTERNAL_ITEM =
			EntityCapability.createVoid(prefix("internal_item"), ItemFlagsComponent.class);
	public static final EntityCapability<KeptItemsComponent, @Nullable Void> KEPT_ITEMS =
			EntityCapability.createVoid(prefix("kept_items"), KeptItemsComponent.class);

	public static void registerDeferredRegisters(IEventBus modBus) {
		ATTACHMENT_TYPES.register(modBus);
	}

	@EventBusSubscriber(modid = LibMisc.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {
		@SubscribeEvent
		public static void registerCaps(RegisterCapabilitiesEvent evt) {
			evt.registerEntity(TNT_ETHICAL, EntityType.TNT, (tnt, ctx) -> new EthicalComponent(tnt));
			// Register GHOST_RAIL for all vanilla minecart types
			evt.registerEntity(GHOST_RAIL, EntityType.MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.CHEST_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.FURNACE_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.HOPPER_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.TNT_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.SPAWNER_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(GHOST_RAIL, EntityType.COMMAND_BLOCK_MINECART, (cart, ctx) -> new SpectralRailComponent());
			evt.registerEntity(INTERNAL_ITEM, EntityType.ITEM, (item, ctx) -> new ItemFlagsComponent());
			evt.registerEntity(KEPT_ITEMS, EntityType.PLAYER, (player, ctx) -> new KeptItemsComponent());
		}
	}

	private ForgeInternalEntityCapabilities() {}
}
