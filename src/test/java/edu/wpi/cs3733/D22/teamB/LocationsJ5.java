package edu.wpi.cs3733.D22.teamB;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseInitializer;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationsJ5 {

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
  void updateLocation() {
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
  void addLocation() {
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
  void deleteLocation() {
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
