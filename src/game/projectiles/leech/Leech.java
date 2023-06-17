package game.projectiles.leech;

import game.Element;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.projectiles.Projectile;
import utils.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Class representing a leech
 */
public class Leech extends Projectile {
    public Leech(double x, double y, Point2D.Double dirVect, GameObject creator) {
        super(x, y, dirVect, creator);
    }

    @Override
    public Element getProjectileElement() {
        return Element.Wood;
    }

    @Override
    protected Image getImage() {
        return ImageLoader.getImage("normal", Leech.class);
    }

    @Override
    public void doActionOnCollide(GameObject creator, Character collide) {
        //If the creator of the projectile is a character
        if (creator.getType() == GameObjectType.Character) {
            //We add 1 / 5 of the base damage done to the enemy as health to the creator
            Character creatorCharacter = (Character)creator;
            creatorCharacter.addHealth(getDamage() / 5);
            super.doActionOnCollide(creator, collide);
        }
    }
}
