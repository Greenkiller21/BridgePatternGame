package game.mechanics.physicalMechanics;

import java.awt.*;

public class GunMechanic extends PhysicalMechanic {
    @Override
    public void render(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.fillRect(x + 1, y + 1, 2, 5);
    }
}
