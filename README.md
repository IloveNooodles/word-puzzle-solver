# word-puzzle-solver

Word Puzzle is a puzzle consisting of letters arranged in a grid which contains a number of hidden words written in various directions.

The solver is made from Java using brute force algorithm. The algorithm itself is quite simple, just match each character in the word with each character in the puzzle itself in 8 direction.

## Folder Structure
```
├── README.md
├── doc
│   └── Tugas-Kecil-1-(2022).pdf
├── src
│   ├── Wordpuzzle.java
│   └── word-puzzle.jar
└── test
    ├── 1000x1000.txt
    ├── 100x100.txt
    ├── 500x500.txt
    ├── 50x50.txt
    ├── big3.txt
    ├── bigeurope.txt
    ├── bigstates.txt
    ├── game_of_thrones.txt
    ├── gensin.txt
    ├── small.txt
    ├── smalllangs.txt
    ├── test1.txt
    └── test2.txt
```

## Requirement
1. Java openJdk(11+)

## Instalation
1. Download the java from the java website
1. Install it

## How to use

1. create txt file in test folder with the puzzle format below

```txt
J S O L U T I S
S U N A R U U A
N E P T U N E T
S O N I E I S U
R C E V T R E R
A H T R A E S N
M M E R C U R Y

EARTH
JUPITER
MARS
MERCURY
NEPTUNE
SATURN
URANUS
VENUS
```

2. Put the txt file in the test folder
3. `cd src` and run the app by using `java Wordpuzzle` 
4. If you're using the jar file that available in the github make sure the folder structure are like this
```
..
├── <name of the jar folder>
│   └── word-puzzle.jar
└── test
    ├── test1.txt
    └── test2.txt
```
5. run the jar by using `java -jar <filename.jar>`