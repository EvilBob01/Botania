/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.record;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;

public class BotaniaRecordItem extends Item {
	public BotaniaRecordItem(ResourceKey<JukeboxSong> song, Properties builder) {
		super(builder.stacksTo(1).jukeboxPlayable(song));
	}
}
