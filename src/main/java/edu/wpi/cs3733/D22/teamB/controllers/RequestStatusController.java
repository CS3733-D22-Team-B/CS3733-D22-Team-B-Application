package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.RequestStatusModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RequestStatusController implements Initializable {
  @FXML private TableView<RequestStatusModel> statusTable;
  @FXML public TableColumn<RequestStatusModel, String> columnType;
  @FXML public TableColumn<RequestStatusModel, String> columnStatus;
  @FXML public TableColumn<RequestStatusModel, String> columnAssigned;
  private ObservableList<RequestStatusModel> requestStatuses =
      FXCollections.observableArrayList(new RequestStatusModel("Pending", "Meal Service", "Staff"));

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // make sure the property value factory should be exactly same as the e.g getStudentId from your
    // model class
    columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    columnStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    columnAssigned.setCellValueFactory(new PropertyValueFactory<>("Assigned"));
    // add your data to the table here.
    statusTable.setItems(requestStatuses);
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
