package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class AnalyticsController implements Initializable {

  private ObservableList<PieChart.Data> data;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    LinkedList<MedicalEquipment> medEq = DatabaseController.getInstance().listMedicalEquipment();

    int numBedsAvailable = 0;
    int numBedsUnavailable = 0;
    int numReclinersAvailable = 0;
    int numReclinersUnavailable = 0;
    int numPumpsAvailable = 0;
    int numPumpsUnavailable = 0;
    int numXraysAvailable = 0;
    int numXraysUnavailable = 0;

    for (MedicalEquipment eq : medEq) {
      switch (eq.getType()) {
        default:
        case "BED":
          if (eq.getAvailability().equalsIgnoreCase("Available")) {
            numBedsAvailable++;
          } else {
            numBedsUnavailable++;
          }
          break;
        case "RECL":
          if (eq.getAvailability().equalsIgnoreCase("Available")) {
            numReclinersAvailable++;
          } else {
            numReclinersUnavailable++;
          }
          break;
        case "PUMP":
          if (eq.getAvailability().equalsIgnoreCase("Available")) {
            numPumpsAvailable++;
          } else {
            numPumpsUnavailable++;
          }
          break;
        case "XR":
          if (eq.getAvailability().equalsIgnoreCase("Available")) {
            numXraysAvailable++;
          } else {
            numXraysUnavailable++;
          }
          break;
      }
    }

    data =
        FXCollections.observableArrayList(
            new PieChart.Data("Available Beds", numBedsAvailable),
            new PieChart.Data("Unavailable Beds", numBedsUnavailable),
            new PieChart.Data("Available Recliners", numReclinersAvailable),
            new PieChart.Data("Recliners", numReclinersUnavailable),
            new PieChart.Data("PUMPS", numPumpsAvailable),
            new PieChart.Data("Unavailable Pumps", numPumpsUnavailable),
            new PieChart.Data("Available X-Rays", numXraysAvailable));
    new PieChart.Data("Unavailable X-Rays", numXraysUnavailable);
    PieChart chart = new PieChart(data);
    chart.setTitle("Equipment Status");
  }
}
