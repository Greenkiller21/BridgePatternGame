package game.mechanics.magicMechanics;

import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.projectiles.Projectile;
import game.projectiles.leech.Leech;
import game.screens.Game;

import java.awt.geom.Point2D;

public class WoodMagicMechanic extends MagicMechanic {
    private static final WoodMagicMechanic instance = new WoodMagicMechanic();
    public static WoodMagicMechanic getInstance() {
        return instance;
    }

    protected WoodMagicMechanic() { }

    @Override
    public void createFirstAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Leech(creator.getX(), creator.getY(), dirVect, creator);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSecondAttack(GameObject creator, Point2D.Double dirVect) {
        if (creator.getType() == GameObjectType.Character) {
            Character go = (Character) creator;
            go.addHealth(25);
        }
    }

    @Override
    public int firstAttackManaCost() {
        return 20;
    }

    @Override
    public int secondAttackManaCost() {
        return 100;
    }
}
