package game.mechanics.physicalMechanics;

import game.gameObjects.GameObject;
import game.projectiles.Projectile;
import game.projectiles.stone.BigStone;
import game.projectiles.stone.SmallStone;
import game.screens.Game;

import java.awt.geom.Point2D;

public class SlingshotMechanic extends PhysicalMechanic {
    private static final SlingshotMechanic instance = new SlingshotMechanic();
    public static SlingshotMechanic getInstance() {
        return instance;
    }

    protected SlingshotMechanic() {}

    @Override
    public void createFirstAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new BigStone(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSecondAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new SmallStone(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public int firstAttackManaCost() {
        return 35;
    }

    @Override
    public int secondAttackManaCost() {
        return 20;
    }
}
