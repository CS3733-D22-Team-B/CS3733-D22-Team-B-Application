package edu.wpi.cs3733.D22.teamB.controllers;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.MedicalEquipment;
import edu.wpi.cs3733.D22.teamB.requests.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import eu.hansolo.fx.charts.*;
import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.heatmap.HeatMap;
import eu.hansolo.fx.charts.heatmap.HeatMapBuilder;
import eu.hansolo.fx.charts.heatmap.OpacityDistribution;
import eu.hansolo.fx.charts.tools.ColorMapping;
import eu.hansolo.fx.charts.tools.Order;
import eu.hansolo.fx.charts.tools.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class AnalyticsController extends MenuBarController {

  @FXML private AnchorPane pane;
  @FXML private ImageView mapImage;

  @FXML private StackedBarChart stackedBarChart;

  @FXML private LineChart lineChart;

  @FXML private Text requestText;
  private CoxcombChart coxcombChart;
  private Legend legend;

  private HeatMap heatMap;

  private List<Point> locationPoints;

  public void initialize() {
    initializeBarChart();
    intializeLineChart();
    initalizePieChart();
    initializeHeatMap();
    loadFloor1HeatMap(null);
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
    int bedsU = 0;
    int recA = 0;
    int recU = 0;
    int pumA = 0;
    int pumpU = 0;
    int xraA = 0;
    int xrayU = 0;

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
    stackedBarChart.setMaxWidth(10);
    stackedBarChart = new StackedBarChart(xAxis, yAxis);
  }

  public void initalizePieChart() {
    DatabaseController db = DatabaseController.getInstance();
    List<Request> requests = db.listRequests();

    requestText.setText(requests.size() + "\nRequests");

    String equipmentColor = "#E91C23FF";
    String labColor = "#ED5929FF";
    String medicineColor = "#F7941DFF";
    String mealColor = "#FBF002FF";
    String langaugeColor = "#3BB44AFF";
    String iptColor = "#28A79EFF";
    String giftColor = "#3CADF1FF";
    String sanitationColor = "#2B73BFFF";
    String laundryColor = "#652B91FF";
    String securityColor = "#922590FF";
    String customColor = "#FF63A7FF";

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
            new ChartItem("Equipment", equipment, Color.web(equipmentColor)),
            new ChartItem("Lab", lab, Color.web(labColor)),
            new ChartItem("Medicine", medicine, Color.web(medicineColor)),
            new ChartItem("Meal", meal, Color.web(mealColor)),
            new ChartItem("Interpreter", language, Color.web(langaugeColor)),
            new ChartItem("Internal Patient Transfer", ipt, Color.web(iptColor)),
            new ChartItem("Gift", gift, Color.web(giftColor)),
            new ChartItem("Sanitation", sanitation, Color.web(sanitationColor)),
            new ChartItem("Laundry", laundry, Color.web(laundryColor)),
            new ChartItem("Security", security, Color.web(securityColor)),
            new ChartItem("Custom", custom, Color.web(customColor)));

    coxcombChart =
        CoxcombChartBuilder.create()
            .items(items)
            .textColor(Color.WHITE)
            .autoTextColor(false)
            .equalSegmentAngles(true)
            .order(Order.ASCENDING)
            .build();

    pane.getChildren().add(coxcombChart);
    coxcombChart.setLayoutX(175);
    coxcombChart.setLayoutY(90);

    // =======================================================
    LegendItem item1 =
        new LegendItem(Symbol.CIRCLE, "Equipment", Color.web(equipmentColor), Color.BLACK);
    LegendItem item2 = new LegendItem(Symbol.CIRCLE, "Lab", Color.web(labColor), Color.BLACK);
    LegendItem item3 =
        new LegendItem(Symbol.CIRCLE, "Medicine", Color.web(medicineColor), Color.BLACK);
    LegendItem item4 = new LegendItem(Symbol.CIRCLE, "Meal", Color.web(mealColor), Color.BLACK);
    LegendItem item5 =
        new LegendItem(Symbol.CIRCLE, "Interpreter", Color.web(langaugeColor), Color.BLACK);
    LegendItem item6 = new LegendItem(Symbol.CIRCLE, "Transfer", Color.web(iptColor), Color.BLACK);
    LegendItem item7 = new LegendItem(Symbol.CIRCLE, "Gift", Color.web(giftColor), Color.BLACK);
    LegendItem item8 =
        new LegendItem(Symbol.CIRCLE, "Sanitation", Color.web(sanitationColor), Color.BLACK);
    LegendItem item9 =
        new LegendItem(Symbol.CIRCLE, "Laundry", Color.web(laundryColor), Color.BLACK);
    LegendItem item10 =
        new LegendItem(Symbol.CIRCLE, "Security", Color.web(securityColor), Color.BLACK);
    LegendItem item11 =
        new LegendItem(Symbol.CIRCLE, "Custom", Color.web(customColor), Color.BLACK);

    legend =
        new Legend(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11);
    legend.setOrientation(Orientation.VERTICAL);

    pane.getChildren().add(legend);
    legend.setLayoutX(305 * 1.25);
    legend.setLayoutY(110 * 0.75);
    legend.setScaleX(0.75);
    legend.setScaleY(0.75);
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

    XYChart.Series series = new XYChart.Series();
    series.setName("Activities");

    for (int i = 0; i < times.size(); i++) {
      series.getData().add(new XYChart.Data(i, times.get(i)));
    }

    NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Hour");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Activity");
    yAxis.setTickLabelFormatter(
        new StringConverter<Number>() {
          @Override
          public String toString(Number number) {
            int num = number.intValue();
            return num + "";
          }

          @Override
          public Number fromString(String s) {
            return Integer.parseInt(s);
          }
        });

    lineChart.getData().add(series);
    lineChart.setTitle("Activity per hour");
    lineChart = new LineChart<>(xAxis, yAxis);
  }

  public void initializeHeatMap() {
    heatMap =
        HeatMapBuilder.create()
            .prefSize(425, 425)
            .colorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
            .spotRadius(25)
            .opacityDistribution(OpacityDistribution.CUSTOM)
            .fadeColors(true)
            .build();

    pane.getChildren().add(heatMap);
    heatMap.setLayoutX(500);
    heatMap.setLayoutY(80);
    heatMap.setFitWidth(425);
    heatMap.setFitHeight(373);
  }

  @FXML
  public void loadLower2HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL2.png"));
    drawHeatMap(0, "L2");
  }

  @FXML
  public void loadLower1HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL1.png"));
    drawHeatMap(1, "L1");
  }

  @FXML
  public void loadFloor1HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor1.png"));
    drawHeatMap(2, "1");
  }

  @FXML
  public void loadFloor2HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor2.png"));
    drawHeatMap(3, "2");
  }

  @FXML
  public void loadFloor3HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor3.png"));
    drawHeatMap(4, "3");
  }

  @FXML
  public void loadFloor4HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor4.png"));
    drawHeatMap(5, "4");
  }

  @FXML
  public void loadFloor5HeatMap(ActionEvent event) {
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor5.png"));
    drawHeatMap(6, "5");
  }

  private void drawHeatMap(int floorNumber, String floor) {
    locationPoints = new ArrayList<Point>();
    clear();

    ActivityDB db = ActivityDB.getInstance();

    for (Activity activity : db.list()) {
      if (activity.getType().equals("Location")) {
        Location location = LocationsDB.getInstance().getLocation(activity.getTypeID());

        if (location != null && location.getFloor().equals(floor)) {
          double[] coords = newCoords(location.getXCoord(), location.getYCoord());
          locationPoints.add(new Point(coords[0], coords[1]));
        }
      } else if (activity.getType().equals("Request")) {
        Request request = ServiceRequestsDB.getInstance().getByID(activity.getTypeID());

        if (request != null) {
          Location location = LocationsDB.getInstance().getLocation(request.getLocationID());

          if (location != null && location.getFloor().equals(floor)) {
            double[] coords = newCoords(location.getXCoord(), location.getYCoord());
            locationPoints.add(new Point(coords[0], coords[1]));
          }
        }
      }
    }

    heatMap.addSpots(locationPoints);
  }

  private void clear() {
    heatMap.clearHeatMap();
  }

  private double[] newCoords(double x, double y) {
    double mapWidth = 1060;
    double mapHeight = 930;
    double fitWidth = mapImage.getFitWidth();
    double fitHeight = mapImage.getFitHeight();
    double ratio = (fitHeight / fitWidth) * 1.05;
    double xView = ((x / mapWidth) * fitWidth) * ratio;
    double yView = ((y / mapHeight) * fitHeight) * ratio;
    return new double[] {xView, yView};
  }
}
