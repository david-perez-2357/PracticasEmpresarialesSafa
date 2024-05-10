package core.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

public class ImageLoader {

    /**
     * Load an image from the file system and set it to the specified ImageView.
     * @param imageView The ImageView to set the image to.
     * @param imagePath The path of the image file.
     */
    public static void loadImage(ImageView imageView, String imagePath) {
        // Create a file object from the provided path
        File file = new File(Objects.requireNonNull(ImageLoader.class.getResource(imagePath)).getFile());

        // Check if the file exists
        if (file.exists()) {
            // Create an Image object with the file
            Image image = new Image(file.toURI().toString());

            // Set the image in the ImageView
            imageView.setImage(image);
        } else {
            System.out.println("El archivo no se ha encontrado: " + imagePath);
        }
    }

}
