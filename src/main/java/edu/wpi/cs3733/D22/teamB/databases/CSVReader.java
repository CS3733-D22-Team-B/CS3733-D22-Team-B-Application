package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVReader {

  public BufferedReader read(String filepath) throws IOException {
    InputStream is = getClass().getResourceAsStream(filepath);
    if (is == null) {
      throw new IOException();
    }
    BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
    return lineReader;
  }
}
