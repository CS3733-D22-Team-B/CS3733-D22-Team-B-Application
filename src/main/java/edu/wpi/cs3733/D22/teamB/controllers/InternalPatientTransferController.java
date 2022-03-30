package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDAO;
import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class InternalPatientTransferController extends RequestController {
    @FXML private ComboBox<String> startFloor;
  @FXML private ChoiceBox startRoomInput;

  private String startRoom;

  public void setStartFloor() {

  }

  public void setStartRoom() {}

    @FXML
    public void sendRequest(ActionEvent actionEvent) {
    setStartRoom();
    requestLabel.setText(
        "Request sent: Moving patient in "
            + startRoom
            + " to "
            + room
            + " by "
            + employeeName);
  }

    @FXML
    public void reset(ActionEvent actionEvent) {
    // TODO: Auto-generated method stub
  }
}
