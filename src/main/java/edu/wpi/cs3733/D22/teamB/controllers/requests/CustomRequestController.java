package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.requests.CustomRequest;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomRequestController extends PatientAndLocationBasedRequestController {
  @FXML private TextField typeInput;

  private String type = "";

  public void initialize() {
    super.initialize();

    typeInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setType();
              }
            });

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

  @FXML
  public void setType() {
    type = typeInput.getText();
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
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
        request =
            new CustomRequest(locationID, patientID, type, notes, (int) prioritySlider.getValue());
        requestLabel.setText(
            "Custom request sent: " + notes + " for " + patientName + " at " + locationName);
      } else {
        request = new CustomRequest(locationID, null, type, notes, (int) prioritySlider.getValue());
        requestLabel.setText("Custom request sent: " + notes + " at " + locationName);
      }
    } else {
      String patientID = patientsDB.getPatientID(patientName);
      request = new CustomRequest(null, patientID, type, notes, (int) prioritySlider.getValue());
      requestLabel.setText("Custom request sent: " + notes + " for " + patientName);
    }
    DatabaseController.getInstance().add(request);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    typeInput.setText("");
    typeInput.setPromptText("Type");
    type = "";
  }
}
