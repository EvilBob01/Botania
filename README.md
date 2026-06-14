![](web/assets/img/logo.png)  
Welcome to the Botania repository.  

> ⚠️ **This is an unofficial community port** by [EvilBob01](https://github.com/EvilBob01), porting Botania to **NeoForge 1.21.1**.
> The original mod is by [Vazkii](https://github.com/VazkiiMods/Botania). This port is **alpha quality** — use at your own risk.

Botania is a [Minecraft](https://minecraft.net/) tech mod themed around natural magic. It's inspired by other magic mods, such as [Thaumcraft](https://www.curseforge.com/minecraft/mc-mods/thaumcraft) or [Blood Magic](https://www.curseforge.com/minecraft/mc-mods/blood-magic).  

Botania is licensed under the [Botania License](http://botaniamod.net/license.php)

For information on contributing, please read `CONTRIBUTING.md`.

## Platform Support

| Minecraft Version | Fabric | NeoForge | Forge |
|---|---|---|---|
| 1.20.1 | ✅ Released | ✅ Released | ✅ Released |
| 1.21.1 | 🚧 Planned | ⚠️ Alpha (this fork) | ❌ Not planned |

> **Note:** The 1.21.1 NeoForge port in this fork compiles and loads but has not been fully gameplay-tested.
> Forge support is not planned for 1.21+. For the official upstream port status, see [VazkiiMods/Botania](https://github.com/VazkiiMods/Botania).

## Maven info

Maven artifacts are located [here](https://maven.blamejared.com/vazkii/botania/Botania/), each folder representing a version.

Note: As of 1.16, intermediate (non-release) Maven builds are no longer persisted.
That is, you must either depend on a *released* version of Botania, e.g. `1.20.1-453`, or specifically opt in to the bleeding-edge
build of the next version. For example, `1.20.1-454-SNAPSHOT` would be the current bleeding edge version of future version `1.20.1-454`. 

Note that `-SNAPSHOT` versions can be broken from time to time, and you are strongly discouraged from using them unless you are helping dogfood, test, or contribute to Botania. They may also be pruned from time to time to save disk space on the server. Do *not* rely on `-SNAPSHOT` versions for anything important!

In NeoForge (1.21.1+), add the following to your `build.gradle`:
```gradle
repositories {
    maven { url 'https://maven.blamejared.com' }
}

dependencies {
    compileOnly "vazkii.botania:Botania:[VERSION]:api"
    runtimeOnly "vazkii.botania:Botania:[VERSION]"
}
```

In Forge (1.20.1 and earlier), add the following to your `build.gradle`:
```gradle
repositories {
    maven { url 'https://maven.blamejared.com' }
}

dependencies {
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


