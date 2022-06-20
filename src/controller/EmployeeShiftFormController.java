package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Attendance;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

import static util.DateAndTime.loadDateAndTime;

public class EmployeeShiftFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cbxEmpIDs;
    public Label lblEmpName;
    public JFXButton btnRecordArrival;
    public JFXButton btnRecordDeparture;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime(lblDate, lblTime);
        setEmployeeIds();

        cbxEmpIDs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                  setEmployeeName(newValue);
                }
        );
        cbxEmpIDs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    btnDisableAfterArrival(newValue);
                }
        );
          /*cbxEmpIDs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    btnDisableAfterDeparture(newValue);
                }
        );
           */
    }

    private void setEmployeeIds(){
        try {
            ObservableList<String> eIdObList = FXCollections.observableArrayList(
                    EmployeeDataController.getEmployeeIds()
            );
            cbxEmpIDs.setItems(eIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setEmployeeName(Object newValue) {
        try {
            ResultSet result2 = CrudUtil.execute("SELECT employee_name FROM Employee WHERE e_Id=?",
                    String.valueOf(cbxEmpIDs.getSelectionModel().getSelectedItem()));

            if (result2.next()) {
                lblEmpName.setText(result2.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void btnDisableAfterArrival(Object newValue) {
        try {
            ResultSet result3 = CrudUtil.execute("SELECT time_of_arrival FROM Attendance WHERE date=? AND e_Id=?",
                    lblDate.getText(), String.valueOf(cbxEmpIDs.getSelectionModel().getSelectedItem()));
            if (result3.next()) {
                // not adding the time of arrival to the attendance table since its already recorded
                btnRecordArrival.setDisable(true);
            }else{
                btnRecordArrival.setDisable(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
      /*private void btnDisableAfterDeparture(Object newValue) {
        try {
            ResultSet result = CrudUtil.execute("SELECT time_of_departure FROM Attendance WHERE date=? AND e_Id=?",
                    lblDate.getText(),String.valueOf(cbxEmpIDs.getSelectionModel().getSelectedItem()));
            if (result.next()){
                // not adding the time of departure to the attendance table since its already recorded
                 new Alert(Alert.AlertType.WARNING, "Time of Departure of the selected Employee ID is already recorded!").show();
                btnRecordDeparture.setDisable(true);
            }else{
                btnRecordDeparture.setDisable(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     */

    public void btnRecordArrivalOnAction(ActionEvent actionEvent) {
        String date = lblDate.getText();
        String empID = String.valueOf(cbxEmpIDs.getSelectionModel().getSelectedItem());
        String timeOfArrival = lblTime.getText();
        // String timeOfDeparture = null;

        /////////insert data to the attendance table in db//////////////////////////////
        Attendance a = new Attendance(date, empID, timeOfArrival,null);

        try {
                CrudUtil.execute("INSERT INTO Attendance VALUES (?,?,?,?)",
                        a.getDate(), a.getE_Id(),
                        a.getTimeOfArrival(), a.getTimeOfDeparture());

                new Alert(Alert.AlertType.CONFIRMATION, "Arrival time for work has been recorded successfully..").show();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnRecordDepartureOnAction (ActionEvent actionEvent){
        /////////insert data to the attendance table in db//////////////////////////////
       try {
            CrudUtil.execute("UPDATE Attendance SET time_of_departure=? WHERE date=? AND e_Id=?;", lblTime.getText(),
                        lblDate.getText(), String.valueOf(cbxEmpIDs.getSelectionModel().getSelectedItem()));

            new Alert(Alert.AlertType.CONFIRMATION, "Leaving time from work has been recorded successfully..").show();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    public void btnBackOnAction (ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}

