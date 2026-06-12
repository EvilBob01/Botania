package vazkii.botania.forge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.common.lib.LibMisc;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

@EventBusSubscriber(modid = LibMisc.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ForgeRegistryHandler {
	@SubscribeEvent
	public static void registerRegistry(NewRegistryEvent evt) {
		evt.create(new RegistryBuilder<>(BotaniaRegistries.BREWS)
				.defaultKey(prefix("fallback")));
	}
}
