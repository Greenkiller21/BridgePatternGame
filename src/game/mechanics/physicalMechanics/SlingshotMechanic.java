package game.mechanics.physicalMechanics;

import game.screens.Game;
import game.gameObjects.GameObject;
import game.projectiles.Stone;
import game.projectiles.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class SlingshotMechanic extends PhysicalMechanic {
    private static final Image[] images = new Image[5];

    static {
        try {
            images[0] = ImageIO.read(new File("assets/slingshot/slingshot_w.png"));
            images[1] = ImageIO.read(new File("assets/slingshot/slingshot_a.png"));
            images[2] = ImageIO.read(new File("assets/slingshot/slingshot_s.png"));
            images[3] = ImageIO.read(new File("assets/slingshot/slingshot_d.png"));
            images[4] = ImageIO.read(new File("assets/slingshot/slingshot_I.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Stone(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Stone(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
