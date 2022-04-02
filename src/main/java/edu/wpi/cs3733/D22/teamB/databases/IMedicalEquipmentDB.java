package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IMedicalEquipmentDB {
  public LinkedList<MedicalEquipment> listMedicalEquipment();

  public LinkedList<MedicalEquipment> searchFor(String input);

  public int updateMedicalEquipment(MedicalEquipment meObj);

  public int addMedicalEquipment(MedicalEquipment meObj);

  public int deleteMedicalEquipment(MedicalEquipment meObj);

  public void quit();
}
