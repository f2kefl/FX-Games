package Saper;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by FL on 19.04.2016 at 15:45.
 * description:
 * TODO:
 */

public class Main
{
    static boolean gameContinues = true;
    static final int BOARD_SIZE_X = 16;
    static final int BOARD_SIZE_Y = 15;
    static int flagsCount = 0;
    static int mines = 0;
    private static Pane root = new Pane();
    private static GridPane gridPane = new GridPane();
    static Cell[][] cells = new Cell[BOARD_SIZE_X][BOARD_SIZE_Y];
    static Label score;
    private static int difficulty = 5;
    private static Button newGame = new Button("New Game");

    private static void createBoard()
    {
        flagsCount = 0;
        for (int i = 0; i < BOARD_SIZE_X; i++) {
            for (int j = 0; j < BOARD_SIZE_Y; j++) {
                int mine = (int) Math.round(Math.random() * difficulty);
                cells[i][j] = new Cell(i, j, mine == difficulty);
                if (cells[i][j].isMine) flagsCount++;
                GridPane.setConstraints(cells[i][j], i, j + 1);
                gridPane.getChildren().add(cells[i][j]);
                score.setText("Осталось флажков: " + flagsCount);
                mines = flagsCount;
            }
        }
        for (int i = 0; i < BOARD_SIZE_X; i++) {
            for (int j = 0; j < BOARD_SIZE_Y; j++) {
                Cell cell = cells[i][j];
                if (!cell.isMine) {
                    for (int k = 0; k < cell.getAround().size(); k++) {
                        if (cell.getAround().get(k).isMine) cell.mineCount++;
                    }
                }
                if (cell.mineCount == 0) cell.isEmpty = true;
            }
        }
    }

    static void win()
    {
        if (mines == 0) {
            gameContinues = false;
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Победа!");
            alert.setContentText("Поздравляю, вы разминировали поле!");
            alert.show();
        }
    }

    static void lose()
    {
        gameContinues = false;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Поражение!");
        alert.setContentText("Вас разорвало на куски :(");
        alert.show();
    }

    public static void start(String name) throws Exception
    {
        Scene scene = new Scene(root, 544, 535);
        Stage primaryStage = new Stage();
        newGame.setOnMouseClicked(event -> {
            createBoard();
            gameContinues = true;
        });
        score = new Label("Осталось флажков: " + flagsCount);
        score.setFont(new Font("Calibri", 19));
        ChoiceBox<String> setDifficulty = new ChoiceBox<>();
        setDifficulty.setItems(FXCollections.observableArrayList("Newbie", "Normal", "Expert"));
        setDifficulty.setValue("Newbie");
        setDifficulty.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            difficulty = 5 - newValue.intValue();
        });

        createBoard();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setLayoutX(2);
        gridPane.setVgap(4);
        gridPane.setHgap(4);
        GridPane.setConstraints(setDifficulty, 12, 0, 4, 1);
        GridPane.setConstraints(score, 1, 0, 6, 1);
        GridPane.setConstraints(newGame, 7, 0, 3, 1);

        gridPane.getChildren().addAll(score, newGame, setDifficulty);
        root.getChildren().addAll(gridPane);

        primaryStage.setTitle(name);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
}
