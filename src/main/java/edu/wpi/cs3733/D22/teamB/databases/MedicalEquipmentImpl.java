package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface MedicalEquipmentImpl {
  public LinkedList<MedicalEquipment> listMedicalEquipment();

  public void medicalEquipmentToCSV();

  public int updateMedicalEquipment(MedicalEquipment meObj);

  public int addMedicalEquipment(MedicalEquipment meObj);

  public int deleteMedicalEquipment(MedicalEquipment meObj);

  public void quit();
}
