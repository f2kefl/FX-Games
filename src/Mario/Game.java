package Mario;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by F4keFLy on 17.03.2016.
 * Enjoy it!
 */
public class Game
{
    static final int marioSize = 40;
    static final int blockSize = 45;

    static ArrayList<Block> platforms = new ArrayList();
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private static Pane appRoot = new Pane();
    static Pane gameRoot = new Pane();

    private static Character player;
    private static int levelWidth;


    private static Image bg = null;
    static {
        bg = new Image(Game.class.getResourceAsStream("background.png"));
    }


    private static void initContent()
    {
        ImageView bgIV = new ImageView(bg);
        bgIV.setFitHeight(14 * blockSize);
        bgIV.setFitWidth(212 * blockSize);

        int levelNumber = 0;
        levelWidth = LevelData.levels[levelNumber][0].length() * blockSize;
        for (int i = 0; i < LevelData.levels[levelNumber].length; i++) {
            String line = LevelData.levels[levelNumber][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Block platform = new Block(Block.BlockType.PLATFORM, j * blockSize, i * blockSize);
                        break;
                    case '2':
                        Block brick = new Block(Block.BlockType.BRICK, j * blockSize, i * blockSize);
                        break;
                    case '3':
                        Block bonus = new Block(Block.BlockType.BONUS, j * blockSize, i * blockSize);
                        break;
                    case '4':
                        Block stone = new Block(Block.BlockType.STONE, j * blockSize, i * blockSize);
                        break;
                    case '5':
                        Block pipeTop = new Block(Block.BlockType.PIPE_TOP, j * blockSize, i * blockSize);
                        break;
                    case '6':
                        Block pipeBottom = new Block(Block.BlockType.PIPE_BOTTOM, j * blockSize, i * blockSize);
                        break;
                    case '*':
                        Block invisible = new Block(Block.BlockType.INVISIBLE_BLOCK, j * blockSize, i * blockSize);
                        break;
                }
            }
        }
        player = new Character();
        player.setTranslateY(400);
        player.setTranslateX(0);
        player.translateXProperty().addListener((obs, old, newV) -> {
            int offset = newV.intValue();
            if (offset > 640 && offset < (levelWidth - 640)) {
                gameRoot.setLayoutX(-(offset - 640));
                bgIV.setLayoutX(-(offset - 640));
            }
        });
        gameRoot.getChildren().add(player);
        appRoot.getChildren().addAll(bgIV, gameRoot);
    }

    private static void update()
    {
        if (isPressed(KeyCode.W) && player.getTranslateY() >= 5) {
            player.jump();
        }

        if (isPressed(KeyCode.A) && player.getTranslateX() >= 5) {
            player.setScaleX(-1);
            player.animation.play();
            player.moveX(-5);
        }

        if (isPressed(KeyCode.D) && player.getTranslateX() + 40 <= levelWidth - 5) {
            player.setScaleX(1);
            player.animation.play();
            player.moveX(5);
        }
        if (player.playerVelocity.getY() < 10) {
            player.playerVelocity = player.playerVelocity.add(0, 1);
        }
        player.moveY((int) player.playerVelocity.getY());
    }

    private static boolean isPressed(KeyCode key)
    {
        return keys.getOrDefault(key, false);
    }

    public static void start(String name)
    {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        initContent();
        Scene scene = new Scene(appRoot, 1200, 630);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            player.animation.stop();
        });
        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update();
            }
        };
        timer.start();
        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
}
