package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IDatabases<L> {
  public LinkedList<L> list();

  public LinkedList<L> listByAttribute(String attribute, String value);

  public LinkedList<L> listByAttribute(String attribute, int value);

  public LinkedList<L> listByAttribute(String attribute, boolean value);

  public L getByID(String id);

  public LinkedList<L> searchFor(String input);

  public int update(L locObj);

  public int add(L locObj);

  public int delete(L locObj);

  public void quit();
}
