package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InterpreterRequestController extends LocationBasedRequestController {
  @FXML private ComboBox<String> languageInput;

  private String language = "";

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

  public void setLanguage() {
    language = languageInput.getValue();
    enableSubmission();
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
    InterpreterRequest request =
        new InterpreterRequest(
            locationID,
            "Language: " + language + "Additional Information: " + notes,
            (int) prioritySlider.getValue());
    ServiceRequestsDB.getInstance().add(request);
    requestLabel.setText("Request Sent: " + language + " interpreter to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    languageInput.getSelectionModel().clearSelection();
    language = "";
  }
}
