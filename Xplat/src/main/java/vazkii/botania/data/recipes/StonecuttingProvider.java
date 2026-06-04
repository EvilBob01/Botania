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
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.lib.LibBlockNames;
import vazkii.botania.common.lib.ResourceLocationHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StonecuttingProvider extends BotaniaRecipeProvider {
	public StonecuttingProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		for (String variant : LibBlockNames.METAMORPHIC_VARIANTS) {
			registerForMetamorphic(variant, output);
		}

		for (String color : LibBlockNames.PAVEMENT_VARIANTS) {
			registerForPavement(color, output);
		}

		for (String variant : LibBlockNames.QUARTZ_VARIANTS) {
			registerForQuartz(variant, output);
		}

		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockStairs);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockWall);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockPolished);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockPolishedSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockPolishedStairs);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockPolishedWall);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockBrick);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockBrickSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockBrickStairs);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockBrickWall);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockBrickChiseled);
		stonecutting(output, BotaniaBlocks.livingrock, BotaniaBlocks.livingrockSlate);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockPolishedSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockPolishedStairs);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockPolishedWall);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockBrick);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockBrickSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockBrickStairs);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockBrickWall);
		stonecutting(output, BotaniaBlocks.livingrockPolished, BotaniaBlocks.livingrockBrickChiseled);
		stonecutting(output, BotaniaBlocks.livingrockBrick, BotaniaBlocks.livingrockBrickSlab, 2);
		stonecutting(output, BotaniaBlocks.livingrockBrick, BotaniaBlocks.livingrockBrickStairs);
		stonecutting(output, BotaniaBlocks.livingrockBrick, BotaniaBlocks.livingrockBrickWall);
		stonecutting(output, BotaniaBlocks.livingrockBrick, BotaniaBlocks.livingrockBrickChiseled);
		stonecutting(output, BotaniaBlocks.livingrockBrickMossy, BotaniaBlocks.livingrockBrickMossySlab, 2);
		stonecutting(output, BotaniaBlocks.livingrockBrickMossy, BotaniaBlocks.livingrockBrickMossyStairs);
		stonecutting(output, BotaniaBlocks.livingrockBrickMossy, BotaniaBlocks.livingrockBrickMossyWall);
		stonecutting(output, BotaniaBlocks.shimmerrock, BotaniaBlocks.shimmerrockSlab, 2);
		stonecutting(output, BotaniaBlocks.shimmerrock, BotaniaBlocks.shimmerrockStairs);

		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaSlab, 2);
		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaStairs);
		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaBrick);
		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaBrickSlab, 2);
		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaBrickStairs);
		stonecutting(output, BotaniaBlocks.corporeaBlock, BotaniaBlocks.corporeaBrickWall);
		stonecutting(output, BotaniaBlocks.corporeaBrick, BotaniaBlocks.corporeaBrickSlab, 2);
		stonecutting(output, BotaniaBlocks.corporeaBrick, BotaniaBlocks.corporeaBrickStairs);
		stonecutting(output, BotaniaBlocks.corporeaBrick, BotaniaBlocks.corporeaBrickWall);

		List<Item> allAzulejos = IntStream.range(0, 16).mapToObj(i -> "azulejo_" + i)
				.map(ResourceLocationHelper::prefix)
				.map(BuiltInRegistries.ITEM::get)
				.collect(Collectors.toList());
		for (Item azulejo : allAzulejos) {
			anyToAnyStonecutting(output, allAzulejos, azulejo);
		}
	}

	private void registerForQuartz(String variant, RecipeOutput output) {
		Block base = BuiltInRegistries.BLOCK.get(prefix(variant));
		Block slab = BuiltInRegistries.BLOCK.get(prefix(variant + LibBlockNames.SLAB_SUFFIX));
		Block stairs = BuiltInRegistries.BLOCK.get(prefix(variant + LibBlockNames.STAIR_SUFFIX));
		Block chiseled = BuiltInRegistries.BLOCK.get(prefix("chiseled_" + variant));
		Block pillar = BuiltInRegistries.BLOCK.get(prefix(variant + "_pillar"));
		stonecutting(output, base, slab, 2);
		stonecutting(output, base, stairs);
		stonecutting(output, base, chiseled);
		stonecutting(output, base, pillar);
	}

	private void registerForPavement(String color, RecipeOutput output) {
		Block base = BuiltInRegistries.BLOCK.get(prefix(color + LibBlockNames.PAVEMENT_SUFFIX));
		Block slab = BuiltInRegistries.BLOCK.get(prefix(color + LibBlockNames.PAVEMENT_SUFFIX + LibBlockNames.SLAB_SUFFIX));
		Block stair = BuiltInRegistries.BLOCK.get(prefix(color + LibBlockNames.PAVEMENT_SUFFIX + LibBlockNames.STAIR_SUFFIX));
		stonecutting(output, base, slab, 2);
		stonecutting(output, base, stair);
	}

	private void registerForMetamorphic(String variant, RecipeOutput output) {
		Block base = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_stone"));
		Block slab = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_stone" + LibBlockNames.SLAB_SUFFIX));
		Block stair = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_stone" + LibBlockNames.STAIR_SUFFIX));
		Block wall = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_stone" + LibBlockNames.WALL_SUFFIX));
		Block brick = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_bricks"));
		Block brickSlab = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_bricks" + LibBlockNames.SLAB_SUFFIX));
		Block brickStair = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_bricks" + LibBlockNames.STAIR_SUFFIX));
		Block brickWall = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_bricks" + LibBlockNames.WALL_SUFFIX));
		Block chiseledBrick = BuiltInRegistries.BLOCK.get(prefix("chiseled_" + LibBlockNames.METAMORPHIC_PREFIX + variant + "_bricks"));
		Block cobble = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_cobblestone"));
		Block cobbleSlab = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_cobblestone" + LibBlockNames.SLAB_SUFFIX));
		Block cobbleStair = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_cobblestone" + LibBlockNames.STAIR_SUFFIX));
		Block cobbleWall = BuiltInRegistries.BLOCK.get(prefix(LibBlockNames.METAMORPHIC_PREFIX + variant + "_cobblestone" + LibBlockNames.WALL_SUFFIX));

		stonecutting(output, base, slab, 2);
		stonecutting(output, base, stair);
		stonecutting(output, base, wall);
		stonecutting(output, base, brick);
		stonecutting(output, base, brickSlab, 2);
		stonecutting(output, base, brickStair);
		stonecutting(output, base, brickWall);
		stonecutting(output, base, chiseledBrick);

		stonecutting(output, brick, brickSlab, 2);
		stonecutting(output, brick, brickStair);
		stonecutting(output, brick, brickWall);
		stonecutting(output, brick, chiseledBrick);

		stonecutting(output, cobble, cobbleSlab, 2);
		stonecutting(output, cobble, cobbleStair);
		stonecutting(output, cobble, cobbleWall);
	}

	@NotNull
	@Override
	public String getName() {
		return "Botania stonecutting recipes";
	}

	protected ResourceLocation idFor(ItemLike a, ItemLike b) {
		ResourceLocation aId = BuiltInRegistries.ITEM.getKey(a.asItem());
		ResourceLocation bId = BuiltInRegistries.ITEM.getKey(b.asItem());
		return prefix("stonecutting/" + aId.getPath() + "_to_" + bId.getPath());
	}

	protected void stonecutting(RecipeOutput output, ItemLike input, ItemLike result) {
		stonecutting(output, input, result, 1);
	}

	protected void stonecutting(RecipeOutput output, ItemLike input, ItemLike result, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result, count)
				.save(output, idFor(input, result));
	}

	protected void anyToAnyStonecutting(RecipeOutput output, List<? extends ItemLike> inputs, ItemLike result) {
		Ingredient input = Ingredient.of(inputs.stream().filter(obj -> result != obj).toArray(ItemLike[]::new));
		SingleItemRecipeBuilder.stonecutting(input, RecipeCategory.BUILDING_BLOCKS, result, 1)
				.save(output, prefix("stonecutting/" + BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()));
	}

	protected ResourceLocation prefix(String path) {
		return ResourceLocationHelper.prefix(path);
	}
}
