package vazkii.botania.common.crafting.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

// Serializer for dynamic recipes that don't have a json/network representation.
// The recipe type fully identifies the recipe; a fixed instance is used.
public class NoOpRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
	private final Supplier<T> constructor;
	private final MapCodec<T> codec;
	private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

	public NoOpRecipeSerializer(Supplier<T> constructor) {
		this.constructor = constructor;
		// Codec always returns the same singleton instance
		this.codec = MapCodec.unit(constructor::get);
		this.streamCodec = StreamCodec.of(
				(buf, recipe) -> {},
				buf -> constructor.get()
		);
	}

	/** Legacy constructor accepting a Function<ResourceLocation, T>; ResourceLocation is ignored in 1.21. */
	public NoOpRecipeSerializer(java.util.function.Function<ResourceLocation, T> constructor) {
		this(() -> constructor.apply(ResourceLocation.fromNamespaceAndPath("botania", "noop")));
	}

	@Override
	public MapCodec<T> codec() {
		return codec;
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
		return streamCodec;
	}
}
