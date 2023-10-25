package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.calculateDamageInterface;
import com.boomchess.game.backend.defendAndBleedInterface;
import com.boomchess.game.backend.takeSelfieInterface;

public class Artillery extends Soldier
        implements takeSelfieInterface, calculateDamageInterface, defendAndBleedInterface {
    /*
     * Artillery.java is a fully new piece in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     * As its speciality, it deals damage to 3 tiles surrounding him
     */

    public Artillery(String teamColor) {
        /*
         * Constructor for the Artillery object, takes positional arguments and team color
         */
        super(teamColor, 40);
    }

    public int calculateDamage(Soldier soldierDefend) {

        // deals 1-5 damage
        // advantages: deals +5 to infantry

        int minValue = 1;
        int maxValue = 5;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if (soldierDefend instanceof Infantry) {
            randomDamage += 5;
        }

        BoomChess.artillerySound.play(BoomChess.soundVolume);

        return randomDamage;

    }

    public int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Wardog){
            return damage - 5;
        }
        System.out.println("The artillery has been damaged for " + damage + " points by " + soldierAttack + ".");
        return damage;
    }

    @Override
    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */
        if (teamColor.equals("red")) {
            return BoomChess.redArtillery;
        } else {
            return BoomChess.greenArtillery;
        }
    }
}