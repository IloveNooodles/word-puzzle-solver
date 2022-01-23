import java.util.*;
import java.io.*;

public class Wordpuzzle {

  public static class ConsoleColor {
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";

    public static void formatText(Character text, int number) {
      String[] colors = {
          RED,
          GREEN,
          YELLOW,
          BLUE,
          PURPLE,
          CYAN,
          RED_BOLD,
          GREEN_BOLD,
          YELLOW_BOLD,
          BLUE_BOLD,
          PURPLE_BOLD,
          CYAN_BOLD,
      };
      System.out.print(colors[number] + text + RESET);
    }

    public static void formatString(String text, int number) {
      String[] colors = {
          RED,
          GREEN,
          YELLOW,
          BLUE,
          PURPLE,
          CYAN,
          RED_BOLD,
          GREEN_BOLD,
          YELLOW_BOLD,
          BLUE_BOLD,
          PURPLE_BOLD,
          CYAN_BOLD,
      };
      System.out.print(colors[number] + text + RESET);
    }
  }

  public static class Puzzle {
    private ArrayList<ArrayList<Character>> puzzle = new ArrayList<ArrayList<Character>>();
    private ArrayList<ArrayList<Integer>> colorCodes = new ArrayList<ArrayList<Integer>>();
    private ArrayList<String> WordList = new ArrayList<String>();

    public void displayWordList() {
      try {
        for (int index = 0; index < this.WordList.size(); index++) {
          System.out.print(this.WordList.get(index));
          if (index != this.WordList.size() - 1) {
            System.out.print(" ");
          }
        }
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    public void displayPuzzle() {
      try {
        for (int row = 0; row < this.puzzle.size(); row++) {
          for (int col = 0; col < this.puzzle.get(row).size(); col++) {
            int currentColor = this.colorCodes.get(row).get(col);
            char currentChar = this.puzzle.get(row).get(col);
            if (currentColor != -1) {
              ConsoleColor.formatText(currentChar, currentColor);
            } else {
              System.out.print(currentChar);
            }

            if (col != this.puzzle.get(row).size() - 1) {
              System.out.print(" ");
            }
          }
          System.out.println("");
        }
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    public void displayColorCodes() {
      try {
        for (int row = 0; row < this.colorCodes.size(); row++) {
          for (int col = 0; col < this.colorCodes.get(row).size(); col++) {
            System.out.print(this.colorCodes.get(row).get(col));
            if (col != this.colorCodes.get(row).size() - 1) {
              System.out.print(" ");
            }
          }
          System.out.println("");
        }
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

  }

  public static class IO {
    public static String menu() {
      System.out.println("Welcome to word-puzzle solver");
      System.out.println(
          "To get started, put the file in the test directory. Make sure the format is correct otherwise it won't work");
      System.out.println("What do you wan't to do? (Please select the number ex: 2)");
      System.out.println("1. Use ");
      System.out.println("2. Exit");
      System.out.print("Select the number: ");
      Scanner scan = new Scanner(System.in);
      int choice = scan.nextInt();
      System.out.println("==================================");
      if (choice == 2) {
        System.out.println("Thank you for using word-puzzle solver!");
        scan.close();
        System.exit(0);
      } else if (choice == 1) {
        System.out.println("Please select file that you wan't to use");
        printListDir();
        System.out.print("Select the number: ");
        choice = scan.nextInt();
        while (choice > getListDir().length) {
          System.out.print("Select the number: ");
          choice = scan.nextInt();
        }
        scan.close();
      }
      return getListDir()[choice - 1].getName();
    }

    public static File[] getListDir() {
      File currentDirectory = new File("../test");
      File[] listFiles = currentDirectory.listFiles();
      return listFiles;
    }

    public static void printListDir() {
      File[] listFiles = getListDir();
      if (listFiles != null) {
        for (int i = 0; i < listFiles.length; i++) {
          System.out.println(String.format("%d. %s", i + 1, listFiles[i].getName()));
        }
      } else {
        System.out.println("Direktori Folder Kosong");
      }
    }

    public static Puzzle textToPuzzle(String fileName) {
      Puzzle puzzle = new Puzzle();
      try {
        FileReader reader = new FileReader(String.format("../test/%s", fileName));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line.length() != 0) {
          char[] lines = line.replaceAll("\\W", "").toCharArray();
          ArrayList<Character> row = new ArrayList<Character>();
          ArrayList<Integer> rowInt = new ArrayList<Integer>();
          for (int i = 0; i < lines.length; i++) {
            row.add(lines[i]);
            rowInt.add(-1);
          }
          puzzle.puzzle.add(row);
          puzzle.colorCodes.add(rowInt);
          line = bufferedReader.readLine();
        }
        while ((line = bufferedReader.readLine()) != null) {
          puzzle.WordList.add(line);
        }
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return puzzle;
    }
  }

  public static class Solver {

    // 8 direction
    private int[] x = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private int[] y = { 0, 1, 1, 1, 0, -1, -1, -1 };
    private ArrayList<Integer> result = new ArrayList<Integer>();

    Puzzle puzzle = new Puzzle();

    public Solver(String fileName) {
      puzzle = IO.textToPuzzle(fileName);
    }

    public void solvePuzzle() {
      long startTime = System.nanoTime();
      int wordCount = 0;
      int comparison = 0;
      int tempVar = 0;
      for (String word : puzzle.WordList) {
        int localComparison = 0;
        boolean found = false;
        for (int row = 0; row < puzzle.puzzle.size(); row++) {
          for (int col = 0; col < puzzle.puzzle.get(row).size(); col++) {
            localComparison++;
            comparison++;
            if (word.charAt(0) == puzzle.puzzle.get(row).get(col)) {
              int counter = 1;
              int dir = 0;
              int multiplier = 1;
              while (dir != 8 || counter == word.length()) {
                try {
                  char currentChar = puzzle.puzzle.get(row + multiplier * x[dir]).get(col + multiplier * y[dir]);
                  localComparison++;
                  comparison++;
                  if (currentChar == word.charAt(counter)) {
                    multiplier++;
                    counter++;
                    if (counter == word.length()) {
                      for (int i = 0; i < word.length(); i++) {
                        puzzle.colorCodes.get(row + i * x[dir]).set(col + i * y[dir], wordCount % 12);
                      }
                      tempVar = localComparison;
                      found = true;
                      wordCount++;
                      break;
                    }
                  } else {
                    counter = 1;
                    multiplier = 1;
                    dir++;
                  }
                } catch (Exception err) {
                  counter = 1;
                  multiplier = 1;
                  dir++;
                }
              }
            }
            if (found) {
              break;
            }
          }
          if (found) {
            break;
          }
        }
        if (found) {
          this.result.add(tempVar);
        } else {
          this.result.add(-1);
        }
      }
      long endTime = System.nanoTime();
      double time = (double) (endTime - startTime) / 1000000;
      System.out.println("==================================");
      System.out.println();
      System.out.println("Your puzzle has been solved");
      System.out.println("Here's the result:");
      System.out.println();
      puzzle.displayPuzzle();
      System.out.println("==================================");
      System.out.println(String.format("Word found (%d/%d): ", wordCount, puzzle.WordList.size()));
      for (int i = 0; i < puzzle.WordList.size(); i++) {
        int color = result.get(i);
        ConsoleColor.formatString(puzzle.WordList.get(i), i % 12);
        if (color != -1) {
          System.out.println(String.format(": %d comparison", color));
        } else {
          System.out.println(String.format(": NOT FOUND"));
        }
      }
      System.out.println("==================================");
      System.out.println(String.format("Time taken: %f ms", time));
      System.out.println(String.format("Total Comparison: %d comparison had been done", comparison));
      System.out.println("Thank you for using word-puzzle solver!");
    }
  }

  public static void main(String[] args) {
    Solver solve = new Solver(IO.menu());
    solve.solvePuzzle();
  }
}
