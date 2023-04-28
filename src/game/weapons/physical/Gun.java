package game.weapons.physical;

import java.awt.*;

public class Gun extends PhysicalWeapon {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 1, y + 1, 2, 5);
    }
}
