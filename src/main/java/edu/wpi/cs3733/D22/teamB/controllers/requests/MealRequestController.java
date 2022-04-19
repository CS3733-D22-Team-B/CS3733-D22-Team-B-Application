package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.MealRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MealRequestController extends PatientBasedRequestController {
  @FXML private TextField mealInput;

  private String mealName = "";

  public void initialize() {
    super.initialize();

    mealInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setMealName();
              }
            });

    additionalInformationInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setNotes();
              }
            });
  }

  public void setMealName() {
    mealName = mealInput.getText();
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
    if (!patientName.equals("") && !mealName.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    MealRequest request =
        new MealRequest(
            patientID,
            "Meal: " + mealName + "\nAdditional Information: " + notes,
            (int) prioritySlider.getValue());
    ServiceRequestsDB.getInstance().add(request);
    requestLabel.setText("Meal request sent: " + mealName + " for " + patientName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    mealInput.clear();
    mealName = "";
  }
}
