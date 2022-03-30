package edu.wpi.cs3733.D22.teamB.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InternalPatientTransferController extends RequestController {
  @FXML private ComboBox<String> startFloorInput;
  @FXML private ComboBox<String> startRoomInput;

  private String startFloor;
  private String startRoom;

  public void setStartFloor() {
    startFloor = startFloorInput.getValue();
    resetStartRoomBox();
  }

  public void setStartRoom() {
    startRoom = startRoomInput.getValue();
  }

  private void resetStartRoomBox() {
    startRoomInput.getItems().clear();
    switch (startFloor) {
      case "F3":
        for (String roomF3 : roomsF3) {
          startRoomInput.getItems().add(roomF3);
        }
        break;
      case "F2":
        for (String roomF2 : roomsF2) {
          startRoomInput.getItems().add(roomF2);
        }
        break;
      case "F1":
        for (String roomF1 : roomsF1) {
          startRoomInput.getItems().add(roomF1);
        }
        break;
      case "L1":
        for (String roomFL1 : roomsFL1) {
          startRoomInput.getItems().add(roomFL1);
        }
        break;
      case "L2":
        for (String roomFL2 : roomsFL2) {
          startRoomInput.getItems().add(roomFL2);
        }
        break;
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setStartRoom();
    requestLabel.setText(
        "Request sent: Moving patient in " + startRoom + " to " + room + " by " + employeeName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    // TODO: Auto-generated method stub
  }
}
