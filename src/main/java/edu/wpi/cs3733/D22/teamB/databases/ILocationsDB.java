package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface ILocationsDB {
  public LinkedList<Location> listLocations();

  public LinkedList<Location> searchFor(String input);

  public int updateLocation(Location locObj);

  public int addLocation(Location locObj);

  public int deleteLocation(Location locObj);

  public void quit();
}
