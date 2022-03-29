package edu.wpi.cs3733.D22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.*;

public class MapViewerController {
  @FXML private JFXButton upButton;
  @FXML private JFXButton downButton;
  @FXML private ImageView mapImage;

  private int floorLevel = 2;

  @FXML
  public void moveUp(ActionEvent event) {
    switch (floorLevel) {
      default:
      case 0:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        downButton.setDisable(false);
        break;
      case 1:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/thirdFloor.png"));
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
        downButton.setDisable(true);
        break;
      case 2:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/lowerLevel1.png"));
        break;
      case 3:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/firstFloor.png"));
        break;
      case 4:
        mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/secondFloor.png"));
        upButton.setDisable(false);
        break;
    }
    floorLevel--;
  }

  @FXML
  void goToHomepage(ActionEvent event) throws Exception {
    Parent homepageRoot =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamB/views/homepage.fxml"));
    Scene homepageScene = new Scene(homepageRoot);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(homepageScene);
    window.show();
  }
}
