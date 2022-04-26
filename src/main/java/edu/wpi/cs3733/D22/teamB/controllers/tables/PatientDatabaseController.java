package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class PatientDatabaseController extends MenuBarController implements Initializable {
  @FXML TableView<Patient> patientTable;
  @FXML TableColumn<Patient, String> columnPatientID;
  @FXML TableColumn<Patient, String> columnName;
  @FXML TableColumn<Patient, String> columnLocation;
  @FXML TableColumn<Patient, Void> columnButtons;

  @FXML Label patientIDText;
  @FXML Text nameText;
  @FXML Text roomText;
  @FXML Text informationText;

  @FXML Label patientIDLabel;
  @FXML TextField nameInput;
  @FXML ComboBox<String> roomInput;
  @FXML TextArea informationInput;

  @FXML TextField addNameInput;
  @FXML ComboBox<String> addRoomInput;
  @FXML TextArea addInformationInput;

  @FXML AnchorPane viewingPane;
  @FXML AnchorPane editingPane;
  @FXML AnchorPane creationPane;
  @FXML AnchorPane otherAnchorPane;

  Patient currentPatient = null;
  DatabaseController db = DatabaseController.getInstance();
  protected PatientsDB dao;

  private ObservableList<Patient> patients = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnPatientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    columnLocation.setCellValueFactory(new PropertyValueFactory<>("longName"));

    columnPatientID.getStyleClass().add("table-column-left");
    columnName.getStyleClass().add("table-column-middle");
    columnLocation.getStyleClass().add("table-column-middle");
    columnButtons.getStyleClass().add("table-column-right");

    dao = PatientsDB.getInstance();
    LinkedList<Patient> patientsL = dao.list();
    for (Patient patientItem : patientsL) {
      patients.add(patientItem);
    }

    roomInput.setValue("No room assigned");
    addRoomInput.setValue("No room assigned");

    for (Location room : LocationsDB.getInstance().list()) {
      if (room.getNodeType().equals("PATI") && room.getAvailability()) {
        String roomName = room.getLongName();
        roomInput.getItems().add(roomName);
        addRoomInput.getItems().add(roomName);
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
                  private final HBox hBox = new HBox();
                  private final Button requestViewerButton = new Button("View");
                  private final Button requestEditButton = new Button("Edit");
                  private final Button requestDeleteButton = new Button("Delete");

                  private final ImageView viewIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/InfoSquare.png"));
                  private final ImageView editIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/EditSquare.png"));
                  private final ImageView deleteIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/CloseSquare.png"));

                  {
                    hBox.setSpacing(10);

                    requestViewerButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestViewerButton.setMinSize(25, 25);
                    requestViewerButton.setPrefSize(25, 25);
                    requestViewerButton.setMaxSize(25, 25);
                    requestViewerButton.setGraphic(viewIcon);
                    viewIcon.setFitHeight(25);
                    viewIcon.setFitWidth(25);

                    requestEditButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestEditButton.setMinSize(25, 25);
                    requestEditButton.setPrefSize(25, 25);
                    requestEditButton.setMaxSize(25, 25);
                    requestEditButton.setGraphic(editIcon);
                    editIcon.setFitHeight(25);
                    editIcon.setFitWidth(25);

                    requestDeleteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestDeleteButton.setMinSize(25, 25);
                    requestDeleteButton.setPrefSize(25, 25);
                    requestDeleteButton.setMaxSize(25, 25);
                    requestDeleteButton.setGraphic(deleteIcon);
                    deleteIcon.setFitHeight(25);
                    deleteIcon.setFitWidth(25);

                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(true);
                          editingPane.setVisible(false);
                          creationPane.setVisible(false);

                          Patient patient = getTableView().getItems().get(getIndex());
                          currentPatient = patient;

                          patientIDText.setText(patient.getPatientID());
                          nameText.setText(patient.getFullName());
                          roomText.setText(patient.getLongName());
                          informationText.setText(patient.getInformation());
                        });

                    requestEditButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(true);
                          creationPane.setVisible(false);

                          Patient patient = getTableView().getItems().get(getIndex());
                          currentPatient = patient;

                          // patientIDLabel.setText(patient.getPatientID());
                          nameInput.setText(patient.getFullName());

                          if (patient.getLocation() == null) {
                            roomInput.setDisable(false);
                          } else {
                            roomInput.setDisable(true);
                          }

                          roomInput.setValue(patient.getLongName());
                          informationInput.setText(patient.getInformation());
                        });

                    requestDeleteButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(true);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(false);
                          creationPane.setVisible(false);

                          Patient patient = getTableView().getItems().get(getIndex());
                          currentPatient = patient;

                          // create a confirmation dialog
                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                          alert.setTitle("Confirm Delete");
                          alert.setHeaderText(
                              "Are you sure you want to remove patient "
                                  + currentPatient.getPatientID()
                                  + "?");
                          alert.setContentText("This action cannot be undone.");

                          ((Button) alert.getDialogPane().lookupButton(ButtonType.OK))
                              .setText("Delete");
                          ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL))
                              .setText("Cancel");

                          Optional<ButtonType> result = alert.showAndWait();
                          if (result.get() == ButtonType.OK) {
                            PatientsDB.getInstance().delete(patient);
                            patients.remove(patient);
                            patientTable.refresh();

                            DatabaseController.getInstance()
                                .add(
                                    new Activity(
                                        new Date(),
                                        App.currentUser.getEmployeeID(),
                                        currentPatient.getPatientID(),
                                        null,
                                        "Patient",
                                        "checked out"));

                            currentPatient.getLocation().setAvailability(true);

                            currentPatient = null;
                          }
                        });

                    hBox.getChildren()
                        .addAll(requestViewerButton, requestEditButton, requestDeleteButton);
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(hBox);
                      requestViewerButton.getStyleClass().add("hidden-button");
                      requestEditButton.getStyleClass().add("hidden-button");
                      requestDeleteButton.getStyleClass().add("hidden-button");
                    }
                  }
                };
            return cell;
          }
        };

    columnButtons.setCellFactory(cellFactory);
  }

  @FXML
  public void saveData(ActionEvent event) {
    currentPatient.setInformation(informationInput.getText());
    if (currentPatient.getLocation() == null
        && roomInput.isVisible()
        && !roomInput.getValue().equals("No room assigned")) {
      currentPatient.setLocation(
          LocationsDB.getInstance()
              .getLocation(LocationsDB.getInstance().getLocationID(roomInput.getValue())));
      roomInput.getItems().remove(roomInput.getValue());
      addRoomInput.getItems().remove(addRoomInput.getValue());
      currentPatient.getLocation().setAvailability(false);

      DatabaseController.getInstance()
          .add(
              new Activity(
                  new Date(),
                  App.currentUser.getEmployeeID(),
                  currentPatient.getPatientID(),
                  LocationsDB.getInstance().getLocationID(roomInput.getValue()),
                  "Patient",
                  "admitted to room"));
    }

    String firstName = nameInput.getText().substring(0, nameInput.getText().indexOf(" "));
    String lastName = nameInput.getText().substring(nameInput.getText().indexOf(" ") + 1);

    currentPatient.setFirstName(firstName);
    currentPatient.setLastName(lastName);

    dao.update(currentPatient);

    nameInput.setText("");
    roomInput.setValue("No room assigned");
    informationInput.setText("");

    patientTable.refresh();

    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);
  }

  @FXML
  public void createPatient(ActionEvent event) {
    otherAnchorPane.setVisible(false);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(true);
  }

  @FXML
  public void cancel(ActionEvent event) {
    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);

    addNameInput.setText("");
    roomInput.setValue("No room assigned");
    addInformationInput.setText("");
  }

  @FXML
  public void addPatient(ActionEvent event) {
    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);

    Patient patient;
    String firstName = addNameInput.getText().substring(0, addNameInput.getText().indexOf(" "));
    String lastName = addNameInput.getText().substring(addNameInput.getText().indexOf(" ") + 1);

    String roomID = null;
    if (!addRoomInput.getValue().equals("No room assigned")) {
      roomID = LocationsDB.getInstance().getLocationID(addRoomInput.getValue());
      roomInput.getItems().remove(roomInput.getValue());
      addRoomInput.getItems().remove(addRoomInput.getValue());

      DatabaseController.getInstance()
          .add(
              new Activity(
                  new Date(),
                  App.currentUser.getEmployeeID(),
                  currentPatient.getPatientID(),
                  roomID,
                  "Patient",
                  "admitted to room"));

      LocationsDB.getInstance().getLocation(roomID).setAvailability(false);
    }

    String information = addInformationInput.getText();

    patient = new Patient(lastName, firstName, roomID, information);

    patients.add(patient);
    PatientsDB.getInstance().add(patient);

    addNameInput.setText("");
    addRoomInput.setValue("No room assigned");
    addInformationInput.setText("");

    patientTable.refresh();
    patientTable.getSelectionModel().select(patient);
    patientTable.scrollTo(patients.size() - 1);
  }

  @FXML
  public void toggleClientServer() {
    db.switchConnection();

    patientTable.refresh();
  }
}
