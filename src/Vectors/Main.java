package Vectors;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by FL on 18.04.2016 at 17:22.
 * description:
 * TODO:
 */
public class Main
{
    static ArrayList<Bullet> bullets = new ArrayList<>();
    static ArrayList<Circle> circles = new ArrayList<>();
    static Pane root = new Pane();

    public static void start(String name) throws Exception {

        Scene scene = new Scene(root, 600, 600);
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        for (int i = 0; i < 4; i++) {
            bullets.add(new Bullet());
        }

        for (int i = 0; i < 10; i++) {
            circles.add(addCircle(Color.RED));
            circles.add(addCircle(Color.GREEN));
            circles.add(addCircle(Color.BLUE));
            circles.add(addCircle(Color.ORANGE));
        }
        circles.forEach(circle -> {
            circle.setTranslateY((int) Math.abs(Math.random()*600));
            circle.setTranslateX((int) Math.abs(Math.random()*600));
        });

        bullets.get(0).rect.setFill(Color.RED);
        bullets.get(1).rect.setFill(Color.BLUE);
        bullets.get(2).rect.setFill(Color.GREEN);
        bullets.get(3).rect.setFill(Color.ORANGE);

        bullets.get(1).setTranslateX(580);
        bullets.get(1).setTranslateY(0);
        bullets.get(2).setTranslateX(0);
        bullets.get(2).setTranslateY(598);
        bullets.get(3).setTranslateX(580);
        bullets.get(3).setTranslateY(598);

        bullets.forEach(bullet -> bullet.multi = bullet.multi + 2);


        root.getChildren().addAll(bullets);
        root.getChildren().addAll(circles);

        scene.setOnMouseMoved(event -> bullets.forEach(bullet -> bullet.setTarget(event.getSceneX(), event.getSceneY())));
        scene.setOnMouseClicked(event -> bullets.forEach(bullet -> bullet.velocity = new Point2D(0, 0)));

        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                bullets.forEach(Bullet::move);
            }
        };
        timer.start();

        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private static Circle addCircle(Color color){
        return new Circle(10, color);
    }

}
