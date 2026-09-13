package net.uhhitscam.knightfall.item.custom.melee;

import java.util.Set;

import static net.uhhitscam.knightfall.item.custom.melee.MeleeInput.*;

public enum MeleeAttackTrait {
    SIMPLE_HIT(LEFT_CLICK),
    THRUST(LEFT_CLICK, RIGHT_CLICK),
    UPPERCUT(LEFT_CLICK, RIGHT_CLICK, HOLD_RIGHT_CLICK),
    SLASH(LEFT_CLICK, RIGHT_CLICK),
    BLASTER_SHOT(LEFT_CLICK, RIGHT_CLICK, HOLD_RIGHT_CLICK),
    SLAM_GROUND(RIGHT_CLICK, HOLD_RIGHT_CLICK, FALL_HOLD_RIGHT_CLICK),
    BREAK_BLOCK(LEFT_CLICK, RIGHT_CLICK, HOLD_RIGHT_CLICK, RUN_HOLD_RIGHT_CLICK, FALL_HOLD_RIGHT_CLICK),
    SWEEP(RIGHT_CLICK, HOLD_RIGHT_CLICK),
    DASH(RIGHT_CLICK, RUN_HOLD_RIGHT_CLICK),
    THROW(HOLD_RIGHT_CLICK),
    QUICK_THROW(RIGHT_CLICK),
    STEADY(HOLD_RIGHT_CLICK, RUN_HOLD_RIGHT_CLICK),
    WHIP_SHOCK(RIGHT_CLICK),
    WHIP_PULL(RIGHT_CLICK),
    BLOCK(HOLD_RIGHT_CLICK),
    LEVITATE(HOLD_RIGHT_CLICK),
    SPIN(HOLD_RIGHT_CLICK),
    PARRY(RIGHT_CLICK, HOLD_RIGHT_CLICK),
    SWITCH(RIGHT_CLICK),
    KNOCKBACK(RIGHT_CLICK, HOLD_RIGHT_CLICK),
    CHARGE(RUN_HOLD_RIGHT_CLICK);

    private final Set<MeleeInput> inputs;

    MeleeAttackTrait(MeleeInput... inputs) {
        this.inputs = Set.of(inputs);
    }

    public Set<MeleeInput> inputs() { return inputs; }
    public boolean supports(MeleeInput input) { return inputs.contains(input); }

    public boolean continuous() {
        return this == STEADY || this == BLOCK || this == LEVITATE || this == SPIN || this == CHARGE;
    }

    public boolean whip() { return this == WHIP_SHOCK || this == WHIP_PULL; }
}
