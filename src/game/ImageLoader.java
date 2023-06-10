package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImageLoader {
    private static class ImagesContainer<T> {
        public final Map<String, String> aliases = new LinkedHashMap<>();
        private final Map<String, Image> images = new LinkedHashMap<>();
        private final Class<T> clazz;

        public ImagesContainer(Class<T> clazz) {
            this.clazz = clazz;
        }

        public void loadAsset(String alias, String path) {
            try {
                Image img = ImageIO.read(new File("assets/" + path));
                images.put(alias, img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public Image getOrLoad(String alias) {
            Image img = images.get(alias);
            if (img != null) {
                return img;
            }

            String path = aliases.get(alias);
            if (path == null) {
                throw new RuntimeException("The alias was not found !");
            }

            loadAsset(alias, path);
            return images.get(alias);
        }

        public void load(String alias, String path) {
            aliases.put(alias, path);
        }

        public Class<T> getClazz() {
            return clazz;
        }
    }


    private static final LinkedList<ImagesContainer<?>> containers = new LinkedList<>();

    public static void addAlias(String alias, String path, Class<?> clazz) {
        getImagesContainer(clazz).load(alias, path);
    }

    public static Image getImage(String alias, Class<?> clazz) {
        return getImagesContainer(clazz).getOrLoad(alias);
    }

    private static ImagesContainer<?> getImagesContainer(Class<?> clazz) {
        ImagesContainer<?> container = containers.stream().filter(a -> a.getClazz() == clazz).findFirst().orElse(null);

        if (container == null) {
            container = new ImagesContainer<>(clazz);
            containers.add(container);
        }

        return container;
    }
}