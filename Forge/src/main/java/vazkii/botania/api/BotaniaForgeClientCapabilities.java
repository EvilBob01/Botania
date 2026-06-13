package vazkii.botania.api;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block.WandHUD;

public final class BotaniaForgeClientCapabilities {
	private static final String MODID = BotaniaAPI.MODID;

	public static final BlockCapability<WandHUD, @Nullable Direction> WAND_HUD =
			BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(MODID, "wand_hud"), WandHUD.class);

	public static final EntityCapability<WandHUD, Void> WAND_HUD_ENTITY =
			EntityCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "wand_hud_entity"), WandHUD.class);

	private BotaniaForgeClientCapabilities() {}
}
