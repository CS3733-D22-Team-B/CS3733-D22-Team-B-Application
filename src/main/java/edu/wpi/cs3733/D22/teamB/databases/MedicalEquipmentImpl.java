package edu.wpi.teamname;

import java.util.LinkedList;

public interface MedicalEquipmentImpl {
  public LinkedList<MedicalEquipment> listMedicalEquipment();

  public void medicalEquipmentToCSV();

  public int updateMedicalEquipment(MedicalEquipment meObj);

  public int addMedicalEquipment(MedicalEquipment meObj);

  public int deleteMedicalEquipment(MedicalEquipment meObj);
}
