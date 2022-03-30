package edu.wpi.cs3733.D22.teamB;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDAO;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicalEquipmentJ5 {

  @BeforeEach
  void setUp() {
    new DatabaseInitializer();
  }

  @AfterEach
  void tearDown() {
    // Parse the CSVs again to reset the database
    new DatabaseInitializer();
  }

  @Test
  void updateMedicalEquipment() {
    MedicalEquipmentDAO medDB = new MedicalEquipmentDAO();
    MedicalEquipment medObj = medDB.listMedicalEquipment().get(0);

    // Update MedicalEquipment in the table (should return 0)
    int success = medDB.updateMedicalEquipment(medObj);
    Assert.assertEquals(0, success);

    medObj.setEquipmentID("sfn");

    // Update MedicalEquipment not in the table (should return -1)
    int failure = medDB.updateMedicalEquipment(medObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  void addMedicalEquipment() {
    MedicalEquipmentDAO medDB = new MedicalEquipmentDAO();
    MedicalEquipment medObj = medDB.listMedicalEquipment().get(0);

    // Add MedicalEquipment in the table (should return -1)
    int failure = medDB.addMedicalEquipment(medObj);
    Assert.assertEquals(-1, failure);

    medObj.setEquipmentID("sfn");

    // Add MedicalEquipment not in the table (should return 0)
    int success = medDB.addMedicalEquipment(medObj);
    Assert.assertEquals(0, success);
  }

  @Test
  void deleteMedicalEquipment() {
    MedicalEquipmentDAO medDB = new MedicalEquipmentDAO();
    MedicalEquipment medObj = medDB.listMedicalEquipment().get(0);

    // Delete MedicalEquipment in the table (should return 0)
    int success = medDB.deleteMedicalEquipment(medObj);
    Assert.assertEquals(0, success);

    medObj.setEquipmentID("sfn");

    // Delete MedicalEquipment not in the table (should return -1)
    int failure = medDB.deleteMedicalEquipment(medObj);
    Assert.assertEquals(-1, failure);
  }
}
