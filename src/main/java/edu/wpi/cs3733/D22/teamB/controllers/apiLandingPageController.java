package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.api.API;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
import edu.wpi.cs3733.D22.teamB.api.ServiceException;
import java.io.IOException;
import javafx.fxml.FXML;

public class apiLandingPageController extends MenuBarController {
  @FXML
  public void launchTeamBAPI() throws ServiceException {
    API api = new API();
    try {
      api.run(0, 0, 600, 400, null, null, null);
      DatabaseController databaseController = new DatabaseController();
      databaseController.addEmployee("Wong", "Wilson");
    } catch (IOException e) {
    }
  }

  @FXML
  public void launchTeamCAPI() throws ServiceException {
    API api = new API();
    try {
      api.run(0, 0, 600, 400, null, null, null);
      DatabaseController databaseController = new DatabaseController();
      databaseController.addEmployee("Wong", "Wilson");
    } catch (IOException e) {
    }
  }

  @FXML
  public void launchTeamDAPI() throws ServiceException {
    API api = new API();
    try {
      api.run(0, 0, 600, 400, null, null, null);
      DatabaseController databaseController = new DatabaseController();
      databaseController.addEmployee("Wong", "Wilson");
    } catch (IOException e) {
    }
  }
}
