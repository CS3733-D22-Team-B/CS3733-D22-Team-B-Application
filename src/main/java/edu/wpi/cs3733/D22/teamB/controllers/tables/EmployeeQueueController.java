package edu.wpi.cs3733.D22.teamB.controllers.tables;

import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeesDB;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class EmployeeQueueController extends MenuBarController implements Initializable {
  @FXML TableView<Employee> employeeTable;
  @FXML TableColumn<Employee, String> columnEmployeeID;
  @FXML TableColumn<Employee, String> columnPosition;
  @FXML TableColumn<Employee, String> columnFirstName;
  @FXML TableColumn<Employee, String> columnLastName;
  @FXML TableColumn<Employee, Void> columnButtons;

  @FXML Button saveButton;
  @FXML Button addSaveButton;
  @FXML Button deleteButton;

  @FXML TextField usernameInput;
  @FXML TextField passwordInput;
  @FXML TextField firstNameInput;
  @FXML TextField lastNameInput;
  @FXML ComboBox<String> positionInput;
  @FXML ComboBox<String> departmentInput;

  Employee currentEmployee = null;
  DatabaseController db = new DatabaseController();
  protected EmployeesDB dao;

  private ObservableList<Employee> employees = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
    columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    usernameInput.setDisable(true);
    passwordInput.setDisable(true);
    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    positionInput.setDisable(true);
    departmentInput.setDisable(true);
    saveButton.setDisable(true);
    deleteButton.setDisable(true);

    dao = EmployeesDB.getInstance();
    LinkedList<Employee> employeesL = dao.list();
    for (Employee employeeItem : employeesL) {
      if (!employeeItem.getEmployeeID().equals("0")) {
        employees.add(employeeItem);
      }
    }

    employeeTable.setItems(employees);
    addButtonToTable();
  }

  private void addButtonToTable() {
    Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>> cellFactory =
        new Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>>() {
          @Override
          public TableCell<Employee, Void> call(final TableColumn<Employee, Void> param) {
            final TableCell<Employee, Void> cell =
                new TableCell<Employee, Void>() {
                  private final Button requestViewerButton = new Button("View Request");

                  {
                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          Employee employee = getTableView().getItems().get(getIndex());
                          currentEmployee = employee;
                          firstNameInput.setText(currentEmployee.getFirstName());
                          lastNameInput.setText(currentEmployee.getLastName());
                          positionInput.setValue(currentEmployee.getPosition());
                          departmentInput.setValue(currentEmployee.getDepartment());
                          usernameInput.setText(currentEmployee.getUsername());
                          passwordInput.setText(currentEmployee.getPassword());

                          usernameInput.setDisable(false);
                          passwordInput.setDisable(false);

                          firstNameInput.setDisable(false);
                          lastNameInput.setDisable(false);
                          positionInput.setDisable(false);
                          departmentInput.setDisable(false);
                          saveButton.setDisable(false);
                          deleteButton.setDisable(false);

                          addSaveButton.setDisable(true);
                          addSaveButton.setVisible(false);
                          saveButton.setVisible(true);
                        });
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(requestViewerButton);
                    }
                  }
                };
            return cell;
          }
        };

    columnButtons.setCellFactory(cellFactory);
    employeeTable.getColumns().add(columnButtons);
  }

  @FXML
  public void saveData(ActionEvent event) {

    currentEmployee.setDepartment(departmentInput.getValue());
    currentEmployee.setPosition(positionInput.getValue());
    currentEmployee.setPassword(passwordInput.getText());
    currentEmployee.setUsername(usernameInput.getText());
    currentEmployee.setFirstName(firstNameInput.getText());
    currentEmployee.setLastName(lastNameInput.getText());
    dao.update(currentEmployee);
    departmentInput.setValue("");
    positionInput.setValue("");
    usernameInput.setText("");
    passwordInput.setText("");
    firstNameInput.setText("");
    lastNameInput.setText("");

    usernameInput.setDisable(true);
    passwordInput.setDisable(true);
    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    positionInput.setDisable(true);
    departmentInput.setDisable(true);
    employeeTable.refresh();
    saveButton.setDisable(true);
    deleteButton.setDisable(true);
  }

  @FXML
  public void addStart(ActionEvent event) {
    departmentInput.setValue("");
    positionInput.setValue("");
    usernameInput.setText("");
    passwordInput.setText("");
    firstNameInput.setText("");
    lastNameInput.setText("");
    departmentInput.setValue("");
    positionInput.setValue("");

    usernameInput.setDisable(false);
    passwordInput.setDisable(false);
    firstNameInput.setDisable(false);
    lastNameInput.setDisable(false);
    positionInput.setDisable(false);
    departmentInput.setDisable(false);
    saveButton.setDisable(true);
    saveButton.setVisible(false);
    deleteButton.setDisable(true);
    addSaveButton.setDisable(false);
    addSaveButton.setVisible(true);
  }

  @FXML
  public void saveAddData(ActionEvent event) {

    Employee newEmp =
        new Employee(
            lastNameInput.getText(),
            firstNameInput.getText(),
            departmentInput.getValue(),
            positionInput.getValue(),
            usernameInput.getText(),
            passwordInput.getText());
    currentEmployee = newEmp;
    dao.add(currentEmployee);
    departmentInput.setValue("");
    positionInput.setValue("");
    employees.add(currentEmployee);
    employeeTable.refresh();

    usernameInput.setDisable(true);
    passwordInput.setDisable(true);
    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    positionInput.setDisable(true);
    departmentInput.setDisable(true);
    addSaveButton.setDisable(true);
    addSaveButton.setVisible(false);
    saveButton.setVisible(true);
    employeeTable.refresh();
  }

  @FXML
  public void deleteData(ActionEvent event) {

    dao.delete(currentEmployee);
    employees.remove(currentEmployee);
    employeeTable.refresh();
    departmentInput.setValue("");
    positionInput.setValue("");
    usernameInput.setText("");
    passwordInput.setText("");
    firstNameInput.setText("");
    lastNameInput.setText("");

    usernameInput.setDisable(true);
    passwordInput.setDisable(true);
    firstNameInput.setDisable(true);
    lastNameInput.setDisable(true);
    positionInput.setDisable(true);
    departmentInput.setDisable(true);
    saveButton.setDisable(true);
    deleteButton.setDisable(true);
  }

  @FXML
  public void toggleClientServer() {
    db.switchConnection();

    employeeTable.refresh();
  }
}
