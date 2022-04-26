package edu.wpi.cs3733.D22.teamB.controllers.tables;

import static edu.wpi.cs3733.D22.teamB.App.currentUser;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamB.App;
import edu.wpi.cs3733.D22.teamB.controllers.MenuBarController;
import edu.wpi.cs3733.D22.teamB.databases.Activity;
import edu.wpi.cs3733.D22.teamB.databases.DatabaseController;
import edu.wpi.cs3733.D22.teamB.databases.Employee;
import edu.wpi.cs3733.D22.teamB.databases.EmployeesDB;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class EmployeeQueueController extends MenuBarController implements Initializable {
  @FXML TableView<Employee> employeeTable;
  @FXML TableColumn<Employee, String> columnEmployeeID;
  @FXML TableColumn<Employee, String> columnName;
  @FXML TableColumn<Employee, Void> columnButtons;

  @FXML Label employeeIDText;
  @FXML Text nameText;
  @FXML Text usernameText;
  @FXML Text positionText;
  @FXML Text departmentText;

  @FXML Label employeeIDLabel;
  @FXML TextField usernameInput;
  @FXML TextField nameInput;
  @FXML TextField departmentInput;
  @FXML TextField positionInput;

  @FXML TextField addNameInput;
  @FXML TextField addUsernameInput;
  @FXML TextField addPasswordInput;
  @FXML TextField addDepartmentInput;
  @FXML TextField addPositionInput;

  @FXML AnchorPane viewingPane;
  @FXML AnchorPane editingPane;
  @FXML AnchorPane creationPane;
  @FXML AnchorPane otherAnchorPane;

  @FXML JFXButton addEmployeeButton;

  Employee currentEmployee = null;
  DatabaseController db = DatabaseController.getInstance();
  protected EmployeesDB dao;

  private ObservableList<Employee> employees = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

    columnEmployeeID.getStyleClass().add("table-column-left");
    columnName.getStyleClass().add("table-column-middle");
    columnButtons.getStyleClass().add("table-column-right");

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
                  private final HBox hBox = new HBox();
                  private final Button requestViewerButton = new Button("View");
                  private final Button requestEditButton = new Button("Edit");
                  private final Button requestDeleteButton = new Button("Delete");

                  private final ImageView viewIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/InfoSquare.png"));
                  private final ImageView editIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/EditSquare.png"));
                  private final ImageView deleteIcon =
                      new ImageView(
                          new Image("/edu/wpi/cs3733/D22/teamB/assets/newAssets/CloseSquare.png"));

                  {
                    hBox.setSpacing(10);

                    requestViewerButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestViewerButton.setMinSize(25, 25);
                    requestViewerButton.setPrefSize(25, 25);
                    requestViewerButton.setMaxSize(25, 25);
                    requestViewerButton.setGraphic(viewIcon);
                    viewIcon.setFitHeight(25);
                    viewIcon.setFitWidth(25);

                    requestEditButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestEditButton.setMinSize(25, 25);
                    requestEditButton.setPrefSize(25, 25);
                    requestEditButton.setMaxSize(25, 25);
                    requestEditButton.setGraphic(editIcon);
                    editIcon.setFitHeight(25);
                    editIcon.setFitWidth(25);

                    requestDeleteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    requestDeleteButton.setMinSize(25, 25);
                    requestDeleteButton.setPrefSize(25, 25);
                    requestDeleteButton.setMaxSize(25, 25);
                    requestDeleteButton.setGraphic(deleteIcon);
                    deleteIcon.setFitHeight(25);
                    deleteIcon.setFitWidth(25);

                    requestViewerButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(true);
                          editingPane.setVisible(false);
                          creationPane.setVisible(false);

                          Employee employee = getTableView().getItems().get(getIndex());
                          currentEmployee = employee;

                          // employeeIDText.setText(employee.getEmployeeID());
                          nameText.setText(employee.getFullName());
                          usernameText.setText(employee.getUsername());
                          departmentText.setText(employee.getDepartment());
                          positionText.setText(employee.getPosition());
                        });

                    requestEditButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(false);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(true);
                          creationPane.setVisible(false);

                          Employee employee = getTableView().getItems().get(getIndex());
                          currentEmployee = employee;

                          // employeeIDLabel.setText(employee.getEmployeeID());
                          nameInput.setText(employee.getFullName());
                          usernameInput.setText(employee.getUsername());
                          departmentInput.setText(employee.getDepartment());
                          positionInput.setText(employee.getPosition());
                        });

                    requestDeleteButton.setOnAction(
                        (ActionEvent event) -> {
                          otherAnchorPane.setVisible(true);
                          viewingPane.setVisible(false);
                          editingPane.setVisible(false);
                          creationPane.setVisible(false);

                          Employee employee = getTableView().getItems().get(getIndex());
                          currentEmployee = employee;

                          // create a confirmation dialog
                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                          alert.setTitle("Confirm Delete");
                          alert.setHeaderText(
                              "Are you sure you want to remove employee "
                                  + currentEmployee.getEmployeeID()
                                  + "?");
                          alert.setContentText("This action cannot be undone.");

                          ((Button) alert.getDialogPane().lookupButton(ButtonType.OK))
                              .setText("Delete");
                          ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL))
                              .setText("Cancel");

                          Optional<ButtonType> result = alert.showAndWait();
                          if (result.get() == ButtonType.OK) {
                            EmployeesDB.getInstance().delete(employee);
                            employees.remove(employee);
                            employeeTable.refresh();

                            DatabaseController.getInstance()
                                .add(
                                    new Activity(
                                        new Date(),
                                        App.currentUser.getEmployeeID(),
                                        currentEmployee.getEmployeeID(),
                                        null,
                                        "Employee",
                                        "removed from system"));

                            currentEmployee = null;
                          }
                        });

                    hBox.getChildren()
                        .addAll(requestViewerButton, requestEditButton, requestDeleteButton);

                    if (!currentUser.getPosition().equals("ADMIN")) {
                      requestEditButton.setDisable(true);
                      requestDeleteButton.setDisable(true);
                      addEmployeeButton.setDisable(true);
                    }
                  }

                  @Override
                  public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                      setGraphic(null);
                    } else {
                      setGraphic(hBox);
                      requestViewerButton.getStyleClass().add("hidden-button");
                      requestEditButton.getStyleClass().add("hidden-button");
                      requestDeleteButton.getStyleClass().add("hidden-button");
                    }
                  }
                };
            return cell;
          }
        };

    columnButtons.setCellFactory(cellFactory);
  }

  @FXML
  public void saveData(ActionEvent event) {
    if (!currentEmployee.getUsername().equals(usernameInput.getText())) {
      DatabaseController.getInstance()
          .add(
              new Activity(
                  new Date(),
                  App.currentUser.getEmployeeID(),
                  currentEmployee.getEmployeeID(),
                  null,
                  "Employee",
                  "username updated"));
    }

    currentEmployee.setDepartment(departmentInput.getText());
    currentEmployee.setPosition(positionInput.getText());
    currentEmployee.setUsername(usernameInput.getText());

    String firstName = nameInput.getText().substring(0, nameInput.getText().indexOf(" "));
    String lastName = nameInput.getText().substring(nameInput.getText().indexOf(" ") + 1);

    currentEmployee.setFirstName(firstName);
    currentEmployee.setLastName(lastName);

    dao.update(currentEmployee);

    departmentInput.setText("");
    positionInput.setText("");
    usernameInput.setText("");
    nameInput.setText("");

    employeeTable.refresh();

    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);
  }

  @FXML
  public void createEmployee(ActionEvent event) {
    otherAnchorPane.setVisible(false);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(true);
  }

  @FXML
  public void cancel(ActionEvent event) {
    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);

    addNameInput.setText("");
    addUsernameInput.setText("");
    addPasswordInput.setText("");
    addDepartmentInput.setText("");
    addPositionInput.setText("");
  }

  @FXML
  public void addEmployee(ActionEvent event) {
    otherAnchorPane.setVisible(true);
    viewingPane.setVisible(false);
    editingPane.setVisible(false);
    creationPane.setVisible(false);

    Employee employee;
    String firstName = addNameInput.getText().substring(0, addNameInput.getText().indexOf(" "));
    String lastName = addNameInput.getText().substring(addNameInput.getText().indexOf(" ") + 1);
    String username = addUsernameInput.getText();
    String password = addPasswordInput.getText();
    String department = addDepartmentInput.getText();
    String position = addPositionInput.getText();

    employee = new Employee(lastName, firstName, department, position, username, password);

    employees.add(employee);
    EmployeesDB.getInstance().add(employee);

    addNameInput.setText("");
    addUsernameInput.setText("");
    addPasswordInput.setText("");
    addDepartmentInput.setText("");
    addPositionInput.setText("");

    employeeTable.refresh();
    employeeTable.getSelectionModel().select(employee);
    employeeTable.scrollTo(employees.size() - 1);
  }

  @FXML
  public void toggleClientServer() {
    db.switchConnection();

    employeeTable.refresh();
  }
}
