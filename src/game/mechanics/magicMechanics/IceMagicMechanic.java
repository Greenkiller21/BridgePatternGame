package game.mechanics.magicMechanics;

import game.gameObjects.GameObject;
import game.projectiles.Projectile;
import game.projectiles.snowball.BigSnowball;
import game.projectiles.snowball.SmallSnowball;
import game.screens.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public class IceMagicMechanic extends MagicMechanic {
    private static final IceMagicMechanic instance = new IceMagicMechanic();
    public static IceMagicMechanic getInstance() {
        return instance;
    }

    protected IceMagicMechanic() {}

    private static final Image[] images = loadImages(IceMagicMechanic.class);

    @Override
    public void createFirstAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new BigSnowball(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSecondAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new SmallSnowball(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public int firstAttackCooldown() {
        return 60;
    }

    @Override
    public int secondAttackCooldown() {
        return 50;
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
