package com.boomchess.game.backend;

import java.util.*;

public class BOT {
    /*
    * BOT.java is the object for the bot move calculations by difficulty in the game Boom Chess.
     */


    public static void easyBotMove(){
        Soldier[][] gameBoard = Board.getGameBoard();

        // create a map of all the possible moves for the bot
        Map<Coordinates, ArrayList<Coordinates>> possibleMovesMap = new HashMap<>();

        int numRows = 8;
        int numColumns = 9;

        // loop through all the soldiers on the board and get their possible moves
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (gameBoard[i][j].getTeamColor().equals("red")){
                    Coordinates currentPos = new Coordinates();
                    currentPos.setCoordinates(i, j);// using i as x and j as y
                    ArrayList<Coordinates> moves = gameBoard[i][j].mathMove(i, j);
                    if (!moves.isEmpty()) { // only if there are moves, put Soldier to map
                        possibleMovesMap.put(currentPos, moves);
                    }
                }
            }
        }

        // choose a random Soldier from the map
        Random random = new Random();
        Set<Coordinates> keys = possibleMovesMap.keySet();
        int max = keys.size();

        if (max > 0) { // Check if there are any valid moves
            // new random number for selecting the move out of the soldiers ArrayList
            int randomNum = random.nextInt(max);
            List<Coordinates> keyList = new ArrayList<>(keys);

            Coordinates soldierPos = keyList.get(randomNum);

            // create the Soldier coordinate variables
            int SX = soldierPos.getX();
            int SY = soldierPos.getY();

            ArrayList<Coordinates> possibleMoves = possibleMovesMap.get(soldierPos);

            int intRandom = random.nextInt(possibleMoves.size());
            Coordinates finalPos = possibleMoves.get(intRandom);

            // create the final coordinate variables
            int x = finalPos.getX();
            int y = finalPos.getY();

            moveSoldierTo(SX, SY, x, y);
        }
    }

    public static void mediumBotMove(){
        /*
        * this method completes a random move for the bot
        * It tries to move a soldier to a position where the overall damage to the opponent is maximised
         */


    }

    public static void hardBotMove(){
        /*
        * this method completes a random move for the bot
        * It tries to move all soldiers as close as possible to the general while maximising
        * the overall damage to the opponent
         */

        // Pathfinding? for obstacles


    }

    public static void moveSoldierTo(int SX, int SY, int x, int y){
        /*
        * this method moves a soldier to a specific position
        * it utilizes the update function in Board.java
         */
        Soldier[][] gameBoard = Board.getGameBoard();
        System.out.println("\nBot Moved" + gameBoard[SX][SY].getClass().getName() + " to " + x + " " + y + "\n");
        Board.update(SX, SY, x, y);
    }
}
