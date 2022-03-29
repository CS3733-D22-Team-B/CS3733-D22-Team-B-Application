package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface LocationDAOImpl {
  public LinkedList<Location> listLocations();

  public void locationsToCSV();

  public int updateLocation(Location locObj);

  public int addLocation(Location locObj);

  public int deleteLocation(Location locObj);
}
