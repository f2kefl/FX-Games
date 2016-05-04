package FlappyBird;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static FlappyBird.Flappy.score;
import static FlappyBird.Flappy.walls;

/**
 * Created by F4keFLy on 04.04.2016.
 * Enjoy it!
 */
class Pig extends Pane
{
    static Point2D velocity;
    Image pig = new Image(getClass().getResourceAsStream("pig.png"));
    ImageView imageView = new ImageView(pig);

    Pig()
    {
        velocity = new Point2D(0, 0);
        setTranslateX(100);
        setTranslateY(300);
        getChildren().add(imageView);
    }

    void moveY(int value)
    {
        boolean moveDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            walls.forEach(wall -> {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if (moveDown) {
                        setTranslateY(getTranslateY() - 1);
                    } else {
                        setTranslateY(getTranslateY() + 1);
                    }
                }
            });
            if (getTranslateY() < 0) setTranslateY(0);
            if (getTranslateY() > 572) setTranslateY(572);
            setTranslateY(getTranslateY() + (moveDown ? 1 : -1));
        }
    }

    void moveX(int value)
    {
        boolean moveRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            walls.forEach(wall -> {
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    if (this.getTranslateX() + 28 == wall.getTranslateX()) {
                        setTranslateX(getTranslateX() - 1);
                    }
                }
                if (this.getTranslateX() + 27 == wall.getTranslateX()){
                    score = score + walls.indexOf(wall)+1;
                }
            });
            if (getTranslateX() < 0) setTranslateX(0);
            setTranslateX(getTranslateX() + (moveRight ? 1 : -1));
        }
    }

    void jump()
    {
        velocity = new Point2D(2, -12);
    }
}
