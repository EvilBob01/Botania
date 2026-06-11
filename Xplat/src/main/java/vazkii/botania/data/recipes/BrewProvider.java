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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.crafting.BotanicalBreweryRecipe;
import vazkii.botania.common.item.BotaniaItems;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class BrewProvider extends BotaniaRecipeProvider {
	public BrewProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		output.accept(idFor("speed"), new BotanicalBreweryRecipe(BotaniaBrews.speed, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SUGAR), Ingredient.of(Items.REDSTONE)), null);
		output.accept(idFor("strength"), new BotanicalBreweryRecipe(BotaniaBrews.strength, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.BLAZE_POWDER), Ingredient.of(Items.GLOWSTONE_DUST)), null);
		output.accept(idFor("haste"), new BotanicalBreweryRecipe(BotaniaBrews.haste, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SUGAR), Ingredient.of(Items.GOLD_NUGGET)), null);
		output.accept(idFor("healing"), new BotanicalBreweryRecipe(BotaniaBrews.healing, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GLISTERING_MELON_SLICE), Ingredient.of(Items.POTATO)), null);
		output.accept(idFor("jump_boost"), new BotanicalBreweryRecipe(BotaniaBrews.jumpBoost, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.FEATHER), Ingredient.of(Items.CARROT)), null);
		output.accept(idFor("regeneration"), new BotanicalBreweryRecipe(BotaniaBrews.regen, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GHAST_TEAR), Ingredient.of(Items.GLOWSTONE_DUST)), null);
		output.accept(idFor("weak_regeneration"), new BotanicalBreweryRecipe(BotaniaBrews.regenWeak, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GHAST_TEAR), Ingredient.of(Items.REDSTONE)), null);
		output.accept(idFor("resistance"), new BotanicalBreweryRecipe(BotaniaBrews.resistance, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.IRON_INGOT), Ingredient.of(Items.LEATHER)), null);
		output.accept(idFor("fire_resistance"), new BotanicalBreweryRecipe(BotaniaBrews.fireResistance, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.MAGMA_CREAM), Ingredient.of(Blocks.NETHERRACK)), null);
		output.accept(idFor("water_breathing"), new BotanicalBreweryRecipe(BotaniaBrews.waterBreathing, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.PRISMARINE_CRYSTALS), Ingredient.of(Items.GLOWSTONE_DUST)), null);
		output.accept(idFor("invisibility"), new BotanicalBreweryRecipe(BotaniaBrews.invisibility, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SNOWBALL), Ingredient.of(Items.GLOWSTONE_DUST)), null);
		output.accept(idFor("night_vision"), new BotanicalBreweryRecipe(BotaniaBrews.nightVision, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SPIDER_EYE), Ingredient.of(Items.GOLDEN_CARROT)), null);
		output.accept(idFor("absorption"), new BotanicalBreweryRecipe(BotaniaBrews.absorption, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GOLDEN_APPLE), Ingredient.of(Items.POTATO)), null);

		output.accept(idFor("overload"), new BotanicalBreweryRecipe(BotaniaBrews.overload, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.BLAZE_POWDER), Ingredient.of(Items.SUGAR), Ingredient.of(Items.GLOWSTONE_DUST), Ingredient.of(BotaniaItems.manaSteel), Ingredient.of(Items.SPIDER_EYE)), null);
		output.accept(idFor("soul_cross"), new BotanicalBreweryRecipe(BotaniaBrews.soulCross, Ingredient.of(Items.NETHER_WART), Ingredient.of(Blocks.SOUL_SAND), Ingredient.of(Items.PAPER), Ingredient.of(Items.APPLE), Ingredient.of(Items.BONE)), null);
		output.accept(idFor("feather_feet"), new BotanicalBreweryRecipe(BotaniaBrews.featherfeet, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.FEATHER), Ingredient.of(Items.LEATHER), Ingredient.of(ItemTags.WOOL)), null);
		output.accept(idFor("emptiness"), new BotanicalBreweryRecipe(BotaniaBrews.emptiness, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GUNPOWDER), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.BONE), Ingredient.of(Items.STRING), Ingredient.of(Items.ENDER_PEARL)), null);
		output.accept(idFor("bloodthirst"), new BotanicalBreweryRecipe(BotaniaBrews.bloodthirst, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.FERMENTED_SPIDER_EYE), Ingredient.of(Items.LAPIS_LAZULI), Ingredient.of(Items.FIRE_CHARGE), Ingredient.of(Items.IRON_INGOT)), null);
		output.accept(idFor("allure"), new BotanicalBreweryRecipe(BotaniaBrews.allure, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.COD), Ingredient.of(Items.QUARTZ), Ingredient.of(Items.GOLDEN_CARROT)), null);
		output.accept(idFor("clear"), new BotanicalBreweryRecipe(BotaniaBrews.clear, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.QUARTZ), Ingredient.of(Items.EMERALD), Ingredient.of(Items.MELON_SLICE)), null);
	}

	private static ResourceLocation idFor(String s) {
		return prefix("brew/" + s);
	}
}
