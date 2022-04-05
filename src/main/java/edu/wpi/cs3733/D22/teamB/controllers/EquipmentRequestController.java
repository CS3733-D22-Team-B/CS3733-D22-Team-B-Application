package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
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
    setNotes();
    if (room.equals("") && equipment.equals("")) {
      requestLabel.setText("Please specify a room and equipment");
    } else if (room.equals("")) {
      requestLabel.setText("Please specify a room");
    } else if (equipment.equals("")) {
      requestLabel.setText("Please select equipment to request");
    } else {
      String locationID = dao.getLocationID(room);
      EquipmentRequest request = new EquipmentRequest(locationID, equipment, notes);
      RequestQueue.addRequest(request);
      requestLabel.setText("Request sent: " + equipment + " to " + room);
    }
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    equipmentInput.setValue("");
    additionalNotesInput.setText("");

    floor = "";
    room = "";
    equipment = "";
    notes = "";
  }
}
