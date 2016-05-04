package Mario;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by F4keFLy on 16.03.2016.
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

    SpriteAnimation(ImageView imageView, Duration duration, int count, int columns,
                    int width, int height, int offsetX, int offsetY)
    {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);

        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    @Override
    protected void interpolate(double frac)
    {
        final int index = Math.min((int) Math.floor(count * frac), count - 1);
        final int x = (index % columns) * width + offsetX;
        final int y = (index / columns) * height + offsetY;

        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
}
