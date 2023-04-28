package game.weapons.magic;

import java.awt.*;

public class IceSpellBook extends SpellBook {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 5, y + 5, 3, 4);
    }
}
