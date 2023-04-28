package game.playerTypes;

import game.Game;
import game.characters.Character;
import game.projectiles.Bullet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends ControllableEntity {
    private static final double SPEED = 2.0;

    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private boolean isShootPressed = false;

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
                case KeyEvent.VK_L -> isShootPressed = newValue;
            }
        }
    };

    public Player(int x, int y, Character character) {
        super(x, y, character);
        Game.getInstance().addKeyListener(kl);
    }

    @Override
    public void tick() {
        velX = 0;
        velY = 0;

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

        if (isShootPressed) {
            Bullet b = new Bullet(x, y);
            b.setVelX(1);
            b.setVelY(0);
            Game.getInstance().getGameHandler().addGameObject(b);
        }

        super.tick();
    }
}
