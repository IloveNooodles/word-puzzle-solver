package Algo;

public class Puzzle {
  private int row, col;
  private String puzzle[][];

  // counstructor
  public Puzzle() {
    this.row = 0;
    this.col = 0;
  }

  public Puzzle(int row, int col) {
    this.row = row;
    this.col = col;
    this.puzzle = new String[row][col];
  }

  public String getString(int row, int col) {
    return this.puzzle[row][col];
  }

  public void setElmt(int row, int col, String currentChar) {
    this.puzzle[row][col] = currentChar;
  }
}
