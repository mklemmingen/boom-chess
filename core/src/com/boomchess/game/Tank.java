package com.boomchess.game;

import java.util.ArrayList;

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

    public static ArrayList<Coordinates> mathMove(Soldier[][] gameBoard, int positionX, int positionY) {
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // a tank can move horizontally and vertically in all directions

        // Loop to move upwards
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            int newX = positionX;
            int newY = positionY + yOffset;

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

        // Loop to move downwards
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            int newX = positionX;
            int newY = positionY - yOffset;

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

        // Loop to move to the right
        for (int xOffset = 1; xOffset <= 7; xOffset++) {
            int newX = positionX + xOffset;
            int newY = positionY;

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

        // Loop to move to the left
        for (int xOffset = 1; xOffset <= 7; xOffset++) {
            int newX = positionX - xOffset;
            int newY = positionY;

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
        if (soldierAttack.equals("wardog")){
            return damage - 5;
        }
        return damage;
    }
}
