package vazkii.botania.common.block.block_entity.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.api.state.BotaniaStateProperties;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;
import vazkii.botania.common.component.BotaniaDataComponents;

public class NightshadeBlockEntity extends GeneratingFlowerBlockEntity {
	private static final String TAG_PASSIVE_DECAY_TICKS = "passiveDecayTicks";
	private int passiveDecayTicks;

	public NightshadeBlockEntity(BlockPos pos, BlockState state) {
		super(BotaniaBlockEntities.NIGHTSHADE, pos, state);
	}

	@Override
	public void tickFlower() {
		super.tickFlower();
		if (getLevel().isClientSide()) {
			return;
		}

		if (getLevel().getBlockState(getBlockPos().below()).is(BotaniaBlocks.ENCHANTED_SOIL)) {
			passiveDecayTicks = 0;
		} else if (++passiveDecayTicks > HydroangeasBlockEntity.DECAY_TIME) {
			getLevel().destroyBlock(getBlockPos(), false);
			if (Blocks.DEAD_BUSH.defaultBlockState().canSurvive(getLevel(), getBlockPos())) {
				getLevel().setBlockAndUpdate(getBlockPos(), Blocks.DEAD_BUSH.defaultBlockState());
			}
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
	public void loadAdditional(CompoundTag cmp, HolderLookup.Provider registries) {
		super.loadAdditional(cmp, registries);
		passiveDecayTicks = cmp.getInt(TAG_PASSIVE_DECAY_TICKS);
	}

	@Override
	public void saveAdditional(CompoundTag cmp, HolderLookup.Provider registries) {
		super.saveAdditional(cmp, registries);
		cmp.putInt(TAG_PASSIVE_DECAY_TICKS, passiveDecayTicks);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);
		if (passiveDecayTicks > 0) {
			components.set(BotaniaDataComponents.DECAY_TICKS, passiveDecayTicks);
		}
	}

	@Override
	protected void applyImplicitComponents(DataComponentInput componentInput) {
		super.applyImplicitComponents(componentInput);
		passiveDecayTicks = componentInput.getOrDefault(BotaniaDataComponents.DECAY_TICKS, 0);
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
