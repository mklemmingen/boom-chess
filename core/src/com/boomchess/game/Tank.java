package com.boomchess.game;

public class Tank {
    /*
     * Tank.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.
     */

    public static int getHealth(){
        return 60;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 10-20 damage
        // advantages: deals +5 to infantry
        // disadvantages: deals -5 to wardogs

        return 0;
    }

    public static int[] mathMove(int positionX, int positionY){
        // this method returns an array of all possible move-location for this soldier (positionXY, position XY)
        int[] possibleMoves = new int[8];

        // TODO
        // a tank can move vertically and horizontally till it hits a wall or another soldier
        // we need to check if the tile is occupied by anything before putting it in the array

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack.equals("wardog")){
            return damage - 5;
        }
        return damage;
    }
}
