package edu.wpi.cs3733.D22.teamB.controllers;

import static java.lang.Math.round;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Location;
import edu.wpi.cs3733.D22.teamB.databases.LocationsDB;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class MapEditorController extends MapViewerController {

  @FXML private JFXButton addButton;
  @FXML private TextField addLocationName;
  @FXML private Button cancelButton;
  @FXML private Button confirmButton;
  @FXML private Label addLabel;
  @FXML private ComboBox<String> typeDropdown;
  @FXML private JFXButton editButton;
  @FXML private JFXButton removeButton;
  @FXML private ComboBox<String> locationsDropdown;
  @FXML private Button editEnableButton;
  @FXML private TextField editNameField;
  @FXML private Button deleteButton;
  @FXML private Label warningLabel;
  @FXML private Circle marker;
  @FXML private Label resetWarning;
  @FXML private JFXButton resetButton;
  @FXML private JFXButton resetConfirm;
  @FXML private JFXButton resetCancel;
  private String currentFunction;
  private double selectedXCoord;
  private double selectedYCoord;
  private double sceneXCoord;
  private double sceneYCoord;
  private Boolean firstClick = true;
  private Boolean selectEnabled = false;

  private LinkedList<String> roomsF3 = new LinkedList<>();
  private LinkedList<String> roomsF2 = new LinkedList<>();
  private LinkedList<String> roomsF1 = new LinkedList<>();
  private LinkedList<String> roomsFL1 = new LinkedList<>();
  private LinkedList<String> roomsFL2 = new LinkedList<>();

  private LocationsDB dao;

  public void initialize() {
    dao = LocationsDB.getInstance();
    LinkedList<Location> locations = dao.list();

    for (Location location : locations) {
      switch (location.getFloor()) {
        case "3":
          roomsF3.add(location.getLongName());
          break;
        case "2":
          roomsF2.add(location.getLongName());
          break;
        case "1":
          roomsF1.add(location.getLongName());
          break;
        case "L1":
          roomsFL1.add(location.getLongName());
          break;
        case "L2":
          roomsFL2.add(location.getLongName());
          break;
      }
    }
  }

  @FXML
  public void hideButtons() {
    addButton.setDisable(true);
    addButton.setVisible(false);

    editButton.setDisable(true);
    editButton.setVisible(false);

    removeButton.setDisable(true);
    removeButton.setVisible(false);
  }

  @FXML
  public void showButtons() {
    addButton.setDisable(false);
    addButton.setVisible(true);

    editButton.setDisable(false);
    editButton.setVisible(true);

    removeButton.setDisable(false);
    removeButton.setVisible(true);
  }

  @FXML
  public void addLocation() {
    hideButtons();
    currentFunction = "Add";
    showView();
  }

  @FXML
  public void editLocations() {
    hideButtons();
    currentFunction = "Edit";
    showView();
  }

  @FXML
  public void removeLocation() {
    hideButtons();
    currentFunction = "Remove";
    showView();
  }

  public void enableEditButton() {
    // Consider renaming
    // When location dropdown has a location selected,
    // the confirm and delete buttons will be enabled depending on the attempted Map edit
    if (currentFunction.equals("Edit") && locationsDropdown.getValue() != null) {
      editEnableButton.setDisable(false);
      editNameField.setDisable(false);
      editNameField.setVisible(true);
      confirmButton.setDisable(false);
    }
    if (currentFunction.equals("Remove") && locationsDropdown.getValue() != null) {
      deleteButton.setDisable(false);
    }
  }

  public void showEditFields() {
    selectEnabled = true;
  }

  public void updateLocationSelect() {
    String floor = getFloorLevel();
    locationsDropdown.getItems().clear();
    switch (floor) {
      default:
        break;
      case "L2":
        for (String roomFL2 : roomsFL2) {
          locationsDropdown.getItems().add(roomFL2);
        }
        break;
      case "L1":
        for (String roomFL1 : roomsFL1) {
          locationsDropdown.getItems().add(roomFL1);
        }
        break;
      case "1":
        for (String roomF1 : roomsF1) {
          locationsDropdown.getItems().add(roomF1);
        }
        break;
      case "2":
        for (String roomF2 : roomsF2) {
          locationsDropdown.getItems().add(roomF2);
        }
        break;
      case "3":
        for (String roomF3 : roomsF3) {
          locationsDropdown.getItems().add(roomF3);
        }
        break;
    }
  }

  public void enableConfirm() {
    if (currentFunction.equals("Add")) {}
  }

  @FXML
  public void showView() {
    disableFloorChange();
    updateLocationSelect();
    switch (currentFunction) {
      case "Edit":
        locationsDropdown.setDisable(false);
        locationsDropdown.setVisible(true);
        editEnableButton.setVisible(true);
        editEnableButton.setDisable(true);
        cancelButton.setDisable(false);
        cancelButton.setVisible(true);
        confirmButton.setDisable(true);
        confirmButton.setVisible(true);
        break;
      case "Add":
        selectEnabled = true;
        addLocationName.setVisible(true);
        addLocationName.setDisable(false);
        cancelButton.setDisable(false);
        cancelButton.setVisible(true);
        confirmButton.setVisible(true);
        confirmButton.setDisable(false);
        addLabel.setVisible(true);
        addLabel.setDisable(false);
        typeDropdown.setVisible(true);
        typeDropdown.setDisable(false);
        break;
      case "Remove":
        locationsDropdown.setDisable(false);
        locationsDropdown.setVisible(true);
        warningLabel.setVisible(true);
        deleteButton.setVisible(true);
        deleteButton.setDisable(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        break;
      default:
        break;
    }
  }

  @FXML
  public void hideView() {
    switch (currentFunction) {
      case "Edit":
        locationsDropdown.setValue(null);
        locationsDropdown.setDisable(true);
        locationsDropdown.setVisible(false);
        editEnableButton.setDisable(true);
        editEnableButton.setVisible(false);
        editNameField.setDisable(true);
        editNameField.setVisible(false);
        cancelButton.setDisable(true);
        cancelButton.setVisible(false);
        confirmButton.setDisable(true);
        confirmButton.setVisible(false);
        break;
      case "Add":
        addLocationName.setVisible(false);
        addLocationName.setDisable(true);
        addLocationName.setText(null);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        confirmButton.setVisible(false);
        confirmButton.setDisable(true);
        addLabel.setVisible(false);
        addLabel.setDisable(true);
        typeDropdown.setValue(null);
        typeDropdown.setVisible(false);
        typeDropdown.setDisable(true);
        break;
      case "Remove":
        locationsDropdown.setDisable(true);
        locationsDropdown.setVisible(false);
        warningLabel.setVisible(false);
        deleteButton.setVisible(false);
        deleteButton.setDisable(true);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        break;
      default:
        break;
    }
    selectEnabled = false;
    clearMarker();
    enableFloorChange();
    showButtons();
    initialize();
  }

  @FXML
  public void getCoords(MouseEvent event) {
    // If select is enabled, clicking will alternate between getting coords + setting the circle
    // and clearing coords and hiding circle
    if (selectEnabled) {
      if (firstClick) {
        selectedXCoord = round(event.getX());
        selectedYCoord = round(event.getY());

        sceneXCoord = round(event.getSceneX());
        sceneYCoord = round(event.getSceneY());

        // We will have to figure out how to not have to hard code the adjustment
        marker.setCenterX(sceneXCoord - 82);
        marker.setCenterY(sceneYCoord - 217);
        marker.setVisible(true);
        firstClick = false;
      } else {
        clearMarker();
      }
    }
  }

  public void clearMarker() {
    // function that clears the red dot marker
    marker.setCenterX(0);
    marker.setCenterY(0);
    marker.setVisible(false);
    selectedXCoord = 0;
    selectedYCoord = 0;
    sceneXCoord = 0;
    sceneYCoord = 0;

    firstClick = true;
  }

  public void confirmChanges() {
    if (currentFunction.equals("Add")
        && selectedXCoord != 0
        && selectedYCoord != 0
        && !typeDropdown.getValue().equals("")
        && !addLocationName.getText().equals("")) {
      // Only works if on Add, there are selected X and Y Coords, typeDropdown and addLocationName
      // have values
      int[] newCoords = imageCoordsToCSVCoords((int) selectedXCoord, (int) selectedYCoord);
      String floor = getFloorLevel();
      String building = "Tower";
      String nodeType = typeDropdown.getValue();
      String nodeID = dao.getNextID(floor, nodeType);
      String name = addLocationName.getText();
      Location newLoc =
          new Location(nodeID, newCoords[0], newCoords[1], floor, building, nodeType, name, name);
      // Pass new location into database
      dao.add(newLoc);
      hideView();
    }
    if (currentFunction.equals("Edit")) {
      String name = locationsDropdown.getValue();
      String newName = editNameField.getText();
      LinkedList<Location> listChange = dao.searchFor(name);
      Location change = listChange.pop();
      if (selectedXCoord != 0 && selectedYCoord != 0) {
        // If coordinates are changed, update in location object
        int[] newCoords = imageCoordsToCSVCoords((int) selectedXCoord, (int) selectedYCoord);
        change.setXCoord(newCoords[0]);
        change.setYCoord(newCoords[1]);
      }
      if (!change.getLongName().equals(newName) && !newName.equals("")) {
        // If the new name is not the same as the original long name, change the names to new name.
        change.setLongName(newName);
        change.setShortName(newName);
      }
      dao.update(change);
      hideView();
    }
  }

  public void deleteLoc() {
    String name = locationsDropdown.getValue();
    LinkedList<Location> listChange = dao.searchFor(name);
    Location target = listChange.pop();
    dao.delete(target);
    hideView();
  }

  @FXML
  public void resetLocations() {
    DatabaseController.getInstance().resetAllDBs();
    // reset view
    cancelReset();
  }

  @FXML
  public void resetWarning() {
    addButton.setDisable(true);
    addButton.setVisible(false);
    editButton.setDisable(true);
    editButton.setVisible(false);
    removeButton.setDisable(true);
    removeButton.setVisible(false);
    upButton.setVisible(false);
    upButton.setDisable(true);
    downButton.setVisible(false);
    downButton.setDisable(true);
    floorDisplay.setVisible(false);

    resetConfirm.setDisable(false);
    resetConfirm.setVisible(true);
    resetCancel.setVisible(true);
    resetCancel.setDisable(false);
    resetWarning.setVisible(true);
    resetButton.setVisible(false);
  }

  @FXML
  public void cancelReset() {
    addButton.setDisable(false);
    addButton.setVisible(true);
    editButton.setDisable(false);
    editButton.setVisible(true);
    removeButton.setDisable(false);
    removeButton.setVisible(true);
    upButton.setVisible(true);
    upButton.setDisable(false);
    downButton.setVisible(true);
    downButton.setDisable(false);
    floorDisplay.setVisible(true);

    resetConfirm.setDisable(true);
    resetConfirm.setVisible(false);
    resetCancel.setVisible(false);
    resetCancel.setDisable(true);
    resetWarning.setVisible(false);
    resetButton.setVisible(true);
  }
}

// Things to Consider Adding
// Locking the Confirm button until all necessary fields are updated.
// Dealing with locations that have the same names (if necessary)
// User feedback that location was added, updated, deleted.
