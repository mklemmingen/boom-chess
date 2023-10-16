package com.boomchess.game;

import java.util.ArrayList;

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


    public static ArrayList<Coordinates> mathMove(Soldier[][] gameBoard, int positionX, int positionY) {
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // an infantry soldier, advanced to the classic pawn piece, can move to all adjacent tiles surrounding it
        // we need to check if the tile is occupied by anything before putting it in the array
        // the 2D-Array will hold the x value in the first-layer array and the y value in the second-layer array

        // here we do math to get the tile coordinates of the tile we want to check
        //   a  b  c
        //   d  xy  e
        //   f  g  h

        // for loop that does -1 0 and +1 onto the x coordinate
        // for loop that does -1 0 and +1 onto the y coordinate
        // in total, we will have 8 iterations

        //   ax = x - 1 ; ay = y + 1
        //   bx = x ; by = y + 1
        //   cx = x + 1 ; cy = y + 1
        //   dx = x - 1 ; dy = y
        //   ex = x + 1 ; ey = y
        //   fx = x - 1 ; fy = y - 1
        //   gx = x ; gy = y - 1
        //   hx = x + 1 ; hy = y - 1
        //   we need to check if the tile is occupied by anything before putting it in the array

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {

                // We do not skip the case where xOffset and yOffset are both 0 (the current position), since
                // it would take more computations doing that each loop that it takes to just check if the
                // tile is occupied and a valid move

                int newX = positionX + xOffset;
                int newY = positionY + yOffset;

                if (Board.isValidMove(newX, newY) && !gameBoard[newX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }
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
