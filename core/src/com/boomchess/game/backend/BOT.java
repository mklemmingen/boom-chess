package com.boomchess.game.backend;

import java.util.*;

import static com.boomchess.game.BoomChess.batch;

public class BOT {
    /*
    * BOT.java is the object for the bot move calculations by difficulty in the game Boom Chess.
     */


    public static void easyBotMove(){
        Soldier[][] gameBoard = Board.getGameBoard();

        Map<Coordinates, ArrayList<Coordinates>> possibleMovesMap = new HashMap<>();

        int numRows = 8;
        int numColumns = 9;

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (gameBoard[i][j].getTeamColor().equals("red")){
                    Coordinates currentPos = new Coordinates();
                    currentPos.setCoordinates(i, j);// Initialize Coordinates directly with i, j
                    ArrayList<Coordinates> moves = gameBoard[i][j].mathMove(i, j);
                    if (moves.size() > 0) {
                        possibleMovesMap.put(currentPos, moves);
                    }
                }
            }
        }

        Random random = new Random();
        Set<Coordinates> keys = possibleMovesMap.keySet();
        int max = keys.size();

        if (max > 0) { // Check if there are any valid moves
            int randomNum = random.nextInt(max);
            List<Coordinates> keyList = new ArrayList<>(keys);

            Coordinates soldierPos = keyList.get(randomNum);

            int SX = soldierPos.getX();
            int SY = soldierPos.getY();

            ArrayList<Coordinates> possibleMoves = possibleMovesMap.get(soldierPos);

            int intRandom = random.nextInt(possibleMoves.size());
            Coordinates finalPos = possibleMoves.get(intRandom);

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
