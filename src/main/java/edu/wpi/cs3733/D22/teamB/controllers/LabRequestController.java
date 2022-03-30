package edu.wpi.cs3733.D22.teamB.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class LabRequestController extends RequestController {
  @FXML private ComboBox<String> labTestInput;
  @FXML private DatePicker testingTimeInput;

  private String labTest;
  private Date testingTime;

  public void setLabTest() {
    labTest = labTestInput.getValue();
  }

  public void setTestingTime() {
    LocalDate localDate = testingTimeInput.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    testingTime = Date.from(instant);
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setLabTest();
    setTestingTime();
    requestLabel.setText("Request sent: " + labTest + " for " + room + " on " + testingTime);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    employeeNameInput.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    labTestInput.setValue("");
    testingTimeInput.setValue(null);

    employeeName = "";
    floor = "";
    room = "";
    labTest = "";
    testingTime = null;
  }
}
