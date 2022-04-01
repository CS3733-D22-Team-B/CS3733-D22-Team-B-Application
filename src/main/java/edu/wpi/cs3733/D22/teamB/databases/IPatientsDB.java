package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IPatientsDB {
  public LinkedList<Patient> listPatients();

  public void patientsToCSV();

  public int updatePatient(Patient patObj);

  public int addPatient(Patient patObj);

  public int deletePatient(Patient patObj);

  public void quit();
}
