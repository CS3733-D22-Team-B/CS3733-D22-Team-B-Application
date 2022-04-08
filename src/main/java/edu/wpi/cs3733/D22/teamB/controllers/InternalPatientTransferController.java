package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import edu.wpi.cs3733.D22.teamB.databases.Patient;
import edu.wpi.cs3733.D22.teamB.databases.PatientsDB;
import edu.wpi.cs3733.D22.teamB.requests.InternalPatientTransferRequest;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.LinkedList;

public class InternalPatientTransferController extends PatientAndLocationBasedRequestController {
  @FXML
  public void enableSubmission() {
    setLocation();
    if (!patientName.equals("") && !locationName.equals("")) {
      submitButton.setDisable(false);
    }
  }
  @Override
  public void initialize()
  {
    locationsDAO = LocationsDB.getInstance();
    LinkedList<Location> locations = locationsDAO.list();

    for (Location location : locations) {
      if(location.getNodeType().equals("PATI"))
      {
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
  }

  @FXML
  public void sendRequest(ActionEvent actionEvent) {
    String patientID = patientsDB.getPatientID(patientName);
    String destinationID = locationsDAO.getLocationID(locationName);
    InternalPatientTransferRequest request =
        new InternalPatientTransferRequest(patientName, destinationID);
    RequestQueue.addRequest(request);
    requestLabel.setText("Request sent: Moving " + patientName + " to " + locationName);
  }

  @FXML
  public void reset(ActionEvent actionEvent) {
    requestLabel.setText("");
    floorInput.setValue("");
    locationInput.setValue("");
    patientInput.setValue("");
    submitButton.setDisable(true);

    floor = "";
    locationName = "";
    patientName = "";
  }
}
