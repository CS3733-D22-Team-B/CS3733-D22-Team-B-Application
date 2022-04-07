package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import edu.wpi.cs3733.D22.teamB.requests.RequestQueue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class App extends Application {
  public static Employee currentUser = null;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
    primaryStage.setTitle("Login");
    primaryStage.setScene(new Scene(root));
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.show();

    // import requests from database
    EquipmentRequestDB equipmentRequestDB = EquipmentRequestDB.getInstance();
    LabRequestsDB labRequestsDB = LabRequestsDB.getInstance();
    ServiceRequestsDB serviceDB = ServiceRequestsDB.getInstance();

    LinkedList<EquipmentRequest> equipmentRequests = equipmentRequestDB.list();
    LinkedList<LabRequest> labRequests = labRequestsDB.list();
    LinkedList<Request> serviceRequests = serviceDB.list();

    for (EquipmentRequest request : equipmentRequests) {
      RequestQueue.addRequest(request);
    }
    for (LabRequest request : labRequests) {
      RequestQueue.addRequest(request);
    }
    for (Request request : serviceRequests) {
      RequestQueue.addRequest(request);
    }
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
