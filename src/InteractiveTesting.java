import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * A class for interactive SudokuSolver testing from the cmd line
 * @author Russell Rivera
 * @since July 9th, 2019
 * Notes: See README.txt for usage, just compile and run from cmd line.
 */
public class InteractiveTesting {
    public InteractiveTesting() {}

    /**
     * Get a board through user input and attempt to solve it.
     */
    public void solveCustomBoard() {
        int[][] custom = getCustomBoard();
        SudokuSolver game = new SudokuSolver(custom);
        game.solve();
    }

    /**
     * Read lines of user input, where user input is 9 digits [0-9] delimited by spaces with no other characters
     * @return Return a 2D array representing a Sudoku board that can be initialized as a SudokuSolver object.
     */
    public int[][] getCustomBoard() {
        int[][] board = new int[9][9];
        System.out.println("For each row, enter the numbers in that row delimited by spaces: e.g. \"8 0 6 2 9 0 4 0 1\"");
        for (int i = 0; i < 9; i++) {
            System.out.println("Enter values for row " + i + ". Use 0 (zero) for an empty cell");
            int[] row = readRowInput();
            board[i] = row;
        }
        return board;
    }

    /**
     * Turn a line of user input into a row of a Sudoku board
     * @return Return an int[9] representing a row in a Sudoku board
     */
    public int[] readRowInput() {
        int[] output = new int[9];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String rowString = reader.readLine();
            int[] row = Arrays.stream(rowString.split("\\s+"))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            System.out.println();
            if (row.length != 9) {
                throw new java.lang.Error("A row must be 9 digits.");
            }
            output = row;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return output;
    }

    public static void main(String[] args) {
        InteractiveTesting tester = new InteractiveTesting();
        tester.solveCustomBoard();
    }
}
