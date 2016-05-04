package Mario;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by F4keFLy on 18.03.2016.
 * Enjoy it!
 */
class Block extends Pane
{
    enum BlockType
    {
        PLATFORM, BRICK, BONUS, PIPE_TOP, PIPE_BOTTOM, INVISIBLE_BLOCK, STONE
    }

    Block(BlockType blocktype, int x, int y)
    {
        Image blocksImg = new Image(getClass().getResourceAsStream("1.png"));
        ImageView block = new ImageView(blocksImg);
        block.setFitHeight(Game.blockSize);
        block.setFitWidth(Game.blockSize);
        setTranslateX(x);
        setTranslateY(y);

        switch (blocktype)
        {
            case PLATFORM:
                block.setViewport(new Rectangle2D(0, 0, 16, 16));
                break;
            case BRICK:
                block.setViewport(new Rectangle2D(16, 0, 16, 16));
                break;
            case BONUS:
                block.setViewport(new Rectangle2D(384, 0, 16, 16));
                break;
            case PIPE_TOP:
                block.setViewport(new Rectangle2D(0, 128, 32, 16));
                block.setFitWidth(Game.blockSize * 2);
                break;
            case PIPE_BOTTOM:
                block.setViewport(new Rectangle2D(0, 145, 32, 14));
                block.setFitWidth(Game.blockSize * 2);
                break;
            case INVISIBLE_BLOCK:
                block.setViewport(new Rectangle2D(0, 0, 16, 16));
                block.setOpacity(0);
                break;
            case STONE:
                block.setViewport(new Rectangle2D(0, 16, 16, 16));
                break;
        }
        getChildren().add(block);
        Game.platforms.add(this);
        Game.gameRoot.getChildren().add(this);
    }
}
