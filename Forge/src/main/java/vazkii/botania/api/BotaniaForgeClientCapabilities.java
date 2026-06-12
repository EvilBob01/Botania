package vazkii.botania.api;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block.WandHUD;

public final class BotaniaForgeClientCapabilities {
	private static final String MODID = BotaniaAPI.MODID;

	public static final BlockCapability<WandHUD, @Nullable Direction> WAND_HUD =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "wand_hud"), WandHUD.class);

	private BotaniaForgeClientCapabilities() {}
}
