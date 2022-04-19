package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class EquipmentBasedRequestController extends RequestController {
  @FXML protected ComboBox<String> equipmentInput;

  private String equipment = "";

  protected MedicalEquipmentDB equDAO = MedicalEquipmentDB.getInstance();

  @FXML
  public void setEquipment() {
    equipment = equipmentInput.getValue();
    enableSubmission();
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    equipmentInput.getSelectionModel().clearSelection();
    additionalInformationInput.clear();
    prioritySlider.setValue(1);
    submitButton.setDisable(true);

    equipment = "";
    notes = "";
  }
}
