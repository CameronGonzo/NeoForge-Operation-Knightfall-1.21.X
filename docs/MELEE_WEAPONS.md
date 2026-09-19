# Melee weapon foundation

The system targets the project's Minecraft 26.2 / NeoForge 26.2.0.87 environment. No new gameplay weapons, models, textures, or sounds are registered by this framework. The existing explosive knife keeps its original attached-explosive behavior because it does not opt into a combat form. See [the migration guide](MIGRATION_26_2.md) for Java 25 and IntelliJ setup.

## Adding a weapon

Use the existing `registerMeleeWeapon` helper in `ModItems`. A `MeleeWeaponForm` defines total attack damage, attacks per second, the input assignments, and inherent properties. The example below belongs inside `ModItems` when its assets are ready:

```java
public static final DeferredItem<Item> EXAMPLE_PIKE = registerMeleeWeapon(
        MeleeWeaponDefinition.builder("example_pike")
                .itemProperties(new Item.Properties().durability(500))
                .form(MeleeWeaponForm.builder(6.0, 1.6)
                        .bind(MeleeInput.LEFT_CLICK, MeleeAttackTrait.SIMPLE_HIT)
                        .bind(MeleeInput.RIGHT_CLICK, MeleeAttackTrait.THRUST)
                        .bind(MeleeInput.HOLD_RIGHT_CLICK, MeleeAttackTrait.STEADY)
                        .properties(MeleeProperty.LIGHTSABER_RESISTANT)
                        .build())
                .build());
```

Forms set the attack attributes, so use their damage/speed values instead of also supplying sword attributes through `itemProperties`. The builder rejects duplicate input slots and every trait/input combination not marked in the supplied table. An omitted input does nothing. Add only the properties the actual weapon design warrants; material resistance is explicit rather than guessed from a registry name or recipe.

The default sound profile is silent and the default hit effect does nothing. `.audio(...)` and `.hitEffect(...)` retain the existing explosive-knife extension points. Optional durability uses normal item breakage. No asset is required to construct a definition.

## Inputs and timing

- Left click uses the main-hand weapon. Normal block mining remains available. Air attacks count as misses. Main-hand direct strikes use `Player.attack`, retaining normal cooldown scaling, critical hits, enchantments, and combat events.
- Right click selects the main-hand melee weapon when it has a right-click binding. Otherwise an offhand melee weapon may use it, including with a non-usable main-hand item. Main-hand blasters, food, charged-use items, and placeable blocks keep their normal use controls. A configured main-hand melee weapon takes priority over an offhand blaster for left click.
- A tap-only binding fires on press. When tap and hold share a weapon, a tap fires on release before the four-tick threshold. A hold consumes the gesture, so releasing it cannot also fire the tap. This is the behavior confirmed for this task.
- Ordinary charged attacks fire once after the larger of the hold threshold and their charge time. `THROW` instead fires on release after charging, like a trident.
- Hold-only `BLOCK` and `PARRY` start on press. When they share right click with a tap, they start after the hold threshold.
- A run input requires sprinting, ground contact, and measured horizontal movement. Fall takes priority over run, which takes priority over an ordinary hold. Continuous actions can transition between running and standing; their contact interval remains enforced.
- A falling input arms during downward movement and fires once on landing while held. Releasing, switching items, opening a menu, death, stun, disconnect, or changing dimension cancels the held action. Fall attacks do not grant fall-damage immunity.
- Parry lasts one server tick by default: about 50 ms at 20 TPS, with server tick timing and normal network latency. It consumes the counter window on the first counterattack. A sustained parry does not renew the window. It blocks eligible frontal damage during the window and counterattacks a reachable melee attacker.
- Action cooldowns are enforced on the server and survive releases and weapon swaps. Left-click ordinary strikes retain vanilla partial-strength attacks, with duplicate requests limited to one per server tick. Charged and continuous attacks use their own configured timings.

The client sends only attack/press/release/cancel messages. The server chooses the hand, trait, target, reach, movement eligibility, and results. Ray attacks stop at blocks; area/contact attacks check visibility, allies, spectators, invulnerable players, and PvP rules.

## Active traits

| Trait | Behavior |
| --- | --- |
| Simple hit | Normal main-hand strike. |
| Thrust | Direct strike with exactly one extra block of entity reach. |
| Uppercut | Strike, then upward and backward impulse on a successful hit. |
| Slash | Close frontal area strike. |
| Blaster shot | Existing `BlasterBoltEntity`, with configured bolt type, damage, speed, classification, and optional firing sound. |
| Slam ground | Grounded radial damage and upward/outward impulse. Falling use waits for landing. |
| Break block | Disables an active frontal shield or melee block, then strikes. |
| Sweep | Radial strike around all sides of the user. |
| Dash | Forward impulse and collision-path damage, once per target during a dash. |
| Throw | Charged release removes one survival inventory item and launches a recoverable weapon. Creative throws do not produce survival pickups. |
| Quick throw | Launches a nonrecoverable copy; keeps the inventory item. |
| Steady | Repeated damage to entities touching the narrow weapon segment ahead, including while standing still. |
| Whip shock | Casts and attaches a hook; holding calls the configured electric-effect hook. |
| Whip pull | Casts and attaches a hook; holding repeatedly pulls the target toward the user. |
| Block | Frontal shield-style blocking with durability damage and axe/break-block interruption. |
| Levitate | Acquires an aimed target, then moves it toward an aimed point while held, using normal collision. |
| Spin | Repeated contact damage in front, without hitting behind the user. |
| Parry | Brief frontal defense and a single automatic counterattack. |
| Switch | Toggles the same item stack between two forms and their attributes. |
| Knockback | Frontal shove; no damage by default. |
| Charge | Sprinting contact pushes targets back; no damage by default. |

Whips own subsequent right-click gestures until detached. After a cast, a quick tap detaches; a hold activates the attached effect, and releasing the hold keeps the latch. A form cannot assign a whip and a separate right-click hold action because they would conflict. Hooks detach when obstructed, out of range, their owner/target becomes invalid, or their live session ends. Their temporary lifetime is 60 seconds. The particle tether is a placeholder presentation for future cord art.

Thrown weapons preserve their item components through save/load and arrow-style pickup. Recoverable projectiles expire after five minutes of loaded time; quick throws expire after five seconds and cannot be picked up. Weapon projectile art uses the item's model automatically.

Hybrid blaster shots use the existing bolt collision, material impacts, damage bonuses, and renderer. They currently cost only the melee action cooldown; full blaster ammo/reload/heat management is not imposed on melee weapons. A shot can apply the originating weapon's inherent effects on a successful hit.

## Balancing

Each binding can use `MeleeAttack.of(trait)` or a tuned copy:

```java
.bind(MeleeInput.HOLD_RIGHT_CLICK,
        MeleeAttack.of(MeleeAttackTrait.UPPERCUT)
                .damage(1.25F)
                .timing(16, 12, 10)
                .motion(0.7, 0.35, 1.2, 6))
```

`timing` means cooldown ticks, charge ticks, and contact interval ticks. `motion` means horizontal knockback, upward impulse, movement/projectile speed, and dash duration ticks. `area` sets the area/contact radius or levitation hold distance. The default values are balancing starting points: 10-tick action cooldown/charge/contact interval, 3-block area, 0.65 knockback, 0.3 lift, 1.2 speed, and 6-tick dash duration. Thrust always adds one block to the player's normal reach. Bolt damage is configured separately in `MeleeBlasterShot`.

`.tuning(new MeleeTuning(...))` configures the weapon's hold threshold, parry window, block-disable duration, fire duration, flurry success/miss speed multipliers, cortosis chance/duration, and tether range. Defaults are 4, 1, 60, 4, 1.5, 0.6, 0.25, 60, and 12 respectively. All tick durations are server-time values. Changing balance does not require editing input or networking code.

## Inherent properties and future integrations

- `ELECTRIC_SHOCK`: calls `.electricEffect(context -> ...)` after successful hits. It deliberately does not introduce the future electric status effect. `WHIP_SHOCK` calls this same hook while attached, even without the inherent property.
- `FIRE_DAMAGE`: ignites a successfully hit target for the configured duration.
- `LIGHTSABER_RESISTANT`: allows configured melee blocking against an active lightsaber. Assign it to weapons whose significant parts are phrik or beskar.
- `DISABLE_LIGHTSABERS`: allows cortosis blocking and rolls the configured shutdown chance on blade contact. Assign it to significant cortosis parts. Future lightsaber items implement `MeleeLightsaber.isBladeActive` and `disableBlade`, and enforce their own blade-disabled state. The melee side already calls this interface on both attacking and defending blade contact; no fictional lightsaber item or effect is registered.
- `FLURRY`: successful damage speeds up main-hand attack recovery; misses, blocks, or invulnerable hits slow it. Speed modifiers are removed when the weapon changes. Successful originating projectiles update the still-held weapon's flurry result without resetting its meter again.
- `STAGGER`: resets a player's attack meter on both server and client. Vanilla mobs using `MeleeAttackGoal` delay melee attacks for 20 ticks. Custom AI with another attack mechanism can call `MeleeWeaponServerEvents.isStaggered` to honor the same restriction.

For a collapsible weapon, provide `.form(base)` and `.alternateForm(extended)`, with a `RIGHT_CLICK / SWITCH` binding in both. Form selection and attributes are saved and synchronized; no replacement item or inventory transfer is needed.

Model predicates automatically register for every `MeleeWeaponItem`:

- `knightfall:held`: 1 in either hand.
- `knightfall:alternate_form`: 0 for the base form, 1 for the alternate form.
- `knightfall:melee_action`: 0 when idle; otherwise `MeleeAttackTrait.ordinal() + 1` for the held action.

Subscribe to `MeleeActionEvent` on the game event bus to add attack/charging sounds or send future animation packets. Its stack is a snapshot; `isCharging()` distinguishes hold startup from a triggered action. The framework supplies the state and hooks; custom spinning, thrusting, and blocking animations still need the future weapon assets and renderer work.

## Verification and handoff

`gradlew.bat check` includes `meleeContractTest`, which validates all 105 cells in the attack/input matrix, invalid definitions, gesture precedence, and immutability. `gradlew.bat runGameTestServer` runs real combat tests with fixtures under the standard `src/test` tree; those fixtures and the test structure are excluded from the shipped jar. The test world lives under `build/gametest-26.2`. Tests now register their functions and instances through the 26.2 registries and `RegisterGameTestsEvent`.

The engine is split between the existing item definitions, `MeleeWeaponClientEvents` for input edges, `MeleeWeaponServerEvents` for live actions, `MeleeTargeting` for collision queries, and `MeleeProjectileEntity` for throws/hooks. The normal blaster firing controller and grenade behavior remain separate. Dedicated-server verification also required guarding the existing client-only key bindings, render-layer subscriber, and GameRenderer accessor.

The next content work is registering actual weapons with their chosen bindings and balancing their values, then adding models, textures, animation presentation, sounds, the electric effect, and the lightsaber-side implementation. No particular weapon roster is assumed by this foundation.

Verified during implementation: 188 contract checks and all 22 dedicated-server GameTests passed. The gameplay checks cover reach/obstruction, tap/hold exclusivity, both shield types, parry expiry, player and mob stagger, flurry recovery, duplicate requests, throwing/pickup eligibility, whip pull/shock, levitation movement, ground impact, charge/dash, frontal versus radial contact, charged bolts, material contact, form persistence, and cancellation. Mock clients discard outgoing presentation packets; an actual multiplayer-client playtest and animation review remain content-integration work.
