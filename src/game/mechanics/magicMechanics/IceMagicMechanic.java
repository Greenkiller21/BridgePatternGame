package game.mechanics.magicMechanics;

import game.Game;
import game.GameHandler;
import game.gameObjects.GameObject;
import game.projectiles.Icicle;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public class IceMagicMechanic extends MagicMechanic {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 5, y + 5, 3, 4);
    }

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Icicle(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Icicle(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }
}
