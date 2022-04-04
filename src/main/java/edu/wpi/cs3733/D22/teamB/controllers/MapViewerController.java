package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.*;

public class MapViewerController extends MenuBarController {
  @FXML private JFXButton upButton;
  @FXML private JFXButton downButton;
  @FXML private ImageView mapImage;
  @FXML private Label floorDisplay;

  private int floorLevel = 2;

  @FXML
  public void moveUp(ActionEvent event) {
    switch (floorLevel) {
      default:
      case 0:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        floorDisplay.setText("L1");
        downButton.setDisable(false);
        break;
      case 1:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        floorDisplay.setText("1");
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        floorDisplay.setText("2");
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/thirdFloor.png"));
        floorDisplay.setText("3");
        upButton.setDisable(true);
        break;
    }
    floorLevel++;
  }

  @FXML
  public void moveDown(ActionEvent event) {
    switch (floorLevel) {
      default:
      case 1:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel2.png"));
        floorDisplay.setText("L2");
        downButton.setDisable(true);
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        floorDisplay.setText("L1");
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        floorDisplay.setText("1");
        break;
      case 4:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        floorDisplay.setText("2");
        upButton.setDisable(false);
        break;
    }
    floorLevel--;
  }

  @FXML
  public void disableFloorChange() {
    upButton.setDisable(true);
    downButton.setDisable(true);
  }

  @FXML
  public void enableFloorChange() {
    upButton.setDisable(false);
    downButton.setDisable(false);
  }

  public String getFloorLevel() {
    return floorDisplay.getText();
  }
}
