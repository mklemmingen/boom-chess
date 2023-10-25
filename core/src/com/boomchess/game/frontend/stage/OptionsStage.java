package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

        // Change Mode button to switch medieval and modern
        String currentMode = "";
        if(isMedievalMode){
            currentMode = "Medieval";
        }
        else{
            currentMode = "Modern";
        }
        TextButton modeButton = new TextButton("Switch between Modern and Medieval: Current is " + currentMode, skin);
        root.add(modeButton).padBottom(20);
        modeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(isMedievalMode){
                    isMedievalMode = false;
                }
                else{
                    isMedievalMode = true;
                }
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        // TODO CHANGE FROM GREEN AND RED TO BLUE AND RED BY CHANGING THE TEXTURE VARIABLES FROM GREEN PNGS TO BLUE
        //  yet to be decided variables in the backend like NIKI Difficulty etc
        // change from current asset manager to medieval asset manager
        // - old sound effects, music, map and figurines

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
