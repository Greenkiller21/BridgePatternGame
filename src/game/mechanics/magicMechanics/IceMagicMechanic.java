package game.mechanics.magicMechanics;

import game.gameObjects.GameObject;
import game.projectiles.Projectile;
import game.projectiles.snowball.BigSnowball;
import game.projectiles.snowball.SmallSnowball;
import game.screens.Game;

import java.awt.geom.Point2D;

/**
 * Class representing an ice magic mechanic
 */
public class IceMagicMechanic extends MagicMechanic {
    private static final IceMagicMechanic instance = new IceMagicMechanic();
    public static IceMagicMechanic getInstance() {
        return instance;
    }

    protected IceMagicMechanic() { }

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
    public int firstAttackManaCost() {
        return 30;
    }

    @Override
    public int secondAttackManaCost() {
        return 20;
    }
}
