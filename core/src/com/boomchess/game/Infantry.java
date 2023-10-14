package com.boomchess.game;

public class Infantry {
    /*
     * Infantry.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.,
     */
    public static int healthRed1 = 40;
    public static int healthRed2 = 40;
    public static int healthRed3 = 40;
    public static int healthRed4 = 40;
    public static int healthRed5 = 40;
    public static int healthRed6 = 40;
    public static int healthRed7 = 40;
    public static int healthRed8 = 40;
    public static int healthGreen1 = 40;
    public static int healthGreen2 = 40;
    public static int healthGreen3= 40;
    public static int healthGreen4 = 40;
    public static int healthGreen5 = 40;
    public static int healthGreen6 = 40;
    public static int healthGreen7 = 40;
    public static int healthGreen8 = 40;


    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 01-20 damage
        // advantages +5 to attacking helicopters

        return 0;
    }

    public static void hurt(int takenDamage, String hurtColor, String attackingSoldier, int soldierTeamID) {

        // calculate resistance to attack based on attackingSoldier
        if (attackingSoldier.equals("commando")){
            takenDamage = takenDamage - 5;
        }

        if (hurtColor.equals("red")) {
            // switch statement for all 8 soldiers
            switch (soldierTeamID) {
                case(1):
                    Infantry.healthRed1 -= takenDamage;
                    if (Infantry.healthRed1 <= 0) {
                        Damage.removeSoldier("red", "infantry", 1);
                    }
                    break;
                case(2):
                    Infantry.healthRed2 -= takenDamage;
                    if (Infantry.healthRed2 <= 0) {
                        Damage.removeSoldier("red", "infantry", 2);
                    }
                    break;
                case(3):
                    Infantry.healthRed3 -= takenDamage;
                    if (Infantry.healthRed3 <= 0) {
                        Damage.removeSoldier("red", "infantry", 3);
                    }
                    break;
                case(4):
                    Infantry.healthRed4 -= takenDamage;
                    if (Infantry.healthRed4 <= 0) {
                        Damage.removeSoldier("red", "infantry", 4);
                    }
                    break;
                case(5):
                    Infantry.healthRed5 -= takenDamage;
                    if (Infantry.healthRed5 <= 0) {
                        Damage.removeSoldier("red", "infantry", 5);
                    }
                    break;
                case(6):
                    Infantry.healthRed6 -= takenDamage;
                    if (Infantry.healthRed6 <= 0) {
                        Damage.removeSoldier("red", "infantry", 6);
                    }
                    break;
                case(7):
                    Infantry.healthRed7 -= takenDamage;
                    if (Infantry.healthRed7 <= 0) {
                        Damage.removeSoldier("red", "infantry", 7);
                    }
                    break;
                case(8):
                    Infantry.healthRed8 -= takenDamage;
                    if (Infantry.healthRed8 <= 0) {
                        Damage.removeSoldier("red", "infantry", 8);
                    }
                    break;
                }
            }
        else if (hurtColor.equals("green")) {
            // switch statement for all 8 soldiers
            switch (soldierTeamID) {
                case(1):
                    Infantry.healthGreen1 -= takenDamage;
                    if (Infantry.healthGreen1 <= 0) {
                        Damage.removeSoldier("green", "infantry", 1);
                    }
                    break;
                case(2):
                    Infantry.healthGreen2 -= takenDamage;
                    if (Infantry.healthGreen2 <= 0) {
                        Damage.removeSoldier("green", "infantry", 2);
                    }
                    break;
                case(3):
                    Infantry.healthGreen3 -= takenDamage;
                    if (Infantry.healthGreen3 <= 0) {
                        Damage.removeSoldier("green", "infantry", 3);
                    }
                    break;
                case(4):
                    Infantry.healthGreen4 -= takenDamage;
                    if (Infantry.healthGreen4 <= 0) {
                        Damage.removeSoldier("green", "infantry", 4);
                    }
                    break;
                case(5):
                    Infantry.healthGreen5 -= takenDamage;
                    if (Infantry.healthGreen5 <= 0) {
                        Damage.removeSoldier("green", "infantry", 5);
                    }
                    break;
                case(6):
                    Infantry.healthGreen6 -= takenDamage;
                    if (Infantry.healthGreen6 <= 0) {
                        Damage.removeSoldier("green", "infantry", 6);
                    }
                    break;
                case(7):
                    Infantry.healthGreen7 -= takenDamage;
                    if (Infantry.healthGreen7 <= 0) {
                        Damage.removeSoldier("green", "infantry", 7);
                    }
                    break;
                case(8):
                    Infantry.healthGreen8 -= takenDamage;
                    if (Infantry.healthGreen8 <= 0) {
                        Damage.removeSoldier("green", "infantry", 8);
                    }
                    break;
                }
            }
        else {
            System.out.println("Error: error hurtColor in parameters of hurt method in Infantry.java");
        }
    }

    public static void defaultHealth() {
        // resets health to initial amount
        Infantry.healthRed1 = 40;
        Infantry.healthRed2 = 40;
        Infantry.healthRed3 = 40;
        Infantry.healthRed4 = 40;
        Infantry.healthRed5 = 40;
        Infantry.healthRed6 = 40;
        Infantry.healthRed7 = 40;
        Infantry.healthRed8 = 40;
        Infantry.healthGreen1 = 40;
        Infantry.healthGreen2 = 40;
        Infantry.healthGreen3= 40;
        Infantry.healthGreen4 = 40;
        Infantry.healthGreen5 = 40;
        Infantry.healthGreen6 = 40;
        Infantry.healthGreen7 = 40;
        Infantry.healthGreen8 = 40;
    }
}
