package edu.wpi.cs3733.D22.teamB.controllers;

import static java.lang.Math.round;
import static java.lang.String.valueOf;

import com.jfoenix.controls.JFXButton;
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

  @FXML private Label tempX;
  @FXML private Label tempY;

  @FXML private Label tempX1;
  @FXML private Label tempY1;

  @FXML private Circle marker;

  private String currentFunction;
  private double selectedXCoord;
  private double selectedYCoord;
  private double sceneXCoord;
  private double sceneYCoord;
  private Boolean firstClick = true;
  private Boolean selectEnabled = false;

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
    //Consider renaming
    //When location dropdown has a location selected,
    //the confirm and delete buttons will be enabled depending on the attempted Map edit
    if (currentFunction.equals("Edit") && locationsDropdown.getValue() != null) {
      editEnableButton.setDisable(false);
      confirmButton.setDisable(false);
    }
    if (currentFunction.equals("Remove") && locationsDropdown.getValue() != null) {
      deleteButton.setDisable(false);
    }
  }

  public void showEditFields() {
    editNameField.setDisable(false);
    editNameField.setVisible(true);
    selectEnabled = true;
  }

  @FXML
  public void showView() {
    disableFloorChange();
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
  }

  @FXML
  public void getCoords(MouseEvent event) {
    //If select is enabled, clicking will alternate between getting coords + setting the circle
    // and clearing coords and hiding circle
    if (selectEnabled) {
      if (firstClick) {
        selectedXCoord = round(event.getX());
        selectedYCoord = round(event.getY());

        sceneXCoord = round(event.getSceneX());
        sceneYCoord = round(event.getSceneY());

        updateTempFields();

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
    //function that clears the red dot marker
    marker.setCenterX(0);
    marker.setCenterY(0);
    marker.setVisible(false);
    selectedXCoord = 0;
    selectedYCoord = 0;
    sceneXCoord = 0;
    sceneYCoord = 0;
    updateTempFields();

    firstClick = true;
  }

  public void updateTempFields() {
    //This function is used to update the labels next to the arrow buttons,
    //show the values of the selected and scene coords
    tempX.setText(valueOf(selectedXCoord));
    tempY.setText(valueOf(selectedYCoord));

    tempX1.setText(valueOf(sceneXCoord));
    tempY1.setText(valueOf(sceneYCoord));
  }
}
