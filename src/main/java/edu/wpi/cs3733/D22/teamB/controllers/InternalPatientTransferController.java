package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InternalPatientTransferController extends RequestController {
  @FXML private ComboBox<String> destinationFloorInput;
  @FXML private ComboBox<String> destinationRoomInput;

  private String destinationFloor;
  private String destinationRoom;

  public void setDestinationFloor() {
    destinationFloor = destinationFloorInput.getValue();
    resetDestinationRoomBox();
  }

  public void setDestinationRoom() {
    destinationRoom = destinationRoomInput.getValue();
  }

  private void resetDestinationRoomBox() {
    destinationRoomInput.getItems().clear();
    switch (destinationFloor) {
      case "F3":
        for (String roomF3 : roomsF3) {
          destinationRoomInput.getItems().add(roomF3);
        }
        break;
      case "F2":
        for (String roomF2 : roomsF2) {
          destinationRoomInput.getItems().add(roomF2);
        }
        break;
      case "F1":
        for (String roomF1 : roomsF1) {
          destinationRoomInput.getItems().add(roomF1);
        }
        break;
      case "L1":
        for (String roomFL1 : roomsFL1) {
          destinationRoomInput.getItems().add(roomFL1);
        }
        break;
      case "L2":
        for (String roomFL2 : roomsFL2) {
          destinationRoomInput.getItems().add(roomFL2);
        }
        break;
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setDestinationRoom();

    String locationID = dao.getLocationID(room);
    String destinationID = dao.getLocationID(destinationRoom);
    InternalPatientTransferRequest request =
        new InternalPatientTransferRequest(locationID, destinationID);
    RequestQueue.addRequest(request);
    requestLabel.setText(
        "Request sent: Moving patient in "
            + room
            + " to "
            + destinationRoom
            + " by "
            + employeeName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    // TODO: Auto-generated method stub
  }
}
