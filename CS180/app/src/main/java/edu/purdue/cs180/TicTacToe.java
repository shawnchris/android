package edu.purdue.cs180;

public class TicTacToe {
    TicTacToeViewInterface view; //The view interface used to make modifications to the view
    char[][] board; //The 3x3 board represented with characters
    char currentPlayer; //Keeps track of whose turn it is currently
    int cellsOccupied; //Keeps track of how many cells on the board are occupied
    char winner; //Keeps track of who won
    /**
     * Constructor. Initializes the instance variables.
     */
    public TicTacToe(TicTacToeViewInterface view) {
        this.view = view;
        board = new char[3][3];
        currentPlayer = 'X';
        cellsOccupied = 0;
        winner = ' ';
    }
    /**
     * This function is called to start a new game.
     *
     */
    public void newGame() {
        //Hint : Call reset(). That's it :)
        reset();
    }

    /**
     * This function should reset each button on the grid.
     */
    public void reset() {
        //Every time a new game begins, make sure to reset the view as well
        //as the board and the other instance variables.
        board = new char[3][3];
        currentPlayer = 'X';
        cellsOccupied = 0;
        winner = ' ';
        view.resetButtons();
    }
    /**
     * This function checks if the given player has won the game or
     not. The checks have to be made in each column, row, forward and
     backward diagonals. If a player has won, call showWinner() where you
     can use a message alert box, toast, etc. to print the winner.
     */
    public boolean checkWinner(char player) {
        //Iterate through the board matrix and check if you find a
        //sequence of winning patters and set winner to the 'X' or 'O'
        //accordingly. Further, return true if there is a winning pattern.
        //check rows and cols
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                winner = player;
                return true;
            }
        }
        //check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            winner = player;
            return true;
        }
        return false;
    }
    /**
     * This function updates the game board whenever a user clicks on
     any of the game buttons, inside this function you need to call
     checkWinner to check if a player has won the last click on the board
     */
    public void updateGameBoard(int x, int y) {
        view.update(x, y, currentPlayer);
        board[x][y] = currentPlayer;
        cellsOccupied++;
        if (checkWinner(currentPlayer))
            view.showWinner(currentPlayer + "");
        if (cellsOccupied >= 9)
            view.gameOver();
        if (currentPlayer == 'X')
            currentPlayer = 'O';
        else
            currentPlayer = 'X';
    }
}
