package edu.wpi.cs3733.D22.teamB;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIController {
  private static UIController instance = new UIController();
  public Stage primaryStage;

  private UIController() {}

  public static UIController getInstance() {
    return instance;
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void goToPage(String fxmlFileName) throws Exception {
    FXMLLoader loader =
        new FXMLLoader(
            instance
                .getClass()
                .getResource("/edu/wpi/cs3733/D22/teamB/views/" + fxmlFileName + ".fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);

    if (App.currentUser != null) {
      scene.getStylesheets().clear();
      scene
          .getStylesheets()
          .add(
              "/edu/wpi/cs3733/D22/teamB/views/newStyles/"
                  + App.currentUser.getColorTheme()
                  + ".css");
    }
    this.primaryStage.setScene(scene);
  }
}
