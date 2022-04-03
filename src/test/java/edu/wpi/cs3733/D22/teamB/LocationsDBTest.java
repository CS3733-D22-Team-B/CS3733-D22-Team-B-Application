package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationsDBTest {

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
  public void updateLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    Location locObj = locDB.list().get(0);

    // Update location in the table (should return 0)
    int success = locDB.update(locObj);
    Assert.assertEquals(0, success);

    locObj.setNodeID("sfn");

    // Update location not in the table (should return -1)
    int failure = locDB.update(locObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  public void addLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    Location locObj = locDB.list().get(0);

    // Add location in the table (should return -1)
    int failure = locDB.add(locObj);
    Assert.assertEquals(-1, failure);

    locObj.setNodeID("sfn");

    // Add location not in the table (should return 0)
    int success = locDB.add(locObj);
    Assert.assertEquals(0, success);
  }

  @Test
  public void deleteLocation() {
    LocationsDB locDB = LocationsDB.getInstance();
    Location locObj = locDB.list().get(0);

    // Delete location in the table (should return 0)
    int success = locDB.delete(locObj);
    Assert.assertEquals(0, success);

    locObj.setNodeID("sfn");

    // Delete location not in the table (should return -1)
    int failure = locDB.delete(locObj);
    Assert.assertEquals(-1, failure);
  }
}
