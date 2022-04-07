package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  public static Employee currentUser = null;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("views/loginPage.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
