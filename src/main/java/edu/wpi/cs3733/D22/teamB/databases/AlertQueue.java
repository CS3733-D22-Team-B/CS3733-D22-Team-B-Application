package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public class AlertQueue {
  private static LinkedList<EquipmentAlert> alerts = new LinkedList<>();
  private static AlertQueue queue = new AlertQueue();

  private AlertQueue() {}

  public static AlertQueue getQueue() {
    return queue;
  }

  public static void addAlert(EquipmentAlert alert) {
    alerts.add(alert);
  }

  public static void removeAlert(EquipmentAlert alert) {
    alerts.remove(alert);
  }

  public static LinkedList<EquipmentAlert> getAlerts() {
    return alerts;
  }

  public static void clear() {
    alerts.clear();
  }
}
