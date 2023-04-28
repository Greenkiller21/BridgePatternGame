import java.awt.*;

public interface IRenderable {
    void tick();
    void render(Graphics g);
    Rectangle getBounds();
}
