package com.boomchess.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
* this class is the object for the dotted line that appears when a piece is selected. It acts as an Actor Object
* on a Scene2DUI Stage
*/
public class DottedLineActor extends Actor {
    // these values are the beginning and the end of the dotted Line
    private final float startX;
    private final float startY;
    private final float endX;
    private final float endY;
    // this is the time that the dotted line has been on the screen
    private float elapsed;
    // this is the maximum duration that the dotted line will be on the screen
    private static final float MAX_DURATION = 2.0f;
    // this is the shapeRenderer that will be used to draw the dotted line
    private final ShapeRenderer shapeRenderer;

    public DottedLineActor(float startX, float startY, float endX, float endY, ShapeRenderer shapeRenderer) {
        /*
        * used to create a DottedLineActor Object
         */
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void act(float delta) {
        /*
        * this method is called every frame to update the DottedLineActor Object and overrides the standard act method
        * so that the dotted line will be removed after a certain amount of time
         */
        super.act(delta);
        elapsed += delta;
        if (elapsed > MAX_DURATION) {
            remove(); // This will remove the actor from the stage
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end(); // To stop the batch temporarily because we'll be using the ShapeRenderer

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        // logic for drawing the dotted line starts here ---------------------------------------

        // calculate Pixel Coordinates of attacker and defender
        Coordinates attacker = BoomChess.calculatePXbyTile((int) startX, (int) startY);
        Coordinates defender = BoomChess.calculatePXbyTile((int) endX, (int) endY);

        // out of the Coordinate objects, get the PX
        float x1 = attacker.getX();
        float y1 = attacker.getY();
        float x2 = defender.getX();
        float y2 = defender.getY();


        // set dotLength and spaceLength
        float dotLength = 10;
        float spaceLength = 5;

        // to calculate the distance between the coordinates, create a simple Vector2 object
        float distance = Vector2.dst(x1, y1, x2, y2);
        // combine dotLength and spaceLength for easy drawing
        float dotSpaceLength = dotLength + spaceLength;
        // calculating the needed numbers of combined patterned Dots
        int numberOfDots = (int) (distance / dotSpaceLength);

        // drawing the dots using a for loop along the vector between the attacker and defender
        for (int i = 0; i < numberOfDots; i++) {
            float x = x1 + (x2 - x1) * (i * dotSpaceLength) / distance;
            float y = y1 + (y2 - y1) * (i * dotSpaceLength) / distance;
            float endX = x + (x2 - x1) * dotLength / distance;
            float endY = y + (y2 - y1) * dotLength / distance;

            shapeRenderer.rectLine(x, y, endX, endY, 5);  // 5 is the thickness of the line
        }

        System.out.println("\nThe dotted line has been drawn");

        // logic for drawing the dottedLine stops here ------------------------------------------

        shapeRenderer.end();

        // blue circle in the middle of a tile for debugging
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE); // Color of your choice
        Coordinates center = BoomChess.calculatePXbyTile((int) startX, (int) startY);
        shapeRenderer.circle(center.getX(), center.getY(), 5);  // Draws a small circle
        shapeRenderer.end();

        batch.begin(); // Restart the batch for subsequent actors or UI elements
    }
}