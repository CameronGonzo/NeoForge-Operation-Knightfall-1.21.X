# Developing Operation Knightfall on Minecraft 26.2

This checkout now targets Minecraft 26.2. The mod version remains **0.6.25**. The folder can keep its existing `NeoForge-Operation-Knightfall-1.21.X` name; the build settings determine the Minecraft version.

| Setting | Version |
| --- | --- |
| Minecraft | 26.2 |
| NeoForge | 26.2.0.87 |
| Java JDK | 25 |
| Gradle wrapper | 9.2.1 |
| ModDevGradle | 2.0.147 |
| FancyModLoader | 11.0.16, supplied by NeoForge |

Minecraft, NeoForge, Java, and ModDevGradle versions are in `gradle.properties`. Gradle's own version is in `gradle/wrapper/gradle-wrapper.properties`. Parchment settings were removed because this Minecraft release ships unobfuscated. These choices follow the [official 26.2 ModDevGradle MDK](https://github.com/NeoForgeMDKs/MDK-26.2-ModDevGradle). Java 25 and the minimum IntelliJ version are covered in the [NeoForge migration primer](https://docs.neoforged.net/primer/docs/26.1/).

## Set up IntelliJ on this PC

1. Open this same project. Use IntelliJ IDEA **2025.2 or newer** with Java 25 support; update IDEA if Java 25 is missing from its language-level choices.
2. Open **File → Project Structure → SDKs**. Add a JDK from disk at:

   ```text
   C:\Users\Camer\.gradle\jdks\eclipse_adoptium-25-amd64-windows.2
   ```

   Gradle already downloaded this JDK during the migration. Select the folder containing `bin`, `lib`, and `release`, not the `bin` folder. On a different computer, use **Download JDK → Version 25 → Eclipse Temurin**, or get [Temurin 25 from Adoptium](https://adoptium.net/temurin/releases/?version=25).
3. In **Project Structure → Project**, select that JDK as **SDK** and set **Language level** to **SDK default / 25**. Use ordinary Java 25, without preview features. Modules should inherit the project SDK.
4. Open **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**. Select this project, set **Gradle JVM** to the JDK 25 you just added, choose the **Gradle wrapper**, and select **Gradle** for building and running.
5. Click **Apply**, then **Reload All Gradle Projects** in the Gradle tool window. Let dependency indexing finish. The first reload can take several minutes.
6. Run the Gradle task **runClient**. The ModDevGradle-generated client configuration is also usable after reload. If an old manually created configuration points to Java 21 or old launch arguments, use `runClient` or recreate it from the refreshed Gradle project.

These are separate IDE settings: changing the project SDK alone does not necessarily change the JVM IntelliJ uses for Gradle. See JetBrains' [project SDK settings](https://www.jetbrains.com/help/idea/project-settings-and-structure.html) and [Gradle settings](https://www.jetbrains.com/help/idea/gradle.html).

You do not need to download Gradle, Minecraft libraries, Parchment mappings, or a separate NeoForge installer for development. The wrapper and ModDevGradle obtain the development dependencies. The Minecraft client and asset downloads already completed on this PC.

## Fix JAVA_HOME for the terminal

The inherited `JAVA_HOME` on this PC points at an old Adoptium folder that no longer exists. IntelliJ's Gradle JVM setting fixes IDE launches, but a fresh terminal can still fail before Gradle starts.

For the current IntelliJ PowerShell terminal, run:

```powershell
$env:JAVA_HOME = 'C:\Users\Camer\.gradle\jdks\eclipse_adoptium-25-amd64-windows.2'
& "$env:JAVA_HOME\bin\java.exe" -version
.\gradlew.bat --version
.\gradlew.bat runClient
```

For a lasting Windows setting, search Start for **Edit environment variables for your account**. Set the user variable **JAVA_HOME** to that JDK folder. If you want the plain `java` command to use it too, put `%JAVA_HOME%\bin` ahead of obsolete Java entries in your user **Path**. Reopen IntelliJ and its terminal afterward. If `java -version` still shows 21, check which entry `Get-Command java` resolves; the Gradle wrapper uses `JAVA_HOME` directly.

No permanent Windows environment variables or global IntelliJ SDK definitions were changed by the migration. Java 21 can remain installed for your older projects.

## Launching and building

Run these from the project root, after correcting `JAVA_HOME` or through IntelliJ's Gradle tool window:

| Task | Purpose | Development directory |
| --- | --- | --- |
| `runClient` | Interactive modding and testing | `run-26.2/client` |
| `runServer` | Dedicated development server | `run-26.2/server` |
| `runData` | Regenerate models, recipes, tags, and loot | `run-26.2/data` |
| `check` | Compile tests and run the 188 combat contract checks | `build` |
| `runGameTestServer` | Run the 23 mod combat GameTests | `build/gametest-26.2` |
| `runClientSmoke` | Open a temporary world, exercise rendering, then close | `build/client-smoke-26.2` |
| `build` | Check and package the mod | `build/libs` |

When changing data generators, run `runData` first, then `build`. Edit providers under `src/main/java`; the generated output goes into `src/generated/resources` and can be overwritten by the next data run. Edit hand-authored weapon models and item definitions under `src/main/resources`.

The output is `build/libs/knightfall-0.7.0.jar`. It targets **26.2 only** and is not compatible with the 1.21.1 game. For testing outside IntelliJ, use a separate Minecraft 26.2 instance with NeoForge 26.2.0.87 or a compatible later 26.2 build, then put this jar into that instance's `mods` folder. Any additional mods also need compatible 26.2 versions. The normal [NeoForge installer](https://projects.neoforged.net/neoforged/neoforge) is for that separate game installation.

The first dedicated-server launch may ask you to accept Minecraft's EULA. Read the generated `run-26.2/server/eula.txt` and accept it yourself if you agree, then rerun `runServer`.

## Code and resource changes

- **Data components:** `ModDataComponentTypes` now uses `DeferredRegister.DataComponents` and `createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID)`. Its helper calls `registerComponentType`. The removed one-argument factory is gone; component IDs and persisted codecs remain the same.
- **Registrations:** item and block properties receive their registry keys before construction. Custom items use `registerItem` factories; entity types build with explicit namespaced keys.
- **General APIs:** resource identifiers, entity/item packages, interaction results, tooltips, cooldowns, server damage, equipment ticking, and game rules use the current signatures. Client packet sends use `ClientPacketDistributor`.
- **Saving:** entities use `ValueInput`/`ValueOutput` and codecs. Remote detonator saved data uses the new namespaced `SavedDataType` and imports the legacy flat saved-data file when present.
- **Combat:** custom blocks and timed parries now run during incoming damage. The shield event in 26.2 no longer covers arbitrary custom melee defenses. The original tests still check tap/hold exclusivity, blocking, parries, materials, throws, and the other combat actions.
- **Rendering:** entities extract render states and submit their geometry through the new rendering pipeline. Particles, beam geometry, impact marks, HUD drawing, thermal highlighting, and blur were ported. The old blur accessor mixin was removed; the thermal rendering mixin uses the new renderer signatures.
- **Item models:** every item has a client definition in `assets/knightfall/items`. The eight old model-override files now use `minecraft:range_dispatch` definitions for held/activated/grenade states. Existing Blockbench geometry remains in `models/item`.
- **Datagen:** client datagen generates the new recipe, tag, loot, block model, and client item formats. Existing ore/drop and recipe contracts remain in the providers.
- **Client loading:** obsolete `@OnlyIn` annotations were removed from the three sound classes. Their callers remain in client-only event handlers. Key categories register through `RegisterKeyMappingsEvent`.

For new weapon artwork, make `models/item/<registry_name>.json`, then point `items/<registry_name>.json` at `knightfall:item/<registry_name>`. Use `items/thermal_detonator.json` as an example for state-dependent models. Do not put old `overrides` arrays back into model JSON.

## Existing unfinished assets

211 registered blasters had no model JSON in the original checkout. Their new client definitions point at `knightfall:item/unfinished_blaster`, a temporary vanilla crossbow model. Search the `items` folder for `unfinished_blaster` to find them. This lets those weapons remain testable while you create their own artwork.

The RD2B sound references now match the files actually present in its folder. The nonexistent eighth default impact variant was removed; the seven available variants remain. The missing equipment-launch recording uses the vanilla crossbow-shot sound temporarily. Replace that event entry when you have its intended recording. Missing particle texture references and the DLT-15 subtitle were also fixed.

## Worlds and remaining manual testing

The new development directories leave the old `run` worlds separate. Start with a fresh 26.2 world. If you test an old world, back it up and copy it into `run-26.2/client/saves`; never use the only copy or reopen an upgraded save in 1.21.1. Minecraft's world conversion is separate from the mod's code migration. The remote-link importer does not make every possible old world or third-party mod combination verified.

Automated checks cover compilation, generated data, the combat contracts, dedicated-server combat tests, and an isolated client rendering smoke test. They do not replace hands-on review of every weapon's appearance, animation, sounds, input feel, or two-player networking. Check representative blasters and ammo, R tap/reload versus R hold/unload, B firing modes, scopes/thermal vision, thrown and placed explosives, remote detonators, and save/reload before using an established world.

Verified on this PC on September 13, 2026: `build`, `runData`, all 188 contract checks, all 23 mod GameTests (24 including Minecraft's default test), and `runClientSmoke` passed. The smoke test resolved 414 item models in every display context and exercised a fresh world, projectile renderers, beam geometry, particles, impact effects, thermal highlighting, and blur. All 760 resource JSON files parsed; the 24 previously tracked recipes and loot tables matched their original behavior after accounting for the new JSON format. Test classes and structures are excluded from the release jar.

The runtime may still log upstream NeoForge shader sampler warnings, Minecraft translation-renaming warnings, or Windows performance-counter diagnostics. Those differ from the mod's missing resources and obsolete annotations fixed here; the client can complete its smoke test with those upstream messages.
