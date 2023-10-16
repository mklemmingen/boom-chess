package com.boomchess.game;

import java.util.ArrayList;

public class Helicopter {
    /*
     * Helicopter.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.,
     */

    public static int getHealth(){
        return 50;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 10-20 damage
        // advantages: deals +5 to tanks

        return 0;
    }

    public static ArrayList<Coordinates> mathMove(Soldier[][] gameBoard, int positionX, int positionY) {
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // A Helicopter can move to any tile that is 3 tiles forward and 1 to the left
        // or right in any direction except diagonal
        // we need to check if the tile is occupied by anything before putting it in the array

        // we have a loop for each direction that the helicopter can move in. We start by creating four variables

        int upwardsY = positionY + 3;
        // Loop to move vertical up and to the right or left
        for (int offset = -1; offset < 2; offset += 2) {
            int newX = positionX + offset;

            if (Board.isValidMove(newX, upwardsY)) {
                if (!gameBoard[newX][upwardsY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, upwardsY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int downwardsY = positionY - 3;
        // Loop to move downwards vertical up and to the left or right
        for (int offset = -1; offset < 2; offset += 2) {
            int newX = positionX + offset;

            if (Board.isValidMove(newX, downwardsY)) {
                if (!gameBoard[newX][downwardsY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, downwardsY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int rightsideX = positionX + 3;
        // Loop to move rightside horizontally and up or down
        for (int offset = -1; offset < 2; offset += 2) {
            int newY = positionY - offset;

            if (Board.isValidMove(rightsideX, newY)) {
                if (!gameBoard[rightsideX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(rightsideX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        int leftsideX = positionX - 3;
        // Loop to move leftside horizontally and up or down
        for (int offset = -1; offset < 2; offset += 2) {
            int newY = positionY - offset;

            if (Board.isValidMove(rightsideX, newY)) {
                if (!gameBoard[rightsideX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(rightsideX, newY);
                    possibleMoves.add(coordinates);
                }
            }
        }

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack.equals("tank")){
            return damage - 5;
        }
        return damage;
    }
}
