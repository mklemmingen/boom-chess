package com.boomchess.game.backend;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;

public class Hill extends Soldier{
    /*
    * Hill.java is the object for the chess piece Hill in the game Boom Chess.
    * It acts as a non-moving and non-passable object on the board.
     */

    public Hill(String teamColor) {
        /*
         * Constructor for the Helicopter object, takes positional arguments and team color
         */
        super(teamColor, -1);
    }

    public Texture takeSelfie() {
        /*
         * this method returns a Texture depending on the team color
         */
        return BoomChess.hill;
    }
}
