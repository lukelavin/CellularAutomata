import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Main extends GameApplication
{
    private final int TILE_SIZE = 4;

    private int[][] map;
    private Random random;

    private int fillPercent;

    private LocalTimer smoothTimer;
    private int smoothCount;

    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setWidth(800);
        gameSettings.setHeight(400);

        gameSettings.setTitle("Map Generation w/ Cellular Automata");
        gameSettings.setVersion("0.1");
        gameSettings.setCloseConfirmation(false);
        gameSettings.setProfilingEnabled(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setIntroEnabled(false);
    }

    @Override
    protected void initGame()
    {
//        long seed = System.nanoTime();
//        random = new Random(seed);
//        System.out.println(seed);

        random = new Random();

        fillPercent = 46;
        fillMap();
        displayMap();

        smoothTimer = FXGL.newLocalTimer();
        smoothTimer.capture();
        smoothCount = 0;
    }

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("generateMap")
        {
            @Override
            protected void onActionBegin()
            {
                initGame();
            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void onUpdate(double tpf)
    {
        if(smoothTimer.elapsed(Duration.millis(500)) && smoothCount < 5)
        {
            smoothMap();
            displayMap();
            smoothTimer.capture();
            smoothCount++;
        }
    }

    private void fillMap()
    {
        map = new int[getHeight() / TILE_SIZE][getWidth() / TILE_SIZE];
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                map[r][c] = (random.nextInt(100) < fillPercent) ? 1 : 0;
                if(r == 0 || r == map.length - 1 || c == 0 || c == map[r].length - 1)
                {
                    map[r][c] = 1;
                }
            }
        }
    }

    private void smoothMap()
    {
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                int wallCount = adjacentWallCount(r, c);
                if(wallCount > 4)
                    map[r][c] = 1;
                if(wallCount < 4)
                    map[r][c] = 0;
            }
        }
    }

    private int adjacentWallCount(int gridR, int gridC)
    {
        int wallCount = 0;
        for (int r = gridR - 1; r <= gridR + 1; r ++) 
        {
            for (int c = gridC - 1; c <= gridC + 1; c ++)
            {
                if (r >= 0 && r < map.length && c >= 0 && c < map[r].length)
                {
                    if (r != gridR || c != gridC) 
                    {
                        wallCount += map[r][c];
                    }
                }
                else 
                {
                    wallCount ++;
                }
            }
        }

        return wallCount;
    }

    private void displayMap()
    {
        getGameWorld().reset();
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[r].length; c++)
            {
                if(map[r][c] == 1)
                {
                    GameEntity tile = Entities.builder()
                            .at(c * TILE_SIZE, r * TILE_SIZE)
                            .viewFromNode(new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLACK))
                            .build();
                    getGameWorld().addEntity(tile);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
