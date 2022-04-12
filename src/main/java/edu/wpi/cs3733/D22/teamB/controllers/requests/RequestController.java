package edu.wpi.cs3733.D22.teamB.controllers.requests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public abstract class RequestController extends MenuBarController {
  @FXML protected Label requestLabel;
  @FXML protected TextArea additionalInformationInput;
  @FXML protected Label charactersRemainingLabel;
  @FXML protected Slider prioritySlider;
  @FXML protected JFXButton submitButton;

  protected String notes = "";

  public void initialize() {
    additionalInformationInput
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.length() > 500) {
                additionalInformationInput.setText(oldValue);
              }

              charactersRemainingLabel.setText(
                  String.valueOf(500 - additionalInformationInput.getText().length())
                      + " characters remaining");
            });
  }

  @FXML
  public void setNotes() {
    notes =
        (additionalInformationInput.getText() == null) ? "" : additionalInformationInput.getText();
    enableSubmission();
  }

  @FXML
  public abstract void enableSubmission();

  @FXML
  public abstract void sendRequest(ActionEvent event);

  @FXML
  public abstract void reset(ActionEvent actionEvent);
}
