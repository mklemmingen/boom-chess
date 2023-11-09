package com.boomchess.game.backend;

import com.boomchess.game.backend.subsoldier.*;

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
        * It tries to move a soldier to a position where the overall damage to the opponent is maximised
        * while minimising the damage to itself and while clustering the soldiers together
        *
        * We use the MoveEvaluation class to evaluate the moves and get the max Score move
         */

        Soldier[][] gameBoard = Board.getGameBoard();

        int numRows = 8;
        int numColumns = 9;

        // these values hold the current max score, the coordinates of the Soldier that has the max score,
        // and the coordinates of the move that has the max score
        int maxScore = 0;
        Coordinates maxScoreSoldier = new Coordinates();
        Coordinates maxScoreMove = new Coordinates();

        // -----------------------------------------------------------------------------------
        // Get the location of the enemy general
        int enemyGeneralX = 0;
        int enemyGeneralY = 0;
        int friendlyGeneralX = 0;
        int friendlyGeneralY = 0;

        // loop through gameBoard for enemy general
        for (int i = 0; i < 9; i++) { // X Coordinate Loop
            for (int j = 0; j < 8; j++) { // Y coordinate loop
                if (gameBoard[i][j] instanceof General) {
                    if (gameBoard[i][j].getTeamColor().equals("green")) {
                        enemyGeneralX = i;
                        enemyGeneralY = j;
                    } else {
                        friendlyGeneralX = i;
                        friendlyGeneralY = j;
                    }
                }
            }

        }

        // logic for going through the 2DArray
        // loop through all the soldiers on the board and get their possible moves
        // loop through all the soldiers on the board and get their possible moves
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (gameBoard[i][j].getTeamColor().equals("red")){
                    // create List of all Moves
                    ArrayList<Coordinates> moves = gameBoard[i][j].mathMove(i, j);
                    // loop through the created list
                    // for each move, evaluate the move
                    for(Coordinates move : moves){

                        int moveX = move.getX();
                        int moveY = move.getY();

                        int score;
                        score = DrMoveJudge.evaluateMove(i, j, moveX, moveY, enemyGeneralX, enemyGeneralY,
                                friendlyGeneralX, friendlyGeneralY);

                        // if the move has a higher score than the current max score, update the max score
                        // and the Coordinates of the Soldier and the move

                        if (score > maxScore){
                            maxScore = score;
                            maxScoreSoldier.setCoordinates(i, j);
                            maxScoreMove.setCoordinates(moveX, moveY);
                        }
                    }
                }
            }
        }

        int SX = maxScoreSoldier.getX();
        int SY = maxScoreSoldier.getY();
        int x = maxScoreMove.getX();
        int y = maxScoreMove.getY();

        moveSoldierTo(SX, SY, x, y);
    }

    public static void hardBotMove(){
        /*
        * Expands the mediumBot by adding a pathfinding as well as
        * a more complex damage calculation by including the type of opponent and exact max possible damage,
        * as well as no restrictions for war dog and commando movement
         */

        // TODO ADD PATHFINDING TO DISTINGUISH FROM MEDIUM BOT

        Soldier[][] gameBoard = Board.getGameBoard();

        int numRows = 8;
        int numColumns = 9;

        // these values hold the current max score, the coordinates of the Soldier that has the max score,
        // and the coordinates of the move that has the max score
        int maxScore = 0;
        Coordinates maxScoreSoldier = new Coordinates();
        Coordinates maxScoreMove = new Coordinates();

        // -----------------------------------------------------------------------------------
        // Get the location of the enemy general
        int enemyGeneralX = 0;
        int enemyGeneralY = 0;
        int friendlyGeneralX = 0;
        int friendlyGeneralY = 0;

        // loop through gameBoard for enemy general
        for (int i = 0; i < 9; i++) { // X Coordinate Loop
            for (int j = 0; j < 8; j++) { // Y coordinate loop
                if (gameBoard[i][j] instanceof General) {
                    if (gameBoard[i][j].getTeamColor().equals("green")) {
                        enemyGeneralX = i;
                        enemyGeneralY = j;
                    } else {
                        friendlyGeneralX = i;
                        friendlyGeneralY = j;
                    }
                }
            }

        }

        // logic for going through the 2DArray
        // loop through all the soldiers on the board and get their possible moves
        // loop through all the soldiers on the board and get their possible moves
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (gameBoard[i][j].getTeamColor().equals("red")){
                    // create List of all Moves
                    ArrayList<Coordinates> moves = gameBoard[i][j].mathMove(i, j);
                    // loop through the created list
                    // for each move, evaluate the move
                    for(Coordinates move : moves){

                        int moveX = move.getX();
                        int moveY = move.getY();

                        int score;
                        score = DrMoveJudge.evaluateMove(i, j, moveX, moveY, enemyGeneralX, enemyGeneralY,
                                friendlyGeneralX, friendlyGeneralY);

                        // if the move has a higher score than the current max score, update the max score
                        // and the Coordinates of the Soldier and the move

                        if (score > maxScore){
                            maxScore = score;
                            maxScoreSoldier.setCoordinates(i, j);
                            maxScoreMove.setCoordinates(moveX, moveY);
                        }
                    }
                }
            }
        }

        int SX = maxScoreSoldier.getX();
        int SY = maxScoreSoldier.getY();
        int x = maxScoreMove.getX();
        int y = maxScoreMove.getY();

        moveSoldierTo(SX, SY, x, y);
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
