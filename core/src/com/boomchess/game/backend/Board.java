package com.boomchess.game.backend;

import com.boomchess.game.backend.subsoldier.*;

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
        board[0][0] = new Tank("green");
        board[0][7] = new Tank("green");
        // helicopters
        board[0][1] = new Helicopter("green");
        board[0][6] = new Helicopter("green");
        // artillery
        board[0][2] = new Artillery("green");
        board[0][5] = new Artillery("green");
        // wardog
        board[1][2] = new Wardog("green");
        board[1][5] = new Wardog("green");
        // general
        board[0][3] = new General("green");
        // commando
        board[0][4] = new Commando("green");
        // infantry
        int i = 1;
        for (int j = 0; j < 8; j++) {
            if(j == 2 || j == 5){
                continue;
            }
            board[i][j] = new Infantry("green");
        }


        // red team
        // tanks
        board[8][0] = new Tank("red");
        board[8][7] = new Tank("red");
        // helicopters
        board[8][1] = new Helicopter("red");
        board[8][6] = new Helicopter("red");
        // artillery
        board[8][2] = new Artillery("red");
        board[8][5] = new Artillery("red");
        // wardog
        board[7][2] = new Wardog("red");
        board[7][5] = new Wardog("red");
        // general
        board[8][3] = new General("red");
        // commando
        board[8][4] = new Commando("red");
        // infantry
        int x = 7;
        for (int j = 0; j < 8; j++) {
            if(j == 2 || j == 5){
                continue;
            }
            board[x][j] = new Infantry("red");
        }


        // Initialize the no-mans-land (empty space)
        for (int row = 2; row < 7; row++) {
            for (int col = 0; col <= 7; col++) {
                board[row][col] = new Empty("empty");
            }
        }
   }

    public static Soldier[][] getGameBoard(){
        return board;
    }

    public static void update(int positionX, int positionY,
                                 int newPositionX, int newPositionY) {

        // we update a board by switching the two objects of the Soldier 2D-Array

        System.out.println("\n Updating board " + positionX + "_" + positionY + " to " + newPositionX + "_" + newPositionY);

        Soldier temp = board[positionX][positionY];
        board[positionX][positionY] = board[newPositionX][newPositionY];
        board[newPositionX][newPositionY] = temp;

        // successfully switched around the objects in the 2D-Array
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

