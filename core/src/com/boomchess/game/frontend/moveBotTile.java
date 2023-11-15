package com.boomchess.game.frontend;

import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;

import com.boomchess.game.backend.Soldier;
import com.boomchess.game.frontend.interfaces.takeSelfieInterface;

import java.awt.*;

public class moveBotTile {
    /*
     * This class is used to simulate the dragging of a piece by the bot. It slowly moves an image
     * of the soldier along the white line
     */

    // properties for beginning and end coordinates
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    private static Coordinates startPx;
    private static Coordinates endPx;

    // properties for elapsed time, boolean isMoving and current position
    private static float elapsedTime;
    private static float moveDuration;
    public static boolean isMoving;
    public boolean movingFinished;

    public static Image soldierImage;

    public moveBotTile() {
        elapsedTime = 0;
        moveDuration = 1.5f; // Duration of the move in seconds
        isMoving = false;
        movingFinished = false;
    }

    // method for starting the move
    public void startMove(int startX, int startY, int endX, int endY) {
        /*
         * Method to simulate a move (start of the Move)
         */

        // coordinates
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        Soldier[][] gameBoard = Board.getGameBoard();

        soldierImage = new Image(gameBoard[startX][startY].takeSelfie());

        startPx = BoomChess.calculatePXbyTile(startX, startY);
        endPx = BoomChess.calculatePXbyTile(endX, endY);

        // setting boolean isMoving to true, since we started moving
        isMoving = true;
        // setting elapsedTime to zero, since time got set to zero
        elapsedTime = 0;

        Coordinates startPx = BoomChess.calculatePXbyTile(startX, startY);

        // render at position
        renderAt(startPx.getX(), startPx.getY());

    }

    // method for updating the move
    public void update(float delta) {
        /*
         * updates the BotMove object every frame
         */

        if (isMoving) {
            elapsedTime += delta; // adding time to elapsed time variable

            if (elapsedTime < moveDuration) { // if the elapsed time has not reached the maximum duration

                // Interpolate position // could be option changed by user

                float progress = elapsedTime / moveDuration;

                int currentX = startPx.getX() + (int) ((endPx.getX() - startPx.getX()) * progress);
                int currentY = startPx.getY() + (int) ((endPx.getY() - startPx.getY()) * progress);

                System.out.println("Moving, currently: " + currentX + " " + currentY);

                // call the renderAt method to render the image at the current position
                renderAt(currentX, currentY);
            } else {
                // Move completed
                isMoving = false;
                movingFinished = true;
            }
        }
    }

    private static void renderAt(int currentX, int currentY) {
        /*
         * This method is used to render the image at the current position
         */

        // clears the used Stage at each call
        BoomChess.deathExplosionStage.clear();
        // then adds the image to the stage at the px position
        BoomChess.deathExplosionStage.addActor(soldierImage.setPosition(currentX, currentY));

    }

    public boolean getIsMoving() {
        return isMoving;
    }


}
