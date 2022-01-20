package Utility;

import java.io.*;
import Algo.Matrix;

public class IO {
  public static File[] getListDir() {
    File currentDirectory = new File("test");
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

  public static int rowCur(String fileName) {
    int rowCount = 0;
    try {
      FileReader reader = new FileReader(String.format("test/%s.txt", fileName));
      BufferedReader bufferedReader = new BufferedReader(reader);

      while (bufferedReader.readLine() != null) {
        rowCount++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rowCount;
  }

  public static int colCur(String fileName) {
    int colCount = 0;
    try {
      FileReader reader = new FileReader(String.format("test/%s.txt", fileName));
      BufferedReader bufferedReader = new BufferedReader(reader);

      String readLine = bufferedReader.readLine();
      String[] line = readLine.split(" ");
      colCount = line.length;
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return colCount;
  }

  public static Matrix textToMatrix(String fileName) {
    Matrix puzzle = new Matrix(rowCur(fileName), rowCur(fileName));
    try {
      String line;
      int row = 0;
      FileReader reader = new FileReader(String.format("test/%s.txt", fileName));
      BufferedReader bufferedReader = new BufferedReader(reader);
      while ((line = bufferedReader.readLine()) != null) {
        String[] lines = line.split(" ");
        for (int i = 0; i < lines.length; i++) {
          puzzle.setElmt(row, i, lines[i]);
        }
        row++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return puzzle;
  }
}
