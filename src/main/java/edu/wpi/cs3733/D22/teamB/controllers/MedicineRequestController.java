package edu.wpi.cs3733.D22.teamB.controllers;

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
