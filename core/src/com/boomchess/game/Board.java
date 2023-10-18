package com.boomchess.game;

public class Board {
    /*
     * Board.java is the object for the chess board in the game Boom Chess.
     * It holds the initialization of the board and the current state of the board.
     * The method initialize creates the initial board with the correct pieces.
     * The method updateBoard updates the board after a move has been made.
     */
    public static Soldier[][] initialise() {
        Soldier[][] board = new Soldier[9][8];
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
        //  2 w  i                 i  w
        //  3 g  i                 i  g
        //  4 c  i                 i  c
        //  5 w  i                 i  w
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
        // wardog
        board[0][2] = new Soldier(true,"wardog", "green", 1, Wardog.getHealth(), 0, 2);
        board[0][5] = new Soldier(true,"wardog", "green", 2, Wardog.getHealth(), 0, 5);
        // general
        board[0][3] = new Soldier(true,"general", "green", 1, General.getHealth(), 0, 3);
        // commando
        board[0][4] = new Soldier(true,"commando", "green", 1, Commando.getHealth(), 0, 4);
        // infantry
        int i = 1;
        for (int j = 0; j < 8; j++) {
            int ID = j + 1;
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
        // wardog
        board[8][2] = new Soldier(true,"wardog", "red", 1, Wardog.getHealth(), 8, 2);
        board[8][5] = new Soldier(true,"wardog", "red", 2, Wardog.getHealth(), 8, 5);
        // general
        board[8][3] = new Soldier(true,"general", "red", 1, General.getHealth(), 8, 3);
        // commando
        board[8][4] = new Soldier(true,"commando", "red", 1, Commando.getHealth(), 8, 4);
        // infantry
        int x = 7;
        for (int j = 0; j < 8; j++) {
            int ID = j + 1;
            board[x][j] = new Soldier(true,"infantry", "red", ID, Infantry.getHealth(), x, j);
        }


        // Initialize the no-mans-land (empty space)
        for (int row = 2; row < 7; row++) {
            for (int col = 0; col <= 7; col++) {
                board[row][col] = new Soldier(false, "empty", "none", 0, 0, row, col);
            }
        }

        return board;
    }

    public static boolean update(Soldier[][] gameBoard, int positionX, int positionY,
                                 int newPositionX, int newPositionY) {
        // run this in a Try loop till it returns true to not allow the player to make a move until it is valid
        boolean validMove = false;
        // make a call to the Soldier object in gameBoard behind newPosition to check if it is taken
        if (gameBoard[newPositionX][newPositionY].getTaken()) {
            // print out "invalid move"
            System.out.println("Invalid move. Position is already taken.");
        }
        // if not taken, replace current position soldier with empty values and replace new position soldier with
        // the old information
        else {
            validMove = true;
            gameBoard[newPositionX][newPositionY].setTaken(true);
            gameBoard[newPositionX][newPositionY].setTeamColor(gameBoard[positionX][positionY].getTeamColor());
            gameBoard[newPositionX][newPositionY].setSoldierType(gameBoard[positionX][positionY].getSoldierType());
            gameBoard[newPositionX][newPositionY].setPieceID(gameBoard[positionX][positionY].getPieceID());
            gameBoard[newPositionX][newPositionY].setHealth(gameBoard[positionX][positionY].getHealth());
            gameBoard[positionX][positionY].setTaken(false);
            gameBoard[positionX][positionY].setTeamColor("none");
            gameBoard[positionX][positionY].setSoldierType("empty");
            gameBoard[positionX][positionY].setPieceID(0);
            gameBoard[positionX][positionY].setHealth(0);
        }
        return validMove;
    }

    // Helper method to check if the coordinates are within bounds
    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 8;
    }
}
