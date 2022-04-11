package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.MedicineRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MedicineRequestController extends PatientBasedRequestController {
  @FXML private ComboBox<String> medicineInput;

  private String medicine = "";

  public void setMedication() {
    medicine = medicineInput.getValue();
  }

  @FXML
  public void enableSubmission() {
    setMedication();
    setNotes();
    if (!patientName.equals("") && !medicine.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    MedicineRequest request = new MedicineRequest(patientID, medicine);
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
