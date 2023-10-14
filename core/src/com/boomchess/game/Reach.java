package com.boomchess.game;

public class Reach {
    /*
     * Reach.java handles the calculation of any possible attack for each soldier at the end of a round.
     */
    public static void calculateReach(Soldier[][] board, int position) {
        /* the methode calculateReach takes a 2D-Array of the board
        * and the current position from which an iteration through each adjacent tile is started.
        * After checking the information of the position-soldier into usable variables, it then iterates through each
        * adjacent tile and checks if it is occupied by a soldier of the enemy team.
        * If yes, it collects the enemy soldiers information and calls upon the
        * Damage.makeBigBoom method to calculate and deal the damage to the enemy soldier.
        */

        // the board has the size 8x8. to calculate the surrounding tiles position,
        // we mathematically add or subtract 1 or 10 to the current position.
        //              a  b   c
        //              d  x   e
        //              f  g   h

        int a = position - 11;
        if (a < 0) {
            a = -1;
        }
        int b = position - 10;
        if (b < 0) {
            b = -1;
        }
        int c = position - 9;
        if (c < 0) {
            c = -1;
        }
        int d = position - 1;
        if (d < 0) {
            d = -1;
        }
        int e = position + 1;
        if (e > 79) {
            e = -1;
        }
        int f = position + 9;
        if (f > 79) {
            f = -1;
        }
        int g = position + 10;
        if (g > 79) {
            g = -1;
        }
        int h = position + 11;
        if (h > 79) {
            h = -1;
        }

        // we go through each of these position values by using a for loop and check if
        // they are occupied by an enemy soldier.
        // if yes, we collect the enemy soldiers information and call upon the Damage.makeBigBoom method
        // to calculate and deal the damage to the enemy soldier.

        int[] surrPositions = {a, b, c, d, e, f, g, h};
        // TODO check in with the way we plan on storing data in the 2D Array and adjust this accordingly
        // in order to get the info if a tile has a soldier, we
    }

}
