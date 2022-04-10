package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.MealRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MealRequestController extends PatientBasedRequestController {
  @FXML private TextField mealInput;

  private String mealName;

  public void setMealName() {
    mealName = mealInput.getText();
  }

  @FXML
  public void enableSubmission() {
    setMealName();
    if (!patientName.equals("") && !mealName.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    MealRequest request = new MealRequest(patientID, mealName);
    ServiceRequestsDB.getInstance().add(request);
    requestLabel.setText("Meal request sent: " + mealName + " for " + patientName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    patientInput.setValue("");
    mealInput.setText("");
    submitButton.setDisable(true);

    patientName = "";
    mealName = "";
  }
}
