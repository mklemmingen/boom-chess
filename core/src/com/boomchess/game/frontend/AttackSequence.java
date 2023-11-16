package com.boomchess.game.frontend;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.boomchess.game.BoomChess;
import com.boomchess.game.frontend.actor.DeathExplosionActor;
import com.boomchess.game.frontend.actor.DottedLineActor;
import com.boomchess.game.frontend.actor.HitMarkerActor;

import java.util.ArrayList;

import static com.boomchess.game.BoomChess.*;
import static com.boomchess.game.frontend.stage.GameStage.createGameStage;

public class AttackSequence {

    private boolean damageSequenceRunning;

    private ArrayList<Actor> attackList;

    private float timePerBreak = 1f;

    private float elapsed;

    private int lengthOfAttackList;

    private int currentIndex;


    // --------------------------------------------------------------------------------------------------

    public AttackSequence(){
        damageSequenceRunning = false;
        attackList = new ArrayList<Actor>();
    }

    public void startSequences(){
        /*
        This method starts off by setting isRunning to true
         */

        if(attackList.isEmpty()){
            // if the checkSurroundings has not found any pieces to attack, then return
            return;
        }

        currentIndex = 0;
        damageSequenceRunning = true;
        lengthOfAttackList = attackList.size();

        actActor();
    }

    public void playNext(float delta){
        /*
        * plays the next sequence stored in the linked list if the first object has reached elapsed > timePerAttack
        * if the linkedlist is empty, sets sequence end
         */

        if (currentIndex >= lengthOfAttackList){
            // if the current index is greater than the length of the attack list,
            // then we are at the end of the sequence

            attackList = new ArrayList<Actor>(); // reset the attackList
            damageSequenceRunning = false;
            switchToStage(createGameStage(isBotMatch));
            // this causes the object to not be called till start turns true
            return;
        }

        elapsed += delta;

        if(elapsed >= timePerBreak) {

            actActor();

            elapsed = 0;
        }
    }

    public void addSequence(Actor actor){
        // add a object
        attackList.add(actor);
    }

    private void actActor(){
        // if statement of instance of either HitMarkerActor or DeathExplosionActor or DottedLineActor

        if (currentIndex >= lengthOfAttackList){
            // if the current index is greater than the length of the attack list,
            // then we are at the end of the sequence

            attackList = new ArrayList<Actor>(); // reset the attackList
            damageSequenceRunning = false;
            // this causes the object to not be called till start turns true
            return;
        }

        Actor actorBuddy = attackList.get(currentIndex);

        if (actorBuddy instanceof HitMarkerActor) {
            // if it is a HitMarkerActor, add it to the GameStage
            smallExplosionSound.play(soundVolume);
            deathExplosionStage.addActor(actorBuddy);
            timePerBreak = 0.25f;
        } else if (actorBuddy instanceof DeathExplosionActor) {
            // if it is a DeathExplosionActor, add it to the deathExplosionStage
            BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
            deathExplosionStage.addActor(actorBuddy);
            timePerBreak = 4f;
        } else if (actorBuddy instanceof DottedLineActor) {
            // if it is a DottedLineActor, add it to the GameStage
            ((DottedLineActor) actorBuddy).makeSound();
            dottedLineStage.addActor(actorBuddy);
            timePerBreak = 1f;
        } else {
            // simulate throwing an error for the log
            System.out.println("attackSequence.playNext() has an unknown actor type");
        }

        currentIndex += 1;
    }

    // --------------------------------------------------------------------------------------------------

    public boolean getDamageSequenceRunning(){
        return damageSequenceRunning;
    }

}
