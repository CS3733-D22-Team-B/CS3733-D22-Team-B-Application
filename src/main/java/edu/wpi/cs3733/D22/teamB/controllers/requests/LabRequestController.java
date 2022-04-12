package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.LabRequest;
import edu.wpi.cs3733.D22.teamB.databases.LabRequestsDB;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class LabRequestController extends PatientBasedRequestController {
  @FXML private ComboBox<String> labTestInput;
  @FXML private DatePicker testingDateInput;
  @FXML private ComboBox<String> testingTimeInput;
  @FXML private ComboBox<String> labRoomInput;

  private String labTest = "";
  private Date testingDate = null;
  private String labRoom = "";

  private LocationsDB locationsDB;

  public void initialize() {
    super.initialize();
    locationsDB = LocationsDB.getInstance();
    List<String> l =
        locationsDB.list().stream()
            .filter(location -> location.getNodeType().equalsIgnoreCase("Labs"))
            .map(location -> location.getLongName())
            .collect(Collectors.toList());
    labRoomInput.getItems().addAll(l);

    additionalInformationInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setNotes();
              }
            });
  }

  public void setLabTest() {
    labTest = labTestInput.getValue();
    enableSubmission();
  }

  public void setTestingTime() {
    LocalDate localDate = testingDateInput.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    testingDate = Date.from(instant);

    String testingTime =
        (testingTimeInput.getValue() == null) ? "00:00" : testingTimeInput.getValue();
    testingDate.setTime(Timestamp.valueOf("2022-04-12 " + testingTime + ":00.000").getTime());

    enableSubmission();
  }

  public void setLabRoom() {
    labRoom = labRoomInput.getValue();
  }

  public void enableSubmission() {
    if (!patientName.equals("")
        && !labTest.equals("")
        && testingDate != null
        && !labRoom.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    String testRoomID = locationsDB.getLocationID(labRoom);
    LabRequest request =
        new LabRequest(
            patientID, labTest, testingDate, testRoomID, notes, (int) prioritySlider.getValue());
    LabRequestsDB.getInstance().add(request);
    requestLabel.setText(
        "Request sent: "
            + labTest
            + " for "
            + patientName
            + " in "
            + labRoom
            + " on "
            + testingDate);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    labRoomInput.getSelectionModel().clearSelection();
    labTestInput.getSelectionModel().clearSelection();
    testingTimeInput.setValue(null);

    labRoom = "";
    labTest = "";
    testingDate = null;
  }
}
