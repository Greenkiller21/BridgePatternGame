package game.mechanics.magicMechanics;

import game.screens.Game;
import game.gameObjects.GameObject;
import game.projectiles.Snowball;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public class IceMagicMechanic extends MagicMechanic {
    private static final Image[] images = loadImages(IceMagicMechanic.class);

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Snowball(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Snowball(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
