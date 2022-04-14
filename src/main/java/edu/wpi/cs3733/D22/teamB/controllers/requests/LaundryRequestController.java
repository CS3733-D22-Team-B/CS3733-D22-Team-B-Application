package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.MealRequest;
import edu.wpi.cs3733.D22.teamB.requests.MedicineRequest;
import edu.wpi.cs3733.D22.teamB.requests.LaundryRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LaundryRequestController extends PatientBasedRequestController {
    @FXML private ComboBox<String> laundryInput;

    private String laundry = "";

    public void initialize() {
        super.initialize();

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

    public void setLaundry() {
        laundry = laundryInput.getValue();
        enableSubmission();
    }

    @FXML
    public void enableSubmission() {
        if (!patientName.equals("") && !laundry.equals("")) {
            submitButton.setDisable(false);
        }
    }

    @FXML
    public void sendRequest(ActionEvent actionEvent) {
        String patientID = patientsDB.getPatientID(patientName);
        LaundryRequest request =
                new LaundryRequest(
                        patientID,
                        "Laundry Request: " + laundry + "Additional Information: " + notes,
                        (int) prioritySlider.getValue());
        ServiceRequestsDB.getInstance().add(request);
        requestLabel.setText("Request sent: " + laundry + " to " + patientName);
    }

    @FXML
    public void reset(ActionEvent actionEvent) {
        super.reset(actionEvent);
        laundryInput.getSelectionModel().clearSelection();
        laundry = "";
    }
}


