package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequest;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentRequestDB;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.requests.SanitationRequest;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ChangeListener;

public class SanitationRequestController extends EquipmentBasedRequestController{
    @FXML private ComboBox<String> equipmentInput;
    @FXML private Button submitButton;

    private DatabaseController DC = DatabaseController.getInstance();

    String equipment = "";

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

        for (MedicalEquipment equipment : equDAO.list()) {
            if (!equipment.getIsClean()) {
                equipmentInput.getItems().add(equipment.getName());
            }
        }
    }

    @FXML
    public void setEquipment() {
        equipment = equipmentInput.getValue();
        enableSubmission();
    }

    @FXML
    public void enableSubmission() {
        if (!equipment.equals("")) {
            submitButton.setDisable(false);
        }
    }

    @FXML
    public void sendRequest(ActionEvent event){
        String equipmentID = equDAO.getEquipmentID(equipment);
        SanitationRequest request =
                new SanitationRequest(equipmentID, notes, (int) prioritySlider.getValue());
        DC.add(request);
        request.getMedicalEquipment().setAvailability("Requested");
        requestLabel.setText("Request sent: Sanitize " + equipment);
    }
}
