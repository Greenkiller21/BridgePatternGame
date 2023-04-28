package game;

import game.characters.Dwarf;
import game.characters.Elf;
import game.playerTypes.AI;
import game.playerTypes.Player;
import game.weapons.magic.IceSpellBook;
import game.weapons.physical.Sword;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private final GameHandler handler;
    private final Window window;
    private int currentFps = 0;

    public Game() {
        window = new Window(1000, 600, "Game - Bridge pattern", this);
        handler = new GameHandler();
        start();

        Player p = new Player(10, 10, new Elf(new IceSpellBook()));
        p.bindListeners(this);

        handler.addGameObject(p);
        handler.addGameObject(new AI(30, 10, new Dwarf(new Sword())));
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

        while (isRunning) {
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
                // System.out.println(frames + " FPS");
                currentFps = frames;
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

        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        Dimension cs = window.getCurrentSize();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(Color.white);
        g.fillRect(0, 0, cs.width, cs.height);

        ////

        int startX = 0;
        int startY = 10;
        int endX = 15;

        //Render all GameObjects
        handler.render(g, startX, startY);

        //Print FPS
        String fps = String.valueOf(currentFps);
        g.setColor(Color.BLACK);

        int wFps = ((int)g.getFont().getStringBounds(fps, g.getFontRenderContext()).getWidth()) + 1;

        g.drawString(fps, window.getCurrentSize().width - endX - wFps, startY);

        ////

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}