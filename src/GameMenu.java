import FlappyBird.Flappy;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by F4keFLy on 04.02.2016.
 * fL
 */

public class GameMenu extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane root = new Pane();
        Image bg = new Image(getClass().getResourceAsStream("11.jpg"));
        ImageView ivBG = new ImageView(bg);

        MenuItem newGame = new MenuItem("Новая игра");
        MenuItem options = new MenuItem("Настройки");
        MenuItem exit = new MenuItem("Выход");
        SubMenu mainMenu = new SubMenu(newGame, options, exit);

        MenuItem video = new MenuItem("Видео");
        MenuItem sound = new MenuItem("Звук");
        MenuItem controls = new MenuItem("Управление");
        MenuItem backFromOptions = new MenuItem("Назад");
        SubMenu optionsMenu = new SubMenu(video, sound, controls, backFromOptions);

        MenuItem minesweeper = new MenuItem("Сапёр");
        MenuItem pickers = new MenuItem("Собиратели (шлак)");
        MenuItem vectors = new MenuItem("Пули (демо версия)");
        MenuItem mario = new MenuItem("Марио (демо вверсия)");
        MenuItem flappyBird = new MenuItem("FlappyBird");
        MenuItem backFromNG = new MenuItem("Назад");
        SubMenu NGMenu = new SubMenu(minesweeper, flappyBird, mario, pickers, vectors, backFromNG);

        MenuBox menuBox = new MenuBox(mainMenu);

        newGame.setOnMouseClicked(event -> menuBox.setSubMenu(NGMenu));
        options.setOnMouseClicked(event -> menuBox.setSubMenu(optionsMenu));
        backFromNG.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));
        backFromOptions.setOnMouseClicked(event -> menuBox.setSubMenu(mainMenu));

        minesweeper.setOnMouseClicked(event1 -> {
            try {
                Saper.Main.start("Minesweeper");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vectors.setOnMouseClicked(event1 -> {
            try {
                Vectors.Main.start("Bullets");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        flappyBird.setOnMouseClicked(event1 -> {
            try {
                Flappy.start("Flappy Bird");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pickers.setOnMouseClicked(event1 -> {
            try {
                Pickers.Main.start("Pickers");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mario.setOnMouseClicked(event1 -> {
            try {
                Mario.Game.start("Mario");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exit.setOnMouseClicked(event -> System.exit(0));

        root.getChildren().addAll(ivBG, menuBox);
        Scene scene = new Scene(root, 750, 421);
        primaryStage.setTitle("Witcher III: Wild Hunt");
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class MenuItem extends StackPane
    {
        MenuItem(String name)
        {
            setAlignment(Pos.CENTER);
            Rectangle bg = new Rectangle(150, 30, Color.GREY);
            bg.setOpacity(0.5);
            Label text = new Label(name);
            text.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
            text.setStyle("-fx-text-fill: #535054");

            FillTransition ft = new FillTransition(Duration.seconds(0.5), bg);

            setOnMouseEntered(event -> {
                ft.setToValue(Color.color(1, 1, 1, 1));
                ft.setFromValue(Color.DARKGRAY);
                ft.setCycleCount(Animation.INDEFINITE);
                ft.setAutoReverse(true);
                ft.play();
            });
            setOnMouseExited(event -> {
                ft.stop();
                bg.setFill(Color.GREY);
            });

            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends Pane
    {
        static SubMenu subMenu;

        MenuBox(SubMenu subMenu)
        {
            MenuBox.subMenu = subMenu;
            setVisible(true);
            getChildren().addAll(subMenu);
        }

        void setSubMenu(SubMenu subMenu)
        {
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().addAll(subMenu);
        }
    }

    private static class SubMenu extends VBox
    {
        SubMenu(MenuItem... items)
        {
            setSpacing(15);
            setTranslateX(535);
            setTranslateY(135);
            for (MenuItem item : items) {
                getChildren().addAll(item);
            }
        }
    }
}
