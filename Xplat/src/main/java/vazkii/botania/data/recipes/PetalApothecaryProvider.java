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
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.crafting.PetalsRecipe;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.Arrays;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class PetalApothecaryProvider extends BotaniaRecipeProvider {
	private static final Ingredient DEFAULT_REAGENT = Ingredient.of(BotaniaTags.Items.SEED_APOTHECARY_REAGENT);

	public PetalApothecaryProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "Botania petal apothecary recipes";
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		Ingredient white = tagIngr("petals/white");
		Ingredient orange = tagIngr("petals/orange");
		Ingredient magenta = tagIngr("petals/magenta");
		Ingredient lightBlue = tagIngr("petals/light_blue");
		Ingredient yellow = tagIngr("petals/yellow");
		Ingredient lime = tagIngr("petals/lime");
		Ingredient pink = tagIngr("petals/pink");
		Ingredient gray = tagIngr("petals/gray");
		Ingredient lightGray = tagIngr("petals/light_gray");
		Ingredient cyan = tagIngr("petals/cyan");
		Ingredient purple = tagIngr("petals/purple");
		Ingredient blue = tagIngr("petals/blue");
		Ingredient brown = tagIngr("petals/brown");
		Ingredient green = tagIngr("petals/green");
		Ingredient red = tagIngr("petals/red");
		Ingredient black = tagIngr("petals/black");
		Ingredient runeWater = Ingredient.of(BotaniaItems.runeWater);
		Ingredient runeFire = Ingredient.of(BotaniaItems.runeFire);
		Ingredient runeEarth = Ingredient.of(BotaniaItems.runeEarth);
		Ingredient runeAir = Ingredient.of(BotaniaItems.runeAir);
		Ingredient runeSpring = Ingredient.of(BotaniaItems.runeSpring);
		Ingredient runeSummer = Ingredient.of(BotaniaItems.runeSummer);
		Ingredient runeAutumn = Ingredient.of(BotaniaItems.runeAutumn);
		Ingredient runeWinter = Ingredient.of(BotaniaItems.runeWinter);
		Ingredient runeMana = Ingredient.of(BotaniaItems.runeMana);
		Ingredient runeLust = Ingredient.of(BotaniaItems.runeLust);
		Ingredient runeGluttony = Ingredient.of(BotaniaItems.runeGluttony);
		Ingredient runeGreed = Ingredient.of(BotaniaItems.runeGreed);
		Ingredient runeSloth = Ingredient.of(BotaniaItems.runeSloth);
		Ingredient runeWrath = Ingredient.of(BotaniaItems.runeWrath);
		Ingredient runeEnvy = Ingredient.of(BotaniaItems.runeEnvy);
		Ingredient runePride = Ingredient.of(BotaniaItems.runePride);

		Ingredient redstoneRoot = Ingredient.of(BotaniaItems.redstoneRoot);
		Ingredient pixieDust = Ingredient.of(BotaniaItems.pixieDust);
		Ingredient gaiaSpirit = Ingredient.of(BotaniaItems.lifeEssence);

		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.pureDaisy.asItem())), make(BotaniaFlowerBlocks.pureDaisy, white, white, white, white), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.manastar.asItem())), make(BotaniaFlowerBlocks.manastar, lightBlue, green, red, cyan), null);

		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.endoflame.asItem())), make(BotaniaFlowerBlocks.endoflame, brown, brown, red, lightGray), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.hydroangeas.asItem())), make(BotaniaFlowerBlocks.hydroangeas, blue, blue, cyan, cyan), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.thermalily.asItem())), make(BotaniaFlowerBlocks.thermalily, red, orange, orange, runeEarth, runeFire), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.rosaArcana.asItem())), make(BotaniaFlowerBlocks.rosaArcana, pink, pink, purple, purple, lime, runeMana), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.munchdew.asItem())), make(BotaniaFlowerBlocks.munchdew, lime, lime, red, red, green, runeGluttony), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.entropinnyum.asItem())), make(BotaniaFlowerBlocks.entropinnyum, red, red, gray, gray, white, white, runeWrath, runeFire), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.kekimurus.asItem())), make(BotaniaFlowerBlocks.kekimurus, white, white, orange, orange, brown, brown, runeGluttony, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.gourmaryllis.asItem())), make(BotaniaFlowerBlocks.gourmaryllis, lightGray, lightGray, yellow, yellow, red, runeFire, runeSummer), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.narslimmus.asItem())), make(BotaniaFlowerBlocks.narslimmus, lime, lime, green, green, black, runeSummer, runeWater), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.spectrolus.asItem())), make(BotaniaFlowerBlocks.spectrolus, red, red, green, green, blue, blue, white, white, runeWinter, runeAir, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.rafflowsia.asItem())), make(BotaniaFlowerBlocks.rafflowsia, purple, purple, green, green, black, runeEarth, runePride, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.shulkMeNot.asItem())), make(BotaniaFlowerBlocks.shulkMeNot, purple, purple, magenta, magenta, lightGray, gaiaSpirit, runeEnvy, runeWrath), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.dandelifeon.asItem())), make(BotaniaFlowerBlocks.dandelifeon, purple, purple, lime, green, runeWater, runeFire, runeEarth, runeAir, redstoneRoot, gaiaSpirit), null);

		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.jadedAmaranthus.asItem())), make(BotaniaFlowerBlocks.jadedAmaranthus, purple, lime, green, runeSpring, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.bellethorn.asItem())), make(BotaniaFlowerBlocks.bellethorn, red, red, red, cyan, cyan, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.dreadthorn.asItem())), make(BotaniaFlowerBlocks.dreadthorn, black, black, black, cyan, cyan, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.heiseiDream.asItem())), make(BotaniaFlowerBlocks.heiseiDream, magenta, magenta, purple, pink, runeWrath, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.tigerseye.asItem())), make(BotaniaFlowerBlocks.tigerseye, yellow, brown, orange, lime, runeAutumn), null);

		ResourceLocation orechidId = idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.orechid.asItem()));
		if (XplatAbstractions.INSTANCE.gogLoaded()) {
			output.accept(orechidId, make(BotaniaFlowerBlocks.orechid, gray, gray, yellow, yellow, green, green, red, red), null);
		} else {
			output.accept(orechidId, make(BotaniaFlowerBlocks.orechid, gray, gray, yellow, green, red, runePride, runeGreed, redstoneRoot, pixieDust), null);
		}

		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.orechidIgnem.asItem())), make(BotaniaFlowerBlocks.orechidIgnem, red, red, white, white, pink, runePride, runeGreed, redstoneRoot, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.fallenKanade.asItem())), make(BotaniaFlowerBlocks.fallenKanade, white, white, yellow, yellow, orange, runeSpring), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.exoflame.asItem())), make(BotaniaFlowerBlocks.exoflame, red, red, gray, lightGray, runeFire, runeSummer), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.agricarnation.asItem())), make(BotaniaFlowerBlocks.agricarnation, lime, lime, green, yellow, runeSpring, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.hopperhock.asItem())), make(BotaniaFlowerBlocks.hopperhock, gray, gray, lightGray, lightGray, runeAir, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.tangleberrie.asItem())), make(BotaniaFlowerBlocks.tangleberrie, cyan, cyan, gray, lightGray, runeAir, runeEarth), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.jiyuulia.asItem())), make(BotaniaFlowerBlocks.jiyuulia, pink, pink, purple, lightGray, runeWater, runeAir), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.rannuncarpus.asItem())), make(BotaniaFlowerBlocks.rannuncarpus, orange, orange, yellow, runeEarth, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.hyacidus.asItem())), make(BotaniaFlowerBlocks.hyacidus, purple, purple, magenta, magenta, green, runeWater, runeAutumn, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.pollidisiac.asItem())), make(BotaniaFlowerBlocks.pollidisiac, red, red, pink, pink, orange, runeLust, runeFire), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.clayconia.asItem())), make(BotaniaFlowerBlocks.clayconia, lightGray, lightGray, gray, cyan, runeEarth), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.loonium.asItem())), make(BotaniaFlowerBlocks.loonium, green, green, green, green, gray, runeSloth, runeGluttony, runeEnvy, redstoneRoot, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.daffomill.asItem())), make(BotaniaFlowerBlocks.daffomill, white, white, brown, yellow, runeAir, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.vinculotus.asItem())), make(BotaniaFlowerBlocks.vinculotus, black, black, purple, purple, green, runeWater, runeSloth, runeLust, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.spectranthemum.asItem())), make(BotaniaFlowerBlocks.spectranthemum, white, white, lightGray, lightGray, cyan, runeEnvy, runeWater, redstoneRoot, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.medumone.asItem())), make(BotaniaFlowerBlocks.medumone, brown, brown, gray, gray, runeEarth, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.marimorphosis.asItem())), make(BotaniaFlowerBlocks.marimorphosis, gray, yellow, green, red, runeEarth, runeFire, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.bubbell.asItem())), make(BotaniaFlowerBlocks.bubbell, cyan, cyan, lightBlue, lightBlue, blue, blue, runeWater, runeSummer, pixieDust), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.solegnolia.asItem())), make(BotaniaFlowerBlocks.solegnolia, brown, brown, red, blue, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.bergamute.asItem())), make(BotaniaFlowerBlocks.bergamute, orange, green, green, redstoneRoot), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaFlowerBlocks.labellia.asItem())), make(BotaniaFlowerBlocks.labellia, yellow, yellow, blue, white, black, runeAutumn, redstoneRoot, pixieDust), null);

		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaBlocks.motifDaybloom.asItem())), make(BotaniaBlocks.motifDaybloom, yellow, yellow, orange, lightBlue), null);
		output.accept(idFor(BuiltInRegistries.ITEM.getKey(BotaniaBlocks.motifNightshade.asItem())), make(BotaniaBlocks.motifNightshade, black, black, purple, gray), null);

		ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
		ItemNBTHelper.setString(stack, "SkullOwner", "Vazkii"); // TODO: migrate SkullOwner to DataComponents
		Ingredient[] inputs = new Ingredient[16];
		Arrays.fill(inputs, pink);
		output.accept(idFor(prefix("vazkii_head")), new PetalsRecipe(stack, DEFAULT_REAGENT, inputs), null);
	}

	protected static Ingredient tagIngr(String tag) {
		return Ingredient.of(TagKey.create(Registries.ITEM, prefix(tag)));
	}

	protected static PetalsRecipe make(ItemLike item, Ingredient... ingredients) {
		return new PetalsRecipe(new ItemStack(item), DEFAULT_REAGENT, ingredients);
	}

	protected static ResourceLocation idFor(ResourceLocation name) {
		return new ResourceLocation(name.getNamespace(), "petal_apothecary/" + name.getPath());
	}
}
