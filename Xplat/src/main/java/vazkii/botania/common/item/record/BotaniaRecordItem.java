/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.record;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.MusicDiscItem;

public class BotaniaRecordItem extends MusicDiscItem {
	public BotaniaRecordItem(int comparator, Holder<SoundEvent> sound, Properties builder, int lengthInSeconds) {
		super(Holder.direct(new JukeboxSong(sound, Component.empty(), (float) lengthInSeconds, comparator)), builder);
	}
}
