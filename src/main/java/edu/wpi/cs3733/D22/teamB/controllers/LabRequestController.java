package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.LabRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class LabRequestController extends RequestController {
  @FXML private ComboBox<String> labTestInput;
  @FXML private DatePicker testingTimeInput;
  @FXML private ComboBox<String> testRoomInput;

  private String labTest;
  private Date testingTime;
  private String testRoom;

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
    setEmployeeName();
    setLabTest();
    setTestingTime();

    String locationID = dao.getLocationID(room);
    String testRoomID = dao.getLocationID(testRoom);
    LabRequest request = new LabRequest(locationID, labTest, testingTime, testRoomID);

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
