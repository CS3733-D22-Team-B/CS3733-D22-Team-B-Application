package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
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
    setNotes();
    if (!locationName.equals("") && !language.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String locationID = locationsDAO.getLocationID(locationName);
    InterpreterRequest request = new InterpreterRequest(locationID, language);
    ServiceRequestsDB.getInstance().add(request);
    requestLabel.setText("Request Sent: " + language + " interpreter to " + locationName);
  }

  @FXML
  public void reset() {
    super.reset();
    languageInput.setValue("");
    languageInput.setPromptText("Language");
    language = "";
  }
}
