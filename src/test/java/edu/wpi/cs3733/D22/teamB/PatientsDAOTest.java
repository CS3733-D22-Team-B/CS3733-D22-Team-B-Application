package edu.wpi.cs3733.D22.teamB;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamB.databases.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatientsDAOTest {

  @Before
  public void setUp() throws Exception {
    new DatabaseInitializer();
  }

  @After
  public void tearDown() throws Exception {
    LocationsDAO locDB = new LocationsDAO();
    MedicalEquipmentDAO medEqDB = new MedicalEquipmentDAO();
    PatientsDAO patDB = new PatientsDAO();

    patDB.quit();
    medEqDB.quit();
    locDB.quit();
  }

  @Test
  public void updatePatient() {
    PatientsDAO patDB = new PatientsDAO();
    Patient patObj = patDB.listPatients().get(0);

    // Update Patient in the table (should return 0)
    int success = patDB.updatePatient(patObj);
    Assert.assertEquals(0, success);

    patObj.setPatientID("sfn");

    // Update Patient not in the table (should return -1)
    int failure = patDB.updatePatient(patObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  public void addPatient() {
    PatientsDAO patDB = new PatientsDAO();
    Patient patObj = patDB.listPatients().get(0);

    // Add Patient in the table (should return -1)
    int failure = patDB.addPatient(patObj);
    Assert.assertEquals(-1, failure);

    patObj.setPatientID("dfdf");

    // Add Patient not in the table (should return 0)
    int success = patDB.addPatient(patObj);
    Assert.assertEquals(0, success);
  }

  @Test
  public void deletePatient() {
    PatientsDAO patDB = new PatientsDAO();
    Patient patObj = patDB.listPatients().get(0);

    // Delete Patient in the table (should return 0)
    int success = patDB.deletePatient(patObj);
    Assert.assertEquals(0, success);

    patObj.setPatientID("sfn");

    // Delete Patient not in the table (should return -1)
    int failure = patDB.deletePatient(patObj);
    Assert.assertEquals(-1, failure);
  }
}
