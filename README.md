# Sudoku

## Overview
This was my attempt at making a digital version of Sudoku using Java. 
This project uses JPanel and Graphics to visualize the game, and ActionListener,
KeyListener, and MouseListener to allow the player to interact with it. 
Several of the key concepts practiced in this game were HashMaps, 
writing and reading data from/to files, implementing a stopwatch, and encapsulation.

## Files

` HighScore.txt `. 

This is a simple text file that initially contains the maximum possible value for an int (2^31 - 1). When the player solves
their first puzzle, their time in seconds will be written to this file. Whenever the program is run, the player's best time
is determined by reading this file. This allows players to close the program and return later without losing their best time.

` Board.java `. 

The Board class draws the board and selects a puzzle based off of the specified difficulty. Puzzles can be easy, medium, or
hard. Difficulty is determined by how many numbers are already inputted on the board as well as their locations. Harder puzzles
generally require more steps and logic to solve. The puzzles themselves are stored in HashMaps, with an Integer to denote the
puzzle's ID and a two-dimensional array of ints that serves as the answer key to the puzzle. They are visualized similarly
using a HashMap, but with a two-dimensional array of Strings that only contains the given information at the beginning and
serves as the puzzle the player interacts with. As players input numbers, they are added to this second HashMap and compared 
against the "answer key" to see if the guess was right. Puzzles are chosen randomly from the five existing for each difficulty,
making fifteen total puzzles for players to solve.

Sources for puzzles:  
      https://dingo.sbs.arizona.edu/~sandiway/sudoku/examples.html (Easy 1-2, Medium 1, Hard 1-2)
      https://krazydad.com/sudoku/sfiles/KD_Sudoku_IM_8_v1.pdf (Medium 2-5)
      http://www.sudokuessentials.com/free_sudoku.html (Easy 3-5, Hard 3-5)

` Screen.java `. 

This is where most of the action in the program occurs. This sets up the size of the window and draws the ` Board ` that is
instantiated with a difficulty determined by the player on the main menu. The stopwatch is also calculated and displayed in this
class. To play the game, players must first click the square they would like to put a number in, which will be highlighted by
a gray box. Then, they can simply type the number they want to go their using their keyboard. To check accuracy, simply press
the "Check Accuracy"  button and it will calculate correctness using a method in ` Board.java `. Once it has been determined to
be entirely accurate, the stopwatch will stop, and the player will be taken back to the main menu to play again if they desire.

` Runner.java `. 

This class instantiates the JFrame and the Screen, and is the file that should be run to play the game. 
