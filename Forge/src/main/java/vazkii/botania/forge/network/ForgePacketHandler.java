package vazkii.botania.forge.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import vazkii.botania.common.lib.LibMisc;
import vazkii.botania.network.BotaniaPacket;
import vazkii.botania.network.TriConsumer;
import vazkii.botania.network.clientbound.*;
import vazkii.botania.network.serverbound.*;

import java.util.function.Consumer;
import java.util.function.Function;

@EventBusSubscriber(modid = LibMisc.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ForgePacketHandler {
	@SubscribeEvent
	public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		var registrar = event.registrar("1").optional();

		// Serverbound
		registrar.playToServer(
				makeType(DodgePacket.ID),
				makeCodec(DodgePacket.ID, DodgePacket::decode),
				makeServerHandler(DodgePacket.ID, DodgePacket::handle));
		registrar.playToServer(
				makeType(IndexKeybindRequestPacket.ID),
				makeCodec(IndexKeybindRequestPacket.ID, IndexKeybindRequestPacket::decode),
				makeServerHandler(IndexKeybindRequestPacket.ID, IndexKeybindRequestPacket::handle));
		registrar.playToServer(
				makeType(IndexStringRequestPacket.ID),
				makeCodec(IndexStringRequestPacket.ID, IndexStringRequestPacket::decode),
				makeServerHandler(IndexStringRequestPacket.ID, IndexStringRequestPacket::handle));
		registrar.playToServer(
				makeType(JumpPacket.ID),
				makeCodec(JumpPacket.ID, JumpPacket::decode),
				makeServerHandler(JumpPacket.ID, JumpPacket::handle));
		registrar.playToServer(
				makeType(LeftClickPacket.ID),
				makeCodec(LeftClickPacket.ID, LeftClickPacket::decode),
				makeServerHandler(LeftClickPacket.ID, LeftClickPacket::handle));

		// Clientbound
		registrar.playToClient(
				makeType(AvatarSkiesRodPacket.ID),
				makeCodec(AvatarSkiesRodPacket.ID, AvatarSkiesRodPacket::decode),
				makeClientHandler(AvatarSkiesRodPacket.Handler::handle));
		registrar.playToClient(
				makeType(BotaniaEffectPacket.ID),
				makeCodec(BotaniaEffectPacket.ID, BotaniaEffectPacket::decode),
				makeClientHandler(BotaniaEffectPacket.Handler::handle));
		registrar.playToClient(
				makeType(GogWorldPacket.ID),
				makeCodec(GogWorldPacket.ID, GogWorldPacket::decode),
				makeClientHandler(GogWorldPacket.Handler::handle));
		registrar.playToClient(
				makeType(ItemAgePacket.ID),
				makeCodec(ItemAgePacket.ID, ItemAgePacket::decode),
				makeClientHandler(ItemAgePacket.Handler::handle));
		registrar.playToClient(
				makeType(SpawnGaiaGuardianPacket.ID),
				makeCodec(SpawnGaiaGuardianPacket.ID, SpawnGaiaGuardianPacket::decode),
				makeClientHandler(SpawnGaiaGuardianPacket.Handler::handle));
		registrar.playToClient(
				makeType(UpdateItemsRemainingPacket.ID),
				makeCodec(UpdateItemsRemainingPacket.ID, UpdateItemsRemainingPacket::decode),
				makeClientHandler(UpdateItemsRemainingPacket.Handler::handle));
	}

	@SuppressWarnings("unchecked")
	private static <T extends BotaniaPacket> CustomPacketPayload.Type<BotaniaPayload<T>> makeType(ResourceLocation id) {
		return (CustomPacketPayload.Type<BotaniaPayload<T>>) (Object) new CustomPacketPayload.Type<>(id);
	}

	private static <T extends BotaniaPacket> StreamCodec<RegistryFriendlyByteBuf, BotaniaPayload<T>> makeCodec(
			ResourceLocation id, Function<RegistryFriendlyByteBuf, T> decoder) {
		CustomPacketPayload.Type<BotaniaPayload<T>> type = new CustomPacketPayload.Type<>(id);
		return StreamCodec.of(
				(buf, payload) -> payload.packet().encode(buf),
				buf -> new BotaniaPayload<>(type, decoder.apply(buf)));
	}

	private static <T extends BotaniaPacket> IPayloadHandler<BotaniaPayload<T>> makeServerHandler(
			ResourceLocation id, TriConsumer<T, MinecraftServer, ServerPlayer> handler) {
		return (payload, ctx) -> {
			var player = (ServerPlayer) ctx.player();
			handler.accept(payload.packet(), player.getServer(), player);
		};
	}

	private static <T extends BotaniaPacket> IPayloadHandler<BotaniaPayload<T>> makeClientHandler(Consumer<T> consumer) {
		return (payload, ctx) -> consumer.accept(payload.packet());
	}

	public static <T extends BotaniaPacket> void sendToPlayer(ServerPlayer player, T packet) {
		PacketDistributor.sendToPlayer(player, makeSendable(packet));
	}

	public static <T extends BotaniaPacket> void sendToTracking(net.minecraft.world.entity.Entity entity, T packet) {
		PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, makeSendable(packet));
	}

	public static <T extends BotaniaPacket> void sendToNear(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos, T packet) {
		if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
			PacketDistributor.sendToPlayersNear(serverLevel, null, pos.getX(), pos.getY(), pos.getZ(), 64, makeSendable(packet));
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends BotaniaPacket> BotaniaPayload<T> makeSendable(T packet) {
		CustomPacketPayload.Type<BotaniaPayload<T>> type = new CustomPacketPayload.Type<>(packet.getFabricId());
		return new BotaniaPayload<>(type, packet);
	}

	/**
	 * Adapter to make BotaniaPacket instances usable as CustomPacketPayload.
	 */
	public record BotaniaPayload<T extends BotaniaPacket>(CustomPacketPayload.Type<BotaniaPayload<T>> type, T packet)
			implements CustomPacketPayload {
		@Override
		public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
			return type;
		}
	}
}
