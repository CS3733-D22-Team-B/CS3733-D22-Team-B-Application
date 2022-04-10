package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.Patient;
import edu.wpi.cs3733.D22.teamB.databases.PatientsDB;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class PatientBasedRequestController extends RequestController {
  @FXML protected ComboBox<String> patientInput;

  protected String patientName;

  protected PatientsDB patientsDB;

  public void initialize() {
    patientsDB = PatientsDB.getInstance();

    for (Patient patient : patientsDB.list()) {
      patientInput.getItems().add(patient.getOverview());
    }
    patientName = patientInput.getValue();
  }

  public void setPatient() {
    patientName = patientInput.getValue();
    enableSubmission();
  }
}
