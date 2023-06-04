package game.mechanics.magicMechanics;

import game.screens.Game;
import game.gameObjects.GameObject;
import game.projectiles.Icicle;
import game.projectiles.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class IceMagicMechanic extends MagicMechanic {
    private static final Image[] images = new Image[5];

    static {
        try {
            images[0] = ImageIO.read(new File("assets/ice_magic/ice_magic_w.png"));
            images[1] = ImageIO.read(new File("assets/ice_magic/ice_magic_a.png"));
            images[2] = ImageIO.read(new File("assets/ice_magic/ice_magic_s.png"));
            images[3] = ImageIO.read(new File("assets/ice_magic/ice_magic_d.png"));
            images[4] = ImageIO.read(new File("assets/ice_magic/ice_magic_I.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBigAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Icicle(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    public void createSmallAttack(GameObject creator, Point2D.Double dirVect) {
        Projectile p = new Icicle(creator.getX(), creator.getY(), creator);
        p.setVelX(dirVect.x);
        p.setVelY(dirVect.y);
        Game.getInstance().getGameHandler().addGameObject(p);
    }

    @Override
    protected Image[] getImages() {
        return images;
    }
}
