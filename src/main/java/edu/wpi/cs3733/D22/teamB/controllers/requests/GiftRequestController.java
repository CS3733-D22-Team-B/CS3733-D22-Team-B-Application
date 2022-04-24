package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.requests.GiftRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GiftRequestController extends PatientBasedRequestController {
  @FXML private TextField giftInput;

  private String giftName = "";

  public void initialize() {
    super.initialize();

    giftInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setGiftName();
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

  public void setGiftName() {
    giftName = giftInput.getText();
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
    if (!patientName.equals("") && !giftName.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    GiftRequest request =
        new GiftRequest(
            patientID,
            "Gift: " + giftName + "\nAdditional Information: " + notes,
            (int) prioritySlider.getValue());
    DatabaseController.getInstance().add(request);
    requestLabel.setText("Gift request sent: " + giftName + " for " + patientName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    giftInput.clear();
    giftName = "";
  }
}
