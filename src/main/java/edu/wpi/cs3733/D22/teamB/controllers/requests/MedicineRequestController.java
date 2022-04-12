package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.MedicineRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MedicineRequestController extends PatientBasedRequestController {
  @FXML private ComboBox<String> medicineInput;

  private String medicine = "";

  public void initialize() {
    super.initialize();

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

  public void setMedication() {
    medicine = medicineInput.getValue();
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
    if (!patientName.equals("") && !medicine.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    MedicineRequest request =
        new MedicineRequest(
            patientID,
            "Medicine Request: " + medicine + "\n\nAdditional Information: " + notes,
            (int) prioritySlider.getValue());
    ServiceRequestsDB.getInstance().add(request);
    requestLabel.setText("Request sent: " + medicine + " to " + patientName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    medicineInput.getSelectionModel().clearSelection();
    medicine = "";
  }
}
