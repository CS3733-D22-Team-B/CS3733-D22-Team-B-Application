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
    setEmployeeName();
    setMedication();

    String locationID = dao.getLocationID(room);
    MedicineRequest request = new MedicineRequest(locationID, medicine);

    RequestQueue.addRequest(request);
    requestLabel.setText("Request sent: " + medicine + " to " + room + " by " + employeeName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    employeeNameInput.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    medicineInput.setValue("");

    employeeName = "";
    floor = "";
    room = "";
    medicine = "";
  }
}
