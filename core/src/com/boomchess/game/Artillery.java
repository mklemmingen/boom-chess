package com.boomchess.game;

import java.util.ArrayList;

public class Artillery {
    /*
     * Artillery.java is a fully new piece in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     * As its speciality, it deals damage to 3 tiles surrounding him
     */

    public static int getHealth(){
        return 40;
    }

    public static int calculateDamage(String soldierDefend) {

        // deals 1-10 damage
        // advantages: deals +5 to infantry

        int minValue = 1;
        int maxValue = 10;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if (soldierDefend.equals("infantry")) {
            randomDamage += 5;
        }

        return randomDamage;

    }

    public static ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // an artillery can move to all adjacent tiles surrounding it
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
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack.equals("wardog")){
            return damage - 5;
        }
        System.out.println("The artillery has been damaged for " + damage + " points by " + soldierAttack + ".");
        return damage;
    }
}
