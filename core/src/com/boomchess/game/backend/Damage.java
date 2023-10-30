package com.boomchess.game.backend;

// import the General, Helicopter, Infantry, Tank, Wardog Classes of other files

import com.boomchess.game.BoomChess;
import com.boomchess.game.backend.subsoldier.Artillery;
import com.boomchess.game.backend.subsoldier.Empty;
import com.boomchess.game.backend.subsoldier.General;
import com.boomchess.game.backend.subsoldier.Hill;
import com.boomchess.game.frontend.stage.GameStage;


public class Damage {

    /*
    The way this class works is that it main function makeBigBoom takes in the type of soldier attacking,
    the type of soldier defending, the color of the soldier defending and its ID.
    Then this method calls the hurt method of the defending soldier and deals damage to it.
    The hurt method of the defending soldier then subtracts the damage dealt from the health of the defending soldier.
    If that health drops below zero, the defending soldier is removed from the board.
    This is done by nesting these methods - only needing a call on calculateDamage.
    The properties of each kind of BoomChess Piece can be individually changed in their respective files.
     */

    public static void checkSurroundings(int x, int y){

        Soldier[][] gameBoard = Board.getGameBoard();
        // this method checks the surroundings of the piece that is attacking. if there is an enemy piece in any,
        // it calls the makeBigBoom method to deal damage to it.
        // the method takes the gameBoard and the position of the attacking piece.

        // from the position value into the gameBoard Array, we gain the information of the attacking piece.
        // this information is used to determine the soldierType of the attacking piece and its teamColor.
        // we put these information into different variables called:
        // String soldierAttack, String attackColor -> for attacker.

        String attackColor = gameBoard[x][y].getTeamColor();

        // the following if statements check the surroundings of the attacking piece
        // the board has the size 8x8. to calculate the surrounding tiles position,
        // we mathematically add or subtract 1 or 10 to the current position.
        //              a  b   c
        //              d  x   e
        //              f  g   h

        // for loop that does -1 0 and +1 onto the x coordinate
        // for loop that does -1 0 and +1 onto the y coordinate
        // in total, we will have 9 iterations

        //   we need to check if the tile is occupied by anything before putting it in the array

        // if the piece is an artillery however, we check the 3 tiles surrounding it in each direction instead
        // this is done in the distance variable that gets changed from 1 to 3 if an artillery is checked
        int distance = gameBoard[x][y] instanceof Artillery ? 2 : 1;

        int startX = Math.max(0, x - distance);
        int endX = Math.min(7, x + distance);

        int startY = Math.max(0, y - distance);
        int endY = Math.min(7, y + distance);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (i == x && j == y) continue; // Skip on checking the original piece
                if (!(gameBoard[i][j] instanceof Empty)){
                    if (!(gameBoard[i][j] instanceof Hill)){
                            String hurtColor = gameBoard[i][j].getTeamColor();
                        if (!hurtColor.equals(attackColor)) {
                            dealBigBoom(x, y, i, j);
                        }
                    }
                }
            }
        }
    }

    public static void dealBigBoom(int positionAttX, int positionAttY, int positionDefX, int positionDefY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        int damage = 0;


        Soldier soldierDefend = gameBoard[positionDefX][positionDefY];
        Soldier soldierAttack = gameBoard[positionAttX][positionAttY];

        // avoids runtime type checks and casts, ensure each soldier object has this method first
        if (soldierAttack instanceof calculateDamageInterface) {
            damage = ((calculateDamageInterface) soldierAttack).calculateDamage(soldierDefend);
        } else {
            System.out.println("The attacking piece is not a calculateDamageInterface");
        }

        damagePiece(damage, positionAttX, positionAttY, positionDefX, positionDefY);
    }

    public static void damagePiece(int damage, int positionAttX, int positionAttY,
                                   int positionDefX, int positionDefY){
        Soldier[][] gameBoard = Board.getGameBoard();

        // drawing the dotted line from the attacking piece to the defending piece
        BoomChess.addDottedLine((float) positionAttX, (float) positionAttY, (float) positionDefX, (float) positionDefY);
        System.out.println("\nThe dotted line has been drawn");

        // we need to get the current health of the defending piece
        int currentHealth = gameBoard[positionDefX][positionDefY].getHealth();

        // avoids runtime type checks and casts, ensure each soldier object has this method first
        if (gameBoard[positionDefX][positionDefY] instanceof defendAndBleedInterface) {
            currentHealth -= ((defendAndBleedInterface) gameBoard[positionDefX][positionDefY])
                    .defendAndBleed(damage, gameBoard[positionAttX][positionAttY]);
        } else {
            System.out.println("The defending piece is not a defendAndBleedInterface");
        }


        // if the defending piece is a general, we need to check if it is dead
        // if it is dead, we need to end the game

        if (currentHealth <= 0) {
            if (gameBoard[positionDefX][positionDefY] instanceof General) {

                // game End by Stage
                String attackerColor = gameBoard[positionAttX][positionAttY].getTeamColor();
                BoomChess.createGameEndStage(attackerColor);
                System.out.println("A General has died!");
            }
            killPiece(positionDefX, positionDefY);
        } else {
            // set the new health of the piece to the currentHealth variable
            gameBoard[positionDefX][positionDefY].setHealth(currentHealth);
        }
    }

    private static void killPiece(int positionX, int positionY) {
        // this method is called when a soldier is killed
        // it empties the tile the soldier was standing on
        // and sets the taken boolean to false

        Soldier[][] gameBoard = Board.getGameBoard();

        // play the boom sound when a piece dies
        BoomChess.bigExplosionSound.play(BoomChess.soundVolume);
        System.out.println("\nBoom! A piece has died");

        BoomChess.addDeathAnimation(positionX, positionY);
        System.out.print("\nDeath animation has been added on the corpse! Oh no!");

        // we use this int-array x and y position to set the tile to an Empty object

        gameBoard[positionX][positionY] = new Empty("empty");
        System.out.println("\nThe tile has been set to an Empty object");

        BoomChess.switchToStage(GameStage.createGameStage(BoomChess.isBotMatch));
    }
}
