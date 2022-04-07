package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CustomRequestController extends RequestController {
  @FXML private TextField typeInput;
  @FXML private TextArea notesInput;
  @FXML private Button sendButton;

  @Override
  public void sendRequest(ActionEvent event) {
    if (floor.equals("") || room.equals("")) {
      requestLabel.setText("Please select a room before submitting.");
    } else if (typeInput.getText().equals("")) {
      requestLabel.setText("Please fill out the request type.");
    } else {
      // String locationID = dao.getLocationID(room);
      // Request request = new Request(locationID, typeInput.getText());
      // RequestQueue.addRequest(request);
      requestLabel.setText("Custom request sent: " + "for " + room);
    }
  }

  @Override
  public void reset(ActionEvent event) {
    typeInput.setText("");
    notesInput.setText("");
  }
}
