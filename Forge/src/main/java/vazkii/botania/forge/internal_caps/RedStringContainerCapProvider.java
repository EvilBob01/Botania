package vazkii.botania.forge.internal_caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.block.block_entity.red_string.RedStringContainerBlockEntity;
import vazkii.botania.common.block.BotaniaBlocks;

/**
 * Provides an item handler capability for the Red String Container block entity.
 * It delegates to the bound block entity's item handler.
 */
public final class RedStringContainerCapProvider {
	@Nullable
	public static IItemHandler getItemHandler(RedStringContainerBlockEntity container, @Nullable Direction side) {
		BlockEntity binding = container.getTileAtBinding();
		if (binding != null && container.getLevel() != null) {
			return container.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, binding.getBlockPos(), side);
		}
		return null;
	}

	private RedStringContainerCapProvider() {}
}
