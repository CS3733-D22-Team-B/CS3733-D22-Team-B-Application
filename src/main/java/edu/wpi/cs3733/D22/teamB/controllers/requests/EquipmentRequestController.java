package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class EquipmentRequestController extends LocationBasedRequestController {
  @FXML private ComboBox<String> equipmentInput;

  private String equipment = "";

  private MedicalEquipmentDB equDAO = MedicalEquipmentDB.getInstance();

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

    for (MedicalEquipment equipment : equDAO.list()) {
      if (equipment.getIsClean() && equipment.getAvailability().equals("Available")) {
        equipmentInput.getItems().add(equipment.getName());
      }
    }
  }

  @FXML
  public void setEquipment() {
    equipment = equipmentInput.getValue();
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
    if (!locationName.equals("") && !equipment.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String locationID = locationsDAO.getLocationID(locationName);
    String equipmentID = equDAO.getEquipmentID(equipment);
    EquipmentRequest request =
        new EquipmentRequest(locationID, equipmentID, notes, (int) prioritySlider.getValue());
    ServiceRequestsDB.getInstance().add(request);
    request.getMedicalEquipment().setAvailability("Requested");
    requestLabel.setText("Request sent: " + equipment + " to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    equipmentInput.getSelectionModel().clearSelection();
    equipment = "";
  }
}
