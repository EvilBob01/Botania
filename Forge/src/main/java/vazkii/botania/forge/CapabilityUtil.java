package vazkii.botania.forge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.VoidFluidHandler;
import net.minecraft.world.level.material.Fluids;

import org.jetbrains.annotations.NotNull;

public final class CapabilityUtil {
	public static class WaterBowlFluidHandler implements IFluidHandlerItem {
		private ItemStack container;

		public WaterBowlFluidHandler(ItemStack stack) {
			this.container = stack;
		}

		@NotNull
		@Override
		public ItemStack getContainer() {
			return container;
		}

		@Override
		public int getTanks() {
			return 1;
		}

		@NotNull
		@Override
		public FluidStack getFluidInTank(int tank) {
			return new FluidStack(Fluids.WATER, FluidType.BUCKET_VOLUME);
		}

		@Override
		public int getTankCapacity(int tank) {
			return FluidType.BUCKET_VOLUME;
		}

		@Override
		public boolean isFluidValid(int tank, FluidStack stack) {
			return false;
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			return 0;
		}

		@NotNull
		@Override
		public FluidStack drain(FluidStack resource, FluidAction action) {
			if (!resource.isEmpty() && FluidStack.isSameFluid(resource, new FluidStack(Fluids.WATER, 1))) {
				return drain(resource.getAmount(), action);
			}
			return FluidStack.EMPTY;
		}

		@NotNull
		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			int amount = Math.min(maxDrain, FluidType.BUCKET_VOLUME);
			if (amount > 0 && action.execute()) {
				container = new ItemStack(Items.BOWL);
			}
			return new FluidStack(Fluids.WATER, amount);
		}
	}

	public static class ExtrapolatedBucketFluidHandler extends VoidFluidHandler implements IFluidHandlerItem {
		private final ItemStack container;

		public ExtrapolatedBucketFluidHandler(ItemStack container) {
			this.container = container;
		}

		@NotNull
		@Override
		public ItemStack getContainer() {
			return container;
		}

		/**
		 * Drain things one bucket worth of fluid at a time.
		 */
		@Override
		public int fill(FluidStack resource, FluidAction action) {
			return Math.min(FluidType.BUCKET_VOLUME, resource.getAmount());
		}
	}

	private CapabilityUtil() {}
}
