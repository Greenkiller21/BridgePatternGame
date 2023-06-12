package game.projectiles.leech;

import game.Element;
import game.characters.Character;
import game.gameObjects.GameObject;
import game.gameObjects.GameObjectType;
import game.projectiles.Projectile;
import utils.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;

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
        if (creator.getType() == GameObjectType.Character) {
            Character creatorCharacter = (Character)creator;
            creatorCharacter.addHealth(getDamage() / 5);
            super.doActionOnCollide(creator, collide);
        }
    }
}
