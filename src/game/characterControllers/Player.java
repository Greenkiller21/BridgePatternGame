package game.characterControllers;

import game.Game;
import game.Utils;
import game.characters.Character;
import game.projectiles.Bullet;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class Player extends CharacterController {
    private static final double SPEED = 2.0;

    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private boolean isShootPressed = false;
    private Point clickLocation = null;

    private final KeyListener kl = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            handle(e, true);
            super.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            handle(e, false);
            super.keyReleased(e);
        }

        private void handle(KeyEvent e, boolean newValue) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> isUpPressed = newValue;
                case KeyEvent.VK_S -> isDownPressed = newValue;
                case KeyEvent.VK_A -> isLeftPressed = newValue;
                case KeyEvent.VK_D -> isRightPressed = newValue;
            }
        }
    };

    private final MouseListener ml = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            handle(e, true);
            super.mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            handle(e, false);
            super.mouseReleased(e);
        }

        private void handle(MouseEvent e, boolean newValue) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (!newValue) {
                    isShootPressed = false;
                    clickLocation = null;
                    return;
                }

                var sl = Game.getInstance().getLocationOnScreen();
                var cl = e.getLocationOnScreen();
                clickLocation = new Point(cl.x - sl.x, cl.y - sl.y);
                isShootPressed = true;
            }
        }
    };

    public Player() {
        Game.getInstance().addKeyListener(kl);
        Game.getInstance().addMouseListener(ml);
    }

    @Override
    public Point2D.Double getVelocities() {
        double velX = 0;
        double velY = 0;

        if (isUpPressed) {
            velY = SPEED;
        }
        if (isDownPressed) {
            velY = -SPEED;
        }
        if (isRightPressed) {
            velX = SPEED;
        }
        if (isLeftPressed) {
            velX = -SPEED;
        }

        if (velX != 0 && Math.abs(velX) == Math.abs(velY)) {
            //sin of 45 -> 1 / sqrt(2) is there to unify the distance travelled in a tick
            double dist = SPEED * Math.sin(45);
            velX = Math.signum(velX) * dist;
            velY = Math.signum(velY) * dist;
        }

        return new Point2D.Double(velX, velY);
    }

    public Point2D.Double getBulletVector(double x, double y) {
        if (isShootPressed) {
            isShootPressed = false;
            Point2D.Double p = new Point2D.Double(clickLocation.x - x, y - clickLocation.y);
            return Utils.normalize(p);
        }
        return null;
    }
}
