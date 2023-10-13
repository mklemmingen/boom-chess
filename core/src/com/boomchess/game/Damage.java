package com.boomchess.game;

// import the General, Helicopter, Infantry, Tank, Wardog Classes of other files

import com.badlogic.gdx.Gdx;

public class Damage {

    /*
    The way this class works is that it main function calculateDamage takes in the type of soldier attacking,
    the type of soldier defending, the color of the soldier defending and its ID.
    Then this method calls the hurt method of the defending soldier and deals damage to it.
    The hurt method of the defending soldier then subtracts the damage dealt from the health of the defending soldier.
    If that health drops below zero, the defending soldier is removed from the board.
    This is done by nesting these methods - only needing a call on calculateDamage.
    The properties of each kind of BoomChess Piece can be individually changed in their respective files.
     */

    public void makeBigBoom(String soldierAttack, String soldierDefend, String hurtColor, int soldierDefTeamID) {
        int damage = 0;
        // switch statement on the type of piece taking damage
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
        hurtPiece(damage, hurtColor, soldierAttack, soldierDefend, soldierDefTeamID);
    }

    public void hurtPiece(int takenDamage, String hurtColor,
                          String soldierAttack, String soldierDefend, int soldierDefTeamID) {

        // switch statement on the type of piece taking damage
        switch (soldierDefend) {
            case "general":
                General.hurt(takenDamage, hurtColor, soldierAttack, soldierDefTeamID);
                break;
            case "infantry":
                Infantry.hurt(takenDamage, hurtColor, soldierAttack, soldierDefTeamID);
                break;
            case "tank":
                Tank.hurt(takenDamage, hurtColor, soldierAttack, soldierDefTeamID);
                break;
            case "wardog":
                Wardog.hurt(takenDamage, hurtColor, soldierAttack, soldierDefTeamID);
                break;
            case "helicopter":
                Helicopter.hurt(takenDamage, hurtColor, soldierAttack, soldierDefTeamID);
                break;
            default:
                System.out.println("Error: Invalid soldier type. Bug in Damage.hurtPiece\n" +
                        "check if a soldiers name was written incorrectly like 'Infantry' instead of 'infantry'");
                // for exiting the game
                Gdx.app.exit();
                // for ending all background activity on Windows systems specifically
                System.exit(0);
        }
    }
    public static void defaultHealth() {
        General.defaultHealth();
        Helicopter.defaultHealth();
        Infantry.defaultHealth();
        Tank.defaultHealth();
        Wardog.defaultHealth();
    }

    public static void removeSoldier(String red, String helicopter, int i) {

    }
}
