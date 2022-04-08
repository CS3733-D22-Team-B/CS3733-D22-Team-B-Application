package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InternalPatientTransferController extends PatientAndLocationBasedRequestController {
  @FXML
  public void enableSubmission() {
    setLocation();
    if (!patientName.equals("") && !locationName.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    String destinationID = locationsDAO.getLocationID(locationName);
    InternalPatientTransferRequest request =
        new InternalPatientTransferRequest(patientName, destinationID);
    RequestQueue.addRequest(request);
    requestLabel.setText("Request sent: Moving " + patientName + " to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    locationInput.setValue("");
    patientInput.setValue("");
    submitButton.setDisable(true);

    floor = "";
    locationName = "";
    patientName = "";
  }
}
