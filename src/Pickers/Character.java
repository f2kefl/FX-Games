package Pickers;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by F4keFLy on 01.03.2016.
 * TODO:
 */

public class Character extends Pane
{
    int score = 0;
    Pickers.SpriteAnimation animation;

    Character(boolean isEnemy)
    {
        int offsetY = 0;
        int offsetX = 0;
        int width = 32;
        int height = 32;
        int count = 3;
        int columns = 3;

        Image image = new Image(getClass().getResourceAsStream("sprite1.png"));
        ImageView imageView = new ImageView(image);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        Image image1 = new Image(getClass().getResourceAsStream("sprite2.png"));
        ImageView imageView1 = new ImageView(image1);
        animation = new SpriteAnimation(isEnemy ? imageView1 : imageView, Duration.millis(200), count, columns, offsetX, offsetY, width, height);
        getChildren().addAll(isEnemy ? imageView1 : imageView);
    }
}
