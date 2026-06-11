/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.crafting.ManaInfusionRecipe;
import vazkii.botania.common.crafting.StateIngredientHelper;
import vazkii.botania.common.helper.ColorHelper;
import vazkii.botania.common.item.BotaniaItems;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class ManaInfusionProvider extends BotaniaRecipeProvider {
	public ManaInfusionProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		output.accept(id("manasteel"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaSteel), Ingredient.of(Items.IRON_INGOT), 3000, null, null), null);
		output.accept(id("manasteel_block"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.manasteelBlock), ingr(Blocks.IRON_BLOCK), 27000, null, null), null);

		output.accept(id("mana_pearl"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaPearl), ingr(Items.ENDER_PEARL), 6000, null, null), null);

		output.accept(id("mana_diamond"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaDiamond), Ingredient.of(Items.DIAMOND), 10000, null, null), null);
		output.accept(id("mana_diamond_block"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.manaDiamondBlock), ingr(Blocks.DIAMOND_BLOCK), 90000, null, null), null);

		Ingredient dust = Ingredient.of(Items.GUNPOWDER, Items.REDSTONE, Items.GLOWSTONE_DUST, Items.SUGAR);
		output.accept(id("mana_powder_dust"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaPowder), dust, 500, null, null), null);
		Ingredient dyeIngredient = Ingredient.of(ColorHelper.supportedColors().map(DyeItem::byColor).toArray(Item[]::new));
		output.accept(id("mana_powder_dye"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaPowder), dyeIngredient, 400, null, null), null);

		output.accept(id("piston_relay"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.pistonRelay), ingr(Blocks.PISTON), 15000, null, null), null);
		output.accept(id("mana_cookie"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaCookie), ingr(Items.COOKIE), 20000, null, null), null);
		output.accept(id("grass_seeds"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.grassSeeds), ingr(Blocks.SHORT_GRASS), 2500, null, null), null);
		output.accept(id("podzol_seeds"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.podzolSeeds), ingr(Blocks.DEAD_BUSH), 2500, null, null), null);

		output.accept(id("mycel_seeds"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.mycelSeeds), Ingredient.of(Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM), 6500, null, null), null);

		output.accept(id("mana_quartz"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaQuartz), ingr(Items.QUARTZ), 250, null, null), null);
		output.accept(id("tiny_potato"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.tinyPotato), ingr(Items.POTATO), 1337, null, null), null);

		output.accept(id("mana_glass"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.manaGlass), ingr(Blocks.GLASS), 150, null, null), null);
		output.accept(id("mana_string"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaString), ingr(Items.STRING), 1250, null, null), null);

		output.accept(id("mana_bottle"), new ManaInfusionRecipe(new ItemStack(BotaniaItems.manaBottle), ingr(Items.GLASS_BOTTLE), 5000, null, null), null);

		output.accept(id("rotten_flesh_to_leather"), new ManaInfusionRecipe(new ItemStack(Items.LEATHER), ingr(Items.ROTTEN_FLESH), 600, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		cycle(output, 40, "botania:log_cycle", Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG);
		cycle(output, 40, "botania:froglight_cycle", Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT);
		cycle(output, 120, "botania:sapling_cycle", Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING, Blocks.MANGROVE_PROPAGULE, Blocks.CHERRY_SAPLING);

		deconstruct(output, "glowstone_deconstruct", Items.GLOWSTONE_DUST, Blocks.GLOWSTONE);
		deconstruct(output, "quartz_deconstruct", Items.QUARTZ, Blocks.QUARTZ_BLOCK);
		deconstruct(output, "dark_quartz_deconstruct", BotaniaItems.darkQuartz, BotaniaBlocks.darkQuartz);
		deconstruct(output, "mana_quartz_deconstruct", BotaniaItems.manaQuartz, BotaniaBlocks.manaQuartz);
		deconstruct(output, "blaze_quartz_deconstruct", BotaniaItems.blazeQuartz, BotaniaBlocks.blazeQuartz);
		deconstruct(output, "lavender_quartz_deconstruct", BotaniaItems.lavenderQuartz, BotaniaBlocks.lavenderQuartz);
		deconstruct(output, "red_quartz_deconstruct", BotaniaItems.redQuartz, BotaniaBlocks.redQuartz);
		deconstruct(output, "elf_quartz_deconstruct", BotaniaItems.elfQuartz, BotaniaBlocks.elfQuartz);
		deconstruct(output, "sunny_quartz_deconstruct", BotaniaItems.sunnyQuartz, BotaniaBlocks.sunnyQuartz);

		output.accept(id("chiseled_stone_bricks"), new ManaInfusionRecipe(new ItemStack(Blocks.CHISELED_STONE_BRICKS, 1), ingr(Blocks.STONE_BRICKS), 150, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("ice"), new ManaInfusionRecipe(new ItemStack(Blocks.ICE), ingr(Blocks.SNOW_BLOCK), 2250, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		final String vineLilypadGroup = "botania:vine_and_lily_pad_cycle";
		output.accept(id("vine_to_lily_pad"), new ManaInfusionRecipe(new ItemStack(Blocks.LILY_PAD), ingr(Blocks.VINE), 320, vineLilypadGroup, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("lily_pad_to_vine"), new ManaInfusionRecipe(new ItemStack(Blocks.VINE), ingr(Blocks.LILY_PAD), 320, vineLilypadGroup, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		cycle(output, 200, "botania:fish_cycle", Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH);
		cycle(output, 6000, "botania:crop_cycle", Items.COCOA_BEANS, Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS);

		output.accept(id("potato_unpoison"), new ManaInfusionRecipe(new ItemStack(Items.POTATO), ingr(Items.POISONOUS_POTATO), 1200, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("blaze_rod_to_nether_wart"), new ManaInfusionRecipe(new ItemStack(Items.NETHER_WART), ingr(Items.BLAZE_ROD), 4000, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		cycle(output, 200, "", Items.GUNPOWDER, Items.FLINT);

		output.accept(id("book_to_name_tag"), new ManaInfusionRecipe(new ItemStack(Items.NAME_TAG), ingr(Items.WRITABLE_BOOK), 6000, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		output.accept(id("wool_deconstruct"), new ManaInfusionRecipe(new ItemStack(Items.STRING, 3), Ingredient.of(ItemTags.WOOL), 100, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		final String cactusSlimeGroup = "botania:cactus_and_slime_cycle";
		output.accept(id("cactus_to_slime"), new ManaInfusionRecipe(new ItemStack(Items.SLIME_BALL), ingr(Blocks.CACTUS), 1200, cactusSlimeGroup, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("slime_to_cactus"), new ManaInfusionRecipe(new ItemStack(Blocks.CACTUS), ingr(Items.SLIME_BALL), 1200, cactusSlimeGroup, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		output.accept(id("ender_pearl_from_ghast_tear"), new ManaInfusionRecipe(new ItemStack(Items.ENDER_PEARL), ingr(Items.GHAST_TEAR), 28000, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		cycle(output, 300, "botania:glowstone_and_redstone_cycle", Items.GLOWSTONE_DUST, Items.REDSTONE);

		output.accept(id("cobble_to_sand"), new ManaInfusionRecipe(new ItemStack(Blocks.SAND), ingr(Blocks.COBBLESTONE), 50, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("terracotta_to_red_sand"), new ManaInfusionRecipe(new ItemStack(Blocks.RED_SAND), ingr(Blocks.TERRACOTTA), 50, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		deconstruct(output, "clay_deconstruct", Items.CLAY_BALL, Blocks.CLAY);
		deconstruct(output, "brick_deconstruct", Items.BRICK, Blocks.BRICKS);

		output.accept(id("coarse_dirt"), new ManaInfusionRecipe(new ItemStack(Blocks.COARSE_DIRT), ingr(Blocks.DIRT), 120, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("soul_soil"), new ManaInfusionRecipe(new ItemStack(Blocks.SOUL_SOIL), ingr(Blocks.SOUL_SAND), 120, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		output.accept(id("stone_to_andesite"), new ManaInfusionRecipe(new ItemStack(Blocks.ANDESITE), ingr(Blocks.STONE), 200, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		cycle(output, 200, "botania:stone_cycle", Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE);

		cycle(output, 200, "botania:117_stone_cycle", Blocks.TUFF, Blocks.CALCITE, Blocks.DEEPSLATE);

		cycle(output, 500, "botania:shrub_cycle", Blocks.FERN, Blocks.DEAD_BUSH, Blocks.SHORT_GRASS);

		// NB: No wither rose is intentional
		cycle(output, 400, "botania:flower_cycle", Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP,
				Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY,
				Blocks.SUNFLOWER, Blocks.LILAC, Blocks.ROSE_BUSH, Blocks.PEONY);

		output.accept(id("dripleaf_shrinking"), new ManaInfusionRecipe(new ItemStack(Blocks.SMALL_DRIPLEAF), ingr(Items.BIG_DRIPLEAF), 500, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		output.accept(id("chorus_fruit_to_flower"), new ManaInfusionRecipe(new ItemStack(Blocks.CHORUS_FLOWER), ingr(Items.POPPED_CHORUS_FRUIT), 10000, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		cycle(output, 240, "botania:berry_cycle", Items.APPLE, Items.SWEET_BERRIES, Items.GLOW_BERRIES);

		mini(output, BotaniaFlowerBlocks.agricarnationChibi, BotaniaFlowerBlocks.agricarnation);
		mini(output, BotaniaFlowerBlocks.clayconiaChibi, BotaniaFlowerBlocks.clayconia);
		mini(output, BotaniaFlowerBlocks.bellethornChibi, BotaniaFlowerBlocks.bellethorn);
		mini(output, BotaniaFlowerBlocks.bubbellChibi, BotaniaFlowerBlocks.bubbell);
		mini(output, BotaniaFlowerBlocks.hopperhockChibi, BotaniaFlowerBlocks.hopperhock);
		mini(output, BotaniaFlowerBlocks.jiyuuliaChibi, BotaniaFlowerBlocks.jiyuulia);
		mini(output, BotaniaFlowerBlocks.tangleberrieChibi, BotaniaFlowerBlocks.tangleberrie);
		mini(output, BotaniaFlowerBlocks.marimorphosisChibi, BotaniaFlowerBlocks.marimorphosis);
		mini(output, BotaniaFlowerBlocks.rannuncarpusChibi, BotaniaFlowerBlocks.rannuncarpus);
		mini(output, BotaniaFlowerBlocks.solegnoliaChibi, BotaniaFlowerBlocks.solegnolia);

		output.accept(id("hydroangeas_motif"), new ManaInfusionRecipe(new ItemStack(BotaniaBlocks.motifHydroangeas), ingr(BotaniaFlowerBlocks.hydroangeas), 2500, null, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);

		output.accept(id("redstone_dupe"), new ManaInfusionRecipe(new ItemStack(Items.REDSTONE, 2), ingr(Items.REDSTONE), 5000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("glowstone_dupe"), new ManaInfusionRecipe(new ItemStack(Items.GLOWSTONE_DUST, 2), ingr(Items.GLOWSTONE_DUST), 5000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("quartz_dupe"), new ManaInfusionRecipe(new ItemStack(Items.QUARTZ, 2), ingr(Items.QUARTZ), 2500, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("coal_dupe"), new ManaInfusionRecipe(new ItemStack(Items.COAL, 2), ingr(Items.COAL), 2100, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("snowball_dupe"), new ManaInfusionRecipe(new ItemStack(Items.SNOWBALL, 2), ingr(Items.SNOWBALL), 200, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("netherrack_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.NETHERRACK, 2), ingr(Blocks.NETHERRACK), 200, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("soul_sand_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.SOUL_SAND, 2), ingr(Blocks.SOUL_SAND), 1500, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("gravel_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.GRAVEL, 2), ingr(Blocks.GRAVEL), 720, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);

		output.accept(id("oak_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.OAK_LEAVES, 2), ingr(Blocks.OAK_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("birch_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.BIRCH_LEAVES, 2), ingr(Blocks.BIRCH_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("spruce_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.SPRUCE_LEAVES, 2), ingr(Blocks.SPRUCE_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("jungle_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.JUNGLE_LEAVES, 2), ingr(Blocks.JUNGLE_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("acacia_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.ACACIA_LEAVES, 2), ingr(Blocks.ACACIA_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("dark_oak_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.DARK_OAK_LEAVES, 2), ingr(Blocks.DARK_OAK_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("azalea_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.AZALEA_LEAVES, 2), ingr(Blocks.AZALEA_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("flowering_azalea_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.FLOWERING_AZALEA_LEAVES, 2), ingr(Blocks.FLOWERING_AZALEA_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("mangrove_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.MANGROVE_LEAVES, 2), ingr(Blocks.MANGROVE_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
		output.accept(id("cherry_leaves_dupe"), new ManaInfusionRecipe(new ItemStack(Blocks.CHERRY_LEAVES, 2), ingr(Blocks.CHERRY_LEAVES), 2000, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);

		output.accept(id("grass"), new ManaInfusionRecipe(new ItemStack(Blocks.SHORT_GRASS, 2), ingr(Blocks.SHORT_GRASS), 800, null, StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst)), null);
	}

	protected void cycle(RecipeOutput output, int cost, String group, ItemLike... items) {
		for (int i = 0; i < items.length; i++) {
			Ingredient in = ingr(items[i]);
			ItemStack out = new ItemStack(i == items.length - 1 ? items[0] : items[i + 1]);
			String idStr = String.format("%s_to_%s", BuiltInRegistries.ITEM.getKey(items[i].asItem()).getPath(), BuiltInRegistries.ITEM.getKey(out.getItem()).getPath());
			output.accept(id(idStr), new ManaInfusionRecipe(out, in, cost, group.isEmpty() ? null : group, StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
		}
	}

	protected void mini(RecipeOutput output, ItemLike mini, ItemLike full) {
		output.accept(id(BuiltInRegistries.ITEM.getKey(mini.asItem()).getPath()), new ManaInfusionRecipe(new ItemStack(mini), ingr(full), 2500, "botania:flower_shrinking", StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
	}

	protected void deconstruct(RecipeOutput output, String id, ItemLike items, ItemLike block) {
		output.accept(id(id), new ManaInfusionRecipe(new ItemStack(items, 4), ingr(block), 25, "botania:block_deconstruction", StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst)), null);
	}

	protected ResourceLocation id(String s) {
		return prefix("mana_infusion/" + s);
	}

	protected static Ingredient ingr(ItemLike i) {
		return Ingredient.of(i);
	}
}
