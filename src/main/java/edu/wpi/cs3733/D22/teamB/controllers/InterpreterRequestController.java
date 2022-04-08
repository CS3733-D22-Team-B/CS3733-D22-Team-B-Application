package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InterpreterRequestController extends LocationBasedRequestController {
  @FXML private ComboBox<String> languageInput;
  private String language;

  public void setLanguage() {
    language = languageInput.getValue();
  }

  @FXML
  public void enableSubmission() {
    if (!locationName.equals("") && !language.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String locationID = locationsDAO.getLocationID(locationName);
    InterpreterRequest request = new InterpreterRequest(locationID, language);
    RequestQueue.addRequest(request);
    requestLabel.setText("Request Sent: " + language + " interpreter to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    locationInput.setValue("");
    languageInput.setValue("");
    submitButton.setDisable(true);

    floor = "";
    locationName = "";
    language = "";
  }
}
