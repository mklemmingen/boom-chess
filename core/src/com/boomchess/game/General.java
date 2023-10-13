package com.boomchess.game;

import java.util.Objects;

public class General {
    /*
        * General.java is the object for the chess piece General in the game Boom Chess.
        * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
        * The method hurtGeneral lowers the current health amount any returns the new health amount.
        * The method defaultGeneral resets the health to the initial amount.
     */
    public static int healthRed = 50;
    public static int healthGreen = 50;

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        return 0;
    }

    public static void hurt(int takenDamage, String hurtColor, String attackingSoldier, int soldierTeamID) {
        // The General is very resistant and only takes half damage to anything. He likes to chill in a Bunker
        takenDamage = takenDamage/2;
        if (Objects.equals(hurtColor, "red")) {
            General.healthRed -= takenDamage;
            if (General.healthRed <= 0) {
                // TODO End Game
                //  Add Killer of the General with parameter attackingSoldier
            }
        }
        else {
            General.healthGreen -= takenDamage;
            if (General.healthGreen <= 0) {
                // TODO End Game
                //  Add Killer of the General with parameter attackingSoldier
            }
        }
    }

    public static void defaultHealth() {
        General.healthRed = 50;
        General.healthGreen = 50;
    }
}
