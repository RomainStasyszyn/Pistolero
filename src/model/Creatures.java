package model;

// Class that represents the ennemies of the pistolero

public abstract class Creatures extends Characters {

    private int bite_damage = 0;

    public int getBiteDamage() { return bite_damage; }

    public void setBite_damage(int new_bite_damage) { bite_damage = new_bite_damage; }
}
