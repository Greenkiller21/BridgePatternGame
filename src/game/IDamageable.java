package game;

import game.projectiles.Projectile;

public interface IDamageable {
    void damageWith(Projectile p);
    int getHealth();
}
