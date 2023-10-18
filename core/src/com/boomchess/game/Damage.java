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

    public void checkSurroundings(int positionX, int positionY){

        Soldier[][] gameBoard = Board.getGameBoard();
        // this method checks the surroundings of the piece that is attacking. if there is an enemy piece in any,
        // it calls the makeBigBoom method to deal damage to it.
        // the method takes the gameBoard and the position of the attacking piece.

        // from the position value into the gameBoard Array, we gain the information of the attacking piece.
        // this information is used to determine the soldierType of the attacking piece and its teamColor.
        // we put these information into different variables called:
        // String soldierAttack, String attackColor -> for attacker.

        String soldierAttack = gameBoard[positionX][positionY].getSoldierType();
        String attackColor = gameBoard[positionX][positionY].getTeamColor();

        // the following if statements check the surroundings of the attacking piece
        // the board has the size 8x8. to calculate the surrounding tiles position,
        // we mathematically add or subtract 1 or 10 to the current position.
        //              a  b   c
        //              d  x   e
        //              f  g   h

        // TODO UPDATE WITH COORDINATES CLASS

        // for loop that does -1 0 and +1 onto the x coordinate
        // for loop that does -1 0 and +1 onto the y coordinate
        // in total, we will have 9 iterations

        //   we need to check if the tile is occupied by anything before putting it in the array

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {

                // We do not skip the case where xOffset and yOffset are both 0 (the current position), since
                // it would take more computations doing that each loop that it takes to just check if the
                // tile is occupied and of the same colour

                int newX = positionX + xOffset;
                int newY = positionY + yOffset;

                if (gameBoard[newX][newY].getTaken()) {
                    String hurtColor = gameBoard[newX][newY].getTeamColor();
                    if (!hurtColor.equals(attackColor)) {
                        dealBigBoom(positionX, positionY, newX, newY);
                    }
                }
            }
        }

        /*
        // turn positionX and positionY into a single String, then turn into integer position
        int position = Integer.parseInt(String.valueOf(positionX + positionY));

        // the following if statements check the surroundings of the attacking piece

        int a = position - 11;
        if (a < 0) {
            a = -1;
        }
        int b = position - 10;
        if (b < 0) {
            b = -1;
        }
        int c = position - 9;
        if (c < 0) {
            c = -1;
        }
        int d = position - 1;
        if (d < 0) {
            d = -1;
        }
        int e = position + 1;
        if (e > 79) {
            e = -1;
        }
        int f = position + 9;
        if (f > 79) {
            f = -1;
        }
        int g = position + 10;
        if (g > 79) {
            g = -1;
        }
        int h = position + 11;
        if (h > 79) {
            h = -1;
        }

        // we go through each of these position values by using a for loop and check if
        // they are occupied by an enemy soldier.
        // if yes, we collect the enemy soldiers information and call upon the Damage.makeBigBoom method
        // to calculate and deal the damage to the enemy soldier.

        int[] surrPositions = {a, b, c, d, e, f, g, h};

        // for loop through surrPositions to turn each Position Value into String, split it into two variables,
        // turn variables into int and then use both int variables into the gameBoard Array to check getTaken for True,
        // then getTeamColor and if enemy team, get String soldierDefend, String hurtColor, int soldierDefTeamID,
        // and call makeBigBoom with these variables.

        for (int i = 0; i < surrPositions.length; i++) {
            if (surrPositions[i] != -1) {
                String positionString = String.valueOf(surrPositions[i]);
                String positionXString = positionString.substring(0, 1);
                String positionYString = positionString.substring(1, 2);
                int positionXInt = Integer.parseInt(positionXString);
                int positionYInt = Integer.parseInt(positionYString);
                if (gameBoard[positionXInt][positionYInt].getTaken()) {
                    String hurtColor = gameBoard[positionXInt][positionYInt].getTeamColor();
                    if (!hurtColor.equals(attackColor)) {
                        dealBigBoom(positionX, positionY, positionXInt, positionYInt, gameBoard);
                    }
                }
            }
        }
        */
    }


    public void dealBigBoom(int positionAttX, int positionAttY, int positionDefX, int positionDefY) {
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

    public void damagePiece(int damage, int positionAttX, int positionAttY,
                            int positionDefX, int positionDefY){
        Soldier[][] gameBoard = Board.getGameBoard();
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
        if (currentHealth <= 0) {
            killPiece(positionDefX, positionDefY);
        }
    }

    private void killPiece(int positionX, int positionY) {
        // this method is called when a soldier is killed
        // it empties the tile the soldier was standing on
        // and sets the taken boolean to false

        Soldier[][] gameBoard = Board.getGameBoard();

        // we use this int-array x and y position to set the tile to empty.

        gameBoard[positionX][positionY].setTaken(false);
        gameBoard[positionX][positionY].setSoldierType("empty");
        gameBoard[positionX][positionY].setTeamColor("none");
        gameBoard[positionX][positionY].setPieceID(0);
    }
}
