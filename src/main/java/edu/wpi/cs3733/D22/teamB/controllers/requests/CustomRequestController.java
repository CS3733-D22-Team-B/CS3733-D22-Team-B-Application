package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.CustomRequest;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomRequestController extends PatientAndLocationBasedRequestController {
  @FXML private TextField typeInput;

  private String type = "";

  @FXML
  public void setType() {
    type = typeInput.getText();
  }

  @FXML
  public void setNotes() {
    notes = additionalInformationInput.getText();
  }

  @FXML
  public void enableSubmission() {
    setType();
    setNotes();
    if ((!locationName.equals("") || !patientName.equals(""))
        && !type.equals("")
        && !notes.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent event) {
    Request request;
    if (!locationName.equals("")) {
      String locationID = locationsDAO.getLocationID(locationName);
      if (!patientName.equals("")) {
        String patientID = patientsDB.getPatientID(patientName);
        request = new CustomRequest(locationID, patientID, type, notes);
        requestLabel.setText(
            "Custom request sent: " + notes + " for " + patientName + " at " + locationName);
      } else {
        request = new CustomRequest(locationID, null, type, notes);
        requestLabel.setText("Custom request sent: " + notes + " at " + locationName);
      }
    } else {
      String patientID = patientsDB.getPatientID(patientName);
      request = new CustomRequest(null, patientID, type, notes);
      requestLabel.setText("Custom request sent: " + notes + " for " + patientName);
    }
    ServiceRequestsDB.getInstance().add(request);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    typeInput.setText("");
    typeInput.setPromptText("Type");
    type = "";
  }
}
