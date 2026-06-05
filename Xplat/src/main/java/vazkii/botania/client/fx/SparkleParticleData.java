/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.client.fx;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import org.jetbrains.annotations.NotNull;

public class SparkleParticleData implements ParticleOptions {
	public static final MapCodec<SparkleParticleData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			com.mojang.serialization.Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
			com.mojang.serialization.Codec.FLOAT.fieldOf("r").forGetter(d -> d.r),
			com.mojang.serialization.Codec.FLOAT.fieldOf("g").forGetter(d -> d.g),
			com.mojang.serialization.Codec.FLOAT.fieldOf("b").forGetter(d -> d.b),
			com.mojang.serialization.Codec.INT.fieldOf("m").forGetter(d -> d.m),
			com.mojang.serialization.Codec.BOOL.fieldOf("noClip").forGetter(d -> d.noClip),
			com.mojang.serialization.Codec.BOOL.fieldOf("fake").forGetter(d -> d.fake),
			com.mojang.serialization.Codec.BOOL.fieldOf("corrupt").forGetter(d -> d.corrupt)
	).apply(instance, SparkleParticleData::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SparkleParticleData> STREAM_CODEC = new StreamCodec<>() {
		@Override
		public SparkleParticleData decode(RegistryFriendlyByteBuf buf) {
			return new SparkleParticleData(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
		}

		@Override
		public void encode(RegistryFriendlyByteBuf buf, SparkleParticleData data) {
			buf.writeFloat(data.size);
			buf.writeFloat(data.r);
			buf.writeFloat(data.g);
			buf.writeFloat(data.b);
			buf.writeInt(data.m);
			buf.writeBoolean(data.noClip);
			buf.writeBoolean(data.fake);
			buf.writeBoolean(data.corrupt);
		}
	};

	public final float size;
	public final float r, g, b;
	public final int m;
	public final boolean noClip;
	public final boolean fake;
	public final boolean corrupt;

	public static SparkleParticleData noClip(float size, float r, float g, float b, int m) {
		return new SparkleParticleData(size, r, g, b, m, true, false, false);
	}

	public static SparkleParticleData fake(float size, float r, float g, float b, int m) {
		return new SparkleParticleData(size, r, g, b, m, false, true, false);
	}

	public static SparkleParticleData corrupt(float size, float r, float g, float b, int m) {
		return new SparkleParticleData(size, r, g, b, m, false, false, true);
	}

	public static SparkleParticleData sparkle(float size, float r, float g, float b, int m) {
		return new SparkleParticleData(size, r, g, b, m, false, false, false);
	}

	private SparkleParticleData(float size, float r, float g, float b, int m, boolean noClip, boolean fake, boolean corrupt) {
		this.size = size;
		this.r = r;
		this.g = g;
		this.b = b;
		this.m = m;
		this.noClip = noClip;
		this.fake = fake;
		this.corrupt = corrupt;
	}

	@NotNull
	@Override
	public ParticleType<SparkleParticleData> getType() {
		return BotaniaParticles.SPARKLE;
	}
}
