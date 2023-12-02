package com.boomchess.game.frontend.picture;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Coordinates;

import static com.boomchess.game.BoomChess.*;

public class SpeechBubbles {
    /*
    This class holds functions to create and load Stack objects that hold the speech bubbles
     */

    // this function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
    public static void createSpeechAttackIncoming(){
        /*
        This function creates a Stack object that holds the speech bubble that says "Attack in Progress!"
         */
        Stack alarm = new Stack();

        Image alarmImage = new Image(BoomChess.alarmTexture);

        alarmImage.setSize(tileSize*1, tileSize*0.5f);
        alarm.add(alarmImage);
        alarm.setSize(tileSize*1, tileSize*0.5f);

        Coordinates generalCoordinates;

        // find the general coordinates to add a speech bubble to him
        if(currentState == GameState.GREEN_TURN){
            // if it is green's turn, find the general coordinates of green
            generalCoordinates = BoomChess.findGeneral(false);
        }
        else{
            // if it is red's turn, find the general coordinates of red
            generalCoordinates = BoomChess.findGeneral(true);
        }

        //set position
        assert generalCoordinates != null;
        alarm.setPosition(generalCoordinates.getX() + (tileSize / 16), generalCoordinates.getY() + tileSize / 4);

        deathExplosionStage.addActor(alarm);
    }

    // this function creates a Stack Object that creates a random speech bubble of defeating the enemy
    // and adds it to the correct position on the stage
    public static void createSpeechDefeatEnemy(int x, int y) {
        /*
        This function creates a Stack Object that creates a random speech bubble of defeating the enemy
         */
        Stack defeatEnemy = new Stack();
        Image defeatEnemyImage = new Image(BoomChess.killSpeeches.getRandomTexture());
        defeatEnemyImage.setSize(tileSize * 1.5f, tileSize * 0.75f);
        defeatEnemy.add(defeatEnemyImage);
        defeatEnemy.setSize(tileSize * 1.5f, tileSize * 0.75f);

        //adds a death kill speech bubble to the attacker
        Coordinates deathCoordinates = calculatePXbyTile(x, y);
        //set position
        defeatEnemy.setPosition(deathCoordinates.getX(), deathCoordinates.getY());

        deathExplosionStage.addActor(defeatEnemy);
    }




}
