package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

public class AnalyticsController extends MenuBarController implements Initializable {

  @FXML private StackedBarChart stackedBarChart;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Types");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Units");

    stackedBarChart = new StackedBarChart(xAxis, yAxis);

    XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
    XYChart.Series<String, Integer> series2 = new XYChart.Series<String, Integer>();

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

    series1.setName("Available");
    series1.getData().add(new XYChart.Data<>("Beds", numBedsAvailable));
    series1.getData().add(new XYChart.Data<>("Recliners", numReclinersAvailable));
    series1.getData().add(new XYChart.Data<>("Pumps", numPumpsAvailable));
    series1.getData().add(new XYChart.Data<>("X-Rays", numXraysAvailable));

    series2.setName("Unavailable");
    series2.getData().add(new XYChart.Data<>("Beds", numBedsUnavailable));
    series2.getData().add(new XYChart.Data<>("Recliners", numReclinersUnavailable));
    series2.getData().add(new XYChart.Data<>("Pumps", numPumpsUnavailable));
    series2.getData().add(new XYChart.Data<>("X-Rays", numXraysUnavailable));

    stackedBarChart.setTitle("Medical Equipment");
    stackedBarChart.setCategoryGap(50);
    stackedBarChart.getData().addAll(series1, series2);
  }
}
