package com.boomchess.game.frontend;

import com.boomchess.game.backend.Coordinates;

public class InnerSequBits {

    // inner bites class hält LinkedList Knoten

    private static Coordinates dottedLineActorStart;
    private static Coordinates dottedLineActorEnd;
    private static boolean typeOfLine;

    private static boolean isDeath;
    private static Coordinates explosion;

    public InnerSequBits (Coordinates dottedLineEnd, Coordinates dottedLineBegin, Coordinates explo, boolean isItDeath,
    boolean isDamage){

        typeOfLine = isDamage;
        dottedLineActorStart = dottedLineBegin;
        dottedLineActorEnd = dottedLineEnd;


        explosion = explo;
        isDeath = isItDeath;

    }

    public boolean getTypeOfLine(){
        return typeOfLine;
    }
    public Coordinates getDottedLineStart(){
        return dottedLineActorStart;
    }
    public Coordinates getDottedLineEnd(){
        return dottedLineActorEnd;
    }

    public Coordinates getExplosion(){
        return explosion;
    }

    public boolean getIsDeath(){
        return isDeath;
    }

}
