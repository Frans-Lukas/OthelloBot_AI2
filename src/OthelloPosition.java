import java.awt.*;
import java.util.*;
import java.lang.*;

/**
 * This class is used to represent game positions. It uses a 2-dimensional char
 * array for the board and a Boolean to keep track of which player has the move.
 * 
 * @author Henrik Bj&ouml;rklund
 */

public class OthelloPosition {

	/** For a normal Othello game, BOARD_SIZE is 8. */
	public static final int BOARD_SIZE = 8;

	/** True if the first player (white) has the move. */
	protected boolean playerToMove;

	/**
	 * The representation of the board. For convenience, the array actually has
	 * two columns and two rows more that the actual game board. The 'middle' is
	 * used for the board. The first index is for rows, and the second for
	 * columns. This means that for a standard 8x8 game board,
	 * <code>board[1][1]</code> represents the upper left corner,
	 * <code>board[1][8]</code> the upper right corner, <code>board[8][1]</code>
	 * the lower left corner, and <code>board[8][8]</code> the lower left
	 * corner. In the array, the charachters 'E', 'W', and 'B' are used to
	 * represent empty, white, and black board squares, respectively.
	 */
	protected char[][] board;

	/** Creates a new position and sets all squares to empty. */
	public OthelloPosition() {
		board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				board[i][j] = 'E';

	}

	public OthelloPosition(String s) {
		if (s.length() != 65) {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			for (int i = 0; i < BOARD_SIZE + 2; i++)
				for (int j = 0; j < BOARD_SIZE + 2; j++)
					board[i][j] = 'E';
		} else {
			board = new char[BOARD_SIZE + 2][BOARD_SIZE + 2];
			if (s.charAt(0) == 'W') {
				playerToMove = true;
			} else {
				playerToMove = false;
			}
			for (int i = 1; i <= 64; i++) {
				char c;
				if (s.charAt(i) == 'E') {
					c = 'E';
				} else if (s.charAt(i) == 'O') {
					c = 'W';
				} else {
					c = 'B';
				}
				int column = ((i - 1) % 8) + 1;
				int row = (i - 1) / 8 + 1;
				board[row][column] = c;
			}
		}

	}

	/**
	 * Initializes the position by placing four markers in the middle of the
	 * board.
	 */
	public void initialize() {
		board[BOARD_SIZE / 2][BOARD_SIZE / 2] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2 + 1] = 'W';
		board[BOARD_SIZE / 2][BOARD_SIZE / 2 + 1] = board[BOARD_SIZE / 2 + 1][BOARD_SIZE / 2] = 'B';
		playerToMove = true;
	}

	/* getMoves and helper functions */

	/**
	 * Returns a linked list of <code>OthelloAction</code> representing all
	 * possible moves in the position. If the list is empty, there are no legal
	 * moves for the player who has the move.
	 */

    public ArrayList<OthelloAction> getMoves() {
        boolean[][] candidates = new boolean[BOARD_SIZE][BOARD_SIZE];
        ArrayList<OthelloAction> moves = new ArrayList<OthelloAction>();
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                candidates[i][j] = isCandidate(i + 1, j + 1);
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if (candidates[i][j])
                    if (isMove(i + 1, j + 1))
                        moves.add(new OthelloAction(i + 1, j + 1));
        return moves;
    }

    private boolean isMove(int row, int column) {
        if (checkNorth(row, column))
            return true;
        if (checkNorthEast(row, column))
            return true;
        if (checkEast(row, column))
            return true;
        if (checkSouthEast(row, column))
            return true;
        if (checkSouth(row, column))
            return true;
        if (checkSouthWest(row, column))
            return true;
        if (checkWest(row, column))
            return true;
        if (checkNorthWest(row, column))
            return true;

        return false;
    }

    private boolean checkNorth(int row, int column) {
        if (!isOpponentSquare(row - 1, column))
            return false;
        for (int i = row - 2; i > 0; i--) {
            if (isFree(i, column))
                return false;
            if (isOwnSquare(i, column))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the east from this position
     */
    private boolean checkEast(int row, int column) {
        if (!isOpponentSquare(row, column + 1))
            return false;
        for (int i = column + 2; i <= BOARD_SIZE; i++) {
            if (isFree(row, i))
                return false;
            if (isOwnSquare(row, i))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the south from this position
     */
    private boolean checkSouth(int row, int column) {
        if (!isOpponentSquare(row + 1, column))
            return false;
        for (int i = row + 2; i <= BOARD_SIZE; i++) {
            if (isFree(i, column))
                return false;
            if (isOwnSquare(i, column))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the west from this position
     */
    private boolean checkWest(int row, int column) {
        if (!isOpponentSquare(row, column - 1))
            return false;
        for (int i = column - 2; i > 0; i--) {
            if (isFree(row, i))
                return false;
            if (isOwnSquare(row, i))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the northest from this position
     */
    private boolean checkNorthEast(int row, int column) {
        if (!isOpponentSquare(row - 1, column + 1))
            return false;
        for (int i = 2; row - i > 0 && column + i <= BOARD_SIZE; i++) {
            if (isFree(row - i, column + i))
                return false;
            if (isOwnSquare(row - i, column + i))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the southeast from this position
     */
    private boolean checkSouthEast(int row, int column) {
        if (!isOpponentSquare(row + 1, column + 1))
            return false;
        for (int i = 2; row + i <= BOARD_SIZE && column + i <= BOARD_SIZE; i++) {
            if (isFree(row + i, column + i))
                return false;
            if (isOwnSquare(row + i, column + i))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the soutwest from this position
     */
    private boolean checkSouthWest(int row, int column) {
        if (!isOpponentSquare(row + 1, column - 1))
            return false;
        for (int i = 2; row + i <= BOARD_SIZE && column - i > 0; i++) {
            if (isFree(row + i, column - i))
                return false;
            if (isOwnSquare(row + i, column - i))
                return true;
        }
        return false;
    }

    /**
     * Check if it is possible to do a move to the northwest from this position
     */
    private boolean checkNorthWest(int row, int column) {
        if (!isOpponentSquare(row - 1, column - 1))
            return false;
        for (int i = 2; row - i > 0 && column - i > 0; i++) {
            if (isFree(row - i, column - i))
                return false;
            if (isOwnSquare(row - i, column - i))
                return true;
        }
        return false;
    }
    private boolean isOpponentSquare(int row, int column) {
        if (playerToMove && (board[row][column] == 'B'))
            return true;
        if (!playerToMove && (board[row][column] == 'W'))
            return true;
        return false;
    }

    private boolean isOwnSquare(int row, int column) {
        if (!playerToMove && (board[row][column] == 'B'))
            return true;
        if (playerToMove && (board[row][column] == 'W'))
            return true;
        return false;
    }
    private boolean isCandidate(int row, int column) {
        if (!isFree(row, column))
            return false;
        if (hasNeighbor(row, column))
            return true;
        return false;
    }

    private boolean hasNeighbor(int row, int column) {
        if (!isFree(row - 1, column))
            return true;
        if (!isFree(row - 1, column + 1))
            return true;
        if (!isFree(row, column + 1))
            return true;
        if (!isFree(row + 1, column + 1))
            return true;
        if (!isFree(row + 1, column))
            return true;
        if (!isFree(row + 1, column - 1))
            return true;
        if (!isFree(row, column - 1))
            return true;
        if (!isFree(row - 1, column - 1))
            return true;
        return false;
    }

    private boolean isFree(int row, int column) {
        if (board[row][column] == 'E')
            return true;
        return false;
    }


    public boolean canMakeMove(){
		char enemyColor = playerToMove ? 'B' : 'W';
		for (int y = 1; y <= BOARD_SIZE; y++) {
			for (int x = 1; x <= BOARD_SIZE; x++) {
				if(board[x][y] == 'E' && enemyNeighbourExists(enemyColor, x, y)){
					OthelloAction acition = new OthelloAction(x, y);
					if(numPointsFor(acition) > 0){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return true if a neighbour of the given color exists at position x and y.
	 */
	private boolean enemyNeighbourExists(char enemyColor, int x_pos, int y_pos) {
		for (int y = y_pos - 1; y <= y_pos + 1; y++) {
			for (int x = x_pos - 1; x <= x_pos + 1; x++) {
				if (inside_bounds(x, y) && board[x][y] == enemyColor) {
					return true;
				}
			}
		}
		return false;
	}

	/* toMove */

	/** Returns true if the first player (white) has the move, otherwise false. */
	public boolean toMove() {
		return playerToMove;
	}

	/* makeMove and helper functions */

	/**
	 * Returns the position resulting from making the move <code>action</code>
	 * in the current position. Observe that this also changes the player to
	 * move next.
	 */
	public OthelloPosition makeMove(OthelloAction action)
			throws IllegalMoveException {

		if(action.isPassMove()){
			playerToMove = !playerToMove;
			return this;
		}

		if(numPointsFor(action) == 0){
			throw new IllegalMoveException(action);
		}

		char playerColor = playerToMove ? 'W' : 'B';
		char enemyColor = playerToMove ? 'B' : 'W';

		flipEnemyDisks(action, enemyColor);
		playerToMove = !playerToMove;


        board[action.getRow()][action.getColumn()] = playerColor;

		return this;
	}

	private void flipEnemyDisks(OthelloAction action, char enemyColor) {
		char playerColor = playerToMove ? 'W' : 'B';
		for (int y = action.getColumn() - 1; y <= action.getColumn() + 1; y++) {
			for (int x = action.getRow() - 1; x <= action.getRow() + 1; x++) {
				if (inside_bounds(x, y) && board[x][y] == enemyColor) {
                    traceLineToFlip(action, enemyColor, playerColor, y, x);
                }
			}
		}
	}

    private void traceLineToFlip(OthelloAction action, char enemyColor, char playerColor, int y, int x) {
        //calculate direction
	    int x_dir = x - action.getRow();
        int y_dir = y - action.getColumn();

        int x_pos = x;
        int y_pos = y;

        ArrayList<Point> disksToFlip = new ArrayList<>();

        //follow the direction until the position is no longer enemy color or out of bounds.
        // Add disks to list for flipping.
        while(inside_bounds(x_pos, y_pos) && board[x_pos][y_pos] == enemyColor){
            disksToFlip.add(new Point(x_pos, y_pos));
            x_pos += x_dir;
            y_pos += y_dir;
        }

        //
        if(inside_bounds(x_pos, y_pos) && board[x_pos][y_pos] == playerColor){
            for (Point disk : disksToFlip) {
                board[disk.x][disk.y] = playerColor;
            }
        }
    }

    /**
	 * Gives the number of points for the given action.
	 * If zero is returned, the move is invalid.
	 */
	public int numPointsFor(OthelloAction action){
		char enemyColor = playerToMove ? 'B' : 'W';
		char playerColor = playerToMove ? 'W' : 'B';
		int numPoints = 0;

		for (int y = action.getColumn() - 1; y <= action.getColumn() + 1; y++) {
			for (int x = action.getRow() - 1; x <= action.getRow() + 1; x++) {
				if(inside_bounds(x, y) && board[x][y] == enemyColor){
					//calculate direction
				    int x_dir = x - action.getRow();
					int y_dir = y - action.getColumn();

					int x_pos = x;
					int y_pos = y;
					int counter = 0;
                    if(x_dir == 0 && y_dir == 0)
                        return 0;
					//follow the direction until the position is no longer enemy color or out of bounds.
					while(inside_bounds(x_pos, y_pos) && board[x_pos][y_pos] == enemyColor){
						counter++;
						x_pos += x_dir;
						y_pos += y_dir;
					}
					if(inside_bounds(x_pos, y_pos) && board[x_pos][y_pos] == playerColor){
						numPoints += counter;
					}
				}
			}
		}


		return numPoints;
	}

	public boolean inside_bounds(int x, int y) {
		if(x <= BOARD_SIZE && x >= 1){
			if(y <= BOARD_SIZE && y >= 1){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a new <code>OthelloPosition</code>, identical to the current one.
	 */
	protected OthelloPosition clone() {
		OthelloPosition newPosition = new OthelloPosition();
		newPosition.playerToMove = playerToMove;
		for (int i = 0; i < BOARD_SIZE + 2; i++)
			for (int j = 0; j < BOARD_SIZE + 2; j++)
				newPosition.board[i][j] = board[i][j];
		return newPosition;
	}

	/* illustrate and other output functions */

	/**
	 * Draws an ASCII representation of the position. White squares are marked
	 * by '0' while black squares are marked by 'X'.
	 */
	public void illustrate() {
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|");
		printHorizontalBorder();
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print(" " + i + " ");
			for (int j = 1; j <= BOARD_SIZE; j++) {
				if (board[i][j] == 'W') {
					System.out.print("| W ");
				} else if (board[i][j] == 'B') {
					System.out.print("| B ");
				} else {
					System.out.print("|   ");
				}
			}
			System.out.println("| " + i + " ");
			printHorizontalBorder();
		}
		System.out.print("   ");
		for (int i = 1; i <= BOARD_SIZE; i++)
			System.out.print("| " + i + " ");
		System.out.println("|\n");
	}

	private void printHorizontalBorder() {
		System.out.print("---");
		for (int i = 1; i <= BOARD_SIZE; i++) {
			System.out.print("|---");
		}
		System.out.println("|---");
	}

	public String toString() {
		String s = "";
		char c, d;
		if (playerToMove) {
			s += "W";
		} else {
			s += "B";
		}
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				d = board[i][j];
				if (d == 'W') {
					c = 'O';
				} else if (d == 'B') {
					c = 'X';
				} else {
					c = 'E';
				}
				s += c;
			}
		}
		return s;
	}

}
