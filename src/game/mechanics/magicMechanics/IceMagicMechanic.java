package game.mechanics.magicMechanics;

import game.projectiles.snowball.BigSnowball;
import game.projectiles.snowball.SmallSnowball;
import game.screens.Game;
import game.gameObjects.GameObject;
import game.projectiles.snowball.Snowball;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public class IceMagicMechanic extends MagicMechanic {
    private static final Image[] images = loadImages(IceMagicMechanic.class);

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new BigSnowball(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new SmallSnowball(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
