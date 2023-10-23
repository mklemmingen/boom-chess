package com.boomchess.game.backend;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;

public class Empty extends Soldier{
    /*
    * Empty.java is the object for the an Empty tile in the game Boom Chess.
    * It acts as a non-moving object on the board.
     */

    public Empty(String teamColor) {
        /*
         * Constructor for the Helicopter object, takes positional arguments and team color
         */
        super(teamColor, -1);
    }
    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */

        return BoomChess.empty;

    }

}
