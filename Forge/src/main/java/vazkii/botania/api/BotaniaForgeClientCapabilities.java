package vazkii.botania.api;

import net.neoforged.neoforge.capabilities.Capability;
import net.neoforged.neoforge.capabilities.CapabilityManager;
import net.neoforged.neoforge.capabilities.CapabilityToken;

import vazkii.botania.api.block.WandHUD;

public final class BotaniaForgeClientCapabilities {
	public static final Capability<WandHUD> WAND_HUD = CapabilityManager.get(new CapabilityToken<>() {});

	private BotaniaForgeClientCapabilities() {}
}
