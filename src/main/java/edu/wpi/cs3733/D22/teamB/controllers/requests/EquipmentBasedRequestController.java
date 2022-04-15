package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipmentDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public abstract class EquipmentBasedRequestController extends RequestController {
	@FXML protected ComboBox<String> equipmentInput;

	private String equipment = "";

	private MedicalEquipmentDB equDAO = MedicalEquipmentDB.getInstance();

	@FXML
	public void setEquipment() {
		equipment = equipmentInput.getValue();
		enableSubmission();
	}

	@FXML
	public void reset(ActionEvent actionEvent) {
		requestLabel.setText("");
		equipmentInput.getSelectionModel().clearSelection();
		additionalInformationInput.clear();
		prioritySlider.setValue(1);
		submitButton.setDisable(true);

		equipment = "";
		notes = "";
	}
}

// PUT THIS METHOD IN YOUR CONTROLLER CLASS
/*
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
 */