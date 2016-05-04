package Vectors;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * Created by FL on 18.04.2016 at 17:22.
 * description:
 * TODO:
 */
class Bullet extends Pane
{
    Point2D velocity = new Point2D(0, 0);
    Rectangle rect = new Rectangle(20, 2);
    double multi = 3;

    Bullet() {
        getChildren().add(rect);
    }

    void setTarget(double x, double y) {
        velocity = new Point2D(x, y).subtract(getTranslateX(), getTranslateY()).normalize().multiply(multi);
        double angle = calcAngle(velocity.getX(), velocity.getY());
        getTransforms().clear();
        getTransforms().add(new Rotate(angle, 0, 0));
    }

    void move() {
        setTranslateX(getTranslateX() + velocity.getX());
        setTranslateY(getTranslateY() + velocity.getY());
        intersects();
    }

    private double calcAngle(double vecX, double vecY) {
        double angle = new Point2D(vecX, vecY).angle(1, 0);
        return vecY > 0 ? angle : -angle;
    }

    private void intersects(){
        Main.bullets.forEach(bullet -> Main.circles.forEach(circle -> {
            if (bullet.getBoundsInParent().intersects(circle.getBoundsInParent())
                    && bullet.rect.getFill() == circle.getFill()
                    && !(bullet.getBoundsInParent().intersects(
                    Main.bullets.get(Main.bullets.indexOf(bullet) < 3 ?
                            Main.bullets.indexOf(bullet) +1 : Main.bullets.indexOf(bullet)-1).getBoundsInParent()))){
                Main.root.getChildren().remove(circle);
                Main.circles.remove(circle);
            }
        }));
    }
}
