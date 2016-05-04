package Mario;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by F4keFLy on 16.03.2016.
 * Enjoy it!
 */
public class Character extends Pane
{
    public SpriteAnimation animation;
    Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;

    Character()
    {
        int count = 3;
        int columns = 3;
        int offsetY = 33;
        int offsetX = 96;
        int width = 16;
        int height = 16;

        Image mario = new Image(getClass().getResourceAsStream("mario.png"));
        ImageView imageView = new ImageView(mario);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        animation = new SpriteAnimation(imageView, Duration.millis(200), count, columns, offsetX, offsetY, width, height);
        getChildren().addAll(imageView);
    }

    void moveX(int value)
    {
        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
            for (Node platform : Game.platforms) {
                if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + Game.marioSize == platform.getTranslateX()){
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == platform.getTranslateX() + Game.blockSize) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    void moveY(int value)
    {
        boolean movingDown = value >0;
        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :Game.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if(this.getTranslateY()+ Game.marioSize == platform.getTranslateY()){
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == platform.getTranslateY()+ Game.blockSize){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            if(this.getTranslateY()>640){
                this.setTranslateX(0);
                this.setTranslateY(400);
                Game.gameRoot.setLayoutX(0);
            }
        }
    }

    void jump()
    {
        if (canJump){
            playerVelocity = playerVelocity.add(0,-30);
            canJump = false;
        }
    }
}
