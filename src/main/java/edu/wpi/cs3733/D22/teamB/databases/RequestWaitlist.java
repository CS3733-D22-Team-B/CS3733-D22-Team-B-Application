package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public class RequestWaitlist {

  private static LinkedList<WaitlistObject> waitlist = new LinkedList<>();
  private static RequestWaitlist queue = new RequestWaitlist();

  private RequestWaitlist() {}

  public static RequestWaitlist getQueue() {
    return queue;
  }

  public static void add(WaitlistObject obj) {
    waitlist.add(obj);
  }

  public static void remove(WaitlistObject obj) {
    waitlist.remove(obj);
  }

  public static void createRequest(MedicalEquipment equipment) {
    for (int i = 0; i < waitlist.size(); i++) {
      if (equipment.getType().equalsIgnoreCase(waitlist.get(i).getType())) {
        EquipmentRequest newEqReq =
            new EquipmentRequest(
                waitlist.get(i).getLocationID(),
                equipment.getEquipmentID(),
                "This is an auto-generated request created from the request waiting list. ",
                4);
        DatabaseController.getInstance().add(newEqReq);
        waitlist.remove(waitlist.get(i));
        break;
      }
    }
  }

  public static LinkedList<WaitlistObject> getWaitlist() {
    return waitlist;
  }

  public static void clear() {
    waitlist.clear();
  }
}
