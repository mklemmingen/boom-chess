package com.boomchess.game;

import java.util.ArrayList;

public class Wardog {
    /*
     * Wardog.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
    */

    public static int getHealth(){
        return 40;
    }

    public static int calculateDamage(String soldierDefend) {

        // damage: 5-20
        // advantages: +5 to infantry
        // disadvantages: -5 to tank

        int minValue = 5;
        int maxValue = 20;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend.equals("tank")){
            randomDamage -= 5;
        } else if (soldierDefend.equals("infantry")){
            randomDamage += 5;
        }

        BoomChess.wardogSound.play();

        return randomDamage;
    }

    public static ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
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

        System.out.println("Wardog defendAndBleed: " + damage);
        return damage;
    }
}
