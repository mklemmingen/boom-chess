package com.boomchess.game.backend;

public class BOT {
    /*
    * BOT.java is the object for the bot move calculations by difficulty in the game Boom Chess.
     */

    // these variables hold the currently calculated Soldier Object, its current and future coordinates
    private static Soldier soldier;
    private static int SX;
    private static int SY;
    private static int x;
    private static int y;

    public static void easyBotMove(){
        /*
        * this method completes a random move for the bot
        * it gives out a random move for the bot, trying to move all soldiers as close as possible to the enemy general
         */


        moveSoldierTo(SX, SY, x, y);
    }

    public static void mediumBotMove(){
        /*
        * this method completes a random move for the bot
        * It tries to move a soldier to a position where the overall damage to the opponent is maximised
         */


        moveSoldierTo(SX, SY, x, y);
    }

    public static void hardBotMove(){
        /*
        * this method completes a random move for the bot
        * It tries to move all soldiers as close as possible to the general while maximising
        * the overall damage to the opponent
         */

        // Pathfinding? for obstacles


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
