package game.characterControllers;

import game.GameHandler;
import game.screens.Game;
import game.Utils;
import game.characters.Character;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class Player extends CharacterController {
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private Point leftClickLocation = null;
    private Point rightClickLocation = null;
    private final boolean[] numbersPressed = new boolean[10];

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
            int kc = e.getKeyCode();
            switch (kc) {
                case KeyEvent.VK_W -> isUpPressed = newValue;
                case KeyEvent.VK_S -> isDownPressed = newValue;
                case KeyEvent.VK_A -> isLeftPressed = newValue;
                case KeyEvent.VK_D -> isRightPressed = newValue;
            }

            if (kc >= KeyEvent.VK_0 && kc <= KeyEvent.VK_9) {
                numbersPressed[kc - KeyEvent.VK_0] = newValue;
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
                leftClickLocation = getLocation(e, newValue);
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                rightClickLocation = getLocation(e, newValue);
            }
        }

        private Point getLocation(MouseEvent e, boolean newValue) {
            if (!newValue) {
                return null;
            }

            var sl = Game.getInstance().getLocationOnScreen();
            var cl = e.getLocationOnScreen();
            return new Point(cl.x - sl.x, cl.y - sl.y);
        }
    };

    public Player() {
        Game.getInstance().addKeyListener(kl);
        Game.getInstance().addMouseListener(ml);
    }

    @Override
    public Point2D.Double getVelocities(Character current) {
        double velX = 0;
        double velY = 0;

        if (isUpPressed) {
            velY = 1;
        }
        if (isDownPressed) {
            velY = -1;
        }
        if (isRightPressed) {
            velX = 1;
        }
        if (isLeftPressed) {
            velX = -1;
        }

        if (velX != 0 && Math.abs(velX) == Math.abs(velY)) {
            //sin of 45 -> 1 / sqrt(2) is there to unify the distance travelled in a tick
            double dist = Math.sin(45);
            velX = Math.signum(velX) * dist;
            velY = Math.signum(velY) * dist;
        }

        return new Point2D.Double(velX, velY);
    }

    public Mechanic getMechanic() {
        Mechanic[] mechanics = GameHandler.getMechanics();
        for (int i = 0; i < 10; ++i) {
            if (numbersPressed[i]) {
                if (i <= mechanics.length) {
                    return mechanics[(i + 9) % 10];
                }
            }
        }

        return null;
    }

    public Point2D.Double getBigAttackVector(double x, double y) {
        Point2D.Double vector = getAttackVector(x, y, leftClickLocation);
        leftClickLocation = null;
        return vector;
    }

    public Point2D.Double getSmallAttackVector(double x, double y) {
        Point2D.Double vector = getAttackVector(x, y, rightClickLocation);
        rightClickLocation = null;
        return vector;
    }

    private Point2D.Double getAttackVector(double x, double y, Point clickLocation) {
        if (clickLocation != null) {
            Point2D.Double p = new Point2D.Double(clickLocation.x - x, y - clickLocation.y);
            return Utils.normalize(p);
        }
        return null;
    }

    @Override
    public void drawHealthBar(Graphics g, Character c) {
        int hbWidth = 250;
        int hbHeight = 30;
        int hbX = 10;
        int hbY = 10;

        int margin = 3;

        int height = Game.getInstance().getHeight();
        int hbRealY = height - hbY - hbHeight;

        drawHealthBarReal(g, hbWidth, hbHeight, hbX, hbRealY, margin, c.getHealth() / 100.);
    }
}
