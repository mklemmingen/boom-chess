package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.stage.GameStage;

import static com.boomchess.game.BoomChess.*;

public class GameEndStage extends Stage{

    public static Stage initializeUI(String winnerTeamColour) {

        BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;

        Stage gameEndStage = new Stage();

        // TODO ADD WINNER EMBLEM AND BETTER LAYOUT/TRIGGER

        // Begin of GameEndLayout - Root Table arranges content automatically and adaptively as ui-structure
        final Table endRoot = new Table();
        endRoot.setFillParent(true);

        if(isColourChanged){
            if(winnerTeamColour.equals("green")){
                winnerTeamColour = "Blue";
            }
        }

        String coolShoutout;
        if(isMedievalMode){
            coolShoutout = "Huzzah! A King as died! Long live ... another King!\n Same same but different!";
        } else {
            coolShoutout = "Huzzah! A General as died! Long live ... another General we just promoted!\nSame same but different!";
        }

        /*
        * Label to be created with WinnerTeamColour in mind, White Background and black font
         */
        Label winnerLabel = new Label("The " + winnerTeamColour + " Team won\n!" + coolShoutout, skin);
        winnerLabel.setColor(Color.BLACK);
        endRoot.add(winnerLabel).padBottom(20);
        endRoot.row();

    gameEndStage.addActor(endRoot);
    System.out.println("GameEndStage initialized");
    return gameEndStage;
    }
}
