package game.playerTypes;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends ControllableEntitiy {
    private KeyListener kl = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
        }
    };

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {

    }

    public KeyListener getKeyListener() {
        return kl;
    }
}
