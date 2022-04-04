package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;

public class MedicalEquipmentDBTest {
  /*

  @Before
  public void setUp() throws Exception {
    new DatabaseInitializer();
  }

  @After
  public void tearDown() throws Exception {
    LocationsDB locDB = LocationsDB.getInstance();
    MedicalEquipmentDB medEqDB = MedicalEquipmentDB.getInstance();
    PatientsDB patDB = PatientsDB.getInstance();

    patDB.quit();
    medEqDB.quit();
    locDB.quit();
  }

  @Test
  public void updateMedicalEquipment() {
    MedicalEquipmentDB medDB = MedicalEquipmentDB.getInstance();
    MedicalEquipment medObj = medDB.list().get(0);

    // Update MedicalEquipment in the table (should return 0)
    int success = medDB.update(medObj);
    Assert.assertEquals(0, success);

    medObj.setEquipmentID("sfn");

    // Update MedicalEquipment not in the table (should return -1)
    int failure = medDB.update(medObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  public void addMedicalEquipment() {
    MedicalEquipmentDB medDB = MedicalEquipmentDB.getInstance();
    MedicalEquipment medObj = medDB.list().get(0);

    // Add MedicalEquipment in the table (should return -1)
    int failure = medDB.add(medObj);
    Assert.assertEquals(-1, failure);

    medObj.setEquipmentID("sfn");

    // Add MedicalEquipment not in the table (should return 0)
    int success = medDB.add(medObj);
    Assert.assertEquals(0, success);
  }

  @Test
  public void deleteMedicalEquipment() {
    MedicalEquipmentDB medDB = MedicalEquipmentDB.getInstance();
    MedicalEquipment medObj = medDB.list().get(0);

    // Delete MedicalEquipment in the table (should return 0)
    int success = medDB.delete(medObj);
    Assert.assertEquals(0, success);

    medObj.setEquipmentID("sfn");

    // Delete MedicalEquipment not in the table (should return -1)
    int failure = medDB.delete(medObj);
    Assert.assertEquals(-1, failure);
  }

   */
}
