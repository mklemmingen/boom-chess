package com.boomchess.game;

import java.util.Objects;

public class Commando {
    /*
     * Commando.java is the object for the chess piece Commando in the game Boom Chess.
     * It holds the specific movement patterns for this piece, the special damage it can deal and the initial health.
     * The method hurtCommando lowers the current health amount any returns the new health amount.
     * The method defaultCommando resets the health to the initial amount.
     */
    public static int healthRed = 50;
    public static int healthGreen = 50;

    public static int calculateDamage(String soldierDefend) {
        // TODO find fitting randomisation in java
        return 0;
    }

    public static void hurt(int takenDamage, String hurtColor, String attackingSoldier, int soldierTeamID) {
        // The Commando is very sneaky and rogue. His resistance is randomized.
        // on a randomized 1-5 scale, he takes (<random number>/5)*100 percent less damage

        // TODO find fitting randomisation in java

        takenDamage = takenDamage/2;
        if (Objects.equals(hurtColor, "red")) {
            com.boomchess.game.Commando.healthRed -= takenDamage;
            if (com.boomchess.game.Commando.healthRed <= 0) {
                Damage.removeSoldier("red", "commando", 1);
            }
        }
        else {
            com.boomchess.game.Commando.healthGreen -= takenDamage;
            if (com.boomchess.game.Commando.healthGreen <= 0) {
                Damage.removeSoldier("green", "commando", 1);
            }
        }
    }

    public static void defaultHealth() {
        com.boomchess.game.Commando.healthRed = 50;
        com.boomchess.game.Commando.healthGreen = 50;
    }
}

