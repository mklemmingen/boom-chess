package com.boomchess.game.backend.subsoldier;

import com.badlogic.gdx.graphics.Texture;
import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.Soldier;
import com.boomchess.game.backend.takeSelfieInterface;

public class Hill extends Soldier implements takeSelfieInterface {
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
