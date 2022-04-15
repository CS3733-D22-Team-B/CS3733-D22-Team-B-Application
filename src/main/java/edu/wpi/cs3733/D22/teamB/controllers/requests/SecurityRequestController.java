package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequestDB;
import edu.wpi.cs3733.D22.teamB.databases.ServiceRequestsDB;
import edu.wpi.cs3733.D22.teamB.requests.SecurityRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

public class SecurityRequestController extends LocationBasedRequestController {

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

    public void enableSubmission() {
        if (!locationName.equals("")) {
            submitButton.setDisable(false);
        }
    }

    public void sendRequest(ActionEvent event) {
        String locationID = locationsDAO.getLocationID(locationName);
        SecurityRequest request =
                new SecurityRequest(locationID, notes, (int) prioritySlider.getValue());
        ServiceRequestsDB.getInstance().add(request);
        requestLabel.setText("Request sent: Security to " + locationName);
    }
}
