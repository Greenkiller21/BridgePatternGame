package game.mechanics.magicMechanics;

import java.awt.*;

public class IceMagicMechanic extends MagicMechanic {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 5, y + 5, 3, 4);
    }
}
