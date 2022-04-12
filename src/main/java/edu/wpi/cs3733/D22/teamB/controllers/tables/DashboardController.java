package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.databases.*;
import edu.wpi.cs3733.D22.teamB.requests.Request;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DashboardController {
  @FXML private TextArea textArea;

  private EquipmentRequestDB equDAO;
  private LabRequestsDB labDAO;
  private ServiceRequestsDB servDAO;
  private MedicalEquipmentDB medDAO;
  private PatientsDB patientDAO;

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

  public void initialize() {
    equDAO = EquipmentRequestDB.getInstance();
    labDAO = LabRequestsDB.getInstance();
    servDAO = ServiceRequestsDB.getInstance();
    medDAO = MedicalEquipmentDB.getInstance();
    patientDAO = PatientsDB.getInstance();

    for (EquipmentRequest equipmentRequest : equDAO.list()) {
      Location equipmentRequestLocation = equipmentRequest.getLocation();
      if (equipmentRequestLocation != null) {
        switch (equipmentRequestLocation.getFloor()) {
          case "1":
            requestsF1.add(equipmentRequest.getRequestID());
            break;
          case "2":
            requestsF2.add(equipmentRequest.getRequestID());
            break;
          case "3":
            requestsF3.add(equipmentRequest.getRequestID());
            break;
          case "4":
            requestsF4.add(equipmentRequest.getRequestID());
            break;
          case "5":
            requestsF5.add(equipmentRequest.getRequestID());
            break;
          case "L1":
            requestsLL1.add(equipmentRequest.getRequestID());
            break;
          case "L2":
            requestsLL2.add(equipmentRequest.getRequestID());
            break;
        }
      }
    }

    for (LabRequest labRequest : labDAO.list()) {
      Location labRequestLocation = labRequest.getLocation();
      if (labRequestLocation != null) {
        switch (labRequestLocation.getFloor()) {
          case "1":
            requestsF1.add(labRequest.getRequestID());
            break;
          case "2":
            requestsF2.add(labRequest.getRequestID());
            break;
          case "3":
            requestsF3.add(labRequest.getRequestID());
            break;
          case "4":
            requestsF4.add(labRequest.getRequestID());
            break;
          case "5":
            requestsF5.add(labRequest.getRequestID());
            break;
          case "L1":
            requestsLL1.add(labRequest.getRequestID());
            break;
          case "L2":
            requestsLL2.add(labRequest.getRequestID());
            break;
        }
      }
    }

    for (Request serviceRequest : servDAO.list()) {
      Location serviceRequestLocation = serviceRequest.getLocation();
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
  }

  @FXML
  public void showLL2Information(ActionEvent event) {
    String text = "";
    if (medEquipmentLL2.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentLL2) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsLL2.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsLL2) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsLL2.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsLL2) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showLL1Information(ActionEvent event) {
    String text = "";
    if (medEquipmentLL1.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentLL1) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsLL1.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsLL1) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsLL1.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsLL1) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showF5Information(ActionEvent event) {
    String text = "";
    if (medEquipmentF5.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentF5) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsF5.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsF5) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsF5.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsF5) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showF4Information(ActionEvent event) {
    String text = "";
    if (medEquipmentF4.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentF4) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsF4.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsF4) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsF4.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsF4) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showF3Information(ActionEvent event) {
    String text = "";
    if (medEquipmentF3.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentF3) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsF3.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsF3) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsF3.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsF3) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showF2Information(ActionEvent event) {
    String text = "";
    if (medEquipmentF2.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentF2) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsF2.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsF2) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsF2.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsF2) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }

  @FXML
  public void showF1Information(ActionEvent event) {
    String text = "";
    if (medEquipmentF1.size() > 0) {
      text += "Medical Equipment:\n";
      for (MedicalEquipment medEquipment : medEquipmentF1) {
        text +=
            medEquipment.getName()
                + "\t"
                + medEquipment.getLocation().getLongName()
                + "\t"
                + (medEquipment.getIsClean() ? "Clean" : "Dirty")
                + "\n";
      }
      text += "\n";
    }
    if (patientsF1.size() > 0) {
      text += "Patients:\n";
      for (Patient patient : patientsF1) {
        text += patient.getOverview() + "\t" + patient.getLocation().getLongName() + "\n";
      }
      text += "\n";
    }
    if (requestsF1.size() > 0) {
      text += "Requests:\n";
      for (String requestID : requestsF1) {
        text += requestID + "\n";
      }
    }

    textArea.setText(text);
  }
}
