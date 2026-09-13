package net.uhhitscam.knightfall.melee;

import net.uhhitscam.knightfall.item.custom.melee.MeleeAttack;
import net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait;
import net.uhhitscam.knightfall.item.custom.melee.MeleeInput;
import net.uhhitscam.knightfall.item.custom.melee.MeleeInputRules;
import net.uhhitscam.knightfall.item.custom.melee.MeleeProperty;
import net.uhhitscam.knightfall.item.custom.melee.MeleeWeaponForm;



import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.uhhitscam.knightfall.item.custom.melee.MeleeAttackTrait.*;
import static net.uhhitscam.knightfall.item.custom.melee.MeleeInput.*;

public final class MeleeContractTest {
    private static int checks;

    public static void main(String[] args) {
        // Independent transcription of the user's table, in enum declaration order.
        List<String> expected = List.of("L", "LR", "LRH", "LR", "LRH", "RHF", "LRHMF", "RH", "RM",
                "H", "R", "HM", "R", "R", "H", "H", "H", "RH", "R", "RH", "M");
        String columns = "LRHMF";
        check(MeleeAttackTrait.values().length == expected.size(), "All 21 active traits exist");
        check(MeleeProperty.values().length == 6, "All six inherent properties exist");
        for (MeleeAttackTrait trait : MeleeAttackTrait.values()) {
            for (MeleeInput input : MeleeInput.values()) {
                boolean allowed = expected.get(trait.ordinal()).indexOf(columns.charAt(input.ordinal())) >= 0;
                check(trait.supports(input) == allowed, "Input matrix: " + trait + "/" + input);
                if (allowed) MeleeWeaponForm.builder(5, 1.6).bind(input, trait).build();
                else rejects(() -> MeleeWeaponForm.builder(5, 1.6).bind(input, trait).build());
            }
        }
        rejects(() -> MeleeWeaponForm.builder(5, 1.6).bind(LEFT_CLICK, SIMPLE_HIT).bind(LEFT_CLICK, THRUST));
        rejects(() -> MeleeWeaponForm.builder(5, 1.6).bind(RIGHT_CLICK, WHIP_PULL).bind(HOLD_RIGHT_CLICK, BLOCK).build());
        rejects(() -> new MeleeWeaponForm(Double.NaN, 1.6, Map.of(), Set.of()));
        rejects(() -> new MeleeWeaponForm(5, 0, Map.of(), Set.of()));
        rejects(() -> MeleeAttack.of(THRUST).timing(0, 1, 1));
        rejects(() -> MeleeAttack.of(THRUST).area(Double.POSITIVE_INFINITY));
        rejects(() -> MeleeAttack.of(THRUST).damage(-1));
        MeleeWeaponForm form = MeleeWeaponForm.builder(5, 1.6)
                .bind(RIGHT_CLICK, THRUST).bind(HOLD_RIGHT_CLICK, BLOCK)
                .bind(RUN_HOLD_RIGHT_CLICK, CHARGE).bind(FALL_HOLD_RIGHT_CLICK, SLAM_GROUND).build();
        check(MeleeInputRules.hold(form, true, true) == FALL_HOLD_RIGHT_CLICK, "Falling takes priority");
        check(MeleeInputRules.hold(form, false, true) == RUN_HOLD_RIGHT_CLICK, "Running takes priority over standing");
        check(MeleeInputRules.hold(form, false, false) == HOLD_RIGHT_CLICK, "Standing hold fallback");
        check(MeleeInputRules.isTap(3, 4, false), "Short release taps");
        check(!MeleeInputRules.isTap(4, 4, false), "Threshold is a hold, not a tap");
        check(!MeleeInputRules.isTap(0, 4, true), "Consumed press never also taps");
        rejectsUnsupported(() -> form.attacks().clear());
        System.out.println("Melee contract checks passed: " + checks);
    }

    private static void check(boolean value, String message) {
        checks++;
        if (!value) throw new AssertionError(message);
    }
    private static void rejects(Runnable action) {
        checks++;
        try { action.run(); } catch (IllegalArgumentException expected) { return; }
        throw new AssertionError("Expected invalid definition to be rejected");
    }
    private static void rejectsUnsupported(Runnable action) {
        checks++;
        try { action.run(); } catch (UnsupportedOperationException expected) { return; }
        throw new AssertionError("Definitions must be immutable");
    }
}
