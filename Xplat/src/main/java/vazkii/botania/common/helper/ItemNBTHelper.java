/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.helper;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.xplat.XplatAbstractions;

public final class ItemNBTHelper {

	private static final int[] EMPTY_INT_ARRAY = new int[0];
	private static final long[] EMPTY_LONG_ARRAY = new long[0];

	// Internal DataComponents helpers ///////////////////////////////////////////

	private static CompoundTag getCustomDataTag(ItemStack stack) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
	}

	private static void setCustomDataTag(ItemStack stack, CompoundTag tag) {
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
	}

	private static boolean hasCustomData(ItemStack stack) {
		return !stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).isEmpty();
	}

	// SETTERS ///////////////////////////////////////////////////////////////////

	public static void set(ItemStack stack, String tag, Tag nbt) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.put(tag, nbt);
		setCustomDataTag(stack, cmp);
	}

	public static void setBoolean(ItemStack stack, String tag, boolean b) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putBoolean(tag, b);
		setCustomDataTag(stack, cmp);
	}

	public static void setByte(ItemStack stack, String tag, byte b) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putByte(tag, b);
		setCustomDataTag(stack, cmp);
	}

	public static void setShort(ItemStack stack, String tag, short s) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putShort(tag, s);
		setCustomDataTag(stack, cmp);
	}

	public static void setInt(ItemStack stack, String tag, int i) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putInt(tag, i);
		setCustomDataTag(stack, cmp);
	}

	public static void setIntArray(ItemStack stack, String tag, int[] val) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putIntArray(tag, val);
		setCustomDataTag(stack, cmp);
	}

	public static void setLong(ItemStack stack, String tag, long l) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putLong(tag, l);
		setCustomDataTag(stack, cmp);
	}

	public static void setLongArray(ItemStack stack, String tag, long[] val) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putLongArray(tag, val);
		setCustomDataTag(stack, cmp);
	}

	public static void setFloat(ItemStack stack, String tag, float f) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putFloat(tag, f);
		setCustomDataTag(stack, cmp);
	}

	public static void setDouble(ItemStack stack, String tag, double d) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putDouble(tag, d);
		setCustomDataTag(stack, cmp);
	}

	public static void setCompound(ItemStack stack, String tag, CompoundTag compound) {
		if (!tag.equalsIgnoreCase("ench")) // not override the enchantments
		{
			CompoundTag cmp = getCustomDataTag(stack);
			cmp.put(tag, compound);
			setCustomDataTag(stack, cmp);
		}
	}

	public static void setString(ItemStack stack, String tag, String s) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.putString(tag, s);
		setCustomDataTag(stack, cmp);
	}

	public static void setList(ItemStack stack, String tag, ListTag list) {
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.put(tag, list);
		setCustomDataTag(stack, cmp);
	}

	public static void removeEntry(ItemStack stack, String tag) {
		if (!hasCustomData(stack)) {
			return;
		}
		CompoundTag cmp = getCustomDataTag(stack);
		cmp.remove(tag);
		if (cmp.isEmpty()) {
			stack.remove(DataComponents.CUSTOM_DATA);
		} else {
			setCustomDataTag(stack, cmp);
		}
	}

	// GETTERS ///////////////////////////////////////////////////////////////////

	public static boolean verifyExistance(ItemStack stack, String tag) {
		return !stack.isEmpty() && hasCustomData(stack) && getCustomDataTag(stack).contains(tag);
	}

	public static boolean verifyType(ItemStack stack, String tag, Class<? extends Tag> tagClass) {
		return !stack.isEmpty() && hasCustomData(stack) && tagClass.isInstance(getCustomDataTag(stack).get(tag));
	}

	@Nullable
	public static Tag get(ItemStack stack, String tag) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).get(tag) : null;
	}

	public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getBoolean(tag) : defaultExpected;
	}

	public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getByte(tag) : defaultExpected;
	}

	public static short getShort(ItemStack stack, String tag, short defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getShort(tag) : defaultExpected;
	}

	public static int getInt(ItemStack stack, String tag, int defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getInt(tag) : defaultExpected;
	}

	public static int[] getIntArray(ItemStack stack, String tag) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getIntArray(tag) : EMPTY_INT_ARRAY;
	}

	public static long getLong(ItemStack stack, String tag, long defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getLong(tag) : defaultExpected;
	}

	public static long[] getLongArray(ItemStack stack, String tag) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getLongArray(tag) : EMPTY_LONG_ARRAY;
	}

	public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getFloat(tag) : defaultExpected;
	}

	public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getDouble(tag) : defaultExpected;
	}

	/**
	 * If nullifyOnFail is true it'll return null if it doesn't find any
	 * compounds, otherwise it'll return a new one.
	 **/
	@Nullable
	@Contract("_, _, false -> !null")
	public static CompoundTag getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getCompound(tag) : nullifyOnFail ? null : new CompoundTag();
	}

	@Nullable
	@Contract("_, _, !null -> !null")
	public static String getString(ItemStack stack, String tag, String defaultExpected) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getString(tag) : defaultExpected;
	}

	@Nullable
	@Contract("_, _, _, false -> !null")
	public static ListTag getList(ItemStack stack, String tag, int objtype, boolean nullifyOnFail) {
		return verifyExistance(stack, tag) ? getCustomDataTag(stack).getList(tag, objtype) : nullifyOnFail ? null : new ListTag();
	}

	// OTHER ///////////////////////////////////////////////////////////////////

	/**
	 * Returns the fullness of the mana item:
	 * 0 if empty, 1 if partially full, 2 if full.
	 */
	public static int getFullness(ManaItem item) {
		int mana = item.getMana();
		if (mana == 0) {
			return 0;
		} else if (mana == item.getMaxMana()) {
			return 2;
		} else {
			return 1;
		}
	}

	public static ItemStack duplicateAndClearMana(ItemStack stack) {
		ItemStack copy = stack.copy();
		ManaItem manaItem = XplatAbstractions.INSTANCE.findManaItem(copy);
		if (manaItem != null) {
			manaItem.addMana(-manaItem.getMana());
		}
		return copy;
	}

	/**
	 * Checks if two items are the same and have the same NBT. If they are `IManaItems`, their mana property is matched
	 * on whether they are empty, partially full, or full.
	 */
	public static boolean matchTagAndManaFullness(ItemStack stack1, ItemStack stack2) {
		if (!ItemStack.isSameItem(stack1, stack2)) {
			return false;
		}
		ManaItem manaItem1 = XplatAbstractions.INSTANCE.findManaItem(stack1);
		ManaItem manaItem2 = XplatAbstractions.INSTANCE.findManaItem(stack2);
		if (manaItem1 != null && manaItem2 != null) {
			if (getFullness(manaItem1) != getFullness(manaItem2)) {
				return false;
			} else {
				return ItemStack.matches(duplicateAndClearMana(stack1), duplicateAndClearMana(stack2));
			}
		}
		return ItemStack.isSameItemSameComponents(stack1, stack2);
	}

	/**
	 * Serializes the given stack such that {@link net.minecraft.world.item.crafting.ShapedRecipe#itemStackFromJson}
	 * would be able to read the result back
	 * TODO: DataComponents migration — ItemStack.save() format changed in 1.20.5+
	 */
	public static JsonObject serializeStack(ItemStack stack) {
		CompoundTag nbt = (CompoundTag) ItemStack.STRICT_CODEC.encodeStart(NbtOps.INSTANCE, stack)
				.result().orElse(new CompoundTag());
		Dynamic<Tag> dyn = new Dynamic<>(NbtOps.INSTANCE, nbt);
		return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
	}

	public static void renameTag(CompoundTag nbt, String oldName, String newName) {
		Tag tag = nbt.get(oldName);
		if (tag != null) {
			nbt.remove(oldName);
			nbt.put(newName, tag);
		}
	}
}
