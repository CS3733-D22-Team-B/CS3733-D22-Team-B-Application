package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;

public class PatientsDBTest {
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
  public void updatePatient() {
    PatientsDB patDB = PatientsDB.getInstance();
    Patient patObj = patDB.list().get(0);

    // Update Patient in the table (should return 0)
    int success = patDB.update(patObj);
    Assert.assertEquals(0, success);

    patObj.setPatientID("sfn");

    // Update Patient not in the table (should return -1)
    int failure = patDB.update(patObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  public void addPatient() {
    PatientsDB patDB = PatientsDB.getInstance();
    Patient patObj = patDB.list().get(0);

    // Add Patient in the table (should return -1)
    int failure = patDB.add(patObj);
    Assert.assertEquals(-1, failure);

    patObj.setPatientID("dfdf");

    // Add Patient not in the table (should return 0)
    int success = patDB.add(patObj);
    Assert.assertEquals(0, success);
  }

  @Test
  public void deletePatient() {
    PatientsDB patDB = PatientsDB.getInstance();
    Patient patObj = patDB.list().get(0);

    // Delete Patient in the table (should return 0)
    int success = patDB.delete(patObj);
    Assert.assertEquals(0, success);

    patObj.setPatientID("sfn");

    // Delete Patient not in the table (should return -1)
    int failure = patDB.delete(patObj);
    Assert.assertEquals(-1, failure);
  }

   */
}
