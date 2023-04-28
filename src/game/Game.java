package game;

import game.gameObjects.MovableGameObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private final GameHandler handler;
    private final Window window;

    public Game() {
        window = new Window(1000, 600, "Game - Bridge pattern", this);
        handler = new GameHandler();
        start();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

        MovableGameObject mgo = new MovableGameObject(10, 10) {
            @Override
            public void tick() {
                x += velX;
                y += velY;
            }

            @Override
            public void render(Graphics g) {
                g.setColor(Color.BLUE);
                g.fillRect(x, y, 32, 32);
            }

            @Override
            public Rectangle getBounds() {
                return null;
            }
        };
        mgo.setVelY(1);
        handler.addGameObject(mgo);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                // updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                // updates = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Dimension cs = window.getCurrentSize();

        g.setColor(Color.white);
        g.fillRect(0, 0, cs.width, cs.height);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}