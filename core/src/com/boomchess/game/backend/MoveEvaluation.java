package com.boomchess.game.backend;

import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.Hill;

public class MoveEvaluation {

    public static int evaluateMove(Soldier[][] board, int X, int Y) {
        int score = 0;

        // Check proximity to enemy pieces after the move
        int proximityScore = calculateProximityScore(board, X, Y);
        score += proximityScore;

        // Estimate potential damage
        int damageScore = calculateDamageScore(board, X, Y);
        score += damageScore;

        // Consider clustering bonus
        int clusteringScore = calculateClusteringScore(board, X, Y);
        score += clusteringScore;

        // Consider the amount of damage the piece might take, gets subtracted from the score
        int hurtValue = calculateHurtValue(board, X, Y);
        score -= hurtValue;

        return score;
    }

    private static int calculateProximityScore(Soldier[][] board, int X, int Y) {
        int funcScore = 0;

        int generalX = BOT.generalPos.getX();
        int generalY = BOT.generalPos.getY();

        // Implementation to calculate the score based on proximity to enemy pieces by relativity to enemy general
        // and the columns of the board
        // if the piece is close to enemy pieces by general column, it is good
        // if the piece is close to enemy general, that is extra points
        // the more left the red piece is (x value is lower), the better, for game attack progression

        // per int below 8, the score is increased by 10
        int addThis = X - 8;
        funcScore += addThis * 5;

        // distance is the current reach of the search. 1 is the closest tiles, 2 adds one more, 3 ....
        int distance = 2;

        int startX = Math.max(0, X - distance);
        int endX = Math.min(7, X + distance);

        int startY = Math.max(0, Y - distance);
        int endY = Math.min(7, Y + distance);

        // variable for holding the current distance that is checked in the loop to the current Piece
        int curDistCheck;

        for (int i = startX; i <= endX; i++) { // X Coordinate Loop

            // nested if for deciding the distance to check
            if (i == X){
                curDistCheck = 1;
                // if we are on the same row as the piece, we only check the tiles above and below
            } else if (i == X - 1 || i == X + 1) {
                curDistCheck = 1;
                // if we are on the row above or below, we only check the tiles above and below (curDistCheck = 1
            } else {
                curDistCheck = 2;
                // otherwise we check the tiles to the distance of 2
            }

            for (int j = startY; j <= endY; j++) { // Y coordinate loop

                if (i == X && j == Y) continue; // Skip on checking the original piece
                if (!(board[i][j] instanceof Empty)){
                    if (!(board[i][j] instanceof Hill)){
                        String currColour = board[i][j].getTeamColor();
                        // if statement depending on if we want to know about bot team (red) or enemy team
                        if (currColour.equals("green")) {
                            // add or subtract to score depending on if the piece is close to the enemy general
                            // and how close to a piece in general: add 2 if in close tile, add 4 if in tile above that

                            if (curDistCheck == 1) {
                                if (i == generalX && j == generalY) { // checking for general proxy
                                    funcScore += 50; // attack, attack, attack!
                                }
                                funcScore += 4;
                            } else {
                                if (board[X][Y] instanceof Artillery) { // checking for general proxy
                                    if (i == generalX && j == generalY) {
                                        funcScore += 50; // attack, attack, attack!
                                    }
                                    // adding additional score if Artillery, so Artillery moves more to 2 tiles away
                                    funcScore += 20;
                                }
                                funcScore += 2;
                            }
                        }
                    }
                }
            }
        }
        return funcScore;
    }

    private static int calculateDamageScore(Soldier[][] board, int X, int Y) {
        // Implementation to calculate the score based on potential damage
        // using the minimal damage dealt by the piece



        return 0; // Placeholder
    }

    private static int calculateClusteringScore(Soldier[][] board, int X, int Y) {
        // Implementation to calculate the score based on clustering of pieces
        // if the piece is clustered with other pieces of the same team, it is good
        // if the piece is close to its general, that is extra points



        return 0; // Placeholder
    }

    private static int calculateHurtValue(Soldier[][] board, int X, int Y){
        // Implementation to calculate the score based on the possible decrease in health of a piece
        // meaning: if the piece is close to good enemy pieces, it is bad
        // if the piece is the friendly general, it is dreadful (the general is the most important piece)



        return 0; // Placeholder
    }

    /* For Surrounding Looping:
        // distance is the current reach of the search. 1 is the closest tiles, 2 adds one more, 3 ....

        int startX = Math.max(0, x - distance);
        int endX = Math.min(7, x + distance);

        int startY = Math.max(0, y - distance);
        int endY = Math.min(7, y + distance);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (i == x && j == y) continue; // Skip on checking the original piece
                if (!(gameBoard[i][j] instanceof Empty)){
                    if (!(gameBoard[i][j] instanceof Hill)){
                            String currColour = gameBoard[i][j].getTeamColor();
                        // if statement depending on if we want to know about bot team (red) or enemy team
                        if (currColour.equals("green")) {
                            // add or subtract to score
                        }
                    }
                }
            }
        }

     */
}
