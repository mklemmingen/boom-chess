package com.boomchess.game;

public class Soldier {
    /*
    * Soldiers.java contains the creation of the 2D-Array in the method initialiseBoard() and acts as an object
    * for what goes into each position of the board.
    * The information it holds is the soldier type, the teamColor, piece ID. It does not
    * hold the health value, since that is stored in the individual soldier objects callable by pieceID and teamColor.
     */

    private boolean isTaken;
    private String soldierType;
    private String teamColor;
    private int pieceID;

    public Soldier(boolean taken, String type, String Color, int ID) {;
        this.isTaken = taken;
        this.soldierType = type;
        this.teamColor = Color;
        this.pieceID = ID;
    }

    public boolean getTaken() {
        return this.isTaken;
    }

    public String getSoldierType() {
        System.out.print(this.soldierType);
        return this.soldierType;
    }

    public String getTeamColor() {
        return this.teamColor;
    }

    public int getPieceID() {
        return this.pieceID;
    }

    public void setTaken(boolean taken) {
        this.isTaken = taken;
    }

    public void setSoldierType(String type) {
        this.soldierType = type;
    }

    public void setTeamColor(String Color) {
        this.teamColor = Color;
    }

    public void setPieceID(int ID) {
        this.pieceID = ID;
    }
}
