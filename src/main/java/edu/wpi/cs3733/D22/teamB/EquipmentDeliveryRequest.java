package edu.wpi.cs3733.D22.teamB;

public class EquipmentDeliveryRequest {
  private String equipment;
  // TODO: location l;
  public EquipmentDeliveryRequest(String equip) {
    equipment = equip;
  }

  public void setEquipment(String equip) {
    equipment = equip;
  }

  public String getEquipment() {
    return equipment;
  }
  // TODO: location object get/set
}
