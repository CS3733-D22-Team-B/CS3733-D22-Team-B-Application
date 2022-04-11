package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.Patient;
import edu.wpi.cs3733.D22.teamB.databases.PatientsDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class PatientBasedRequestController extends RequestController {
  @FXML protected ComboBox<String> patientInput;

  protected String patientName = "";

  protected PatientsDB patientsDB;

  public void initialize() {
    super.initialize();
    patientsDB = PatientsDB.getInstance();

    for (Patient patient : patientsDB.list()) {
      patientInput.getItems().add(patient.getOverview());
    }
  }

  public void setPatient() {
    patientName = patientInput.getValue();
    enableSubmission();
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    patientInput.getSelectionModel().clearSelection();
    additionalInformationInput.clear();
    prioritySlider.setValue(1);
    submitButton.setDisable(true);

    patientName = "";
    notes = "";
  }
}
