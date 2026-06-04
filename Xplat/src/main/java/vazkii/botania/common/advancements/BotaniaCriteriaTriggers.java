/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.advancements;

import vazkii.botania.mixin.CriteriaTriggersAccessor;

public class BotaniaCriteriaTriggers {
	public static void init() {
		CriteriaTriggersAccessor.botania_register(AlfheimPortalTrigger.ID, AlfheimPortalTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(CorporeaRequestTrigger.ID, CorporeaRequestTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(GaiaGuardianNoArmorTrigger.ID, GaiaGuardianNoArmorTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(RelicBindTrigger.ID, RelicBindTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(UseItemSuccessTrigger.ID, UseItemSuccessTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(ManaBlasterTrigger.ID, ManaBlasterTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(LokiPlaceTrigger.ID, LokiPlaceTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(AlfheimPortalBreadTrigger.ID, AlfheimPortalBreadTrigger.INSTANCE);
	}
}
