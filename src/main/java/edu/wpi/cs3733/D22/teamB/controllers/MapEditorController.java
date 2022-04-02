package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

  private String currentFunction;

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
  public void editLocations() {
    hideButtons();
    currentFunction = "Edit";
    showView();
  }

  @FXML
  public void showView() {
    switch (currentFunction) {
      case "Edit":
        locationsDropdown.setDisable(false);
        locationsDropdown.setVisible(true);
        editEnableButton.setDisable(false);
        editEnableButton.setVisible(true);
        editNameField.setDisable(false);
        editNameField.setVisible(true);
        cancelButton.setDisable(false);
        cancelButton.setVisible(true);
        confirmButton.setDisable(false);
        confirmButton.setVisible(true);
        break;
      case "Add":
        break;
      case "Remove":
        break;
      default:
        break;
    }
  }

  @FXML
  public void hideView() {
    switch (currentFunction) {
      case "Edit":
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
        break;
      case "Remove":
        break;
      default:
        break;
    }
    showButtons();
  }
}
