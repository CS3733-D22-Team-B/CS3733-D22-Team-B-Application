package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class EquipmentRequestController extends LocationBasedRequestController {
  @FXML private ComboBox<String> equipmentInput;

  private String equipment = "";

  private MedicalEquipmentDB equDAO = MedicalEquipmentDB.getInstance();

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

  @FXML
  public void setEquipment() {
    equipment = getNearestEquipmentByType(equipmentInput.getValue());
    enableSubmission();
  }

  @FXML
  public void enableSubmission() {
    if (!locationName.equals("") && !equipment.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String locationID = locationsDAO.getLocationID(locationName);
    String equipmentID = equipment;
    EquipmentRequest request =
        new EquipmentRequest(locationID, equipmentID, notes, (int) prioritySlider.getValue());
    ServiceRequestsDB.getInstance().add(request);
    request.getMedicalEquipment().setAvailability("Requested");
    requestLabel.setText("Request sent: " + equipment + " to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    super.reset(actionEvent);
    equipmentInput.getSelectionModel().clearSelection();
    equipment = "";
  }

  private String getNearestEquipmentByType(String type) {
    MedicalEquipment closestEquip = null;
    type = convertType(type);
    double minDist = -1;

    for (MedicalEquipment equipment : equDAO.list()) {
      if (equipment.getType().equals(type)
          && equipment.getIsClean()
          && equipment.getAvailability().equals("Available")) {
        Location location = locationsDAO.getLocation(locationsDAO.getLocationID(locationName));
        double newDist = calculateDistance(equipment, location);
        if (minDist == -1 || newDist < minDist) {
          closestEquip = equipment;
        }
        break;
      }
    }

    return closestEquip.getEquipmentID();
  }

  public double calculateDistance(MedicalEquipment equipment, Location location) {
    double XDist = equipment.getLocation().getXCoord() - location.getXCoord();
    double YDist = equipment.getLocation().getYCoord() - location.getYCoord();

    return Math.sqrt(Math.pow(XDist, 2) + Math.pow(YDist, 2));
  }

  private String convertType(String type) {
    switch (type) {
      case "Bed":
        type = "BED";
        break;
      case "Infusion Pump":
        type = "PUMP";
        break;
      case "Recliner":
        type = "RECL";
        break;
      case "X-Ray":
        type = "XRAY";
        break;
      default:
        break;
    }

    return type;
  }
}
