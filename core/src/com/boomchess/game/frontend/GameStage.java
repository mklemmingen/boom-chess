package com.boomchess.game.frontend;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.takeSelfieInterface;

import java.util.ArrayList;

import static com.boomchess.game.BoomChess.*;

public class GameStage {

    private Stage gameStage;
    private final boolean isBotMatch;

    public GameStage(boolean isBotMatch) {
        this.isBotMatch = isBotMatch;
        this.gameStage = createGameStage(isBotMatch);
    }

    public static Stage createGameStage(final boolean isBotMatch) {

        if (isBotMatch){
            System.out.println("Bot Match");
            // TODO CREATE MULTIPLE BOT DIFFICULTIES
        }

        BoomChess.showMove = true;

        // add the audio table to gameStage as Actor and position on the far right of the Screen
        Stage gameStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // xMarkerOverlay
        BoomChess.possibleMoveOverlay = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        // CHECKED try to implement the game board as a tiled map and the pieces as actors on top of it
        //  combine the tiled map renderer with the stage renderer? Research: addressing individual .tmx tiles in code
        //  - corresponding to the 2D Array Game Board, the pieces on it, their stats as clean health bars.
        //  ----------------------------------------------------------------------------------------------
        //  Actor-Images must be 80x80px. Add Exit-Button in the Bottom right corner of the screen
        //  Actors should be able to be drag-droppable and snap to the grid. They can only move to tiles
        //  their chess characteristics allow them to. This should be checked by the backend, and be sent back as tile
        //  coordinates, so the allowed tiles can temporarily be highlighted. If piece is dropped on an allowed tile,
        //  update 2D Array with this new information. End turn.
        //  Calculate Damage from all the current Players pieces onto enemy pieces. All hit pieces should be highlighted.
        //  If a piece is killed, remove it from the board and the 2D Array. If a piece is killed, check if that piece
        //  was the general and end the game by saving who won (who killed the other general).
        //  If not, switch to the next player.
        //  If yes, create Game-End-Stage: display the winner and a button to return to the main menu.

        // render the gameBoard by iterating through the 2D Array Soldiers[][] gameBoard
        // checking which type of piece at position and drawing the correct image there

        // add game board
        // Begin of GameLayout - Root Table arranges content automatically and adaptively as ui-structure
        Table root = new Table();

        root.setSize(720, 640);
        root.center(); // Center the gameBoard in the parent container (stage)
        // refine the position of the root Table, since the orthoCamera is centered on a screen that may change size
        root.setPosition((Gdx.graphics.getWidth() - root.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - root.getHeight()) / 2f);


        // for the size of the tiles
        int tileSize = 80;
        int numRows = 8;
        int numColumns = 9;

        batch.begin();

        for (int j = 0; j < numRows; j++) {
            // add a new stage Table row after each row of the gameBoard
            root.row();
            for (int i = 0; i < numColumns; i++) {

                // create a new box like widget at each position of the board and add it to the root table
                // it is 80x80 pixels, holds the image of the piece at that position and is movable to other positions
                // if soldierType is a general, use an animation instead of an Image SolPiece

                // TODO GENERAL ANIMATION

                Soldier[][] gameBoard = Board.getGameBoard();
                final Soldier soldier = gameBoard[i][j];

                Image solPiece;

                // load the corresponding image through the Soldier Take Selfie Method
                if (soldier instanceof takeSelfieInterface) {
                    solPiece = new Image(((takeSelfieInterface) soldier).takeSelfie());
                } else {
                    System.out.println("Error: Soldier is not an instance of takeSelfieInterface!");
                    solPiece = new Image(empty); // Ensure "empty" is properly defined before this line.
                }

                int health = soldier.getHealth(); // You can directly use the 'soldier' object since it's the same as gameBoard[X][Y]

                // draw the image at the correct position
                solPiece.setSize(80, 80);
                solPiece.setScaling(Scaling.fit);

                final Stack tileWidget = new Stack();

                if (health < 15 && health > 0) {
                    // Apply a light red hue effect to the tileWidget's image
                    Color lightRed = new Color(1.0f, 0.5f, 0.5f, 1.0f);
                    solPiece.setColor(lightRed);
                }

                tileWidget.add(solPiece);

                if (!(health == -1)) {
                    // tileWidget is only move able if a Piece is on it, meaning it has health
                    tileWidget.setTouchable(Touchable.enabled);
                    // draw the health bar
                    final ProgressBar healthBar = new ProgressBar(0f, 60f, 1f, false,
                            progressBarSkin);
                    healthBar.setSize(0.025F, 0.1F);

                    healthBar.setValue(health);
                    tileWidget.add(healthBar);

                    // hide the health bar by default
                    healthBar.setVisible(false);

                    // the tileWidget Listener checks if the mouse is over the tile and if yes, displays healthBar
                    tileWidget.addListener(new InputListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            healthBar.setVisible(true); // Show the health bar when the mouse enters
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            healthBar.setVisible(false); // Hide the health bar when the mouse exits
                        }
                    });
                }


                final int finalI = i;
                final int finalJ = j;
                tileWidget.addListener(new DragListener() {
                    @Override
                    public void dragStart(InputEvent event, float x, float y, int pointer) {
                        // Code runs when dragging starts:
                        System.out.println("\n Started dragging the actor!");

                        // Get the team color of the current tile
                        Soldier[][] gameBoard = Board.getGameBoard();
                        String tileTeamColor = gameBoard[finalI][finalJ].getTeamColor();

                        // If it's not the current team's turn, cancel the drag and return
                        if ((currentState == BoomChess.GameState.RED_TURN && !tileTeamColor.equals("red")) ||
                                (currentState == BoomChess.GameState.GREEN_TURN && !tileTeamColor.equals("green"))) {
                            event.cancel();
                            System.out.println("\n It's not your turn!");
                            BoomChess.reRenderGame();
                            return;
                        }

                        tileWidget.toFront(); // Bring the actor to the front, so it appears above other actors
                        // as long as the mouse is pressed down, the actor is moved to the mouse position
                        // we calculate the tiles it can move to and highlight these tiles with a slightly red hue
                        // the calculated tiles are part of a ArrayList variable that is created at create
                        // of the whole programm
                        // and gets cleared once we touchDragged the actor to a new position

                        // switch statement for deciding which
                        // Chess Pieces Class mathMove is used to assign the ArrayList validMoveTiles

                        setAllowedTiles(soldier.mathMove(finalI, finalJ));
                    }

                    @Override
                    public void drag(InputEvent event, float x, float y, int pointer) {
                        // Code here will run during the dragging
                        tileWidget.moveBy(x - tileWidget.getWidth() / 2, y - tileWidget.getHeight() / 2);
                    }

                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer) {
                        // Code here will run when the player lets go of the actor

                        // Get the position of the tileWidget relative to the parent actor (the gameBoard)
                        Vector2 localCoords = new Vector2(x, y);
                        // Convert the position to stage (screen) coordinates
                        Vector2 screenCoords = tileWidget.localToStageCoordinates(localCoords);

                        System.out.println("\n Drag stopped at screen position: " + screenCoords.x + ", " + screenCoords.y);

                        Coordinates currentCoord = calculateTileByPX((int) screenCoords.x, (int) screenCoords.y);

                        // for loop through validMoveTiles, at each tile we check for equality of currentCoord with the Coordinate
                        // in the ArrayList by using currentCoord.checkEqual(validMoveTiles[i]) and if true, we set the
                        // validMove Variable to true, call on the update method of the Board class and break the for loop
                        // then clear the Board.


                        ArrayList<Coordinates> validMoveTiles = Board.getValidMoveTiles();
                        for (Coordinates validMoveTile : validMoveTiles) {
                            if (currentCoord.checkEqual(validMoveTile)) {
                                // Board.update with oldX, oldY, newX, newY
                                Board.update(finalI, finalJ, currentCoord.getX(), currentCoord.getY());
                                legitTurn = true;
                                break;
                            }
                        }

                        // and the validMoveTiles are cleared
                        clearAllowedTiles(); // for turning off the Overlay
                        Board.emptyValidMoveTiles();
                        BoomChess.reRenderGame();

                    }
                });
                root.add(tileWidget).size(tileSize);
            }
        }
        System.out.println("\n New Board has been rendered.");
        batch.end();

        gameStage.addActor(root);


        // create another stage for the back to main menu button
        Table backTable = new Table();
        backTable.setSize(400, 80); // determines the frame size for the backTable (button: to main menu)
        // bottom right the table in the parent container
        backTable.setPosition(Gdx.graphics.getWidth() - backTable.getWidth(), 0);
        gameStage.addActor(backTable); // Add the table to the stage

        // Exit to Main Menu button to return to the main menu
        TextButton menuButton = new TextButton("Return to Main Menu", skin);
        menuButton.align(Align.bottomRight);
        backTable.add(menuButton);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMainMenuStage();
                // create a new gameBoard since game has ended
                BoomChess.currentState = BoomChess.GameState.NOT_IN_GAME;
                setGameBoard();
            }
        });

        return gameStage;
    }

    public static void setGameBoard() {
        Board.initialise();
    }

    public void render() {
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
    }

    public Stage getStage() {
        return gameStage;
    }


}

