package com.lukelavin.automata;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;

public class Main extends GameApplication
{
    private final int TILE_SIZE = 10;
    private final int[][] PERMUTATIONS = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}, {1, 0, 0}, {0, 1, 1}, {0, 1, 0}, {0, 0, 1}, {0, 0, 0}};
    private String ruleset;
    private int rulesetNum;

    private int[] current;
    private int[] next;

    private int iteration;
    private LocalTimer simTimer;
    private GameSettings gameSettings;

    @Override
    protected void initSettings(GameSettings settings)
    {
        gameSettings = settings;
        gameSettings.setWidth(1000);
        gameSettings.setHeight(1000);

        gameSettings.setTitle("Elementary Cellular Automata");

        gameSettings.setProfilingEnabled(false);
        gameSettings.setIntroEnabled(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setCloseConfirmation(false);
    }

    @Override
    protected void initGame()
    {
        ruleset = decimalToBinary(0);
        rulesetNum = 0;
        addRulesetLabel();

        initGrids();

        simTimer = FXGL.newLocalTimer();
    }

    private void initGrids()
    {
        current = new int[getWidth() / TILE_SIZE];
        current[getWidth() / TILE_SIZE / 2] = 1;
        addBlackSquare(getWidth() / 2, 0);
        next = new int[getWidth() / TILE_SIZE];
    }

    @Override
    protected void onUpdate(double tpf)
    {
        if(simTimer != null && simTimer.elapsed(Duration.millis(10)))
        {
            simTimer.capture();
            nextGen();
        }

        if(iteration > getHeight() / TILE_SIZE)
        {
            if(rulesetNum >= 255)
            {
                simTimer = null;
            }
            else
            {
                rulesetNum++;
                ruleset = decimalToBinary(rulesetNum);
                clearGameScene();
                getGameWorld().reset();
                addRulesetLabel();

                initGrids();
                iteration = 0;
            }
        }
    }

    Rectangle progressGuide;
    public void nextGen()
    {
        getGameScene().removeUINode(progressGuide);
        progressGuide = new Rectangle(0, (iteration + 2) * TILE_SIZE, getWidth(), 3);
        progressGuide.setFill(Color.RED);
        getGameScene().addUINode(progressGuide);
        for(int index = 0; index < current.length; index++)
        {
            int[] neighborhood = new int[3];
            if(index == 0)
            {
                neighborhood[0] = current[current.length - 1];
                neighborhood[1] = current[index];
                neighborhood[2] = current[index + 1];
            }
            else if (index == current.length - 1)
            {

                neighborhood[0] = current[index - 1];
                neighborhood[1] = current[index];
                neighborhood[2] = current[0];
            }
            else
            {
                neighborhood[0] = current[index - 1];
                neighborhood[1] = current[index];
                neighborhood[2] = current[index + 1];
            }
            int permutation = findMatchingPermutation(neighborhood);
            int result = Integer.parseInt("" + ruleset.charAt(permutation));
            next[index] = result;
            System.out.println(result);
            if(result == 1)
                addBlackSquare(index * TILE_SIZE, (iteration + 1) * TILE_SIZE);
        }
        current = next;
        next = new int[100];

        iteration++;
    }

    private int findMatchingPermutation(int[] arr)
    {
        if(equals(arr, PERMUTATIONS[0]))
            return 0;
        else if(equals(arr, PERMUTATIONS[1]))
            return 1;
        else if(equals(arr, PERMUTATIONS[2]))
            return 2;
        else if(equals(arr, PERMUTATIONS[3]))
            return 3;
        else if(equals(arr, PERMUTATIONS[4]))
            return 4;
        else if(equals(arr, PERMUTATIONS[5]))
            return 5;
        else if(equals(arr, PERMUTATIONS[6]))
            return 6;
        else
            return 7;
    }

    private boolean equals(int[] a, int[] b){
        if(a.length != b.length)
            return false;

        boolean equal = true;
        for(int i = 0; i < a.length; i++)
        {
            if (a[i] != b[i])
            {
                equal = false;
                break;
            }
        }

        return equal;
    }

    private String decimalToBinary(int decimal)
    {
        String output = "";
        for(int i = 7; i >= 0; i--)
        {
            if(decimal >= Math.pow(2, i))
            {
                decimal = (int) (decimal - Math.pow(2, i));
                output += "1";
            }
            else
                output += "0";
        }
        return output;
    }

    private void addBlackSquare(double x, double y)
    {
        GameEntity square = Entities.builder()
                .at(x, y)
                .viewFromNode(new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
//        Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLACK);
//        square.setTranslateX(x);
//        square.setTranslateY(y);
//        square.setTranslateZ(-100);
        getGameWorld().addEntity(square);
    }

    private void addRulesetLabel()
    {
        Label rulesetLabel = new Label("Rule " + rulesetNum);
        rulesetLabel.setFont(new Font("Arial Black", 30));
        rulesetLabel.setTranslateY(-10);
        rulesetLabel.setTextFill(Color.CRIMSON);
        rulesetLabel.toFront();
        getGameScene().addUINode(rulesetLabel);
    }

    private void clearGameScene()
    {
        List<Node> nodes = getGameScene().getUINodes();
        for(int i = nodes.size() - 1; i >= 0; i--)
            getGameScene().removeUINode(nodes.get(i));
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
