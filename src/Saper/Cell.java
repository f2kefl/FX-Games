package Saper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static Saper.Main.*;


/**
 * Created by FL on 19.04.2016 at 15:45.
 * description:
 * TODO:
 */
class Cell extends StackPane
{
    boolean isMine = false;
    boolean isEmpty = false;
    private boolean isFlag = false;
    private boolean isClicked = false;
    private int x;
    private int y;
    int mineCount = 0;
    private Rectangle rect;
    private Text text = new Text();
    private Image mine = new Image(getClass().getResourceAsStream("mine.png"));
    private Image flag = new Image(getClass().getResourceAsStream("flag.png"));
    private ImageView flagIV = new ImageView(flag);

    Cell(int x, int y, boolean isMine)
    {
        this.isMine = isMine;
        this.x = x;
        this.y = y;

        rect = new Rectangle(30, 30);
        rect.setFill(Color.LIGHTGRAY);
        setOnMouseClicked(this::handler);

        getChildren().addAll(rect, text);
    }

    private void handler(MouseEvent event)
    {
        if (gameContinues) {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!isEmpty && !isMine) {
                    isClicked = true;
                    text.setText("" + mineCount);
                    rect.setFill(Color.WHITESMOKE);
                }
                else if (isMine) {
                    for (int k = 0; k < BOARD_SIZE_X; k++) {
                        for (int l = 0; l < BOARD_SIZE_Y; l++) {
                            if (cells[k][l].isMine) cells[k][l].getChildren().add(new ImageView(mine));
                        }
                    }
                    lose();
                }
                else {
                    isClicked = true;
                    rect.setFill(Color.WHITESMOKE);
                    getAround().forEach(cell -> {
                        if (!cell.isClicked) {
                            cell.handler(event);
                        }
                    });
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                if (!isFlag) {
                    getChildren().add(flagIV);
                    isFlag = true;
                    flagsCount--;
                    score.setText("Осталось флажков: " + flagsCount);
                    if (isMine) mines--;
                    win();
                }
                else {
                    getChildren().remove(flagIV);
                    isFlag = false;
                    flagsCount++;
                    score.setText("Осталось флажков: " + flagsCount);
                    if (isMine) mines++;
                }
            }
        }
    }

    List<Cell> getAround()
    {
        List<Cell> around = new ArrayList<>();

        int[] points = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

        for (int i = 0; i < points.length; i++) {
            int newX = x + points[i];
            int newY = y + points[++i];
            if (newX >= 0 && newX < BOARD_SIZE_X && newY >= 0 && newY < BOARD_SIZE_Y) {
                around.add(cells[newX][newY]);
            }
        }
        return around;
    }
}
