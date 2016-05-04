package Pickers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by F4keFLy on 01.03.2016.
 * Enjoy it!
 */

class SpriteAnimation extends Transition
{
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;

    SpriteAnimation(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height)
    {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE); // число циклов анимации не определено
        setInterpolator(Interpolator.LINEAR); // линейное передвижение по кадрам

        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    void setOffsetY(int y)
    {
        this.offsetY = y;
    }

    @Override
    protected void interpolate(double frac)
    {
        final int index = Math.min((int) Math.floor(count * frac), count - 1); // индекс картинки для анимации
        final int x = (index % columns) * width + offsetX; // х позиция картинки
        final int y = (index / columns) * height + offsetY; // y позиция

        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
