package game.mechanics.physicalMechanics;

import game.Game;
import game.gameObjects.GameObject;
import game.projectiles.Bullet;
import game.projectiles.Icicle;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public class GunMechanic extends PhysicalMechanic {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 1, y + 1, 2, 5);
    }

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Bullet(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Bullet(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }
}
