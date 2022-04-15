package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SanitationRequestController extends EquipmentBasedController{
    @FXML private ComboBox<String> equipmentInput;
    @FXML private Button submitButton;

    private DatabaseController DC = DatabaseController.getInstance();

    String equipment = "";

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
}
