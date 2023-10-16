package com.boomchess.game;

public class Coordinates {

    private int X;
    private int Y;

    // method for comparing two Coordinates objects for equality
    public boolean equals(Coordinates coordinates) {
        return (this.X == coordinates.getX() && this.Y == coordinates.getY());
    }

    // methods for setting and getting the coordinates

    public void setCoordinates(int x, int y) {
        // this method sets BOTH coordinates, for separate setting use setX and setY
        this.X = x;
        this.Y = y;
    }

    public void setX(int x) {
        this.X = x;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }


}
