package edu.wpi.cs3733.D22.teamB;

import javafx.beans.property.SimpleStringProperty;

public class RequestStatusModel {
  private SimpleStringProperty status;
  private SimpleStringProperty type;
  private SimpleStringProperty assigned;

  public RequestStatusModel(String stat, String tp, String assign) {
    status = new SimpleStringProperty(stat);
    type = new SimpleStringProperty(tp);
    assigned = new SimpleStringProperty(assign);
  }

  public void setStatus(String stat) {
    status = new SimpleStringProperty(stat);
  }

  public void setType(String tp) {
    type = new SimpleStringProperty(tp);
  }

  public void setAssigned(String assign) {
    assigned = new SimpleStringProperty(assign);
  }

  public String getStatus() {
    return status.get();
  }

  public String getType() {
    return type.get();
  }

  public String getAssigned() {
    return assigned.get();
  }
}
