package com.boomchess.game.backend;

import com.boomchess.game.BoomChess;

import java.util.ArrayList;

public class Tank extends Soldier{
    /*
     * Tank.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, mathMove,
     * the special damage it can deal calculateDamage,
     * and the initial health. getHealth
     */

    public Tank(String teamColor) {
        /*
         * Constructor for the Tank object, takes positional arguments and team color
         */
        super(teamColor, 50);
    }

    public static int calculateDamage(Soldier soldierDefend) {

        // deals 10-20 damage
        // advantages: deals +5 to infantry
        // disadvantages: deals -5 to wardogs

        int minValue = 10;
        int maxValue = 20;

        // we achieve this randomisation using random.Math`s floor and random methods
        // that generate a random number between 0 and 1 that we multiply
        int randomDamage = (int) (minValue + Math.floor((maxValue - minValue + 1) * Math.random()));

        if(soldierDefend.equals("infantry")){
            randomDamage += 5;
        } else if (soldierDefend.equals("wardog")){
            randomDamage -= 5;
        }

        BoomChess.tankSound.play(BoomChess.soundVolume);

        return randomDamage;
    }

    @Override
    public ArrayList<Coordinates> mathMove(int positionX, int positionY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        // this method returns a Coordinates array of all possible move-location for this soldier
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        // a tank can move horizontally and vertically in all directions

        // Loop to move upwards
        for (int yOffset = 1; yOffset <= 7; yOffset++) {
            int newY = positionY + yOffset;

            if (Board.isValidMove(positionX, newY)) {
                if (!(gameBoard[positionX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(positionX, newY);
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
            int newY = positionY - yOffset;

            if (Board.isValidMove(positionX, newY)) {
                if (!(gameBoard[positionX][newY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(positionX, newY);
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

            if (Board.isValidMove(newX, positionY)) {
                if (!(gameBoard[newX][positionY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, positionY);
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

            if (Board.isValidMove(newX, positionY)) {
                if (!(gameBoard[newX][positionY] instanceof Empty)) {
                    Coordinates coordinates = new Coordinates();
                    coordinates.setCoordinates(newX, positionY);
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

    public static int defendAndBleed(int damage, Soldier soldierAttack) {
        // calculate resistance to attack based on attackingSoldier
        if (soldierAttack instanceof Wardog){
            return damage - 5;
        }
        System.out.println("The tank has been damaged for " + damage + " points by " + soldierAttack + ".");
        return damage;
    }
}