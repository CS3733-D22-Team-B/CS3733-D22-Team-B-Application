package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.requests.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import eu.hansolo.fx.charts.*;
import eu.hansolo.fx.charts.Axis;
import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.data.TYChartItem;
import eu.hansolo.fx.charts.data.XYChartItem;
import eu.hansolo.fx.charts.series.XYSeries;
import eu.hansolo.fx.charts.tools.Order;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class AnalyticsController extends MenuBarController {

  @FXML private AnchorPane pane;
  @FXML private StackedBarChart stackedBarChart;
  private eu.hansolo.fx.charts.XYChart<XYChartItem> lineChart;
  private CoxcombChart coxcombChart;

  public void initialize() {
    initializeBarChart();
    intializeLineChart();
    initalizePieChart();
  }

  public void initializeBarChart() {
    stackedBarChart.setTitle("Medical Equipment");

    DatabaseController medEqDAO = DatabaseController.getInstance();
    List<MedicalEquipment> equipmentList = medEqDAO.listMedicalEquipment();

    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Medical Equipment");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Number of Medical Equipment");

    XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
    series1.setName("Available");
    XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
    series2.setName("Unavailable");

    int bedsA = 0;
    int bedsU = 12;
    int recA = 0;
    int recU = 10;
    int pumA = 0;
    int pumpU = 1;
    int xraA = 0;
    int xrayU = 1;

    for (MedicalEquipment equipment : equipmentList) {
      switch (equipment.getType()) {
        case "BED":
          if (equipment.getAvailability().equalsIgnoreCase("AVAILABLE")) {
            bedsA++;
          } else {
            bedsU++;
          }
          break;
        case "RECL":
          if (equipment.getAvailability().equalsIgnoreCase("AVAILABLE")) {
            recA++;
          } else {
            recU++;
          }
          break;
        case "PUMP":
          if (equipment.getAvailability().equalsIgnoreCase("AVAILABLE")) {
            pumA++;
          } else {
            pumpU++;
          }
          break;
        case "XR":
          if (equipment.getAvailability().equalsIgnoreCase("AVAILABLE")) {
            xraA++;
          } else {
            xrayU++;
          }
          break;
      }
    }

    series1.getData().add(new XYChart.Data<>("Beds", bedsA));
    series1.getData().add(new XYChart.Data<>("Recliners", recA));
    series1.getData().add(new XYChart.Data<>("Pumps", pumA));
    series1.getData().add(new XYChart.Data<>("X-Rays", xraA));

    series2.getData().add(new XYChart.Data<>("Beds", bedsU));
    series2.getData().add(new XYChart.Data<>("Recliners", recU));
    series2.getData().add(new XYChart.Data<>("Pumps", pumpU));
    series2.getData().add(new XYChart.Data<>("X-Rays", xrayU));

    stackedBarChart.getData().addAll(series1, series2);
    stackedBarChart.setLegendVisible(true);
    stackedBarChart = new StackedBarChart(xAxis, yAxis);
  }

  public void initalizePieChart() {
    DatabaseController db = DatabaseController.getInstance();
    List<Request> requests = db.listRequests();

    int equipment = 0;
    int lab = 0;
    int medicine = 0;
    int meal = 0;
    int language = 0;
    int ipt = 0;
    int gift = 0;
    int sanitation = 0;
    int laundry = 0;
    int security = 0;
    int custom = 0;

    for (Request request : requests) {
      if (request instanceof EquipmentRequest) {
        equipment++;
      } else if (request instanceof LabRequest) {
        lab++;
      } else if (request instanceof MedicineRequest) {
        medicine++;
      } else if (request instanceof MealRequest) {
        meal++;
      } else if (request instanceof InterpreterRequest) {
        language++;
      } else if (request instanceof InternalPatientTransferRequest) {
        ipt++;
      } else if (request instanceof GiftRequest) {
        gift++;
      } else if (request instanceof SanitationRequest) {
        sanitation++;
      } else if (request instanceof LaundryRequest) {
        laundry++;
      } else if (request instanceof SecurityRequest) {
        security++;
      } else if (request instanceof CustomRequest) {
        custom++;
      }
    }

    List<ChartItem> items =
        List.of(
            new ChartItem("Equipment", equipment, Color.web("#ff0000")),
            new ChartItem("Lab", lab, Color.web("#7f7f00")),
            new ChartItem("Medicine", medicine, Color.web("#ffff00")),
            new ChartItem("Meal", meal, Color.web("#00ff00")),
            new ChartItem("Interpreter", language, Color.web("#007f7f")),
            new ChartItem("Internal Patient Transfer", ipt, Color.web("#007fff")),
            new ChartItem("Gift", gift, Color.web("#ff00ff")),
            new ChartItem("Sanitation", sanitation, Color.web("#ff7f00")),
            new ChartItem("Laundry", laundry, Color.web("#ff7f7f")),
            new ChartItem("Security", security, Color.web("#ff00ff")),
            new ChartItem("Custom", custom, Color.web("#7f7f7f")));

    coxcombChart =
        CoxcombChartBuilder.create()
            .items(items)
            .textColor(Color.WHITE)
            .autoTextColor(false)
            .equalSegmentAngles(true)
            .order(Order.ASCENDING)
            .build();

    pane.getChildren().add(coxcombChart);
    coxcombChart.setLayoutX(300);
    coxcombChart.setLayoutY(300);
  }

  public void intializeLineChart() {
    int millisInHour = 3600000;

    DatabaseController db = DatabaseController.getInstance();
    LinkedList<Activity> activities = db.listActivities();

    ArrayList<Integer> times = new ArrayList<>();
    long lastHour =
        activities.getFirst().getDateAndTime().toInstant().toEpochMilli() / millisInHour;
    int count = 0;

    for (Activity activity : activities) {
      long currentHour = activity.getDateAndTime().toInstant().toEpochMilli() / millisInHour;
      if (currentHour == lastHour) {
        count++;
      } else {
        times.add(count);
        count = 1;
        lastHour = currentHour;
      }
    }

    LocalDateTime start =
        LocalDateTime.ofInstant(activities.getFirst().getDateAndTime().toInstant(), ZoneOffset.UTC);
    LocalDateTime end =
        LocalDateTime.ofInstant(activities.getLast().getDateAndTime().toInstant(), ZoneOffset.UTC);

    System.out.println(start);
    System.out.println(end);

    List<ChartItem> tyData1 = new ArrayList<>();

    for (int i = 0; i < times.size(); i++) {
      System.out.println(
          start.plusHours(i).toInstant(ZoneOffset.UTC).toEpochMilli() / 3600000 - 458000);
      tyData1.add(new ChartItem(times.get(i), Color.web("#00AEF5")));
    }

    XYSeries<TYChartItem> tySeries1 =
        new XYSeries(tyData1, ChartType.LINE, Color.RED, Color.rgb(255, 0, 0, 0.5));
    tySeries1.setSymbolsVisible(false);

    // XYChart

    Axis xAxisBottom = createBottomTimeAxis(start, end, "HH", true);
    Axis yAxisLeft = createLeftYAxis(0, 20, true);
    eu.hansolo.fx.charts.XYChart<TYChartItem> tyChart =
        new eu.hansolo.fx.charts.XYChart<>(new XYPane(tySeries1), yAxisLeft, xAxisBottom);

    pane.getChildren().add(tyChart);
    ;
    tyChart.setLayoutX(500);
    tyChart.setLayoutY(200);
  }

  private Axis createLeftYAxis(final double MIN, final double MAX, final boolean AUTO_SCALE) {
    Axis axis = new Axis(Orientation.VERTICAL, Position.LEFT);
    axis.setMinValue(MIN);
    axis.setMaxValue(MAX);
    axis.setAutoScale(AUTO_SCALE);

    AnchorPane.setTopAnchor(axis, 0d);

    return axis;
  }

  private Axis createBottomTimeAxis(
      final LocalDateTime START,
      final LocalDateTime END,
      final String PATTERN,
      final boolean AUTO_SCALE) {
    Axis axis =
        new Axis(
            START.toInstant(ZoneOffset.UTC).toEpochMilli() / 3600000 - 458000,
            END.toInstant(ZoneOffset.UTC).toEpochMilli() / 3600000 - 458000,
            Orientation.HORIZONTAL,
            Position.BOTTOM);
    axis.setDateTimeFormatPattern(PATTERN);
    axis.setAutoScale(AUTO_SCALE);

    AnchorPane.setBottomAnchor(axis, 0d);

    System.out.println(axis.getMinValue());
    System.out.println(axis.getMaxValue());

    return axis;
  }
}
