package com.boomchess.game;

public class General {
    /*
        * General.java is the object for the chess piece General in the game Boom Chess.
        * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
        * The method hurtGeneral lowers the current health amount any returns the new health amount.
        * The method defaultGeneral resets the health to the initial amount.
     */

    public static int getHealth(){
        return 50;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // only deals 1-5 damage

        return 0;
    }

    public static int[] mathMove(int positionX, int positionY){
        // this method returns an array of all possible move-location for this soldier (positionXY, position XY)
        int[] possibleMoves = new int[8];

        // TODO
        // a general can move to all 8 adjacent tiles
        // we need to check if the tile is occupied by anything before putting it in the array

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // The General is very resistant and only takes half damage to anything. He likes to chill in a Bunker
        return damage/2;
    }
}
