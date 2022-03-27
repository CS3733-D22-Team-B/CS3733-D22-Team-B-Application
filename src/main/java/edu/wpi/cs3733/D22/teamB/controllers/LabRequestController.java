package edu.wpi.cs3733.D22.teamB.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LabRequestController {
  @FXML private Label resultLabel;
  @FXML private ComboBox patientNameComboBox;
  @FXML private ComboBox labTestComboBox;
  @FXML private DatePicker testingDatePicker;

  private String patientName;
  private String labTestName;
  private Date testingTime;

  @FXML
  void setPatient(ActionEvent event) {
    patientName = patientNameComboBox.getValue().toString();
  }

  @FXML
  void setLabTest(ActionEvent event) {
    labTestName = labTestComboBox.getValue().toString();
  }

  @FXML
  void setTestingTime(ActionEvent event) {
    LocalDate localDate = testingDatePicker.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    testingTime = Date.from(instant);
  }

  @FXML
  void sendRequest() {
    resultLabel.setText(
        "Request sent: " + labTestName + " for " + patientName + " on " + testingTime);
  }

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }
}
