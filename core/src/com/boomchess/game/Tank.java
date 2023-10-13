package com.boomchess.game;

import java.util.Objects;

public class Tank {
    /*
     * Tank.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.
     */
    public static int healthRed1 = 50;
    public static int healthRed2 = 50;
    public static int healthGreen1 = 50;
    public static int healthGreen2 = 50;

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        return 0;
    }

    public static void hurt(int takenDamage, String hurtColor, String attackingSoldier, int soldierTeamID) {

        // calculate resistance to attack based on attackingSoldier
        if (attackingSoldier.equals("wardog")){
            takenDamage = takenDamage - 5;
        }

        if (Objects.equals(hurtColor, "red")) {
            if (soldierTeamID == 1) {
                Tank.healthRed1 -= takenDamage;
                if (Tank.healthRed1 <= 0) {
                    Damage.removeSoldier("red", "tank", 1);
                }
            }
            else {
                Tank.healthRed2 -= takenDamage;
                if (Tank.healthRed2 <= 0) {
                    Damage.removeSoldier("red", "tank", 2);
                }
            }
        }
        else if (Objects.equals(hurtColor, "green")){
            if (soldierTeamID == 1) {
                Tank.healthGreen1 -= takenDamage;
                if (Tank.healthGreen1 <= 0) {
                    Damage.removeSoldier("green", "tank", 1);
                }
            }
            else {
                Tank.healthGreen2 -= takenDamage;
                if (Tank.healthGreen2 <= 0) {
                    Damage.removeSoldier("green", "tank", 2);
                }
            }
        }
        else {
            System.out.println("Error: error in parameters of hurt method in Tank.java");
        }
    }
    public static void defaultHealth() {
        Tank.healthRed1 = 50;
        Tank.healthGreen1 = 50;
        Tank.healthRed2 = 50;
        Tank.healthGreen2 = 50;
    }
}
