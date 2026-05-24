package net.uhhitscam.knightfall.item.custom;

public enum WeaponCooldownAction {
    RELOAD(0),
    SWITCH(1),
    EQUIP(2);

    private final int id;

    WeaponCooldownAction(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static WeaponCooldownAction byId(int id) {
        for (WeaponCooldownAction action : values()) {
            if (action.id == id) {
                return action;
            }
        }

        return SWITCH;
    }
}