package com.boomchess.game;

import java.util.ArrayList;

public class Board {
    /*
     * Board.java is the object for the chess board in the game Boom Chess.
     * It holds the initialization of the board and the current state of the board.
     * The method initialize creates the initial board with the correct pieces.
     * The method updateBoard updates the board after a move has been made.
     */

    // Define the board as a 2D array
    private static Soldier[][] board;

    // current validMoveTiles
    public static ArrayList<Coordinates> validMoveTiles;

    public static void initialise() {

        // declaring the board

        board = new Soldier[9][8];

        // initialize the board with the correct pieces

        // the tank is the tower,
        // the helicopter the knight,
        // the wardog the bishop,
        // the general the king,
        // the commando
        // the queen and the infantry the pawns.
        // as to the standard layout of the board:
        // the red team is on the right of the screen
        // and the green team on the left

        //   green               red
        //    0  1  2  3  4  5  6  7  8
        //  0 t  i                 i  t
        //  1 h  i                 i  h
        //  2 a  w                 w  a
        //  3 g  i                 i  g
        //  4 c  i                 i  c
        //  5 w  w                 w  a
        //  6 h  i                 i  h
        //  7 t  i                 i  t


        // start of the creation of the board
        // green team
        // tanks
        board[0][0] = new Soldier(true,"tank", "green", 1, Tank.getHealth(), 0, 0);
        board[0][7] = new Soldier(true,"tank", "green", 2, Tank.getHealth(), 0, 7);
        // helicopters
        board[0][1] = new Soldier(true,"helicopter", "green", 1,
                Helicopter.getHealth(), 0, 1);
        board[0][6] = new Soldier(true,"helicopter", "green", 2,
                Helicopter.getHealth(), 0, 6);
        // artillery
        board[0][2] = new Soldier(true,"artillery", "green", 1, Artillery.getHealth(), 0, 2);
        board[0][5] = new Soldier(true,"artillery", "green", 2, Artillery.getHealth(), 0, 5);
        // wardog
        board[1][2] = new Soldier(true,"wardog", "green", 1, Wardog.getHealth(), 1, 2);
        board[1][5] = new Soldier(true,"wardog", "green", 2, Wardog.getHealth(), 1, 5);
        // general
        board[0][3] = new Soldier(true,"general", "green", 1, General.getHealth(), 0, 3);
        // commando
        board[0][4] = new Soldier(true,"commando", "green", 1, Commando.getHealth(), 0, 4);
        // infantry
        int i = 1;
        for (int j = 0; j < 8; j++) {
            int ID = j + 1;
            if(j == 2 || j == 5){
                continue;
            }
            board[i][j] = new Soldier(true,"infantry", "green", ID, Infantry.getHealth(), i, j);
        }


        // red team
        // tanks
        board[8][0] = new Soldier(true,"tank", "red", 1, Tank.getHealth(), 8, 0);
        board[8][7] = new Soldier(true,"tank", "red", 2, Tank.getHealth(), 8, 7);
        // helicopters
        board[8][1] = new Soldier(true,"helicopter", "red", 1,
                Helicopter.getHealth(), 8, 1);
        board[8][6] = new Soldier(true,"helicopter", "red", 2,
                Helicopter.getHealth(), 8, 6);
        // artillery
        board[8][2] = new Soldier(true,"artillery", "red", 1, Artillery.getHealth(), 8, 2);
        board[8][5] = new Soldier(true,"artillery", "red", 2, Artillery.getHealth(), 8, 5);
        // wardog
        board[7][2] = new Soldier(true,"wardog", "red", 1, Wardog.getHealth(), 7, 2);
        board[7][5] = new Soldier(true,"wardog", "red", 2, Wardog.getHealth(), 7, 5);
        // general
        board[8][3] = new Soldier(true,"general", "red", 1, General.getHealth(), 8, 3);
        // commando
        board[8][4] = new Soldier(true,"commando", "red", 1, Commando.getHealth(), 8, 4);
        // infantry
        int x = 7;
        for (int j = 0; j < 8; j++) {
            int ID = j + 1;
            if(j == 2 || j == 5){
                continue;
            }
            board[x][j] = new Soldier(true,"infantry", "red", ID, Infantry.getHealth(), x, j);
        }


        // Initialize the no-mans-land (empty space)
        for (int row = 2; row < 7; row++) {
            for (int col = 0; col <= 7; col++) {
                board[row][col] = new Soldier(false, "empty", "none", 0, -1, row, col);
            }
        }
   }

    public static Soldier[][] getGameBoard(){
        return board;
    }

    public static void update(int positionX, int positionY,
                                 int newPositionX, int newPositionY) {

        System.out.println("\n Updating board " + positionX + "_" + positionY + " to " + newPositionX + "_" + newPositionY);
        board[newPositionX][newPositionY].setTaken(true);
        board[newPositionX][newPositionY].setTeamColor(board[positionX][positionY].getTeamColor());
        board[newPositionX][newPositionY].setSoldierType(board[positionX][positionY].getSoldierType());
        board[newPositionX][newPositionY].setPieceID(board[positionX][positionY].getPieceID());
        board[newPositionX][newPositionY].setHealth(board[positionX][positionY].getHealth());
        board[positionX][positionY].setTaken(false);
        board[positionX][positionY].setTeamColor("none");
        board[positionX][positionY].setSoldierType("empty");
        board[positionX][positionY].setPieceID(0);
        board[positionX][positionY].setHealth(-1);
    }

    // Helper method to check if the coordinates are within bounds
    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 8;
    }

    // methods for setting and getting the validMoveTiles, as well as emptying them
    public static void setValidMoveTiles(ArrayList<Coordinates> newValidMoveTiles) {
        validMoveTiles = newValidMoveTiles;
    }

    public static void emptyValidMoveTiles() {
        validMoveTiles = new ArrayList<>();
    }

    public static ArrayList<Coordinates> getValidMoveTiles() {
        return validMoveTiles;
    }
}

