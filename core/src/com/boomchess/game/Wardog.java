package com.boomchess.game;

import java.util.Objects;

public class Wardog {
    /*
     * Wardog.java is the object for the chess piece General in the game Boom Chess.
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
        if (attackingSoldier.equals("infantry")){
            takenDamage = takenDamage - 5;
        }

        if (Objects.equals(hurtColor, "red")) {
            if (soldierTeamID == 1) {
                Wardog.healthRed1 -= takenDamage;
                if (Wardog.healthRed1 <= 0) {
                    Damage.removeSoldier("red", "wardog", 1);
                }
            }
            else {
                Wardog.healthRed2 -= takenDamage;
                if (Wardog.healthRed2 <= 0) {
                    Damage.removeSoldier("red", "wardog", 2);
                }
            }
        }
        else if (Objects.equals(hurtColor, "green")){
            if (soldierTeamID == 1) {
                Wardog.healthGreen1 -= takenDamage;
                if (Wardog.healthGreen1 <= 0) {
                    Damage.removeSoldier("green", "wardog", 1);
                }
            }
            else {
                Wardog.healthGreen2 -= takenDamage;
                if (Wardog.healthGreen2 <= 0) {
                    Damage.removeSoldier("green", "wardog", 2);
                }
            }
        }
        else {
            System.out.println("Error: error in parameters of hurt method in Wardog.java");
        }
    }
    public static void defaultHealth() {
        Wardog.healthRed1 = 50;
        Wardog.healthGreen1 = 50;
        Wardog.healthRed2 = 50;
        Wardog.healthGreen2 = 50;
    }
}
