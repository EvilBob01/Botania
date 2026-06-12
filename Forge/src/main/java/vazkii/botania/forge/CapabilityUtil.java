package vazkii.botania.forge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.fluids.capability.templates.VoidFluidHandler;
import net.minecraft.world.level.material.Fluids;

import org.jetbrains.annotations.NotNull;

public final class CapabilityUtil {
	public static class WaterBowlFluidHandler extends FluidHandlerItemStackSimple.SwapEmpty {
		public WaterBowlFluidHandler(ItemStack stack) {
			super(stack, new ItemStack(Items.BOWL), FluidType.BUCKET_VOLUME);
			setFluid(new FluidStack(Fluids.WATER, FluidType.BUCKET_VOLUME));
		}

		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			return false;
		}

		@Override
		public boolean canDrainFluidType(FluidStack fluid) {
			return fluid.getFluid() == Fluids.WATER;
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
