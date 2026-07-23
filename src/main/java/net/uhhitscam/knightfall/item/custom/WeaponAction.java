package net.uhhitscam.knightfall.item.custom;

public enum WeaponAction {
    RELOAD(0),
    UNLOAD(1),
    SWITCH_MODE(2);

    private final int id;

    WeaponAction(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static WeaponAction byId(int id) {
        for (WeaponAction action : values()) {
            if (action.id == id) {
                return action;
            }
        }

        return RELOAD;
    }
}
