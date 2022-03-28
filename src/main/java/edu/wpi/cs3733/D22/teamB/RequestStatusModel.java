package edu.wpi.cs3733.D22.teamB;

import javafx.beans.property.SimpleStringProperty;

public class RequestStatusModel {
  private SimpleStringProperty status;
  private SimpleStringProperty type;

  public RequestStatusModel(String stat, String tp) {
    status = new SimpleStringProperty(stat);
    type = new SimpleStringProperty(tp);
  }

  public void setStatus(String stat) {
    status = new SimpleStringProperty(stat);
  }

  public void setType(String tp) {
    type = new SimpleStringProperty(tp);
  }

  public String getStatus() {
    return status.get();
  }

  public String getType() {
    return type.get();
  }
}
