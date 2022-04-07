package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.LabRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class LabRequestController extends RequestController {
  @FXML private ComboBox<String> labTestInput;
  @FXML private DatePicker testingTimeInput;
  @FXML private ComboBox<String> labFloorInput;
  @FXML private ComboBox<String> labRoomInput;

  private String labTest;
  private Date testingTime;
  private String labFloor;
  private String labRoom;

  public void setLabTest() {
    labTest = labTestInput.getValue();
  }

  public void setTestingTime() {
    LocalDate localDate = testingTimeInput.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    testingTime = Date.from(instant);
  }

  public void setLabFloor() {
    labFloor = labFloorInput.getValue();
    resetRoomBox();
  }

  public void setLabRoom() {
    labRoom = labRoomInput.getValue();
  }

  private void resetRoomBox() {
    labRoomInput.getItems().clear();
    switch (labFloor) {
      case "F3":
        for (String roomF3 : roomsF3) {
          labRoomInput.getItems().add(roomF3);
        }
        break;
      case "F2":
        for (String roomF2 : roomsF2) {
          labRoomInput.getItems().add(roomF2);
        }
        break;
      case "F1":
        for (String roomF1 : roomsF1) {
          labRoomInput.getItems().add(roomF1);
        }
        break;
      case "L1":
        for (String roomFL1 : roomsFL1) {
          labRoomInput.getItems().add(roomFL1);
        }
        break;
      case "L2":
        for (String roomFL2 : roomsFL2) {
          labRoomInput.getItems().add(roomFL2);
        }
        break;
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setLabTest();
    setTestingTime();
    if (room.equals("") && labTest.equals("") && testingTime == null && labRoom.equals("")) {
      requestLabel.setText(
          "Please specify a room, a lab test, a testing time, and a testing room.");
    } else if (room.equals("") && labTest.equals("") && testingTime == null) {
      requestLabel.setText("Please specify a room, a lab test, and a testing time.");
    } else if (room.equals("") && labTest.equals("") && labRoom.equals("")) {
      requestLabel.setText("Please specify a room, a lab test, and a testing room.");
    } else if (room.equals("") && testingTime == null && labRoom.equals("")) {
      requestLabel.setText("Please specify a room, a testing time, and a testing room.");
    } else if (labTest.equals("") && testingTime == null && labRoom.equals("")) {
      requestLabel.setText("Please specify a lab test, a testing time, and a testing room.");
    } else if (room.equals("") && labTest.equals("")) {
      requestLabel.setText("Please specify a room and a lab test.");
    } else if (room.equals("") && testingTime == null) {
      requestLabel.setText("Please specify a room and a testing time.");
    } else if (room.equals("") && labRoom.equals("")) {
      requestLabel.setText("Please specify a room and a testing room.");
    } else if (labTest.equals("") && testingTime == null) {
      requestLabel.setText("Please specify a lab test and a testing time.");
    } else if (labTest.equals("") && labRoom.equals("")) {
      requestLabel.setText("Please specify a lab test and a testing room.");
    } else if (testingTime == null && labRoom.equals("")) {
      requestLabel.setText("Please specify a testing time and a testing room.");
    } else if (room.equals("")) {
      requestLabel.setText("Please specify a room.");
    } else if (labTest.equals("")) {
      requestLabel.setText("Please specify a lab test.");
    } else if (testingTime == null) {
      requestLabel.setText("Please specify a testing time.");
    } else if (labRoom.equals("")) {
      requestLabel.setText("Please specify a testing room.");
    } else {
      String locationID = dao.getLocationID(room);
      String testRoomID = dao.getLocationID(labRoom);
      LabRequest request = new LabRequest(locationID, labTest, testingTime, testRoomID);
      RequestQueue.addRequest(request);
      requestLabel.setText("Request sent: " + labTest + " in " + labRoom + " on " + testingTime);
    }
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    labTestInput.setValue("");
    testingTimeInput.setValue(null);

    floor = "";
    room = "";
    labTest = "";
    testingTime = null;
  }
}
