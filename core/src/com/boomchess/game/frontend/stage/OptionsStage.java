package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.*;

public class OptionsStage extends Stage{

    public static Stage initalizeUI() {
        Stage optionsStage = new Stage();

        // Begin of Options Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);

        // button to change colour of green figurines to blue in modern mode

        // TODO

        // text that displays a text saying "Number of Obstacles"
        final TextButton numberObstaclesText = new TextButton("Number of Obstacles 0-10", skin);
        root.add(numberObstaclesText).padBottom(10);
        root.row();

        // slider for setting the number of obstacles in the initial no mans land
        final Slider obstacleSlider;
        obstacleSlider = new Slider(0, 10, 1f, false, skin);
        obstacleSlider.setValue(numberObstacle);
        root.add(obstacleSlider).padBottom(50);
        obstacleSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.numberObstacle = obstacleSlider.getValue();
            }
        });
        root.row();

        // Change Mode button to switch blue and green
        String currentMode = "";
        if(isColourChanged){
            currentMode = "Blue";
        }
        else{
            currentMode = "Green";
        }
        TextButton modeButton = new TextButton("Switch 1.Player Colour: " + currentMode, skin);
        root.add(modeButton).padBottom(50);
        modeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(isColourChanged){
                    isColourChanged = false;
                }
                else{
                    isColourChanged = true;
                }
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        // back button to return to the main menu
        TextButton backButton = new TextButton("Back", skin);
        root.add(backButton).padBottom(20);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                BoomChess.createMainMenuStage();
            }
        });

        optionsStage.addActor(root);
        return optionsStage;
    }
}
