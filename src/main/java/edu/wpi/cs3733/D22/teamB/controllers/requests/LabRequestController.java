package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.LabRequest;
import edu.wpi.cs3733.D22.teamB.databases.LabRequestsDB;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class LabRequestController extends PatientBasedRequestController {
  @FXML private ComboBox<String> labTestInput;
  @FXML private DatePicker testingTimeInput;
  @FXML private ComboBox<String> labRoomInput;

  private String labTest;
  private Date testingTime;
  private String labRoom;

  private LocationsDB locationsDB;

  public void initialize() {
    locationsDB = LocationsDB.getInstance();
    labRoomInput
        .getItems()
        .addAll(
            locationsDB.list().stream()
                .filter(location -> location.getNodeType().equals("Lab"))
                .map(location -> location.getLongName())
                .collect(Collectors.toList()));
  }

  public void setLabTest() {
    labTest = labTestInput.getValue();
    enableSubmission();
  }

  public void setTestingTime() {
    LocalDate localDate = testingTimeInput.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    testingTime = Date.from(instant);
    enableSubmission();
  }

  public void setLabRoom() {
    labRoom = labRoomInput.getValue();
  }

  public void enableSubmission() {
    setLabTest();
    setTestingTime();
    setNotes();
    if (!patientName.equals("")
        && !labTest.equals("")
        && testingTime != null
        && !labRoom.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    String testRoomID = locationsDB.getLocationID(labRoom);
    LabRequest request = new LabRequest(patientID, labTest, testingTime, testRoomID, notes);
    LabRequestsDB.getInstance().add(request);
    requestLabel.setText(
        "Request sent: "
            + labTest
            + " for "
            + patientName
            + " in "
            + labRoom
            + " on "
            + testingTime);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    labRoomInput.setValue("");
    labTestInput.setValue("");
    testingTimeInput.setValue(null);
    additionalInformationInput.setText("");
    submitButton.setDisable(true);

    labRoom = "";
    labTest = "";
    testingTime = null;
    notes = "";
  }
}
