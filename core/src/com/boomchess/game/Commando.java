package com.boomchess.game;

public class Commando {
    /*
     * Commando.java is the object for the chess piece Commando in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtCommando lowers the current health amount any returns the new health amount.
     * The method defaultCommando resets the health to the initial amount.
     */

    public static int getHealth(){
        return 50;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 1-30 damage
        // deals +10 to attacking tanks

        return 0;
    }

    public static int[] tellMoves(int positionX, int positionY){
        // this method returns an array of all possible move-location for this soldier (positionXY, position XY)
        int[] possibleMoves = new int[8];

        // TODO
        // a commando can move horizontally, diagonally and vertically
        // as much as it wants until it hits a wall or another soldier
        // we need to check if the tile is occupied by anything before putting it in the array

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // The Commando is very sneaky and rogue. His resistance is randomized.
        // on a randomized 1-5 scale, he takes (<random number>/5)*100 percent less damage

        // TODO find fitting randomisation in java
        return damage;
    }
}

