package edu.wpi.cs3733.D22.teamB;

import static org.junit.Assert.*;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationsDAOTest {

  @Before
  public void setUp() throws Exception {
    new DatabaseInitializer();
  }

  @After
  public void tearDown() throws Exception {
    // Parse the CSVs again to reset the database
    new DatabaseInitializer();
  }

  @Test
  public void updateLocation() {
    LocationsDAO locDB = new LocationsDAO();
    Location locObj = locDB.listLocations().get(0);

    // Update location in the table (should return 0)
    int success = locDB.updateLocation(locObj);
    Assert.assertEquals(0, success);

    locObj.setNodeID("sfn");

    // Update location not in the table (should return -1)
    int failure = locDB.updateLocation(locObj);
    Assert.assertEquals(-1, failure);
  }

  @Test
  public void addLocation() {
    LocationsDAO locDB = new LocationsDAO();
    Location locObj = locDB.listLocations().get(0);

    // Add location in the table (should return -1)
    int failure = locDB.addLocation(locObj);
    Assert.assertEquals(-1, failure);

    locObj.setNodeID("sfn");

    // Add location not in the table (should return 0)
    int success = locDB.addLocation(locObj);
    Assert.assertEquals(0, success);
  }

  @Test
  public void deleteLocation() {
    LocationsDAO locDB = new LocationsDAO();
    Location locObj = locDB.listLocations().get(0);

    // Delete location in the table (should return 0)
    int success = locDB.deleteLocation(locObj);
    Assert.assertEquals(0, success);

    locObj.setNodeID("sfn");

    // Delete location not in the table (should return -1)
    int failure = locDB.deleteLocation(locObj);
    Assert.assertEquals(-1, failure);
  }
}
