package com.boomchess.game;

// import the General, Helicopter, Infantry, Tank, Wardog Classes of other files

import com.badlogic.gdx.Gdx;

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

        int startX = Math.max(0, x - 1); // Ensures no out of bounds on the left/upside
        int endX = Math.min(8, x + 1);   // Ensures no out of bounds on the right/downside

        int startY = Math.max(0, y - 1);
        int endY = Math.min(7, y + 1);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (gameBoard[i][j].getTaken()) {
                    String hurtColor = gameBoard[i][j].getTeamColor();
                    if (!hurtColor.equals(attackColor)) {
                        dealBigBoom(x, y, i, j);
                    }
                }
            }
        }
    }


    public static void dealBigBoom(int positionAttX, int positionAttY, int positionDefX, int positionDefY) {
        Soldier[][] gameBoard = Board.getGameBoard();
        int damage = 0;
        // switch statement on the type of piece taking damage
        String soldierAttack = gameBoard[positionAttX][positionAttY].getSoldierType();
        String soldierDefend = gameBoard[positionDefX][positionDefY].getSoldierType();
        switch (soldierAttack) {
            case "general":
                damage = General.calculateDamage(soldierDefend);
                break;
            case "infantry":
                damage = Infantry.calculateDamage(soldierDefend);
                break;
            case "tank":
                damage = Tank.calculateDamage(soldierDefend);
                break;
            case "wardog":
                damage = Wardog.calculateDamage(soldierDefend);
                break;
            case "helicopter":
                damage = Helicopter.calculateDamage(soldierDefend);
                break;
            case "commando":
                damage = Commando.calculateDamage(soldierDefend);
                break;
            default:
                // if none of the switch cases are true, this error gets triggered and the game exits
                System.out.println("Error: Invalid soldier type. Bug in Damage.java or Soldier-Objects \n" +
                        "or check if a soldiers name was written incorrectly like 'Infantry' instead of 'infantry'");
                // for exiting the game
                Gdx.app.exit();
                // for ending all background activity on Windows systems specifically
                System.exit(0);
        }
        damagePiece(damage, positionAttX, positionAttY, positionDefX, positionDefY);
    }

    public static void damagePiece(int damage, int positionAttX, int positionAttY,
                                   int positionDefX, int positionDefY){
        Soldier[][] gameBoard = Board.getGameBoard();

        // drawing the dotted line from the attacking piece to the defending piece
        BoomChess.addDottedLine((float) positionAttX, (float) positionAttY, (float) positionDefX, (float) positionDefY);
        System.out.println("\nThe dotted line has been drawn");

        // switch statement on the type of piece taking damage
        String soldierAttack = gameBoard[positionAttX][positionAttY].getSoldierType();
        String soldierDefend = gameBoard[positionDefX][positionDefY].getSoldierType();
        int currentHealth = gameBoard[positionDefX][positionDefY].getHealth();
        switch (soldierDefend) {
            case "general":
                currentHealth -= General.defendAndBleed(damage, soldierAttack);
                if (currentHealth <= 0) {
                    String attackerColor = gameBoard[positionAttX][positionAttY].getTeamColor();
                    BoomChess.createGameEndStage(attackerColor);
                }
                break;
            case "infantry":
                currentHealth -= Infantry.defendAndBleed(damage, soldierAttack);
                break;
            case "tank":
                currentHealth -= Tank.defendAndBleed(damage, soldierAttack);
                break;
            case "wardog":
                currentHealth -= Wardog.defendAndBleed(damage, soldierAttack);
                break;
            case "helicopter":
                currentHealth -= Helicopter.defendAndBleed(damage, soldierAttack);
                break;
            case "commando":
                currentHealth -= Commando.defendAndBleed(damage, soldierAttack);
                break;
            default:
                System.out.println("Error: Invalid soldier type. Bug in Damage.hurtPiece\n" +
                        "check if a soldiers name was written incorrectly like 'Infantry' instead of 'infantry'");
                // for exiting the game
                Gdx.app.exit();
                // for ending all background activity on Windows systems specifically
                System.exit(0);
        }
        // put currentHealth as new setHealth() onto the gameBoard Soldier object
        gameBoard[positionDefX][positionDefY].setHealth(currentHealth);
        if (currentHealth <= 0) {
            killPiece(positionDefX, positionDefY);
        }

    }

    private static void killPiece(int positionX, int positionY) {
        // this method is called when a soldier is killed
        // it empties the tile the soldier was standing on
        // and sets the taken boolean to false

        Soldier[][] gameBoard = Board.getGameBoard();

        // play the boom sound when a piece dies
        BoomChess.boom.play();
        System.out.println("\nBoom! A piece has died");

        BoomChess.addDeathAnimation(positionX, positionY);
        System.out.print("\nDeath animation has been added on the corpse! Oh no!");

        // we use this int-array x and y position to set the tile to empty.

        gameBoard[positionX][positionY].setTaken(false);
        gameBoard[positionX][positionY].setSoldierType("empty");
        gameBoard[positionX][positionY].setTeamColor("none");
        gameBoard[positionX][positionY].setPieceID(0);
        gameBoard[positionX][positionY].setHealth(-1);

        // TODO FIND LESS INVASIVE METHOD OF REFRESHING THE PIECE DRAWINGS
        BoomChess.reRenderGame();
    }
}
