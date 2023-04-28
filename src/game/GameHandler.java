import java.awt.*;
import java.util.LinkedList;

public class GameHandler {
    private LinkedList<IRenderable> renderables = new LinkedList<>();

    public void render(Graphics g) {
        for (IRenderable renderable : renderables) {
            renderable.render(g);
        }
    }

    public void tick() {
        for (IRenderable renderable : renderables) {
            renderable.tick();
        }
    }

    public void addRenderable(IRenderable renderable) {
        renderables.add(renderable);
    }

    public void removeRenderable(IRenderable renderable) {
        renderables.remove(renderable);
    }
}
