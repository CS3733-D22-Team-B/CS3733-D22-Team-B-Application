package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVReader {
  /*
  private String locationCSVFilePath = "CSVs/Locations.csv";
  private String medicalEQCSVFilePath = "CSVs/MedicalEquipment.csv";
  private String employeesCSVFilePath = "CSVs/Employees.csv";
  private String patientsCSVFilePath = "CSVs/Patients.csv";
  private String equipmentRequestCSVFilePath = "CSVs/EquipmentRequest.csv";
  private String labRequestCSVFilePath = "CSVs/LabRequest.csv";
  private String serviceRequestCSVFilePath = "CSVs/ServiceRequest.csv";

  public void test(String filepath) {
    InputStream is = getClass().getResourceAsStream(filepath);
    BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
    System.out.println(is);

    try {
      String line = lineReader.readLine();
      while (line != null) {
        line = lineReader.readLine();
        System.out.println(line);
      }
    } catch (Exception e) {
    }
  }

  public void bigTest() {
    // test(locationCSVFilePath);
    // test(medicalEQCSVFilePath);
    // test(employeesCSVFilePath);
    // test(patientsCSVFilePath);
    // test(equipmentRequestCSVFilePath);
    // test(labRequestCSVFilePath);
    test(serviceRequestCSVFilePath);
  }
   */

  public BufferedReader read(String filepath) throws IOException {
    InputStream is = getClass().getResourceAsStream(filepath);
    if (is == null) {
      throw new IOException();
    }
    BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
    return lineReader;
  }
}
