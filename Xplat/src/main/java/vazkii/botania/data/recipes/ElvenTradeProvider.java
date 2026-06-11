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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.ElvenTradeRecipe;
import vazkii.botania.common.crafting.LexiconElvenTradeRecipe;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class ElvenTradeProvider extends BotaniaRecipeProvider {
	public ElvenTradeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		output.accept(id("dreamwood_log"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaBlocks.dreamwoodLog) }, Ingredient.of(BotaniaBlocks.livingwoodLog)), null);
		output.accept(id("dreamwood"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaBlocks.dreamwood) }, Ingredient.of(BotaniaBlocks.livingwood)), null);

		Ingredient manaDiamond = Ingredient.of(BotaniaTags.Items.GEMS_MANA_DIAMOND);
		Ingredient manaSteel = Ingredient.of(BotaniaTags.Items.INGOTS_MANASTEEL);
		output.accept(id("elementium"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaItems.elementium) }, manaSteel, manaSteel), null);
		output.accept(id("elementium_block"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaBlocks.elementiumBlock) }, Ingredient.of(BotaniaBlocks.manasteelBlock), Ingredient.of(BotaniaBlocks.manasteelBlock)), null);

		output.accept(id("pixie_dust"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaItems.pixieDust) }, Ingredient.of(BotaniaItems.manaPearl)), null);
		output.accept(id("dragonstone"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaItems.dragonstone) }, manaDiamond), null);
		output.accept(id("dragonstone_block"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaBlocks.dragonstoneBlock) }, Ingredient.of(BotaniaBlocks.manaDiamondBlock)), null);

		output.accept(id("elf_quartz"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaItems.elfQuartz) }, Ingredient.of(Items.QUARTZ)), null);
		output.accept(id("elf_glass"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(BotaniaBlocks.elfGlass) }, Ingredient.of(BotaniaBlocks.manaGlass)), null);

		output.accept(id("iron_return"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(Items.IRON_INGOT) }, Ingredient.of(Items.IRON_INGOT)), null);
		output.accept(id("iron_block_return"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(Blocks.IRON_BLOCK) }, Ingredient.of(Blocks.IRON_BLOCK)), null);
		output.accept(id("ender_pearl_return"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(Items.ENDER_PEARL) }, Ingredient.of(Items.ENDER_PEARL)), null);
		output.accept(id("diamond_return"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(Items.DIAMOND) }, Ingredient.of(Items.DIAMOND)), null);
		output.accept(id("diamond_block_return"), new ElvenTradeRecipe(new ItemStack[] { new ItemStack(Blocks.DIAMOND_BLOCK) }, Ingredient.of(Blocks.DIAMOND_BLOCK)), null);

		output.accept(id("lexicon_elven"), new LexiconElvenTradeRecipe(), null);
	}

	private static ResourceLocation id(String path) {
		return prefix("elven_trade/" + path);
	}

}
