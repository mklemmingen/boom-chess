package com.boomchess.game;

import java.util.Objects;

public class Helicopter {
    /*
     * Helicopter.java is the object for the chess piece General in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtGeneral lowers the current health amount any returns the new health amount.
     * The method defaultGeneral resets the health to the initial amount.,
     */
    public static int healthRed1 = 50;
    public static int healthRed2 = 50;
    public static int healthGreen1 = 50;
    public static int healthGreen2 = 50;

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        // deals 10-20 damage
        // advantages: deals +5 to tanks

        return 0;
    }

    public static void hurt(int takenDamage, String hurtColor, String attackingSoldier, int soldierTeamID) {

        // calculate resistance to attack based on attackingSoldier
        if (attackingSoldier.equals("tank")){
            takenDamage = takenDamage - 5;
        }

        if (Objects.equals(hurtColor, "red")) {
            if (soldierTeamID == 1) {
                Helicopter.healthRed1 -= takenDamage;
                if (Helicopter.healthRed1 <= 0) {
                    Damage.removeSoldier("red", "helicopter", 1);
                }
            }
            else {
                Helicopter.healthRed2 -= takenDamage;
                if (Helicopter.healthRed2 <= 0) {
                    Damage.removeSoldier("red", "helicopter", 2);
                }
            }
        }
        else if (Objects.equals(hurtColor, "green")){
            if (soldierTeamID == 1) {
                Helicopter.healthGreen1 -= takenDamage;
                if (Helicopter.healthGreen1 <= 0) {
                    Damage.removeSoldier("green", "helicopter", 1);
                }
            }
            else {
                Helicopter.healthGreen2 -= takenDamage;
                if (Helicopter.healthGreen2 <= 0) {
                    Damage.removeSoldier("green", "helicopter", 2);
                }
            }
        }
        else {
            System.out.println("Error: error in parameters of hurt method in Helicopter.java");
        }
    }

    public static void defaultHealth() {
        Helicopter.healthRed1 = 50;
        Helicopter.healthGreen1 = 50;
        Helicopter.healthRed2 = 50;
        Helicopter.healthGreen2 = 50;
    }
}
