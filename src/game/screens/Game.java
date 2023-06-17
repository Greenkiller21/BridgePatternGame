package game.screens;

import game.GameHandler;
import game.MasterWindow;
import game.characterControllers.AI;
import game.characterControllers.Player;
import game.characters.Character;
import game.mechanics.Mechanic;
import game.mechanics.magicMechanics.IceMagicMechanic;
import utils.CharacterConstructor;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * The game screen
 */
public class Game extends Canvas implements Runnable {
    private static Game instance;
    private final GameHandler handler = new GameHandler();
    private boolean isRunning = false;
    private Thread thread;
    private int currentFps = 0;

    /**
     * The action to run when the game is not running anymore
     */
    private Runnable whenFinished;

    /**
     * The constructor to create the player character
     */
    private CharacterConstructor playerCreator;

    /**
     * The current stage
     */
    private int currentStage;

    private Game() { }

    /**
     * Starts the game
     * @param playerCreator The constructor to create the player character
     */
    public void beginGame(CharacterConstructor playerCreator) {
        this.playerCreator = playerCreator;

        currentStage = 1;
        beginStage();
    }

    /**
     * Adds the player to the game
     */
    private void addPlayer() {
        int screenWidth = Game.getInstance().getWidth();
        int screenHeight = Game.getInstance().getHeight();
        Character playerCharacter;
        do {
            double x = Utils.getRandom().nextInt(1, screenWidth);
            double y = Utils.getRandom().nextInt(1, screenHeight);

            playerCharacter = playerCreator.apply(x, y, IceMagicMechanic.getInstance());
        } while (!playerCharacter.isGeneratedPositionValid());

        playerCharacter.setController(new Player());
        handler.addPlayer(playerCharacter);
    }

    /**
     * Adds multiple enemies to the game
     * @param number The number of enemies
     */
    private void addEnemies(int number) {
        int screenWidth = Game.getInstance().getWidth();
        int screenHeight = Game.getInstance().getHeight();

        for (int i = 0; i < number; ++i) {
            double x = Utils.getRandom().nextInt(1, screenWidth);
            double y = Utils.getRandom().nextInt(1, screenHeight);

            Mechanic randomMechanic = GameHandler.getMechanics()[Utils.getRandom().nextInt(0, GameHandler.getMechanics().length)];
            CharacterConstructor characterCreator = GameHandler.getCharacters().values().stream().toList().get(Utils.getRandom().nextInt(0, GameHandler.getCharacters().size()));
            Character c = characterCreator.apply(x, y, randomMechanic);

            if (!c.isGeneratedPositionValid()) {
                i--;
                continue;
            }

            c.setController(new AI());
            handler.addGameObject(c);
        }
    }

    /**
     * Sets the game as finished and sets whenFinished to go to the game over screen
     */
    public void gameOver() {
        whenFinished = () -> MasterWindow.getInstance().goToGameOverScreen();
        isRunning = false;
    }

    /**
     * Sets the game as finished and sets whenFinished to go to the win screen
     */
    public void win() {
        whenFinished = () -> MasterWindow.getInstance().goToWinScreen();
        isRunning = false;
    }

    /**
     * Starts the game thread
     */
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();

        new Thread(() -> {
            try {
                thread.join(); //Waits for the thread to end
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Runs the whenFinished method if it exists
            if (whenFinished != null) {
                whenFinished.run();
            }
        }).start();
    }

    /**
     * Inspired from here : https://gamedev.stackexchange.com/a/154986
     */
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int targetFPS = 100; //FPS limit
        long frameTime = 1000000000 / targetFPS;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                currentFps = frames;
                frames = 0;
            }

            // Limit FPS by introducing a delay
            long frameEndTime = System.nanoTime();
            long elapsedTime = frameEndTime - now;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1000000); // Convert nanoseconds to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Tick the handler
     */
    public void tick() {
        handler.tick();
    }

    /**
     * Rendering
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        do {
            do {
                Graphics2D g = (Graphics2D)bs.getDrawGraphics();
                Dimension cs = getSize();

                //Disabled rendering hint because of poor performances

                //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                //g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                //Clears all the area
                g.clearRect(0, 0, cs.width, cs.height);

                ////

                //Render all GameObjects
                handler.render(g, 0, 0);

                //Print FPS
                String fps = String.valueOf(currentFps);
                g.setColor(Color.BLACK);

                int wFps = Utils.getStringWidth(g, fps);
                g.drawString(fps, getWidth() - wFps, Utils.getStringHeight(g, fps));

                ////

                g.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while(bs.contentsLost());
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

    /**
     * Go to the next stage
     */
    public void nextStage() {
        ++currentStage;
        beginStage();
    }

    /**
     * Starts the stage
     */
    private void beginStage() {
        handler.reset();

        addEnemies(currentStage * 2);
        addPlayer();

        start();
    }
}