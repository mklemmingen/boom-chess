package com.boomchess.game.frontend.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;

import static com.boomchess.game.BoomChess.createMainMenuStage;
import static com.boomchess.game.BoomChess.skin;

public class GameEndStage extends Stage{

    public static Actor initializeUI(String winnerTeamColour) {

        BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;

        // TODO ADD WINNER EMBLEM AND BETTER LAYOUT/TRIGGER

        // Begin of GameEndLayout - Root Table arranges content automatically and adaptively as ui-structure
        final Table endRoot = new Table();
        endRoot.setFillParent(true);

        // display the winner and a button to return to the main menu.
        Label winnerLabel = new Label("The " + winnerTeamColour + " Team won!", skin);
        endRoot.add(winnerLabel).padBottom(20);
        endRoot.row();

        // back button to return to the main menu
        TextButton backButton = new TextButton("Return To Main Menu", skin);
        endRoot.add(backButton).padBottom(20);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                // refresh gameBoard to initial state by going to the mainMenu
                createMainMenuStage();
                // create a new gameBoard since the old one is still in memory
                GameStage.setGameBoard();
            }
        });
    endRoot.row();
    return endRoot;
    }
}
