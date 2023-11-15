package com.boomchess.game.frontend;

import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Board;
import com.boomchess.game.backend.Coordinates;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.frontend.stage.GameStage;

import java.util.LinkedList;

public class attackSequence {

    private static boolean damageSequenceRunning;

    private static LinkedList<InnerSequBits> attackList;

    private static float timePerAttack = 1.0f;

    private static float elapsed;

    public attackSequence(){
        damageSequenceRunning = false;
        attackList = new LinkedList<InnerSequBits>();
    }

    public void startSequences(){
        /*
        This method starts off by setting isRunning to true
         */
        damageSequenceRunning = true;
    }

    public void playNext(float delta){
        /*
        * plays the next sequence stored in the linked list if the first object has reached elapsed > timePerAttack
        * if the linkedlist is empty, sets sequence end
         */

        // take the first object of the linked list
        InnerSequBits curBitty = attackList.get(0);

        elapsed += delta;

        // check if its elapsed float is bigger than its allowed timePerAttack
        if(elapsed < timePerAttack) {
            // if it is not, do all the screen thingies and then return

            if(!(curBitty.getExplosion() == null)) {
                if (curBitty.getIsDeath()) {
                    // play the boom sound when a piece dies
                    BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
                    BoomChess.addDeathAnimation(curBitty.getExplosion().getX(), curBitty.getExplosion().getY());
                    // we use this int-array x and y position to set the tile to an Empty object
                    Soldier[][] gameBoard = Board.getGameBoard();
                    System.out.println("\nBoom! A piece has died");
                    gameBoard[curBitty.getExplosion().getX()][curBitty.getExplosion().getY()] = new Empty("empty");
                    System.out.println("\nThe tile has been set to an Empty object");
                    BoomChess.switchToStage(GameStage.createGameStage(BoomChess.isBotMatch));
                } else {
                    BoomChess.addHitMarker(curBitty.getExplosion().getX(), curBitty.getExplosion().getY());
                }
            }

            if(!(curBitty.getDottedLineStart() == null)) {
                BoomChess.addDottedLine(curBitty.getDottedLineStart().getX(), curBitty.getDottedLineStart().getY(),
                        curBitty.getDottedLineEnd().getX(), curBitty.getDottedLineEnd().getY(), curBitty.getTypeOfLine());
                System.out.println("\nThe dotted line has been drawn");
            }

            return;
        } else {
            // if it is, clear the deathExplosionStage and remove this linked list object
            elapsed = 0;
            removeSequence();
        }

        if (attackList.isEmpty()){
            // if linked list is now empty, set damageSequenceRunning = false
            damageSequenceRunning = false;
            // this causes the object to not be called till start turns true
        }
    }

    public void addSequences(Coordinates dottedLineActorStart, Coordinates dottedLineActorEnd,
                                    Coordinates explosion, boolean isDeathExplosion, boolean isDamage){
        // add a object

        InnerSequBits newBitty = new InnerSequBits(dottedLineActorStart, dottedLineActorEnd, explosion,
                isDeathExplosion, isDamage);
        attackList.add(newBitty);

    }

    public static void removeSequence(){
        /*
        * called when a sequence has been played
        * removes the head of the linkedList
         */

        attackList.removeFirst();
    }

    public boolean getdamageSequenceRunning(){
        return damageSequenceRunning;
    }
}
