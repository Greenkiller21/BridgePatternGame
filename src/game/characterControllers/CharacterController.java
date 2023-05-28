package game.characterControllers;

import java.awt.geom.Point2D;

public abstract class CharacterController {
    public abstract Point2D.Double getVelocities();
    public abstract Point2D.Double getBigAttackVector(double x, double y);
    public abstract Point2D.Double getSmallAttackVector(double x, double y);
}
