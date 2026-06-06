package vazkii.botania.client.patchouli.processor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import vazkii.botania.api.recipe.RecipeWithReagent;
import vazkii.botania.client.patchouli.PatchouliUtils;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public abstract class ReagentRecipeProcessor implements IComponentProcessor {
	protected RecipeWithReagent recipe;
	protected ResourceLocation recipeId;

	@Override
	public abstract void setup(Level level, IVariableProvider variables);

	@Override
	public IVariable process(Level level, String key) {
		if (recipe == null) {
			return null;
		}
		return switch (key) {
			case "recipe" -> recipeId != null ? IVariable.wrap(recipeId.toString()) : null;
			case "reagent" -> PatchouliUtils.interweaveIngredients(List.of(recipe.getReagent()));
			case "output" -> IVariable.wrap(recipe.getResultItem(level.registryAccess()));
			case "heading" -> IVariable.wrap(recipe.getResultItem(level.registryAccess()).getHoverName());
			default -> null;
		};
	}
}
