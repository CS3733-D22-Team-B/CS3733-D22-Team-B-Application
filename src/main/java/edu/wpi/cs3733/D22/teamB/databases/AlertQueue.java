package edu.wpi.cs3733.D22.teamB.databases;

import java.util.LinkedList;

public class AlertQueue {
  private static LinkedList<Alert> alerts = new LinkedList<>();
  private static AlertQueue queue = new AlertQueue();

  private AlertQueue() {}

  public static AlertQueue getQueue() {
    return queue;
  }

  public static void addAlert(Alert alert) {
    alerts.add(alert);
  }

  public static void removeAlert(Alert alert) {
    alerts.remove(alert);
  }

  public static LinkedList<Alert> getAlerts() {
    return alerts;
  }

  public static void clear() {
    alerts.clear();
  }
}
