package com.boomchess.game;

import java.util.ArrayList;

public class General {
    /*
     * General.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */

    public static int getHealth(){
        return 50;
    }

    public static int calculateDamage(String soldierDefend) {
        // only deals 5-10 damage

        int minValue = 5;
        int maxValue = 10;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply

        return (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));
    }

    public static ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // a General, advanced to the classic General piece, can move to all adjacent tiles surrounding it
        // we need to check if the tile is occupied by anything before putting it in the array
        // the 2D-Array will hold the x value in the first-layer array and the y value in the second-layer array

        // here we do math to get the tile coordinates of the tile we want to check
        //   a  b  c
        //   d  xy  e
        //   f  g  h

        // for loop that does -1 0 and +1 onto the x coordinate
        // for loop that does -1 0 and +1 onto the y coordinate
        // in total, we will have 9 iterations

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
        // The General is very resistant and only takes half damage to anything. He likes to chill in a Bunker

        System.out.println("The General is in a Bunker and only takes half damage!");
        return damage/2;
    }
}
