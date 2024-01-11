package com.boomchess.game.frontend.stage;

import static com.boomchess.game.BoomChess.GameState;
import static com.boomchess.game.BoomChess.actionSequence;
import static com.boomchess.game.BoomChess.batch;
import static com.boomchess.game.BoomChess.botMovingStage;
import static com.boomchess.game.BoomChess.calculatePXbyTile;
import static com.boomchess.game.BoomChess.calculateTileByPX;
import static com.boomchess.game.BoomChess.calculateTileByPXNonGDX;
import static com.boomchess.game.BoomChess.clearAllowedTiles;
import static com.boomchess.game.BoomChess.createInGameOptionStages;
import static com.boomchess.game.BoomChess.createMainMenuStage;
import static com.boomchess.game.BoomChess.crossOfDeathStage;
import static com.boomchess.game.BoomChess.currentState;
import static com.boomchess.game.BoomChess.damageNumberStage;
import static com.boomchess.game.BoomChess.deathExplosionStage;
import static com.boomchess.game.BoomChess.dottedLineStage;
import static com.boomchess.game.BoomChess.empty;
import static com.boomchess.game.BoomChess.emptyX;
import static com.boomchess.game.BoomChess.emptyY;
import static com.boomchess.game.BoomChess.gameEndStage;
import static com.boomchess.game.BoomChess.inGame;
import static com.boomchess.game.BoomChess.inTutorial;
import static com.boomchess.game.BoomChess.isColourChanged;
import static com.boomchess.game.BoomChess.legitTurn;
import static com.boomchess.game.BoomChess.reRenderGame;
import static com.boomchess.game.BoomChess.setAllowedTiles;
import static com.boomchess.game.BoomChess.showHelp;
import static com.boomchess.game.BoomChess.showInGameOptions;
import static com.boomchess.game.BoomChess.skin;
import static com.boomchess.game.BoomChess.speechBubbleStage;
import static com.boomchess.game.BoomChess.tileSize;
import static com.boomchess.game.BoomChess.useEmpty;
import static com.boomchess.game.BoomChess.wrongMoveStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.Hill;
import com.boomchess.game.frontend.actor.AttackSequence;
import com.boomchess.game.frontend.actor.HealthNumber;
import com.boomchess.game.frontend.actor.SpecialDamageIndicator;
import com.boomchess.game.frontend.actor.WrongMoveIndicator;
import com.boomchess.game.frontend.actor.tileWidget;
import com.boomchess.game.frontend.animations.soldierAnimation;
import com.boomchess.game.frontend.interfaces.takeIntervalSelfie;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameStage {

    private static boolean  showHealth = false;

    private final Stage gameStage;

    // linkedlist of all tileWidgets created in the gameStage
    public static LinkedList<tileWidget> currentTileWidgets;

    public GameStage(boolean isBotMatch) {
        this.gameStage = createGameStage(isBotMatch);
    }

    public static Stage createGameStage(final boolean isBotMatch) {

        // clears the crossOfDeathStage
        crossOfDeathStage.clear();

        // allows the game have a MoveLogo
        BoomChess.showMove = true;

        // add the audio table to gameStage as Actor and position on the far right of the Screen
        Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // xMarkerOverlay
        BoomChess.possibleMoveOverlay = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // for the size of the tiles
        int numRows = 8;
        int numColumns = 9;

        batch.begin();

        tileWidget thisTile = null;

        Soldier[][] gameBoard = Board.getGameBoard();

        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numColumns; i++) {

                if(gameBoard[i][j] instanceof Empty){
                    continue;
                }

                thisTile = new tileWidget(i, j);
                currentTileWidgets.add(thisTile);
                Coordinates coords = calculatePXbyTile(i, j);
                thisTile.setPosition(coords.getX(), coords.getY());
                gameStage.addActor(thisTile);
            }
        }

        if(inTutorial){
            // add tutorialtexture to the upper right corner
            Image tutorialTexture = new Image(BoomChess.tutorialTexture);
            tutorialTexture.setSize(tileSize*6, tileSize*7);
            tutorialTexture.setPosition(Gdx.graphics.getWidth() - tutorialTexture.getWidth(),
                    Gdx.graphics.getHeight() - tutorialTexture.getHeight());
            gameStage.addActor(tutorialTexture);
        }



        // create another table for the option buttons

        Table backTable = new Table();
        backTable.setSize(tileSize*5, tileSize*2f);
        // bottom right the table in the parent container
        backTable.setPosition(Gdx.graphics.getWidth() - backTable.getWidth(), tileSize);
        gameStage.addActor(backTable); // Add the table to the stage


        // help button
        backTable.row().padBottom(tileSize/4);
        TextButton helpButton = new TextButton("Help", skin);
        helpButton.align(Align.bottomRight);
        backTable.add(helpButton);
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHelp = !showHelp;
            }
        });

        backTable.row().padBottom(tileSize/4);

        // show intervals button
        TextButton intervalsButton = new TextButton("Damage", skin);
        intervalsButton.align(Align.bottomRight);
        backTable.add(intervalsButton);
        intervalsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.showIntervals = !BoomChess.showIntervals;
                reRenderGame();
            }
        });

        backTable.row().padBottom(tileSize/4);

        // show health button
        backTable.row().padBottom(tileSize/4);
        TextButton healthButton = new TextButton("Health", skin);
        healthButton.align(Align.bottomRight);
        backTable.add(healthButton);
        healthButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHealth = !showHealth;
                reRenderGame();
            }
        });

        backTable.row().padBottom(tileSize/4);

        // turn on options
        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.align(Align.bottomRight);
        backTable.add(optionsButton);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createInGameOptionStages();
                showInGameOptions = !showInGameOptions;
            }
        });
        backTable.row().padBottom(tileSize/4);

        // Exit to Main Menu button to return to the main menu
        TextButton menuButton = new TextButton("Main Menu", skin);
        menuButton.align(Align.bottomRight);
        backTable.add(menuButton);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deathExplosionStage.clear();
                botMovingStage.clear();
                dottedLineStage.clear();
                gameEndStage.clear();
                speechBubbleStage.clear();
                crossOfDeathStage.clear();

                actionSequence = new AttackSequence();

                BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;
                
                inGame = false;
                showHelp = false;
                
                createMainMenuStage();
            }
        });

        return gameStage;
    }

    public Stage getStage() {
        return gameStage;
    }

    public LinkedList<tileWidget> getCurrentTileWidgets(){
        return currentTileWidgets;
    }

    public void setCurrentTileWidgets(LinkedList<tileWidget> tileWidgetList){
        currentTileWidgets = tileWidgetList;
    }

    public tileWidget giveTileWidgetOf(Coordinates coordsSearch){
        for (tileWidget tile: currentTileWidgets){
            if(tile.equalLocationTo(coordsSearch))
            {
                return tile;
            }
        }
        return null;
    }

}

