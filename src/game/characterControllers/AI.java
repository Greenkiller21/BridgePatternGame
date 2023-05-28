package game.characterControllers;

import game.characters.Character;

import java.awt.geom.Point2D;

public class AI extends CharacterController {
    @Override
    public Point2D.Double getVelocities() {
        return new Point2D.Double(0, 0);
    }

    @Override
    public Point2D.Double getBulletVector(double x, double y) {
        return null;
    }
}
