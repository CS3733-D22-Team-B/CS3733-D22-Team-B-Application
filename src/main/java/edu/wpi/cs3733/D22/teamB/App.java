package edu.wpi.cs3733.D22.teamB;

import edu.wpi.cs3733.D22.teamB.databases.*;
import javafx.application.Application;
import javafx.stage.Stage;
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
    UIController.getInstance().setPrimaryStage(primaryStage);
    UIController.getInstance().goToPage("analyticsPage");

    primaryStage.setTitle("CS3733 Project");
    primaryStage.setMinHeight(560);
    primaryStage.setMinWidth(960);
    // primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
