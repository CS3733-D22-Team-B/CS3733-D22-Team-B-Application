package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.requests.LocationBasedRequestController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PatientDatabaseController extends LocationBasedRequestController
    implements Initializable {
  @FXML TableView<Patient> patientTable;
  @FXML TableColumn<Patient, String> columnPatientID;
  @FXML TableColumn<Patient, String> columnPatientLocation;
  @FXML TableColumn<Patient, String> columnFirstName;
  @FXML TableColumn<Patient, String> columnLastName;
  @FXML TableColumn<Patient, Void> columnButtons;

  @FXML Button saveButton;
  @FXML Button addSaveButton;
  @FXML Button deleteButton;

  @FXML TextField firstNameInput;
  @FXML TextField lastNameInput;

  Patient currentPatient = null;
  DatabaseController db = new DatabaseController();
  protected PatientsDB dao;
  protected LocationsDB dao2;

  private ObservableList<Patient> patients = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locationsDAO = LocationsDB.getInstance();
    LinkedList<Location> locations = locationsDAO.list();

    for (Location location2 : locations) {
      switch (location2.getFloor()) {
        case "3":
          locationsF3.add(location2.getLongName());
          break;
        case "2":
          locationsF2.add(location2.getLongName());
          break;
        case "1":
          locationsF1.add(location2.getLongName());
          break;
        case "L1":
          locationsFL1.add(location2.getLongName());
          break;
        case "L2":
          locationsFL2.add(location2.getLongName());
          break;
      }
    }
    columnPatientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
    columnPatientLocation.setCellValueFactory(new PropertyValueFactory<>("longName"));
    columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    saveButton.setDisable(true);
    deleteButton.setDisable(true);

    dao = PatientsDB.getInstance();
    LinkedList<Patient> patientsL = dao.list();
    for (Patient patientItem : patientsL) {
      if (!patientItem.getPatientID().equals("0")) {
        patients.add(patientItem);
      }
    }

    patientTable.setItems(patients);
    addButtonToTable();
  }

  private void addButtonToTable() {
    Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory =
        new Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>>() {
          @Override
          public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
            final TableCell<Patient, Void> cell =
                new TableCell<Patient, Void>() {
                  private final Button requestViewerButton = new Button("View Patient");

                  {
                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          Patient patient = getTableView().getItems().get(getIndex());
                          currentPatient = patient;
                          firstNameInput.setText(currentPatient.getFirstName());
                          lastNameInput.setText(currentPatient.getLastName());

                          switch (currentPatient.getLocation().getFloor()) {
                            case "5":
                              floorInput.setValue("F5");
                              break;
                            case "4":
                              floorInput.setValue("F4");
                              break;
                            case "3":
                              floorInput.setValue("F3");
                              break;
                            case "2":
                              floorInput.setValue("F2");
                              break;
                            case "1":
                              floorInput.setValue("F1");
                              break;
                            case "L1":
                              floorInput.setValue("L1");
                              break;
                            case "L2":
                              floorInput.setValue("L2");
                              ;
                              break;
                          }
                          setFloor();
                          locationInput.setValue(currentPatient.getLocation().getLongName());

                          firstNameInput.setDisable(false);
                          lastNameInput.setDisable(false);
                          floorInput.setDisable(false);
                          locationInput.setDisable(false);

                          saveButton.setDisable(false);
                          deleteButton.setDisable(false);
                          addSaveButton.setDisable(true);
                          addSaveButton.setVisible(false);
                        });
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(requestViewerButton);
                    }
                  }
                };
            return cell;
          }
        };

    columnButtons.setCellFactory(cellFactory);
    patientTable.getColumns().add(columnButtons);
  }

  @FXML
  public void saveData(ActionEvent event) {

    currentPatient.setLocation(
        locationsDAO.getLocation(locationsDAO.getLocationID(locationInput.getValue())));
    currentPatient.setNodeID(locationsDAO.getLocationID(locationInput.getValue()));
    currentPatient.setFirstName(firstNameInput.getText());
    currentPatient.setLastName(lastNameInput.getText());
    dao.update(currentPatient);
    locationInput.setValue("");
    floorInput.setValue("");
    firstNameInput.setText("");
    lastNameInput.setText("");

    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);

    addSaveButton.setDisable(true);
    addSaveButton.setVisible(false);

    saveButton.setDisable(true);
    deleteButton.setDisable(true);
    patientTable.refresh();
  }

  @FXML
  public void addStart(ActionEvent event) {

    locationInput.setValue("");
    floorInput.setValue("");

    firstNameInput.setText("");
    lastNameInput.setText("");
    locationInput.setValue("");

    firstNameInput.setDisable(false);
    lastNameInput.setDisable(false);
    locationInput.setDisable(false);
    floorInput.setDisable(false);

    saveButton.setDisable(true);
    saveButton.setVisible(false);
    deleteButton.setDisable(true);
    addSaveButton.setDisable(false);
    addSaveButton.setVisible(true);
  }

  @FXML
  public void saveAddData(ActionEvent event) {

    Patient newPat =
        new Patient(
            lastNameInput.getText(),
            firstNameInput.getText(),
            locationsDAO.getLocationID(locationInput.getValue()));
    currentPatient = newPat;
    dao.add(currentPatient);
    locationInput.setValue("");
    floorInput.setValue("");

    patients.add(currentPatient);
    patientTable.refresh();

    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    locationInput.setDisable(true);
    floorInput.setDisable(true);

    addSaveButton.setDisable(true);
    addSaveButton.setVisible(false);
    saveButton.setVisible(true);
    patientTable.refresh();
  }

  @FXML
  public void deleteData(ActionEvent event) {

    dao.delete(currentPatient);
    patients.remove(currentPatient);
    patientTable.refresh();
    locationInput.setValue("");
    floorInput.setValue("");

    firstNameInput.setText("");
    lastNameInput.setText("");

    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    locationInput.setDisable(true);
    floorInput.setDisable(true);

    saveButton.setDisable(true);
    deleteButton.setDisable(true);
  }

  @FXML
  public void toggleClientServer() {
    db.switchConnection();

    patientTable.refresh();
  }

  @Override
  public void enableSubmission() {}

  @Override
  public void sendRequest(ActionEvent event) {}
}
