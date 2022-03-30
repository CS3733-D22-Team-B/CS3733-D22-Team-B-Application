package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InterpreterRequestController extends RequestController {
  @FXML private ComboBox<String> languageInput;
  private String language;

  public void setLanguage() {
    language = languageInput.getValue();
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String locationID = dao.getLocationID(room);
    InterpreterRequest request = new InterpreterRequest("Please Fix This!", language, locationID);

    requestLabel.setText("You have selected " + language);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    employeeNameInput.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    languageInput.setValue("");

    employeeName = "";
    floor = "";
    room = "";
    language = "";
  }
}
