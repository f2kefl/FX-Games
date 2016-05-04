package Pickers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static javafx.application.Application.launch;

/**
 * Created by F4keFLy on 01.03.2016.
 * fL
 */

public class Main
{
    private static ArrayList<Rectangle> bonuses = new ArrayList<>();
    private static Pane root = new Pane();
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private static final Character player = new Character(false);
    private static final Character npc = new Character(true);
    private static Label playerScore = new Label("Ваши очки: ");
    private static Label npcScore = new Label("Очки противника: ");

    private static AnimationTimer timer;

    private static void update()
    {
        if (isPressed(KeyCode.W))
        {
            player.animation.play();
            player.animation.setOffsetY(96);
            if ((int) player.getTranslateY() > 21)
            {
                moveY((int) (-1 - Math.floor(player.score / 5)), player);
            }
            npc.animation.play();
            npc.animation.setOffsetY(0);
            if ((int) npc.getTranslateY() < 369)
            {
                moveY((int) (2 + Math.floor(npc.score / 5)), npc);
            }
        } else if (isPressed(KeyCode.S))
        {
            player.animation.play();
            player.animation.setOffsetY(0);
            if ((int) player.getTranslateY() < 370)
            {
                moveY((int) (1 + Math.floor(player.score / 5)), player);
            }
            npc.animation.play();
            npc.animation.setOffsetY(96);
            if ((int) npc.getTranslateY() > 21)
            {
                moveY((int) (-2 - Math.floor(npc.score / 5)), npc);
            }
        } else if (isPressed(KeyCode.D))
        {
            player.animation.play();
            player.animation.setOffsetY(64);
            if ((int) player.getTranslateX() < 371)
            {
                moveX((int) (1 + Math.floor(player.score / 5)), player);
            }
            npc.animation.play();
            npc.animation.setOffsetY(32);
            if ((int) npc.getTranslateX() > -3)
            {
                moveX((int) (-2 - Math.floor(npc.score / 5)), npc);
            }
        } else if (isPressed(KeyCode.A))
        {
            player.animation.play();
            player.animation.setOffsetY(32);
            if ((int) player.getTranslateX() > -1)
            {
                moveX((int) (-1 - Math.floor(player.score / 5)), player);
            }
            npc.animation.play();
            npc.animation.setOffsetY(64);
            if ((int) npc.getTranslateX() < 365)
            {
                moveX((int) (2 + Math.floor(npc.score / 5)), npc);
            }
        } else
        {
            player.animation.stop();
            npc.animation.stop();
        }

        if (npc.score>2) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Поражение!");
            alert.setHeaderText("Вы проиграли.");
            alert.setContentText("Мда. Как тут можно проиграть?");
            alert.show();
        }

        if (bonuses.size()<1){
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Победа!");
            alert.setHeaderText("Вы победили!");
            alert.setContentText("Впрочем, не удивительно.");
            alert.show();
        }

        playerScore.setText("Ваши очки: " + player.score);
        npcScore.setText("Очки противника: " + npc.score);
    }

    private static void moveX(int x, Character character)
    {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++)
        {
            if (right) character.setTranslateX(character.getTranslateX() + 1);
            else character.setTranslateX(character.getTranslateX() - 1);
            isBonusEat(character);
        }
    }

    private static void moveY(int y, Character character)
    {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++)
        {
            if (down) character.setTranslateY(character.getTranslateY() + 1);
            else character.setTranslateY(character.getTranslateY() - 1);
            isBonusEat(character);
        }
    }

    private static void isBonusEat(Character character)
    {
        bonuses.forEach((rectangle -> {
            if (character.getBoundsInParent().intersects(rectangle.getBoundsInParent()))
            {
                bonuses.remove(rectangle);
                root.getChildren().remove(rectangle);
                character.score++;
            }
        }));
    }

    private static void newGame()
    {
        for (int i = 0; i < 20; i++)
        {
            Rectangle rect = new Rectangle(20, 20, Color.RED);
            rect.setX(20 + (int) Math.floor(Math.random() * 310));
            rect.setY(20 + (int) Math.floor(Math.random() * 310));
            bonuses.add(rect);
            root.getChildren().addAll(rect);
        }

        player.setTranslateX(1);
        player.setTranslateY(20);
        npc.setTranslateX(368);
        npc.setTranslateY(368);
    }

    private static boolean isPressed(KeyCode key)
    {
        return keys.getOrDefault(key, false);
    }

    public static void start(String name) throws Exception
    {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root, 400, 400);
        newGame();
        Rectangle rectangle = new Rectangle(400,20, Color.LIGHTGRAY);

        playerScore.setTranslateX(30);
        playerScore.setTranslateY(2);

        npcScore.setTranslateX(250);
        npcScore.setTranslateY(2);

        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        Collections.sort(bonuses, (o1, o2) -> (int) ((o2.getY() + o2.getX()) - (o1.getX() + o1.getY())));

        timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update();
            }
        };
        timer.start();

        root.getChildren().addAll(player, npc, rectangle, playerScore, npcScore);

        Platform.setImplicitExit(false);

        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.setTitle("NEW GAME");
        primaryStage.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
