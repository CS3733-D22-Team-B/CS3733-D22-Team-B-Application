package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public abstract class RequestController extends MenuBarController {
  @FXML protected Label requestLabel;
  @FXML protected TextArea additionalInformationInput;
  @FXML protected Slider prioritySlider;
  @FXML protected Button submitButton;

  protected String notes;

  @FXML
  public void setNotes() {
    notes =
        (additionalInformationInput.getText() == null) ? "" : additionalInformationInput.getText();
  }

  @FXML
  public abstract void enableSubmission();

  @FXML
  public abstract void sendRequest(ActionEvent event);

  @FXML
  public abstract void reset(ActionEvent event);
}
