package game.interfaces;

import game.projectiles.Projectile;

public interface IDamageable {
    void damageWith(Projectile p);
    void heal(int amount);
    int getHealth();
}
