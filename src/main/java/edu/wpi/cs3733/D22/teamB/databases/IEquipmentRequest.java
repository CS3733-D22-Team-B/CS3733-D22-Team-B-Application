package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public interface IEquipmentRequest {
  public LinkedList<EquipmentRequest> listEquipmentRequests();

  public int updateEquipmentRequest(EquipmentRequest eqreqObj);

  public int addEquipmentRequest(EquipmentRequest eqreqObj);

  public int deleteEquipmentRequest(EquipmentRequest eqreqObj);

  public void quit();
}
