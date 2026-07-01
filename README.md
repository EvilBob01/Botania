![](web/assets/img/logo.png)  
Welcome to the Botania repository.  

> ⚠️ **This is an unofficial community fork** by [EvilBob01](https://github.com/EvilBob01), tracking the upstream [VazkiiMods/Botania](https://github.com/VazkiiMods/Botania) NeoForge 1.21.1 porting branch. The `neoforge-1.21.1` branch here mirrors upstream's port with any additional fixes on top. Alpha quality — use at your own risk.

## Why this fork exists

Official Botania is being ported to NeoForge 1.21.1 by the upstream team on the
[`1.21.1-porting`](https://github.com/VazkiiMods/Botania/tree/1.21.1-porting) branch, but that port is
still in progress and doesn't build/run standalone yet. This fork tracks that branch closely
(merging upstream commits in regularly) so it stays buildable on NeoForge 1.21.1, and layers a
small set of early-game gameplay tweaks on top — see below.

This is **not** an attempt to compete with or replace official Botania. Once the upstream port is
finished and released, most players should just use that instead.

## Custom Changes (EvilBob01 fork)

The following features are **not** present in upstream Botania and have been added on top of the
NeoForge 1.21.1 port:

| Change | Type | Summary |
|---|---|---|
| [Enchanted Soil](#enchanted-soil) | New block | Grass-like block that stops early-game flowers from decaying |
| [Overgrowth Seed](#overgrowth-seed) | New item | Crafted from 9 wheat seeds; converts grass into Enchanted Soil |
| [Daybloom](#daybloom-re-added) | Re-added flower | Removed upstream in an earlier version; restored with decay logic |
| [Nightshade](#nightshade-re-added) | Re-added flower | Removed upstream in an earlier version; restored with decay logic |

### Enchanted Soil
A special grass-like block, craftable via the Overgrowth Seed. Any of the three low-tier
mana-generating flowers (Daybloom, Nightshade, Hydroangeas) placed on top will **never decay** —
their passive decay timer is reset every tick while the flower sits on Enchanted Soil. It behaves
like normal grass otherwise (tillable, can be walked on, supports flowers/crops).

### Overgrowth Seed
A new craftable item, since there's no other in-world way to obtain Enchanted Soil.
- **Recipe:** fill a 3×3 crafting grid with wheat seeds (9 total).
- **Use:** right-click any grass block to convert it into Enchanted Soil. The seed is consumed on use.

### Daybloom (re-added)
Present in older versions of Botania but removed upstream; restored here as a low-tier generating
flower that produces mana **only during daylight hours**.
- Generates 1 mana every 20 ticks while the sun is up.
- Maximum mana pool: 300.
- Decays after ~1 hour (72 000 ticks) unless planted on Enchanted Soil — matching how Hydroangeas
  already behaved, so the three early flowers are now consistent with each other.

### Nightshade (re-added)
The nocturnal counterpart to Daybloom, restored the same way: a low-tier generating flower that
produces mana **only at night**.
- Generates 1 mana every 20 ticks while it is night.
- Maximum mana pool: 300.
- Decays after ~1 hour (72 000 ticks) unless planted on Enchanted Soil, same as Daybloom and
  Hydroangeas.

> Daybloom, Nightshade, and Hydroangeas are intentionally the lowest-output generating flowers in
> the mod, which is what makes Enchanted Soil a meaningful early-game crafting goal rather than
> something that trivializes progression.

---

Botania is a [Minecraft](https://minecraft.net/) tech mod themed around natural magic. It's inspired by other magic mods, such as [Thaumcraft](https://www.curseforge.com/minecraft/mc-mods/thaumcraft) or [Blood Magic](https://www.curseforge.com/minecraft/mc-mods/blood-magic).  

Botania is licensed under the [Botania License](http://botaniamod.net/license.php)

For information on contributing, please read `CONTRIBUTING.md`.

## Maven info

Maven artifacts are located [here](https://maven.blamejared.com/vazkii/botania/Botania/), each folder representing a version.

Note: As of 1.16, intermediate (non-release) Maven builds are no longer persisted.
That is, you must either depend on a *released* version of Botania, e.g. `1.16.2-407`, or specifically opt in to the bleeding-edge
build of the next version. For example, `1.16.2-408-SNAPSHOT` would be the current bleeding edge version of future version `1.16.2-408`. 

Note that `-SNAPSHOT` versions can be broken from time to time, and you are strongly discouraged from using them unless you are helping dogfood, test, or contribute to Botania. They may also be pruned from time to time to save disk space on the server. Do *not* rely on `-SNAPSHOT` versions for anything important!

In Forge, add the following to your `build.gradle`
```gradle
repositories {
    maven { url 'https://maven.blamejared.com' }
}

dependencies {
    // 1.14+
    compileOnly fg.deobf("vazkii.botania:Botania:[VERSION]:api")
    runtimeOnly fg.deobf("vazkii.botania:Botania:[VERSION]")
}
```
## Mixin Troubleshooting

Read this if you get crashes when depending on Botania and trying to launch in-dev.
Botania uses Mixins to implement various features.
This may cause issues when depending on Botania in-dev, since ForgeGradle/MixinGradle
do not yet properly support this in-dev like Fabric does.
As a workaround, disable refmaps by defining the `mixin.env.disableRefMap`
JVM argument to `true`.


