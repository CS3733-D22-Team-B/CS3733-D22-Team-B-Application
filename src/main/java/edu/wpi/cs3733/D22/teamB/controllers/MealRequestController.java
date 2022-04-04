package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.requests.MealRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;

public class MealRequestController extends RequestController {
  @FXML private TextField mealInput;

  private String mealName;

  public void setMealName() {
    mealName = mealInput.getText();
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    setMealName();
    String locationID = dao.getLocationID(room);
    MealRequest request = new MealRequest(employeeName, locationID, mealName);

    RequestQueue.addRequest(request);
    requestLabel.setText("Meal request sent: " + mealName + " for " + room);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    employeeNameInput.setText("");
    floorInput.setValue("");
    roomInput.setValue("");
    mealInput.setText("");

    employeeName = "";
    floor = "";
    room = "";
    mealName = "";
  }
}
