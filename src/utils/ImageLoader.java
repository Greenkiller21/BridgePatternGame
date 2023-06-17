package utils;

import game.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Image manager
 */
public class ImageLoader {
    /**
     * Image container for a specific class
     * @param <T> The class
     */
    private static class ImagesContainer<T> {
        public final Map<String, String> aliases = new LinkedHashMap<>();
        private final Map<String, Image> images = new LinkedHashMap<>();
        private final Class<T> clazz;

        public ImagesContainer(Class<T> clazz) {
            this.clazz = clazz;
        }

        /**
         * Loads an image
         * @param alias The alias
         * @param path The path of the image (without the /assets/)
         */
        public void loadAsset(String alias, String path) {
            try {
                //Reads the images and put it in the map
                Image img = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/assets/" + path)));
                images.put(alias, img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Gets the image from the map or loads the image and store it in the map
         * @param alias The alias to load
         * @return The image
         */
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

        /**
         * Adds an alias with a path
         * @param alias The alias
         * @param path The path
         */
        public void addAlias(String alias, String path) {
            aliases.put(alias, path);
        }

        public Class<T> getClazz() {
            return clazz;
        }
    }

    /**
     * Stores all the containers
     */
    private static final LinkedList<ImagesContainer<?>> containers = new LinkedList<>();

    /**
     * Adds an alias for a specific class
     * @param alias The alias
     * @param path The path (without the /assets/)
     * @param clazz The class
     */
    public static void addAlias(String alias, String path, Class<?> clazz) {
        getImagesContainer(clazz).addAlias(alias, path);
    }

    /**
     * Returns the image from the image container
     * @param alias The alias
     * @param clazz The class
     * @return The image
     */
    public static Image getImage(String alias, Class<?> clazz) {
        return getImagesContainer(clazz).getOrLoad(alias);
    }

    /**
     * Creates or return the image container
     * @param clazz The class
     * @return The image container
     */
    private static ImagesContainer<?> getImagesContainer(Class<?> clazz) {
        ImagesContainer<?> container = containers.stream().filter(a -> a.getClazz() == clazz).findFirst().orElse(null);

        if (container == null) {
            container = new ImagesContainer<>(clazz);
            containers.add(container);
        }

        return container;
    }
}