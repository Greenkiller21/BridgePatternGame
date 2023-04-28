package game.playerTypes;

import game.characters.Character;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends ControllableEntity {
    private static final double SPEED_X = 1.0;
    private static final double SPEED_Y = 1.0;

    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;

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

    public Player(int x, int y, Character character) {
        super(x, y, character);
    }

    @Override
    public void tick() {
        velX = 0;
        velY = 0;

        if (isUpPressed) {
            velY = SPEED_Y;
        }
        if (isDownPressed) {
            velY = -SPEED_Y;
        }
        if (isRightPressed) {
            velX = SPEED_X;
        }
        if (isLeftPressed) {
            velX = -SPEED_X;
        }

        if (velX != 0 && Math.abs(velX) == Math.abs(velY)) {
            double dist = 1.0 / Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
            velX = Math.signum(velX) * dist;
            velY = Math.signum(velY) * dist;
        }

        super.tick();
    }

    public void bindListeners(Component c) {
        c.addKeyListener(kl);
    }
}
