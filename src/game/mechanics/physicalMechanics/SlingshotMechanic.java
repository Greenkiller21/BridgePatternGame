package game.mechanics.physicalMechanics;

import game.projectiles.stone.BigStone;
import game.projectiles.stone.SmallStone;
import game.screens.Game;
import game.gameObjects.GameObject;
import game.projectiles.stone.Stone;
import game.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.Point2D;

public class SlingshotMechanic extends PhysicalMechanic {
    private static final Image[] images = loadImages(SlingshotMechanic.class);

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new BigStone(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new SmallStone(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
