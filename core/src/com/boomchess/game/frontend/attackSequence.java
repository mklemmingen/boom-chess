package com.boomchess.game.frontend;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.actor.DeathExplosionActor;
import com.boomchess.game.frontend.actor.DottedLineActor;
import com.boomchess.game.frontend.actor.HitMarkerActor;

import java.util.ArrayList;

import static com.boomchess.game.BoomChess.*;

public class attackSequence {

    private static boolean damageSequenceRunning;

    private static ArrayList<Actor> attackList;

    private static float timePerBreak = 1.0f;

    private static float elapsed;

    private static int lengthOfAttackList;

    private static int currentIndex;

    private static float startTime;

    // --------------------------------------------------------------------------------------------------

    public attackSequence(){
        damageSequenceRunning = false;
        attackList = new ArrayList<Actor>();
    }

    public void startSequences(){
        /*
        This method starts off by setting isRunning to true
         */
        if (!(attackList.isEmpty())) {
            damageSequenceRunning = true;
        }
        lengthOfAttackList = attackList.size();
    }

    public void playNext(float delta){
        /*
        * plays the next sequence stored in the linked list if the first object has reached elapsed > timePerAttack
        * if the linkedlist is empty, sets sequence end
         */

        if (attackList.isEmpty()){ // Exception handling
            // if linked list is now empty, set damageSequenceRunning = false
            damageSequenceRunning = false;
            // this causes the object to not be called till start turns true
            return;
        }

        // if elapsed is 0, then we are at the start of the sequence
        // so we need to add the actor to the stage correct stage

        if(elapsed == 0) {

            // if statement of instance of either HitMarkerActor or DeathExplosionActor or DottedLineActor

            Actor actorBuddy = attackList.get(currentIndex);

            startTime = System.currentTimeMillis();

            if (actorBuddy instanceof HitMarkerActor) {
                // if it is a HitMarkerActor, add it to the GameStage
                deathExplosionStage.addActor(actorBuddy);
            } else if (actorBuddy instanceof DeathExplosionActor) {
                // if it is a DeathExplosionActor, add it to the deathExplosionStage
                BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
                deathExplosionStage.addActor(actorBuddy);
            } else if (actorBuddy instanceof DottedLineActor) {
                // if it is a DottedLineActor, add it to the GameStage
                dottedLineStage.addActor(actorBuddy);
            } else {
                // simulate throwing an error for the log
                System.out.println("attackSequence.playNext() has an unknown actor type");
            }
        }

        if(elapsed >= timePerBreak) {
            currentIndex += 1;
        }

        elapsed += startTime - delta;

        // check if currentIndex is greater than lengthOfAttackList
        if (currentIndex > (lengthOfAttackList - 1)){
            // if linked list is now empty, set damageSequenceRunning = false
            damageSequenceRunning = false;
            // this causes the object to not be called till start turns true
        }
    }

    public void addSequence(Actor actor){
        // add a object
        attackList.add(actor);
    }

    public boolean getdamageSequenceRunning(){
        return damageSequenceRunning;
    }

    public ArrayList<Actor> getAttackList() {
        return attackList;
    }

    public static int getIndex(){
        return currentIndex;
    }
}
