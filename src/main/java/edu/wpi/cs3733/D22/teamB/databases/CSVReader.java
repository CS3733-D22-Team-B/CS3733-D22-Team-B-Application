package edu.wpi.cs3733.D22.teamB.databases;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVReader {
  private String locationCSVFilePath = "CSVs/ApplicationLocations.csv";
  private String medicalEQCSVFilePath = "CSVs/ApplicationMedicalEquipment.csv";
  private String employeesCSVFilePath = "CSVs/ApplicationEmployees.csv";
  private String patientsCSVFilePath = "CSVs/ApplicationPatients.csv";
  private String equipmentRequestCSVFilePath = "CSVs/ApplicationEquipmentRequest.csv";
  private String labRequestCSVFilePath = "CSVs/ApplicationLabRequest.csv";
  private String serviceRequestCSVFilePath = "CSVs/ApplicationServiceRequest.csv";

  public void test(String filepath) {
    InputStream is = getClass().getResourceAsStream(filepath);
    BufferedReader lineReader = new BufferedReader(new InputStreamReader(is));
    System.out.println(is);
    try {
      String line = lineReader.readLine();
      System.out.println(line);
    } catch (Exception e) {
    }
  }

  public void bigTest() {
    test(locationCSVFilePath);
    test(medicalEQCSVFilePath);
    test(employeesCSVFilePath);
    test(patientsCSVFilePath);
    test(equipmentRequestCSVFilePath);
    test(labRequestCSVFilePath);
    test(serviceRequestCSVFilePath);
  }
}
