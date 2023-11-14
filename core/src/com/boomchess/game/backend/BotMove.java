package com.boomchess.game.backend;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.boomchess.game.BoomChess;

public class BotMove implements InputProcessor {
    /*
     * This class is used to simulate the dragging of a piece by the bot. Instead of the default InputProcessor, the
     * drag and drop listeners of red Pieces in a bot match have this class as their InputProcessor
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


    // Constructor
    public BotMove() {
        elapsedTime = 0;
        moveDuration = 1.5f; // Duration of the move in seconds
        isMoving = false;
        movingFinished = false;
    }

    public void startMove(int startX, int startY, int endX, int endY) {
        /*
         * Method to simulate a move (start of the Move)
         */

        // coordinates
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        startPx = BoomChess.calculatePXbyTile(startX, startY);
        endPx = BoomChess.calculatePXbyTile(endX, endY);

        // setting boolean isMoving to true, since we started moving
        isMoving = true;
        // setting elapsedTime to zero, since time got set to zero
        elapsedTime = 0;

        Coordinates startPx = BoomChess.calculatePXbyTile(startX, startY);

        // simulate first held down touch
        touchDown(startPx.getX(), startPx.getY(), 0, 0);
    }

    // method for game loop
    public void update(float delta) {
        /*
         * updates the BotMove object every frame
         */

        if (isMoving) {
            elapsedTime += delta; // adding time to elapsed time variable

            if (elapsedTime < moveDuration) { // if the elapsed time has not reached the maximum duration
                // Interpolate position // could be option changed by user // TODO
                float progress = elapsedTime / moveDuration;

                int currentX = startPx.getX() + (int) ((endPx.getX() - startPx.getX()) * progress);
                int currentY = startPx.getY() + (int) ((endPx.getY() - startPx.getY()) * progress);

                System.out.println("Moving, currently: " + currentX + " " + currentY);

                touchDragged(currentX, currentY, 0);
            } else {
                // Move completed
                isMoving = false;
                movingFinished = true;
                touchUp(endPx.getX(), endPx.getY(), 0, 0);
            }
        }
    }

    /*

    public void simulateDragEvent(Actor actor, int eventType, int screenX, int screenY) {
        InputEvent event = new InputEvent();
        event.setStage(actor.getStage());
        event.setRelatedActor(actor);
        event.setPointer(0); // usually 0 for the first touch
        event.setStageX(screenX);
        event.setStageY(screenY);

        switch (eventType) {
            case InputEvent.Type.touchDown():
                event.setType(InputEvent.Type.touchDown);
                actor.fire(event);
                break;
            case InputEvent.Type.touchDragged():
                event.setType(InputEvent.Type.touchDragged);
                actor.fire(event);
                break;
            case InputEvent.Type.touchUp():
                event.setType(InputEvent.Type.touchUp);
                actor.fire(event);
                break;
        }
    }

     */

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("Dragging");
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touching down");
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touching up");
        return true;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean getIsMoving() {
        return isMoving;
    }
}