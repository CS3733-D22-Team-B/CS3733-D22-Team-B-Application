package edu.wpi.cs3733.D22.teamB.controllers.tables;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.DateHelper;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.databases.EquipmentAlert;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DashboardController extends MenuBarController {
  @FXML ImageView mapImage;
  @FXML JFXButton f5Button;
  @FXML JFXButton f4Button;
  @FXML JFXButton f3Button;
  @FXML JFXButton f2Button;
  @FXML JFXButton f1Button;
  @FXML JFXButton l1Button;
  @FXML JFXButton l2Button;
  @FXML private JFXButton currentButton;
  @FXML private Label overviewLabel;
  @FXML private Label cleanBed, cleanRecliner, cleanPump, cleanXRay;
  @FXML private Label dirtyBed, dirtyRecliner, dirtyPump, dirtyXRay;

  @FXML private AnchorPane equipmentCardsPane;
  @FXML private AnchorPane requestCardsPane;
  @FXML private AnchorPane patientsCardsPane;
  @FXML private Accordion activityPane;

  @FXML private TabPane tabPane;
  @FXML TableView alertTable;
  @FXML TableColumn<EquipmentAlert, String> columnLocation;
  @FXML TableColumn<EquipmentAlert, String> columnType;

  private ServiceRequestsDB servDAO;
  private MedicalEquipmentDB medDAO;
  private PatientsDB patientDAO;
  private ActivityDB activityDAO;
  private ObservableList<EquipmentAlert> alerts = FXCollections.observableArrayList();

  private LinkedList<String> requestsF1 = new LinkedList<>(),
      requestsF2 = new LinkedList<String>(),
      requestsF3 = new LinkedList<>(),
      requestsF4 = new LinkedList<>(),
      requestsF5 = new LinkedList<>(),
      requestsLL1 = new LinkedList<>(),
      requestsLL2 = new LinkedList<>();
  private LinkedList<MedicalEquipment> medEquipmentF1 = new LinkedList<>(),
      medEquipmentF2 = new LinkedList<>(),
      medEquipmentF3 = new LinkedList<>(),
      medEquipmentF4 = new LinkedList<>(),
      medEquipmentF5 = new LinkedList<>(),
      medEquipmentLL1 = new LinkedList<>(),
      medEquipmentLL2 = new LinkedList<>();
  private LinkedList<Patient> patientsF1 = new LinkedList<>(),
      patientsF2 = new LinkedList<>(),
      patientsF3 = new LinkedList<>(),
      patientsF4 = new LinkedList<>(),
      patientsF5 = new LinkedList<>(),
      patientsLL1 = new LinkedList<>(),
      patientsLL2 = new LinkedList<>();

  private HashMap<String, LinkedList<Activity>> activityMap = new HashMap<>();

  public void initialize() {
    servDAO = ServiceRequestsDB.getInstance();
    medDAO = MedicalEquipmentDB.getInstance();
    patientDAO = PatientsDB.getInstance();
    activityDAO = ActivityDB.getInstance();

    List<Request> requests = servDAO.list();

    for (Request serviceRequest : servDAO.list()) {
      Location serviceRequestLocation = serviceRequest.getLocation();
      Patient serviceRequestPatient = serviceRequest.getPatient();

      if (serviceRequestLocation != null) {
        switch (serviceRequestLocation.getFloor()) {
          case "1":
            requestsF1.add(serviceRequest.getRequestID());
            break;
          case "2":
            requestsF2.add(serviceRequest.getRequestID());
            break;
          case "3":
            requestsF3.add(serviceRequest.getRequestID());
            break;
          case "4":
            requestsF4.add(serviceRequest.getRequestID());
            break;
          case "5":
            requestsF5.add(serviceRequest.getRequestID());
            break;
          case "L1":
            requestsLL1.add(serviceRequest.getRequestID());
            break;
          case "L2":
            requestsLL2.add(serviceRequest.getRequestID());
            break;
        }
      } else if (serviceRequestPatient != null) {
        switch (serviceRequestPatient.getFloor()) {
          case "1":
            requestsF1.add(serviceRequest.getRequestID());
            break;
          case "2":
            requestsF2.add(serviceRequest.getRequestID());
            break;
          case "3":
            requestsF3.add(serviceRequest.getRequestID());
            break;
          case "4":
            requestsF4.add(serviceRequest.getRequestID());
            break;
          case "5":
            requestsF5.add(serviceRequest.getRequestID());
            break;
          case "L1":
            requestsLL1.add(serviceRequest.getRequestID());
            break;
          case "L2":
            requestsLL2.add(serviceRequest.getRequestID());
            break;
        }
      }
    }

    for (MedicalEquipment medEquipment : medDAO.list()) {
      Location equipmentLocation = medEquipment.getLocation();

      switch (equipmentLocation.getFloor()) {
        case "1":
          medEquipmentF1.add(medEquipment);
          break;
        case "2":
          medEquipmentF2.add(medEquipment);
          break;
        case "3":
          medEquipmentF3.add(medEquipment);
          break;
        case "4":
          medEquipmentF4.add(medEquipment);
          break;
        case "5":
          medEquipmentF5.add(medEquipment);
          break;
        case "L1":
          medEquipmentLL1.add(medEquipment);
          break;
        case "L2":
          medEquipmentLL2.add(medEquipment);
          break;
      }
    }

    for (Patient patient : patientDAO.list()) {
      Location patientLocation = patient.getLocation();
      switch (patientLocation.getFloor()) {
        case "1":
          patientsF1.add(patient);
          break;
        case "2":
          patientsF2.add(patient);
          break;
        case "3":
          patientsF3.add(patient);
          break;
        case "4":
          patientsF4.add(patient);
          break;
        case "5":
          patientsF5.add(patient);
          break;
        case "L1":
          patientsLL1.add(patient);
          break;
        case "L2":
          patientsLL2.add(patient);
          break;
      }
    }

    populateActivityMap();
    displayActivityReport();

    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnLocation.setCellValueFactory(new PropertyValueFactory<>("floor"));
    for (EquipmentAlert alert : AlertQueue.getAlerts()) {
      alerts.add(alert);
    }

    alertTable.setItems(alerts);

    currentButton = f1Button;
    currentButton.getStyleClass().add("request-button-selected");
    loadFloor1Information(null);
  }

  private void drawEquipmentCard(int x, int y, MedicalEquipment equipment) {
    Rectangle rectangle = new Rectangle(x, y, 160, 210);
    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(1);
    rectangle.setArcHeight(25);
    rectangle.setArcWidth(25);
    rectangle.setStrokeType(StrokeType.INSIDE);
    rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
    rectangle.setStrokeLineJoin(StrokeLineJoin.ROUND);
    rectangle.setStrokeMiterLimit(10);

    Label equipmentName = new Label(equipment.getName());
    equipmentName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    equipmentName.setTextAlignment(TextAlignment.CENTER);
    equipmentName.setAlignment(Pos.CENTER);
    equipmentName.setLayoutX(x);
    equipmentName.setLayoutY(y + 10);
    equipmentName.setPrefWidth(160);
    equipmentName.setPrefHeight(25);

    Label equipmentID = new Label("(" + equipment.getEquipmentID() + ")");
    equipmentID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    equipmentID.setTextAlignment(TextAlignment.CENTER);
    equipmentID.setAlignment(Pos.CENTER);
    equipmentID.setLayoutX(x);
    equipmentID.setLayoutY(y + 35);
    equipmentID.setPrefWidth(160);
    equipmentID.setPrefHeight(25);

    Text locationText = new Text(x + 50, y + 80, "Location:");
    locationText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    locationText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentLocation = new Text(x + 10, y + 100, equipment.getLocation().getLongName());
    equipmentLocation.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentLocation.setTextAlignment(TextAlignment.CENTER);
    equipmentLocation.setWrappingWidth(140);

    Text statusText = new Text(x + 60, y + 150, "Status:");
    statusText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    statusText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentStatus = new Text(x + 10, y + 170, equipment.getStatus());
    equipmentStatus.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentStatus.setTextAlignment(TextAlignment.CENTER);
    equipmentStatus.setWrappingWidth(140);

    equipmentCardsPane.getChildren().add(rectangle);
    equipmentCardsPane.getChildren().add(equipmentName);
    equipmentCardsPane.getChildren().add(equipmentID);
    equipmentCardsPane.getChildren().add(locationText);
    equipmentCardsPane.getChildren().add(equipmentLocation);
    equipmentCardsPane.getChildren().add(statusText);
    equipmentCardsPane.getChildren().add(equipmentStatus);
  }

  private void drawPatientCard(int x, int y, Patient patient) {
    Rectangle rectangle = new Rectangle(x, y, 160, 210);
    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(1);
    rectangle.setArcHeight(25);
    rectangle.setArcWidth(25);
    rectangle.setStrokeType(StrokeType.INSIDE);
    rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
    rectangle.setStrokeLineJoin(StrokeLineJoin.ROUND);
    rectangle.setStrokeMiterLimit(10);

    Label equipmentName = new Label(patient.getPatientID());
    equipmentName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    equipmentName.setTextAlignment(TextAlignment.CENTER);
    equipmentName.setAlignment(Pos.CENTER);
    equipmentName.setLayoutX(x);
    equipmentName.setLayoutY(y + 10);
    equipmentName.setPrefWidth(160);
    equipmentName.setPrefHeight(25);

    Label equipmentID = new Label("(" + patient.getOverview() + ")");
    equipmentID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    equipmentID.setTextAlignment(TextAlignment.CENTER);
    equipmentID.setAlignment(Pos.CENTER);
    equipmentID.setLayoutX(x);
    equipmentID.setLayoutY(y + 35);
    equipmentID.setPrefWidth(160);
    equipmentID.setPrefHeight(25);

    Text locationText = new Text(x + 50, y + 80, "Location:");
    locationText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    locationText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentLocation = new Text(x + 10, y + 100, "");
    if (patient.getLocation() != null) {
      equipmentLocation.setText(patient.getLocation().getLongName());
    } else {
    }
    equipmentLocation.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentLocation.setTextAlignment(TextAlignment.CENTER);
    equipmentLocation.setWrappingWidth(140);

    Text statusText = new Text(x + 60, y + 150, "Status:");
    statusText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    statusText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentStatus = new Text(x + 10, y + 170, patient.getInformation());
    equipmentStatus.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentStatus.setTextAlignment(TextAlignment.CENTER);
    equipmentStatus.setWrappingWidth(140);

    patientsCardsPane.getChildren().add(rectangle);
    patientsCardsPane.getChildren().add(equipmentName);
    patientsCardsPane.getChildren().add(equipmentID);
    patientsCardsPane.getChildren().add(locationText);
    patientsCardsPane.getChildren().add(equipmentLocation);
    patientsCardsPane.getChildren().add(statusText);
    patientsCardsPane.getChildren().add(equipmentStatus);
  }

  private void drawRequestCard(int x, int y, Request request) {
    Button button = new Button("");
    button.setLayoutX(x);
    button.setLayoutY(y);
    button.setPrefWidth(160);
    button.setPrefHeight(210);
    button.getStyleClass().add("hidden-button");
    button.setOnAction(
        event -> {
          try {
            RequestQueueController.currentRequest = request;
            goToRequestQueue(null);
          } catch (Exception e) {
          }
        });

    Rectangle rectangle = new Rectangle(x, y, 160, 210);
    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(1);
    rectangle.setArcHeight(25);
    rectangle.setArcWidth(25);
    rectangle.setStrokeType(StrokeType.INSIDE);
    rectangle.setStrokeLineCap(StrokeLineCap.ROUND);
    rectangle.setStrokeLineJoin(StrokeLineJoin.ROUND);
    rectangle.setStrokeMiterLimit(10);

    Label equipmentName = new Label(request.getRequestID());
    equipmentName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    equipmentName.setTextAlignment(TextAlignment.CENTER);
    equipmentName.setAlignment(Pos.CENTER);
    equipmentName.setLayoutX(x);
    equipmentName.setLayoutY(y + 10);
    equipmentName.setPrefWidth(160);
    equipmentName.setPrefHeight(25);

    Label equipmentID = new Label("(" + request.getType() + ")");
    equipmentID.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    equipmentID.setTextAlignment(TextAlignment.CENTER);
    equipmentID.setAlignment(Pos.CENTER);
    equipmentID.setLayoutX(x);
    equipmentID.setLayoutY(y + 35);
    equipmentID.setPrefWidth(160);
    equipmentID.setPrefHeight(25);

    Text locationText = new Text(x + 50, y + 80, "Location:");
    locationText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    locationText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentLocation = new Text(x + 10, y + 100, "");
    if (request.getLocation() != null) {
      equipmentLocation.setText(request.getLocation().getLongName());
    } else {
      equipmentLocation.setText(request.getPatient().getLocation().getLongName());
    }
    equipmentLocation.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentLocation.setTextAlignment(TextAlignment.CENTER);
    equipmentLocation.setWrappingWidth(140);

    Text statusText = new Text(x + 60, y + 150, "Status:");
    statusText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    statusText.setTextAlignment(TextAlignment.CENTER);

    Text equipmentStatus = new Text(x + 10, y + 170, request.getStatus());
    equipmentStatus.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    equipmentStatus.setTextAlignment(TextAlignment.CENTER);
    equipmentStatus.setWrappingWidth(140);

    requestCardsPane.getChildren().add(rectangle);
    requestCardsPane.getChildren().add(equipmentName);
    requestCardsPane.getChildren().add(equipmentID);
    requestCardsPane.getChildren().add(locationText);
    requestCardsPane.getChildren().add(equipmentLocation);
    requestCardsPane.getChildren().add(statusText);
    requestCardsPane.getChildren().add(equipmentStatus);
    requestCardsPane.getChildren().add(button);
  }

  private int[] nextCardLocation(int cardNumber) {
    int[] location = new int[2];

    location[0] = (160 * (cardNumber / 2)) + (10 * ((cardNumber / 2) + 1));
    location[1] = (cardNumber % 2 == 0) ? 10 : 230;

    return location;
  }

  private void loadInformation(int floorNumber) {
    equipmentCardsPane.getChildren().clear();
    requestCardsPane.getChildren().clear();
    patientsCardsPane.getChildren().clear();
    ServiceRequestsDB requests = ServiceRequestsDB.getInstance();
    switch (floorNumber) {
      case 0:
        overviewLabel.setText("Overview of Lower Level 2");
        setEquipmentInformation(medEquipmentLL2);

        if (medEquipmentLL2.size() > 0) {
          for (int i = 0; i < medEquipmentLL2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentLL2.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }

        if (requestsLL2.size() > 0) {
          for (int i = 0; i < requestsLL2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsLL2.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsLL2.size() > 0) {
          for (int i = 0; i < patientsLL2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsLL2.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 1:
        overviewLabel.setText("Overview of Lower Level 1");
        setEquipmentInformation(medEquipmentLL1);

        if (medEquipmentLL1.size() > 0) {
          for (int i = 0; i < medEquipmentLL1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentLL1.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsLL1.size() > 0) {
          for (int i = 0; i < requestsLL1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsLL1.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsLL1.size() > 0) {
          for (int i = 0; i < patientsLL1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsLL1.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 2:
        overviewLabel.setText("Overview of Floor 1");
        setEquipmentInformation(medEquipmentF1);

        if (medEquipmentF1.size() > 0) {
          for (int i = 0; i < medEquipmentF1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentF1.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsF1.size() > 0) {
          for (int i = 0; i < requestsF1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsF1.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsF1.size() > 0) {
          for (int i = 0; i < patientsF1.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsF1.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 3:
        overviewLabel.setText("Overview of Floor 2");
        setEquipmentInformation(medEquipmentF2);

        if (medEquipmentF2.size() > 0) {
          for (int i = 0; i < medEquipmentF2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentF2.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsF2.size() > 0) {
          for (int i = 0; i < requestsF2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsF2.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsF2.size() > 0) {
          for (int i = 0; i < patientsF2.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsF2.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 4:
        overviewLabel.setText("Overview of Floor 3");
        setEquipmentInformation(medEquipmentF3);

        if (medEquipmentF3.size() > 0) {
          for (int i = 0; i < medEquipmentF3.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentF3.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsF3.size() > 0) {
          for (int i = 0; i < requestsF3.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsF3.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (patientsF3.size() > 0) {
          for (int i = 0; i < patientsF3.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsF3.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 5:
        overviewLabel.setText("Overview of Floor 4");
        setEquipmentInformation(medEquipmentF4);

        if (medEquipmentF4.size() > 0) {
          for (int i = 0; i < medEquipmentF4.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentF4.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsF4.size() > 0) {
          for (int i = 0; i < requestsF4.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsF4.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsF4.size() > 0) {
          for (int i = 0; i < patientsF4.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsF4.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
      case 6:
        overviewLabel.setText("Overview of Floor 5");
        setEquipmentInformation(medEquipmentF5);

        if (medEquipmentF5.size() > 0) {
          for (int i = 0; i < medEquipmentF5.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawEquipmentCard(cardCoordinates[0], cardCoordinates[1], medEquipmentF5.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No equipment on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          equipmentCardsPane.getChildren().add(noEquipment);
        }
        if (requestsF5.size() > 0) {
          for (int i = 0; i < requestsF5.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawRequestCard(
                cardCoordinates[0], cardCoordinates[1], requests.getByID(requestsF5.get(i)));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No requests on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          requestCardsPane.getChildren().add(noEquipment);
        }
        if (patientsF5.size() > 0) {
          for (int i = 0; i < patientsF5.size(); i++) {
            int[] cardCoordinates = nextCardLocation(i);
            drawPatientCard(cardCoordinates[0], cardCoordinates[1], patientsF5.get(i));
          }
        } else {
          Text noEquipment = new Text(100, 175, "No patients on this floor");
          noEquipment.setFont(Font.font("Arial", FontWeight.BOLD, 48));
          noEquipment.setFill(Color.WHITE);
          noEquipment.setTextAlignment(TextAlignment.CENTER);
          noEquipment.setWrappingWidth(315);

          patientsCardsPane.getChildren().add(noEquipment);
        }
        break;
    }
  }

  @FXML
  public void loadFloor5Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    f5Button.getStyleClass().add("request-button-selected");
    currentButton = f5Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor5.png"));
    loadInformation(6);
  }

  @FXML
  public void loadFloor4Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    f4Button.getStyleClass().add("request-button-selected");
    currentButton = f4Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor4.png"));
    loadInformation(5);
  }

  @FXML
  public void loadFloor3Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    f3Button.getStyleClass().add("request-button-selected");
    currentButton = f3Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor3.png"));
    loadInformation(4);
  }

  @FXML
  public void loadFloor2Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    f2Button.getStyleClass().add("request-button-selected");
    currentButton = f2Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor2.png"));
    loadInformation(3);
  }

  @FXML
  public void loadFloor1Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    f1Button.getStyleClass().add("request-button-selected");
    currentButton = f1Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/Floor1.png"));
    loadInformation(2);
  }

  @FXML
  public void loadLower1Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    l1Button.getStyleClass().add("request-button-selected");
    currentButton = l1Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL1.png"));
    loadInformation(1);
  }

  @FXML
  public void loadLower2Information(ActionEvent event) {
    currentButton.getStyleClass().remove("request-button-selected");
    l2Button.getStyleClass().add("request-button-selected");
    currentButton = l2Button;
    mapImage.setImage(new Image("/edu/wpi/cs3733/D22/teamB/assets/mapAssets/FloorL2.png"));
    loadInformation(0);
  }

  private int getCleanEquipmentCount(int floorNumber) {
    int count = 0;
    switch (floorNumber) {
      case 0:
        for (MedicalEquipment equipment : medEquipmentLL2) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 1:
        for (MedicalEquipment equipment : medEquipmentLL1) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 2:
        for (MedicalEquipment equipment : medEquipmentF1) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 3:
        for (MedicalEquipment equipment : medEquipmentF2) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 4:
        for (MedicalEquipment equipment : medEquipmentF3) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 5:
        for (MedicalEquipment equipment : medEquipmentF4) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 6:
        for (MedicalEquipment equipment : medEquipmentF5) {
          if (equipment.getIsClean()) {
            count++;
          }
        }
        break;
    }
    return count;
  }

  private int getDirtyEquipmentCount(int floorNumber) {
    int count = 0;
    switch (floorNumber) {
      case 0:
        for (MedicalEquipment equipment : medEquipmentLL2) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 1:
        for (MedicalEquipment equipment : medEquipmentLL1) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 2:
        for (MedicalEquipment equipment : medEquipmentF1) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 3:
        for (MedicalEquipment equipment : medEquipmentF2) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 4:
        for (MedicalEquipment equipment : medEquipmentF3) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 5:
        for (MedicalEquipment equipment : medEquipmentF4) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
      case 6:
        for (MedicalEquipment equipment : medEquipmentF5) {
          if (!equipment.getIsClean()) {
            count++;
          }
        }
        break;
    }
    return count;
  }

  private void setEquipmentInformation(LinkedList<MedicalEquipment> equipmentList) {
    int countCleanBed = 0,
        countCleanRecliner = 0,
        countCleanPump = 0,
        countCleanXRay = 0,
        countDirtyBed = 0,
        countDirtyRecliner = 0,
        countDirtyPump = 0,
        countDirtyXRay = 0;

    for (MedicalEquipment equipment : equipmentList) {
      if (equipment.getIsClean()) {
        switch (equipment.getType()) {
          case "BED":
            countCleanBed++;
            break;
          case "RECL":
            countCleanRecliner++;
            break;
          case "PUMP":
            countCleanPump++;
            break;
          case "XR":
            countCleanXRay++;
            break;
        }
      } else {
        switch (equipment.getType()) {
          case "BED":
            countDirtyBed++;
            break;
          case "RECL":
            countDirtyRecliner++;
            break;
          case "PUMP":
            countDirtyPump++;
            break;
          case "XR":
            countDirtyXRay++;
            break;
        }
      }
    }

    cleanBed.setText(String.valueOf(countCleanBed));
    cleanRecliner.setText(String.valueOf(countCleanRecliner));
    cleanPump.setText(String.valueOf(countCleanPump));
    cleanXRay.setText(String.valueOf(countCleanXRay));

    dirtyBed.setText(String.valueOf(countDirtyBed));
    dirtyRecliner.setText(String.valueOf(countDirtyRecliner));
    dirtyPump.setText(String.valueOf(countDirtyPump));
    dirtyXRay.setText(String.valueOf(countDirtyXRay));
  }

  private void populateActivityMap() {
    for (Activity activity : activityDAO.getInstance().list()) {
      String date = DateHelper.properDate(activity.getDateAndTime());

      if (activityMap.containsKey(date)) {
        activityMap.get(date).add(activity);
      } else {
        LinkedList<Activity> activityList = new LinkedList<>(Arrays.asList(activity));
        activityMap.put(date, activityList);
      }
    }
  }

  public void displayActivityReport() {
    ArrayList<String> keys = new ArrayList<>(activityMap.keySet());
    DateHelper.sortDates(keys);

    for (String date : keys) {
      TitledPane titledPane = new TitledPane();
      titledPane.setText(date);

      TableView<Activity> activityTable = new TableView<>();

      TableColumn<Activity, String> columnTime = new TableColumn<>("Time");
      columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
      columnTime.setPrefWidth(75);
      activityTable.getColumns().add(columnTime);

      TableColumn<Activity, String> columnAction = new TableColumn<>("Action");
      columnAction.setCellValueFactory(new PropertyValueFactory<>("summary"));
      columnAction.setPrefWidth(330);
      activityTable.getColumns().add(columnAction);

      TableColumn<Activity, String> columnEmployee = new TableColumn<>("Employee");
      columnEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
      columnEmployee.setPrefWidth(110);
      activityTable.getColumns().add(columnEmployee);

      ObservableList<Activity> activityList = FXCollections.observableArrayList();

      for (Activity activity : activityMap.get(date)) {
        activityList.add(activity);
      }

      activityTable.setItems(activityList);
      activityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      activityTable.getStyleClass().add("simple-table");
      AnchorPane anchorPane = new AnchorPane();
      anchorPane.setStyle("-fx-background-color: transparent");
      anchorPane.getChildren().add(activityTable);
      AnchorPane.setBottomAnchor(activityTable, 0.0);
      AnchorPane.setTopAnchor(activityTable, 0.0);
      AnchorPane.setLeftAnchor(activityTable, 0.0);
      AnchorPane.setRightAnchor(activityTable, 0.0);
      titledPane.setContent(anchorPane);
      activityPane.getPanes().add(titledPane);
    }
  }
}
