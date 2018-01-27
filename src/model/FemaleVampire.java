package model;

// Class that represents the female vampire archetype

import java.io.IOException;

public class FemaleVampire extends Creatures {

    private static final int BITE_DAMAGE = 100;
    private static final String FEMALE_VAMPIRE_PATH_SPRITE = "res/img/female_vampire_sprite.png";

    public FemaleVampire(int x, int y, int speed) throws IOException {
        setX(x);
        setY(y);
        setSpeed(speed);
        setBite_damage(BITE_DAMAGE);
        try {
            setSprite(FEMALE_VAMPIRE_PATH_SPRITE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getBiteDamage() { return BITE_DAMAGE; }

    public String getPathSprite() { return FEMALE_VAMPIRE_PATH_SPRITE; }
}
