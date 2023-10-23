package com.boomchess.game.backend;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;

import java.util.ArrayList;

public class General extends Soldier{
    /*
     * General.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */


    public General(String teamColor) {
        /*
         * Constructor for the General object, takes positional arguments and team color
         */
        super(teamColor, 50);
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        if (teamColor.equals("red")) {
            return BoomChess.redGeneral;
        } else {
            return BoomChess.greenGeneral;
        }
    }

    public static int calculateDamage(String soldierDefend) {
        // only deals 5-10 damage

        int minValue = 5;
        int maxValue = 10;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply

        BoomChess.generalSound.play(BoomChess.soundVolume);

        return (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));
    }

    public static int defendAndBleed(int damage, Soldier soldierAttack) {
        // The General is very resistant and only takes half damage to anything. He likes to chill in a Bunker

        System.out.println("The General is in a Bunker and only takes half damage!");
        return damage/2;
    }
}
