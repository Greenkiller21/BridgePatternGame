package game.characterControllers;

import game.GameHandler;
import game.screens.Game;
import utils.Utils;
import game.characters.Character;
import game.mechanics.Mechanic;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class Player extends CharacterController {
    private static final int BARS_X = 10;
    private static final int BARS_WIDTH = 250;
    private static final int BARS_HEIGHT = 30;
    private static final int BARS_MARGIN = 3;

    private static final int MANABAR_Y = 10;
    private static final int HEALTHBAR_Y = MANABAR_Y + BARS_HEIGHT + 1;

    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private Point leftClickLocation = null;
    private Point rightClickLocation = null;
    private final boolean[] numbersPressed = new boolean[10];
    private Point2D.Double firstAttackVector;
    private Point2D.Double secondAttackVector;
    private Point2D.Double velocities;
    private Mechanic mechanic;

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
    public Point2D.Double getVelocities() {
        return velocities;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public Point2D.Double getFirstAttackVector() {
        return firstAttackVector;
    }

    public Point2D.Double getSecondAttackVector() {
        return secondAttackVector;
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
        int height = Game.getInstance().getHeight();
        int hbRealY = height - HEALTHBAR_Y - BARS_HEIGHT;

        drawProgressBar(g, BARS_WIDTH, BARS_HEIGHT, BARS_X, hbRealY, BARS_MARGIN, c.getHealth() / 100., Color.RED);
    }

    @Override
    public void drawManaBar(Graphics g, Character c) {
        int height = Game.getInstance().getHeight();
        int hbRealY = height - MANABAR_Y - BARS_HEIGHT;

        drawProgressBar(g, BARS_WIDTH, BARS_HEIGHT, BARS_X, hbRealY, BARS_MARGIN, c.getMana() / 100., Color.BLUE);
    }

    @Override
    public void tick(Character c) {
        //Attack vectors
        firstAttackVector = getAttackVector(c.getX(), c.getY(), leftClickLocation);
        secondAttackVector = getAttackVector(c.getX(), c.getY(), rightClickLocation);
        leftClickLocation = rightClickLocation = null;

        //Velocities
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

        velocities = new Point2D.Double(velX, velY);

        //Mechanic
        Mechanic[] mechanics = GameHandler.getMechanics();
        mechanic = null;
        for (int i = 0; i < 10; ++i) {
            if (numbersPressed[i]) {
                if (i <= mechanics.length) {
                    mechanic = mechanics[(i + 9) % 10];
                }
            }
        }
    }
}
