package com.lukelavin.automata;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;

public class Main extends GameApplication
{
    private final int TILE_SIZE = 5;
    private final int max = 200;
    private final int[][] PERMUTATIONS =
            {{1, 1, 1, 1}, {1, 1, 1, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 0, 0, 1}, {1, 0, 0, 0},
            //    15            14            13            12            11            10             9             8
             {0, 1, 1, 1}, {0, 1, 1, 0}, {0, 1, 0, 1}, {0, 1, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 0}};
    //             7             6            5              4             3             2             1             0
    private String ruleset;
    private int rulesetNum;

    private int[] old;
    private int[] current;
    private int[] next;

    private int iteration;
    private LocalTimer simTimer;
    private GameSettings gameSettings;

    private boolean random;
    private int target;

    private Label randomLabel;
    private Label targetLabel;

    @Override
    protected void initSettings(GameSettings settings)
    {
        gameSettings = settings;
        gameSettings.setWidth(1000);
        gameSettings.setHeight(1015);

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

        random = false;
    }

    private void initGrids()
    {
        old = new int[max];
        current = new int[max];
        current[max / 2] = 1;
        addBlackSquare(getWidth() / 2, 0);
        next = new int[max];
    }

    @Override
    protected void initInput()
    {
        getInput().addAction(new UserAction("RANDOM")
        {
            @Override
            protected void onActionBegin()
            {
                random = !random;
                if (random)
                {
                    addRandomLabel();
                }
                else
                {
                    getGameScene().removeUINode(randomLabel);
                }
            }
        }, KeyCode.R);

        getInput().addAction(new UserAction("1")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(1);
            }
        }, KeyCode.DIGIT1);
        getInput().addAction(new UserAction("num1")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(1);
            }
        }, KeyCode.NUMPAD1);
        getInput().addAction(new UserAction("2")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(2);
            }
        }, KeyCode.DIGIT2);
        getInput().addAction(new UserAction("num2")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(2);
            }
        }, KeyCode.NUMPAD2);
        getInput().addAction(new UserAction("3")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(3);
            }
        }, KeyCode.DIGIT3);
        getInput().addAction(new UserAction("num3")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(3);
            }
        }, KeyCode.NUMPAD3);
        getInput().addAction(new UserAction("4")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(4);
            }
        }, KeyCode.DIGIT4);
        getInput().addAction(new UserAction("num4")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(4);
            }
        }, KeyCode.NUMPAD4);
        getInput().addAction(new UserAction("5")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(5);
            }
        }, KeyCode.DIGIT5);
        getInput().addAction(new UserAction("num5")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(5);
            }
        }, KeyCode.NUMPAD5);
        getInput().addAction(new UserAction("6")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(6);
            }
        }, KeyCode.DIGIT6);
        getInput().addAction(new UserAction("num6")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(6);
            }
        }, KeyCode.NUMPAD6);
        getInput().addAction(new UserAction("7")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(7);
            }
        }, KeyCode.DIGIT7);
        getInput().addAction(new UserAction("num7")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(7);
            }
        }, KeyCode.NUMPAD7);
        getInput().addAction(new UserAction("8")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(8);
            }
        }, KeyCode.DIGIT8);
        getInput().addAction(new UserAction("num8")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(8);
            }
        }, KeyCode.NUMPAD8);
        getInput().addAction(new UserAction("9")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(9);
            }
        }, KeyCode.DIGIT9);
        getInput().addAction(new UserAction("num9")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(9);
            }
        }, KeyCode.NUMPAD9);
        getInput().addAction(new UserAction("0")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(0);
            }
        }, KeyCode.DIGIT0);
        getInput().addAction(new UserAction("num0")
        {
            @Override
            protected void onActionBegin()
            {
                addToTarget(0);
            }
        }, KeyCode.NUMPAD0);
        getInput().addAction(new UserAction("ENTER")
        {
            @Override
            protected void onActionBegin()
            {
                    rulesetNum = target;
                    ruleset = decimalToBinary(rulesetNum);
                    clearGameScene();
                    getGameWorld().reset();
                    addRulesetLabel();

                    initGrids();
                    iteration = 0;

                    target = 0;
                    targetLabel = null;
            }
        }, KeyCode.ENTER);
        getInput().addAction(new UserAction("BACKSPACE")
        {
            @Override
            protected void onActionBegin()
            {
                target = target / 10;

                if(targetLabel != null)
                    getGameScene().removeUINode(targetLabel);
                targetLabel = new Label("GOTO: " + target);
                targetLabel.setFont(new Font("Lucida Console", 12));
                targetLabel.setTextFill(Color.BLACK);
                targetLabel.setTranslateX(800);
                targetLabel.setTranslateY(1000);
                getGameScene().addUINode(targetLabel);
            }
        }, KeyCode.BACK_SPACE);
    }

    private void addToTarget(int num)
    {
        target = target * 10 + num;

        if(targetLabel != null)
            getGameScene().removeUINode(targetLabel);
        targetLabel = new Label("GOTO: " + target);
        targetLabel.setFont(new Font("Lucida Console", 12));
        targetLabel.setTextFill(Color.BLACK);
        targetLabel.setTranslateX(800);
        targetLabel.setTranslateY(1000);
        getGameScene().addUINode(targetLabel);
    }

    private void addRandomLabel()
    {
        randomLabel = new Label("Random Rule: ON");
        randomLabel.setFont(new Font("Lucida Console", 12));
        randomLabel.setTextFill(Color.BLACK);
        randomLabel.setTranslateX(220);
        randomLabel.setTranslateY(1000);
        getGameScene().addUINode(randomLabel);
    }

    @Override
    protected void onUpdate(double tpf)
    {
        if(simTimer != null && simTimer.elapsed(Duration.millis(1)))
        {
            simTimer.capture();
            nextGen();
        }

        if(iteration > max - 1)
        {
            if(random)
            {
                rulesetNum = (int) (Math.random() * Math.pow(2, 16));
                ruleset = decimalToBinary(rulesetNum);
                clearGameScene();
                getGameWorld().reset();
                addRulesetLabel();
                addRandomLabel();

                initGrids();
                iteration = 0;
            }
            else
            {
                if (rulesetNum >= Math.pow(2, 16))
                {
                    simTimer = null;
                } else
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
            if(targetLabel != null)
                getGameScene().addUINode(targetLabel);
        }
    }

    Rectangle progressGuide;
    public void nextGen()
    {
        if(iteration < max - 2)
        {
            getGameScene().removeUINode(progressGuide);
            progressGuide = new Rectangle(0, (iteration + 2) * TILE_SIZE, getWidth(), 3);
            progressGuide.setFill(Color.GRAY);
            getGameScene().addUINode(progressGuide);
        }
        for(int index = 0; index < current.length; index++)
        {
            int[] neighborhood = new int[4];
            if(index == 0)
            {
                neighborhood[0] = old[index];
                neighborhood[1] = current[current.length - 1];
                neighborhood[2] = current[index];
                neighborhood[3] = current[index + 1];
            }
            else if (index == current.length - 1)
            {
                neighborhood[0] = old[index];
                neighborhood[1] = current[index - 1];
                neighborhood[2] = current[index];
                neighborhood[3] = current[0];
            }
            else
            {
                neighborhood[0] = old[index];
                neighborhood[1] = current[index - 1];
                neighborhood[2] = current[index];
                neighborhood[3] = current[index + 1];
            }

            if(iteration == 0)
                neighborhood[0] = 0;
            int permutation = findMatchingPermutation(neighborhood);
            int result = Integer.parseInt("" + ruleset.charAt(permutation));
            next[index] = result;
            System.out.println(result);
            if(result == 1)
                addBlackSquare(index * TILE_SIZE, (iteration + 1) * TILE_SIZE);
        }
        old = Arrays.copyOf(current, current.length);
        current = next;
        next = new int[max];

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
        else if(equals(arr, PERMUTATIONS[7]))
            return 7;
        else if(equals(arr, PERMUTATIONS[8]))
            return 8;
        else if(equals(arr, PERMUTATIONS[9]))
            return 9;
        else if(equals(arr, PERMUTATIONS[10]))
            return 10;
        else if(equals(arr, PERMUTATIONS[11]))
            return 11;
        else if(equals(arr, PERMUTATIONS[12]))
            return 12;
        else if(equals(arr, PERMUTATIONS[13]))
            return 13;
        else if(equals(arr, PERMUTATIONS[14]))
            return 14;
        else
            return 15;
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
        for(int i = 15; i >= 0; i--)
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
        getGameWorld().addEntity(square);
    }

    private void addRulesetLabel()
    {
        Label rulesetLabel = new Label("Rule " + rulesetNum + " | " + ruleset);
        rulesetLabel.setFont(new Font("Lucida Console", 12));
        rulesetLabel.setTranslateY(1000);
        rulesetLabel.setTextFill(Color.BLACK);
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
