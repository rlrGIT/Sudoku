/
Author: Russell Rivera
Date: July 10th, 2020
Desc: README file
*/

Contents and Files:

- SudokuSolver.java is my actual Sudoku solver.

- TestCaseRunner.java is what I used as a test suite when building SudokuSolver. It just runs some
hard coded tests. Note: some of these take a while, esp. the last test.

- InteractiveTesting.java is some code I have to read in user-input rows of a Sudoku board, and
automatically build and attempt to solve the board generated from that user input. This is what
I used to test corner cases after my solver passed my test cases in TestCaseRunner.java.


#########################################################################################################
Testing and Usage:

From the command line,

>> javac TestCaseRunner.java
>> java TestCaseRunner

will automatically run my own hard-coded test suite. You can add a test case by adding a int[][] to
the "boards" constant in the TestCaseRunner class.

Alternatively, you can run a test case interactively by using:

>> javac InteractiveTesting.java
>> java InteractiveTesting

the command prompt will ask you to input 9 numbers (separated by spaces) a total of 9 times. Each
set of 9 numbers you input will become a row in a new Sudoku board. The program will then attempt to
solve the board it was given. Use 0 (zeroes) to represent an empty space. Non-numeric inputs,
and inputs that are not [0-9] are not allowed.

E.g. "0 1 3 8 0 0 4 0 5" is a valid input for trying your own Sudoku.

#########################################################################################################

Assumptions and Constraints:

1) All inputs used by the SudokuSolver class take the form of a 9x9 2D array of integers (int[9][9]).

2) A board may only contain the digits [0-9], where zero represents a square that has not been assigned a
value. No other values are allowed.

3) All unsolved input boards given to the SudokuSolver class must be valid, partially solved Sudoku boards.
This means that a board must be either a completely empty board (all zeroes), or partially filled such that
none of Sudoku's constraints are violated.

e.g. {    /* the empty board is a valid sub-problem! */
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0}

     }

and

     {    /* legal input - no collisions in any row, column, or subgrid */
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 2, 0, 0, 4, 0, 0, 0},
          {0, 0, 0, 5, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 1, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 3, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0, 7, 0, 6},
          {0, 0, 0, 0, 0, 0, 8, 9, 0}

     }

are both valid inputs, but

     {    /* invalid input -- board is faulty */
          {2, 8, 1, 9, 6, 1, 3, 9, 2},
          {3, 5, 2, 9, 6, 6, 8, 5, 6},
          {8, 2, 1, 5, 3, 6, 7, 7, 1},
          {8, 1, 2, 8, 9, 3, 1, 3, 1},
          {6, 9, 4, 6, 6, 9, 9, 1, 1},
          {5, 8, 9, 3, 4, 2, 9, 3, 8},
          {3, 6, 9, 9, 7, 9, 5, 6, 2},
          {4, 3, 7, 9, 7, 4, 4, 9, 4},
          {4, 5, 3, 1, 4, 1, 3, 8, 1}
     }

is not a valid input because it already violates Sudoku's constraints. While the example above
is a completely filled board, any partially filled board that also violates the rules of Sudoku
is not a valid input.

4) Function/Subroutine specific constraints are included in the javadoc above that function, e.g.
input restraints, preconditions, etc.

#########################################################################################################




