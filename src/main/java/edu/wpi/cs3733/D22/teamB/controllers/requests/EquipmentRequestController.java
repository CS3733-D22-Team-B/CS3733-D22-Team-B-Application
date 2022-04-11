package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequestDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class EquipmentRequestController extends LocationBasedRequestController {
  @FXML private ComboBox<String> equipmentInput;

  private String equipment = "";

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
    setNotes();
    String locationID = locationsDAO.getLocationID(locationName);
    EquipmentRequest request = new EquipmentRequest(locationID, equipment, notes);
    EquipmentRequestDB.getInstance().add(request);
    requestLabel.setText("Request sent: " + equipment + " to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    locationInput.setValue("");
    equipmentInput.setValue("");
    additionalInformationInput.setText("");
    submitButton.setDisable(true);

    floor = "";
    locationName = "";
    equipment = "";
    notes = "";
  }
}
