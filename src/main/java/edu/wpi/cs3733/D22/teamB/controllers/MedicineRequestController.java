package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.MedicineRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MedicineRequestController extends RequestController {
  @FXML private ComboBox<String> medicineInput;

  private String medicine;

  public void setMedication() {
    medicine = medicineInput.getValue();
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setMedication();
    if (room.equals("") && medicine.equals("")) {
      requestLabel.setText("Please enter a room and medication");
    } else if (room.equals("")) {
      requestLabel.setText("Please enter a room");
    } else if (medicine.equals("")) {
      requestLabel.setText("Please enter a medication");
    } else {
      String locationID = dao.getLocationID(room);
      MedicineRequest request = new MedicineRequest(locationID, medicine);
      RequestQueue.addRequest(request);
      requestLabel.setText("Request sent: " + medicine + " to " + room);
    }
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    medicineInput.setValue("");

    floor = "";
    room = "";
    medicine = "";
  }
}
