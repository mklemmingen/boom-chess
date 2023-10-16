package com.boomchess.game;

import java.util.ArrayList;

public class Wardog {
    /*
     * Wardog.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.
    */

    public static int getHealth(){
        return 40;
    }

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // damage: 10-20
        // advantages: +5 to infantry
        // disadvantages: -5 to tank

        return 0;
    }

    public static ArrayList<Coordinates> mathMove(Soldier[][] gameBoard, int positionX, int positionY) {
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // a war dog can move diagonally in all directions

        // Loop to move diagonally up and to the right
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX + offset;
            int newY = positionY + offset;

            if (Board.isValidMove(newX, newY)) {
                if (!gameBoard[newX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally up and to the left
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX - offset;
            int newY = positionY + offset;

            if (Board.isValidMove(newX, newY)) {
                if (!gameBoard[newX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally down and to the right
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX + offset;
            int newY = positionY - offset;

            if (Board.isValidMove(newX, newY)) {
                if (!gameBoard[newX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        // Loop to move diagonally down and to the left
        for (int offset = 1; offset <= 7; offset++) {
            int newX = positionX - offset;
            int newY = positionY - offset;

            if (Board.isValidMove(newX, newY)) {
                if (!gameBoard[newX][newY].getTaken()) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, newY);
                    possibleMoves.add(coordinates);
                } else {
                    break; // Stop if the tile is not empty
                }
            } else {
                break; // Stop if it goes out of bounds
            }
        }

        return possibleMoves;
    }

    public static int defendAndBleed(int damage, String soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack.equals("infantry")){
            return damage - 5;
        }
        return damage;
    }
}
