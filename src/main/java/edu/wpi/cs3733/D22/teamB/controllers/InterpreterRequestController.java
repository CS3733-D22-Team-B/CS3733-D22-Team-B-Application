package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.InterpreterRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
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
    if (room.equals("") && language.equals("")) {
      requestLabel.setText("Please enter a room and language");
    } else if (room.equals("")) {
      requestLabel.setText("Please enter a room");
    } else if (language.equals("")) {
      requestLabel.setText("Please enter a language");
    } else {
      String locationID = dao.getLocationID(room);
      InterpreterRequest request = new InterpreterRequest(locationID, language);
      RequestQueue.addRequest(request);
      requestLabel.setText("You have selected " + language);
    }
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    languageInput.setValue("");

    floor = "";
    room = "";
    language = "";
  }
}
