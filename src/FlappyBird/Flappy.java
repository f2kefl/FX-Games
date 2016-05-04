package FlappyBird;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by F4keFLy on 04.04.2016.
 * Enjoy it!
 */
public class Flappy
{
    private static Pane gameRoot = new Pane();
    static ArrayList<Wall> walls = new ArrayList<>();
    private static Pig pig = new Pig();
    static int score = 0;
    private static Label scoreLabel = new Label("Score is: " + score);
    private static Button newGame = new Button("New Game");

    private static Parent createContent()
    {
        Pane appRoot = new Pane();
        HBox hBox = new HBox(30);
        hBox.getChildren().addAll(scoreLabel, newGame);

        newGame.setOnMouseClicked(event -> {
            gameRoot.setLayoutX(0);
            pig.setTranslateY(300);
            pig.setTranslateX(0);
            score = 0;
        });

        gameRoot.setPrefSize(600, 630);

        for (int i = 3; i < 100; i++) {
            int enter = (int) (Math.random() * 100) + 86;
            int height = new Random().nextInt(600 - enter);
            int HDistance = (int) (Math.round(Math.random()*250)+600);
            Wall wall = new Wall(height);
            wall.setTranslateX(i * 350 - HDistance);
            wall.setTranslateY(0);
            walls.add(wall);

            Wall wall2 = new Wall(600 - enter - height);
            wall2.setTranslateX(i * 350 - HDistance);
            wall2.setTranslateY(height + enter);
            walls.add(wall2);
            gameRoot.getChildren().addAll(wall, wall2);
        }

        gameRoot.setTranslateY(30);
        gameRoot.getChildren().add(pig);
        appRoot.getChildren().addAll(hBox, gameRoot);
        return appRoot;
    }

    private static void update()
    {
        if (Pig.velocity.getY()<5){
            Pig.velocity = Pig.velocity.add(0,1);
        }
        pig.moveX((int) Pig.velocity.getX());
        pig.moveY((int) Pig.velocity.getY());

        scoreLabel.setText("Score is: " + score);
        pig.translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            if(offset>200) gameRoot.setLayoutX(-(offset-200));
        });
    }

    public static void start(String name) throws Exception
    {
        Stage primaryStage = new Stage();
        primaryStage.setTitle(name);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(createContent());
        scene.setOnMouseClicked(event -> pig.jump());

        primaryStage.setScene(scene);

        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update();
            }
        };
        timer.start();
        primaryStage.showAndWait();
    }

    static class Wall extends Pane
    {
        Wall(int height)
        {
            Rectangle rect = new Rectangle(20, height);
            getChildren().add(rect);
        }
    }
}
