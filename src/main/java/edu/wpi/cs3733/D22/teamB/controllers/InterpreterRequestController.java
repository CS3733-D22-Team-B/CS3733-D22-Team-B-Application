package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import java.util.LinkedList;

import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class InterpreterRequestController extends RequestController {
  @FXML private ChoiceBox languageInput;

  private String language;

  public void setLanguage() {
    language = languageInput.getSelectionModel().getSelectedItem().toString();
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
