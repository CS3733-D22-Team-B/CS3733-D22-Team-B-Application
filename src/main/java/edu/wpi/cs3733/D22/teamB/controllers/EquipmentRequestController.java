package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.EquipmentRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class EquipmentRequestController extends RequestController {
  @FXML private ComboBox<String> equipmentInput;
  @FXML private TextArea additionalNotesInput;

  private String equipment = "";
  private String notes = "";

  @FXML
  public void setEquipment() {
    equipment = equipmentInput.getValue();
  }

  @FXML
  public void setNotes() {
    notes = (additionalNotesInput.getText() == null) ? "" : additionalNotesInput.getText();
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setEmployeeName();
    setNotes();
    if (room.equals("") && equipment.equals("") && employeeName.equals("")) {
      requestLabel.setText(
          "Fields are empty, please specify room, equipment, and name of requester");
    } else if (room.equals("")) {
      requestLabel.setText("Please specify a room");
    } else if (employeeName.equals("")) {
      requestLabel.setText("Please input name of requester");
    } else if (equipment.equals("")) {
      requestLabel.setText("Please select equipment to request");
    } else {
      String locationID = dao.getLocationID(room);
      EquipmentRequest request = new EquipmentRequest(equipment, employeeName, locationID, notes);

      requestLabel.setText("Request sent: " + equipment + " to " + room + " by " + employeeName);
    }
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    employeeNameInput.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    equipmentInput.setValue("");
    additionalNotesInput.setText("");

    employeeName = "";
    floor = "";
    room = "";
    equipment = "";
    notes = "";
  }
}
