package vazkii.botania.common.block.block_entity.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.api.state.BotaniaStateProperties;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

public class DaybloomBlockEntity extends GeneratingFlowerBlockEntity {
	public DaybloomBlockEntity(BlockPos pos, BlockState state) {
		super(BotaniaBlockEntities.DAYBLOOM, pos, state);
	}

	@Override
	public void tickFlower() {
		super.tickFlower();
		if (getLevel().isClientSide()) {
			return;
		}

		boolean isDay = getLevel().isDay();
		BlockState state = getBlockState();
		if (state.hasProperty(BotaniaStateProperties.GENERATING)
				&& state.getValue(BotaniaStateProperties.GENERATING) != isDay) {
			getLevel().setBlock(getBlockPos(), state.setValue(BotaniaStateProperties.GENERATING, isDay),
					Block.UPDATE_CLIENTS);
		}

		if (isDay && shouldUpdateThisTick() && getMana() < getMaxMana()) {
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
		return 0xFFC200;
	}

	@Nullable
	@Override
	public RadiusDescriptor getRadius() {
		return null;
	}
}
