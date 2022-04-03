package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IDatabases<L> {
  public LinkedList<L> list();

  public L getByID(String id);

  public int update(L locObj);

  public int add(L locObj);

  public int delete(L locObj);

  public void quit();
}
