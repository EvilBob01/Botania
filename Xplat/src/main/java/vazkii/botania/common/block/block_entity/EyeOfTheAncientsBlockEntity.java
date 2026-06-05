/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.block.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EyeOfTheAncientsBlockEntity extends BotaniaBlockEntity {
	public int entities = 0;

	public EyeOfTheAncientsBlockEntity(BlockPos pos, BlockState state) {
		super(BotaniaBlockEntities.FOREST_EYE, pos, state);
	}

	public static void serverTick(Level level, BlockPos worldPosition, BlockState state, EyeOfTheAncientsBlockEntity self) {
		int range = 6;
		int entityCount = level.getEntitiesOfClass(Animal.class, new AABB(worldPosition.getX() - range, worldPosition.getY() - range, worldPosition.getZ() - range, worldPosition.getX() + range + 2.0, worldPosition.getY() + range + 2.0, worldPosition.getZ() + range + 2.0)).size();
		if (entityCount != self.entities) {
			self.entities = entityCount;
			level.updateNeighbourForOutputSignal(worldPosition, state.getBlock());
		}
	}

}
