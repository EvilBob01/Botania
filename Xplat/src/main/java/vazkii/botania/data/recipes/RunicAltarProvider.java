/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.RunicAltarRecipe;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class RunicAltarProvider extends BotaniaRecipeProvider {
	public RunicAltarProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "Botania runic altar recipes";
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		final int costTier1 = 5200;
		final int costTier2 = 8000;
		final int costTier3 = 12000;

		Ingredient manaSteel = Ingredient.of(BotaniaTags.Items.INGOTS_MANASTEEL);
		Ingredient manaDiamond = Ingredient.of(BotaniaTags.Items.GEMS_MANA_DIAMOND);
		Ingredient manaPowder = Ingredient.of(BotaniaTags.Items.DUSTS_MANA);
		output.accept(idFor("water"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeWater, 2), costTier1, manaPowder, manaSteel, Ingredient.of(Items.BONE_MEAL), Ingredient.of(Blocks.SUGAR_CANE), Ingredient.of(Items.FISHING_ROD)), null);
		output.accept(idFor("fire"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeFire, 2), costTier1, manaPowder, manaSteel, Ingredient.of(Items.NETHER_BRICK), Ingredient.of(Items.GUNPOWDER), Ingredient.of(Items.NETHER_WART)), null);

		Ingredient stone = Ingredient.of(Blocks.STONE);
		Ingredient coalBlock = Ingredient.of(Blocks.COAL_BLOCK);
		output.accept(idFor("earth"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeEarth, 2), costTier1, manaPowder, manaSteel, stone, coalBlock, Ingredient.of(Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM)), null);

		output.accept(idFor("air"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeAir, 2), costTier1, manaPowder, manaSteel, Ingredient.of(ItemTags.WOOL_CARPETS), Ingredient.of(Items.FEATHER), Ingredient.of(Items.STRING)), null);

		Ingredient fire = Ingredient.of(BotaniaItems.runeFire);
		Ingredient water = Ingredient.of(BotaniaItems.runeWater);
		Ingredient earth = Ingredient.of(BotaniaItems.runeEarth);
		Ingredient air = Ingredient.of(BotaniaItems.runeAir);

		Ingredient sapling = Ingredient.of(ItemTags.SAPLINGS);
		Ingredient leaves = Ingredient.of(ItemTags.LEAVES);
		Ingredient sand = Ingredient.of(ItemTags.SAND);
		output.accept(idFor("spring"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeSpring), costTier2, water, fire, sapling, sapling, sapling, Ingredient.of(Items.WHEAT)), null);
		output.accept(idFor("summer"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeSummer), costTier2, earth, air, sand, sand, Ingredient.of(Items.SLIME_BALL), Ingredient.of(Items.MELON_SLICE)), null);
		output.accept(idFor("autumn"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeAutumn), costTier2, fire, air, leaves, leaves, leaves, Ingredient.of(Items.SPIDER_EYE)), null);

		output.accept(idFor("winter"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeWinter), costTier2, water, earth, Ingredient.of(Blocks.SNOW_BLOCK), Ingredient.of(Blocks.SNOW_BLOCK), Ingredient.of(ItemTags.WOOL), Ingredient.of(Blocks.CAKE)), null);

		Ingredient spring = Ingredient.of(BotaniaItems.runeSpring);
		Ingredient summer = Ingredient.of(BotaniaItems.runeSummer);
		Ingredient autumn = Ingredient.of(BotaniaItems.runeAutumn);
		Ingredient winter = Ingredient.of(BotaniaItems.runeWinter);

		output.accept(idFor("mana"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeMana), costTier2, manaSteel, manaSteel, manaSteel, manaSteel, manaSteel, Ingredient.of(BotaniaItems.manaPearl)), null);

		output.accept(idFor("lust"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeLust), costTier3, manaDiamond, manaDiamond, summer, air), null);
		output.accept(idFor("gluttony"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeGluttony), costTier3, manaDiamond, manaDiamond, winter, fire), null);
		output.accept(idFor("greed"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeGreed), costTier3, manaDiamond, manaDiamond, spring, water), null);
		output.accept(idFor("sloth"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeSloth), costTier3, manaDiamond, manaDiamond, autumn, air), null);
		output.accept(idFor("wrath"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeWrath), costTier3, manaDiamond, manaDiamond, winter, earth), null);
		output.accept(idFor("envy"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runeEnvy), costTier3, manaDiamond, manaDiamond, winter, water), null);
		output.accept(idFor("pride"), new RunicAltarRecipe(new ItemStack(BotaniaItems.runePride), costTier3, manaDiamond, manaDiamond, summer, fire), null);

		output.accept(idFor("head"), new RunicAltarRecipe(new ItemStack(Items.PLAYER_HEAD), 22500, Ingredient.of(Items.SKELETON_SKULL), Ingredient.of(BotaniaItems.pixieDust), Ingredient.of(Items.PRISMARINE_CRYSTALS), Ingredient.of(Items.NAME_TAG, Items.WRITTEN_BOOK), Ingredient.of(Items.GOLDEN_APPLE)) {
			@Override
			public RecipeSerializer<?> getSerializer() {
				return BotaniaRecipeTypes.RUNE_HEAD_SERIALIZER;
			}
		}, null);
	}

	private static ResourceLocation idFor(String s) {
		return prefix("runic_altar/" + s);
	}
}
