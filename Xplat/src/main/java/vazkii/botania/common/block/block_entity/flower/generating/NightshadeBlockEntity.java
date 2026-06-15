package vazkii.botania.common.block.block_entity.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.api.state.BotaniaStateProperties;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

public class NightshadeBlockEntity extends GeneratingFlowerBlockEntity {
	public NightshadeBlockEntity(BlockPos pos, BlockState state) {
		super(BotaniaBlockEntities.NIGHTSHADE, pos, state);
	}

	@Override
	public void tickFlower() {
		super.tickFlower();
		if (getLevel().isClientSide()) {
			return;
		}

		boolean isNight = !getLevel().isDay();
		BlockState state = getBlockState();
		if (state.hasProperty(BotaniaStateProperties.GENERATING)
				&& state.getValue(BotaniaStateProperties.GENERATING) != isNight) {
			getLevel().setBlock(getBlockPos(), state.setValue(BotaniaStateProperties.GENERATING, isNight),
					Block.UPDATE_CLIENTS);
		}

		if (isNight && shouldUpdateThisTick() && getMana() < getMaxMana()) {
			addMana(1);
		}
	}

	@Override
	protected int getUpdateInterval() {
		return 20;
	}

	@Override
	public int getMaxMana() {
		return 300;
	}

	@Override
	public int getColor() {
		return 0x3C0064;
	}

	@Nullable
	@Override
	public RadiusDescriptor getRadius() {
		return null;
	}
}
