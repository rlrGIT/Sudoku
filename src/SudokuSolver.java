import java.util.*;

/**
 * A class for solving Sudoku Puzzles
 @author Russell Rivera
 @since July 5, 2020 - July 10th, 2020
 Desc: Fortify SCA Application
 Notes: See README.md for additional documentation, see TestCaseRunner.java for automated tester
 * or look at InteractiveTesting.java for running your own tests.
 */

public class SudokuSolver {
    private int[][] sudokuBoard;
    private HashMap<Integer, HashSet<Integer>> rowContents;
    private HashMap<Integer, HashSet<Integer>> colContents;
    private HashMap<Integer, HashSet<Integer>> gridContents;

    /**
     * Constructs and initializes a Sudoku Board with all necessary row, column, and sub-grid information
     * @param board a 2D array representing an unsolved Sudoku Board
     */
    public SudokuSolver(int[][] board) {
        this.sudokuBoard = board;
        initRowContents();
        initColContents();
        initGridContents();
    }

    /* constants */
    public static final int BOARD_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;

    /**
     * Initializes a HashMap of HashSets: the HashMap is keyed by an integer r referring to a row on the board,
     * (0 <= r <= 8) with a corresponding HashSet of integers (value) that contains the elements of row r.
     * This initialization is to make use of is O(1) lookup with queries like: Map.get(row).contains(number).
     */
    void initRowContents() {
        HashMap<Integer, HashSet<Integer>> rows = new HashMap<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            HashSet<Integer> rowEntries = new HashSet<>();
            for (int col = 0; col < BOARD_SIZE; col++) {
                int squareValue = sudokuBoard[row][col];
                if (squareValue != 0)
                    rowEntries.add(squareValue);
            }
            rows.put(row, rowEntries);
        }
        rowContents = rows;
    }

    /**
     * Initializes a HashMap of HashSets: the HashMap contains a integer key c referring to a column on the board,
     * (0 <= c <= 8) with a corresponding HashSet of integers (value) that contains the elements of column c.
     * This initialization is to make use of is O(1) lookup with queries like: Map.get(column).contains(number).
     */
    void initColContents() {
        HashMap<Integer, HashSet<Integer>> cols = new HashMap<>();
        for (int col = 0; col < BOARD_SIZE; col++) {
            HashSet<Integer> colEntries = new HashSet<>();
            for (int row = 0; row < BOARD_SIZE; row++) {
                int squareValue = sudokuBoard[row][col];
                if (squareValue != 0)
                    colEntries.add(squareValue);
            }
            cols.put(col, colEntries);
        }
        colContents = cols;
    }

    /**
     * Initializes a HashMap of HashSets: the HashMap is keyed by an integer g referring to a specific sub-grid
     * on the board, (0 <= g <= 8) with a corresponding HashSet of integers (value) that contains the elements
     * inside of grid g. Again, this is used to leverage constant lookup times.
     */
    void initGridContents() {
        HashMap<Integer, HashSet<Integer>> grids = new HashMap<>();
        int gridNumber = 0;
        // iterate across and down the board in steps of three (iterate over the grids)
        for (int row = 0; row < BOARD_SIZE; row += SUBGRID_SIZE) {
            for (int col = 0; col < BOARD_SIZE; col += SUBGRID_SIZE) {
                HashSet<Integer> gridEntries = new HashSet<>();

                // for each grid we iterate over, iterate over a sub-grid (iterate within a grid)
                for (int gridRow = row; gridRow < SUBGRID_SIZE + row; gridRow++) {
                    for (int gridCol = col; gridCol < SUBGRID_SIZE + col; gridCol++) {
                        int squareValue = sudokuBoard[gridRow][gridCol];
                        if (squareValue != 0)
                            gridEntries.add(squareValue);
                    }
                }
                grids.put(gridNumber++, gridEntries);
            }
        }
        gridContents = grids;
    }

    /**
     * Determine whether a given value (val) can be legally placed at a given spot on the Sudoku Board, i.e.
     * (sudokuBoard[row][col]) does not violate the rules
     * @param val The value to be placed on the board (assumes a valid input 1 <= val <= 9)
     * @param rowNum The row coordinate of a square on the board (assumes a valid input 0 <= row <= 8)
     * @param colNum The col coordinate of a square on the board (assumes a valid input 0 <= col <= 8)
     * @return Returns true iff sudokuBoard[row][col] = val is a legal assignment
     */
    boolean legalPlacement(int val, int rowNum, int colNum) {
        HashSet<Integer> rowEntries = rowContents.get(rowNum);
        HashSet<Integer> colEntries = colContents.get(colNum);
        int gridNum = (colNum / SUBGRID_SIZE) + ((rowNum / SUBGRID_SIZE) * SUBGRID_SIZE);
        HashSet<Integer> gridEntries = gridContents.get(gridNum);
        /* placement is legal if val is not already in the same row, col, or grid */
        return  !rowEntries.contains(val) &&
                !colEntries.contains(val) &&
                !gridEntries.contains(val);
    }

    /**
     * Places a given value at a location on the board specified by (row, col). Updates internal HashMaps
     * for the row, column, and grid in which the value was placed to check for future collisions.
     * @param val The value to be placed at the given coordinates (assumes a valid input 1 <= val <= 9)
     * @param row The row coordinate on the board (assumes a valid input 0 <= row <= 8)
     * @param col The column coordinate on the board (assumes a valid input 0 <= col <= 8)
     */
    void placeNumber(int val, int row, int col) {
        sudokuBoard[row][col] = val;
        rowContents.get(row).add(val);
        colContents.get(col).add(val);
        int gridNumber = (col / SUBGRID_SIZE) + ((row / SUBGRID_SIZE) * SUBGRID_SIZE);
        gridContents.get(gridNumber).add(val);
    }

    /**
     * Remove the number on the board at the position given by (row, col). Assumes (row, col) is not already 0.
     * Updates internal HashMaps for the row, column, and grid in which the value was placed to check for
     * future collisions.
     * @param row The row coordinate on the board
     * @param col The column coordinate on the board
     */
    void removeNumber(int row, int col) {
        int val = sudokuBoard[row][col];
        rowContents.get(row).remove(val);
        colContents.get(col).remove(val);
        int gridNumber = (col / SUBGRID_SIZE) + ((row / SUBGRID_SIZE) * SUBGRID_SIZE);
        gridContents.get(gridNumber).remove(val);
        sudokuBoard[row][col] = 0;
    }

    /**
     * Recursively partially complete Sudoku boards: For each empty square on the board, if that square is empty,
     * place a legal value down, and attempt to solve the rest of the board with that particular value in place.
     * If a solution cannot be found with the number that was placed, (sudokuValidSoFar == False) then remove this
     * number from the grid, and select the next possible number. Repeat until a solution is found.
     * @return Returns true while a solution or sub-solution is valid.
     */
    boolean sudokuValidSoFar() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (sudokuBoard[row][col] == 0) {
                    for (int num = 0; num < 10; num++) {
                        if (legalPlacement(num, row, col)) {
                            placeNumber(num, row, col);
                            if (sudokuValidSoFar())
                                return true;
                            removeNumber(row, col);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Solve a board, print both the original board and the solution
     */
    void solve() {
        System.out.println("Solving for solution on board: ");
        printResult();
        if (sudokuValidSoFar()) {
            System.out.println("\nSolution found: ");
            printResult();
            System.out.println("\n");
        }
        else
            System.out.println("Unsolvable.\n");
    }

    /**
     * Verify that a solution is correct by checking if each square has no repeating value in a shared row,
     * column, or sub-grid. This solution assumes that the a board has been solved using the solve() method,
     * so that this function can the HashMaps built up by that function to check containment.
     * @return Returns true if the solution is a legal sudoku, false if otherwise (assumes board is solved)
     */
    boolean verifySolution() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int gridNum = (col / SUBGRID_SIZE) + ((row / SUBGRID_SIZE) * SUBGRID_SIZE);
                int value = sudokuBoard[row][col];
                boolean validSquare = !rowContents.get(row).contains(value) &&
                                      !colContents.get(col).contains(value) &&
                                      !gridContents.get(gridNum).contains(value);
                if (!validSquare)
                    return false;
            }
        }
        return true;
    }

    /**
     * Print out a Sudoku Board
     */
    public void printResult() {
        for (int[] row : sudokuBoard) {
            System.out.println(Arrays.toString(row));
        }
    }
}
