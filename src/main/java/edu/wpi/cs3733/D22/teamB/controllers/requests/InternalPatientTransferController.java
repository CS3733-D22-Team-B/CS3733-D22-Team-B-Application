package edu.wpi.cs3733.D22.teamB.controllers.requests;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import java.util.LinkedList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InternalPatientTransferController extends PatientAndLocationBasedRequestController {
  @FXML
  public void enableSubmission() {
    if (!patientName.equals("") && !locationName.equals("")) {
      submitButton.setDisable(false);
    }
  }

  @Override
  public void initialize() {
    super.initialize();
    locationsDAO = LocationsDB.getInstance();
    LinkedList<Location> locations = locationsDAO.list();

    for (Location location : locations) {
      if (location.getNodeType().equals("PATI")) {
        switch (location.getFloor()) {
          case "3":
            locationsF3.add(location.getLongName());
            break;
          case "2":
            locationsF2.add(location.getLongName());
            break;
          case "1":
            locationsF1.add(location.getLongName());
            break;
          case "L1":
            locationsFL1.add(location.getLongName());
            break;
          case "L2":
            locationsFL2.add(location.getLongName());
            break;
        }
      }
    }
    locationName = "";
    floor = "";

    patientsDB = PatientsDB.getInstance();

    for (Patient patient : patientsDB.list()) {
      patientInput.getItems().add(patient.getOverview());
    }
    patientName = patientInput.getValue();

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
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    String destinationID = locationsDAO.getLocationID(locationName);
    InternalPatientTransferRequest request =
        new InternalPatientTransferRequest(
            patientID, destinationID, notes, (int) prioritySlider.getValue());
    DatabaseController.getInstance().add(request);
    requestLabel.setText("Request sent: Moving " + patientName + " to " + locationName);
  }
}
