package game;

import game.characters.Character;
import game.characters.Orc;
import game.characters.Elf;
import game.characterControllers.AI;
import game.characterControllers.Player;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.GunMechanic;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static Game instance;
    private final GameHandler handler = new GameHandler();
    private boolean isRunning = false;
    private Thread thread;
    private final Window window;
    private int currentFps = 0;

    private Game() {
        window = new Window(1000, 600, "Game - Bridge pattern", this);
    }

    private void beginGame() {
        start();

        Character playerCharacter = new Elf(100, 10, new IceMagicMechanic());
        playerCharacter.setController(new Player());
        handler.addGameObject(playerCharacter);
        for (int i = 0; i < 1; ++i) {
            Character aiCharacter = new Orc(30, 10, new GunMechanic());
            aiCharacter.setController(new AI());
            handler.addGameObject(aiCharacter);
        }
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

    public GameHandler getGameHandler() {
        return handler;
    }

    public static Game getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Game();
        instance.beginGame();
    }
}