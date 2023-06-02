package game.screens;

import game.Game;
import game.Utils;

import java.awt.*;

public class WinScreen extends Screen {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        Font old = g.getFont();
        g.setFont(old.deriveFont(Font.BOLD, 40));
        Utils.drawCenteredString(g, "You won !", Game.getInstance().getWidth() / 2, Game.getInstance().getHeight() / 2, Game.getInstance().getWidth(), Game.getInstance().getHeight());
        g.setFont(old);
    }

    @Override
    public void tick() {

    }
}
