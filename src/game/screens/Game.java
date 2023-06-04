package game.screens;

import game.GameHandler;
import game.MasterWindow;
import game.ThreeParametersFunction;
import game.Utils;
import game.characters.Character;
import game.characters.Orc;
import game.characterControllers.AI;
import game.characterControllers.Player;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import game.mechanics.physicalMechanics.SlingshotMechanic;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static Game instance;
    private final GameHandler handler = new GameHandler();
    private boolean isRunning = false;
    private Thread thread;
    private int currentFps = 0;
    private Runnable whenFinished;

    private Game() { }

    public void beginGame(ThreeParametersFunction<Double, Double, Mechanic, Character> funcCrea) {
        Character playerCharacter = funcCrea.apply(100., 10., new IceMagicMechanic());
        playerCharacter.setController(new Player());
        handler.addPlayer(playerCharacter);

        for (int i = 0; i < 1; ++i) {
            Character aiCharacter = new Orc(30, 10, new SlingshotMechanic());
            aiCharacter.setController(new AI());
            handler.addGameObject(aiCharacter);
        }

        handler.setCurrentScreen(handler.getNewGameScreen());

        start();
    }

    public void gameOver() {
        isRunning = false;
        whenFinished = () -> MasterWindow.getInstance().goToGameOverScreen();
    }

    public void win() {
        isRunning = false;
        whenFinished = () -> MasterWindow.getInstance().goToWinScreen();
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();

        new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (whenFinished != null) {
                whenFinished.run();
            }
        }).start();
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
        Dimension cs = getSize();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(Color.white);
        g.fillRect(0, 0, cs.width, cs.height);

        ////

        int startX = 0;
        int startY = 5;

        //Render all GameObjects
        handler.render(g, startX, startY);

        //Print FPS
        String fps = String.valueOf(currentFps);
        g.setColor(Color.BLACK);

        int wFps = Utils.getStringWidth(g, fps);
        g.drawString(fps, getWidth() - wFps, startY + Utils.getStringHeight(g, fps) / 2);

        ////

        g.dispose();
        bs.show();
    }

    public GameHandler getGameHandler() {
        return handler;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
}