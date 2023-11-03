package com.boomchess.game.backend;

import java.util.*;

import static com.boomchess.game.BoomChess.batch;

public class BOT {
    /*
    * BOT.java is the object for the bot move calculations by difficulty in the game Boom Chess.
     */


    public static void easyBotMove(){
        /*
        * this method completes a random move for the bot
        * it gives out a random move for the bot, trying to move all soldiers as close as possible to the enemy general
         */

        Soldier[][] gameBoard = Board.getGameBoard();

        Map<Coordinates, ArrayList<Coordinates>> possibleMovesMap = new HashMap<>();

        // we iterate over the chessboard and add a possiblemove arraylist to all soldier coordinates
        // if soldier red and any moves possible

        int numRows = 8;
        int numColumns = 9;

        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numColumns; i++) {
                if (gameBoard[i][j].getTeamColor().equals("red")){
                    Coordinates currentPos = new Coordinates();
                    currentPos.setCoordinates(i, j);
                    ArrayList<Coordinates> moves = gameBoard[i][j].mathMove(i, j);
                    if (moves.size() > 0) {
                        possibleMovesMap.put(currentPos, moves);
                    }
                }
            }
        }

        Random random = new Random(); // added instance of random
        Set<Coordinates> keys = possibleMovesMap.keySet();
        int max = keys.size();

        // Generate the random int
        int randomNum = random.nextInt(max);

        // Convert key to list
        List<Coordinates> keyList = new ArrayList<>(keys);

        Coordinates soldierPos = new Coordinates();
        // select key with randomNum
        for (int m = 0; m<max; m++){
            if(m == randomNum){
                soldierPos = keyList.get(m);
                break;
            }
        }

        // create SX and SY out of random Soldier
        int SX = soldierPos.getX();
        int SY = soldierPos.getY();

        ArrayList<Coordinates> possibleMoves = possibleMovesMap.get(soldierPos);
        Coordinates finalPos = new Coordinates();

        // new Random
        int intRandom = random.nextInt(possibleMoves.size());
        possibleMoves.set(intRandom, finalPos);

        // x and y
        int x = finalPos.getX();
        int y = finalPos.getY();

        moveSoldierTo(SX, SY, x, y);
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
