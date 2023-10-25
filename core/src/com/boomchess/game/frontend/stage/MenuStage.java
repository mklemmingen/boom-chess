package com.boomchess.game.frontend.stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;

import static com.boomchess.game.BoomChess.*;
import static com.boomchess.game.frontend.stage.GameStage.setGameBoard;

public class MenuStage extends Stage{

    public static Stage initializeUI() {
        BoomChess.showMove = false;

        // setOverlay to false
        BoomChess.renderOverlay = false;

        Stage menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);

        // start menu music
        background_music.stop();
        menu_music.setLooping(true);
        menu_music.play();
        menu_music.setVolume(volume);

        // Begin of Main Menu Layout - Root Table arranges content automatically and adaptively as ui-structure
        final Table root = new Table();
        root.setFillParent(true);
        menuStage.addActor(root);

        final Image title = boomLogo;
        root.add(title).top().padBottom(20);
        root.row();

        TextButton helpButton = new TextButton("Help!", skin);
        root.add(helpButton).padBottom(20);
        // if help button is pressed, create a new stage for the help information
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createHelpStage();
            }
        });
        root.row();

        TextButton play2Button = new TextButton("Play a 2 Player Game", skin);
        root.add(play2Button).padBottom(20);

        play2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // create the first gameBoard
                GameStage.setGameBoard();

                // stop menu music and start background_music
                menu_music.stop();
                background_music.setLooping(true);
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.RED_TURN;

                boolean isBotMatch = false;

                // create the big game Board as an object of the Board class
                Board.initialise();

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });
        root.row();

        TextButton playBotButton = new TextButton("Play a Game Against the Computer", skin);
        root.add(playBotButton).padBottom(20);
        playBotButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // create the first gameBoard
                setGameBoard();

                // stop menu music and start background_music
                menu_music.stop();
                background_music.setLooping(true);
                background_music.play();
                background_music.setVolume(volume);

                currentState = GameState.RED_TURN;

                boolean isBotMatch = true;

                // create the big game Board as an object of the Board class
                Board.initialise();

                switchToStage(GameStage.createGameStage(isBotMatch));
            }
        });
        root.row();

        // Change Mode button to switch medieval and modern
        String currentMode = "";
        if(isMedievalMode){
            currentMode = "Medieval";
        }
        else{
            currentMode = "Modern";
        }
        TextButton modeButton = new TextButton("Switch Mode: " + currentMode, skin);
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
                BoomChess.createMainMenuStage();
            }
        });
        root.row();

        TextButton optionsButton = new TextButton("Options", skin);
        root.add(optionsButton).padBottom(20);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createOptionsStage();
            }
        });
        root.row();

        TextButton creditsButton = new TextButton("Credits", skin);
        root.add(creditsButton).padBottom(20);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BoomChess.createCreditsStage();
            }
        });
        root.row();

        TextButton exitButton = new TextButton("Exit", skin);
        root.add(exitButton).padBottom(20).padRight(2);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // for exiting the game
                Gdx.app.exit();
                // for ending all background activity on Windows systems specifically
                System.exit(0);
            }
        });
        root.row();

       return menuStage;
    }

}
