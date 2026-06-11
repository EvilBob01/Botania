package vazkii.botania.common.helper;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.mixin.BundleItemAccessor;

import java.util.ArrayList;
import java.util.List;

public class FilterHelper {

	public static final String ITEMS_TAG = "Items";

	public static List<ItemStack> getFilterItems(ItemFrame filterFrame) {
		ItemStack filterStack = filterFrame.getItem();
		if (filterStack.isEmpty()) {
			return List.of();
		}
		return filterFrame instanceof GlowItemFrame ? getFilterStacks(filterStack) : List.of(filterStack);
	}

	/**
	 * Expands the given filter item into the list of filter items it represents. This is NOT recursive.
	 * Non-empty container items stand for their contents, not for themselves.
	 */
	public static List<ItemStack> getFilterStacks(ItemStack filterStack) {
		if (filterStack.is(Items.BUNDLE)) {
			// get bundle content
			List<ItemStack> bundledItems = BundleItemAccessor.call_getContents(filterStack).toList();
			if (!bundledItems.isEmpty()) {
				return bundledItems;
			}
		} else {
			// BlockItems (especially shulker boxes) can contain BlockEntity data, which may include an inventory.
			// Otherwise, items may represent an inventory themselves (e.g. Flower Pouch or Bauble Box)
			CompoundTag tag = filterStack.getItem() instanceof BlockItem
					? (filterStack.has(DataComponents.BLOCK_ENTITY_DATA) ? filterStack.get(DataComponents.BLOCK_ENTITY_DATA).copyTag() : null)
					: (filterStack.has(DataComponents.CUSTOM_DATA) ? filterStack.get(DataComponents.CUSTOM_DATA).copyTag() : null);
			if (tag != null && tag.contains(ITEMS_TAG, Tag.TAG_LIST)) {
				// item might contain an inventory
				List<ItemStack> items = getItemStacks(tag);
				if (items != null) {
					return items;
				}
			}
		}
		return List.of(filterStack);
	}

	@Nullable
	private static List<ItemStack> getItemStacks(CompoundTag tag) {
		try {
			ListTag contents = tag.getList(ITEMS_TAG, CompoundTag.TAG_COMPOUND);
			List<ItemStack> items = new ArrayList<>(contents.size());
			for (int i = 0; i < contents.size(); i++) {
				CompoundTag entry = contents.getCompound(i);
				ItemStack stack = ItemStack.parseOptional(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), entry);
				if (!stack.isEmpty()) {
					items.add(stack);
				}
			}
			if (!items.isEmpty()) {
				return items;
			}
		} catch (ClassCastException ce) {
			// apparently not a typical container inventory
		}
		return null;
	}

	public record WeightedItemStack(ItemStack stack, Weight weight) implements WeightedEntry {
		public static WeightedItemStack of(ItemStack stack, int weight) {
			return new WeightedItemStack(stack, Weight.of(weight));
		}

		@NotNull
		@Override
		public Weight getWeight() {
			return weight;
		}
	}
}
