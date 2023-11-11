package com.boomchess.game.frontend.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;

public class MoveSoldierActor extends Actor {
    // these values are the beginning and the end of the Moving
    private final float startX;
    private final float startY;
    private final float endX;
    private final float endY;

    // this is the time that the Soldier has been travelling
    private float elapsed;
    // this is the maximum duration that the Soldier will be on the screen
    private static final float MAX_DURATION = 4f;

    // this is the Image that will move, give with takeSelfie and create new image in parameter
    private final Image soldierImage;

    public MoveSoldierActor(float startX, float startY, float endX, float endY, Image soldierImage) {
        /*
         * used to create a DottedLineActor Object
         */
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.soldierImage = soldierImage;
    }

    @Override
    public void act(float delta) {
        /*
         * this method is called every frame to update the MoveSoldierActor Object and overrides the standard act method
         * so that the Actor will be removed after a certain amount of time
         */
        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            remove(); // This will remove the actor from the stage
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // logic for drawing the soldier starts here ---------------------------------------

        // calculate Pixel Coordinates of attacker and defender
        Coordinates beginning = BoomChess.calculatePXbyTile((int) startX, (int) startY);
        Coordinates end = BoomChess.calculatePXbyTile((int) endX, (int) endY);

        // out of the Coordinate objects, get the PX
        float x1 = beginning.getX();
        float y1 = end.getY();
        float x2 = beginning.getX();
        float y2 = end.getY();

        // to calculate the distance between the coordinates, create a simple Vector2 object
        float distance = Vector2.dst(x1, y1, x2, y2);

    }
}
