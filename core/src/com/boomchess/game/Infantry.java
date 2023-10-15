package com.boomchess.game;

public class Infantry {
    /*
     * Infantry.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.,
     */
    public static int getHealth(){
        return 40;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 01-20 damage
        // advantages +5 to attacking helicopters

        return 0;
    }


    public static int[] mathMove(int positionX, int positionY){
        // this method returns an array of all possible move-location for this soldier (positionXY, position XY)
        int[] possibleMoves = new int[8];

        // TODO
        // an Infantry soldier can move to all 8 adjacent tiles
        // we need to check if the tile is occupied by anything before putting it in the array

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack.equals("commando")){
            return damage - 5;
        }
        return damage;
    }
}
