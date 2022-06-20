package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static util.DateAndTime.loadDateAndTime;

public class DashboardFormController {
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
       loadDateAndTime(lblDate,lblTime);
    }

    public void btnOwnerLoginOnAction(ActionEvent actionEvent) throws IOException {
        setUi("OwnerLoginForm");

       /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/OwnerLoginForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        */
    }

    public void btnServiceOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddToServiceForm");
    }

    public void btnRepairOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddToRepairForm");
    }

    public void btnWashOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddToWashForm");
    }

    public void btnVhclWrkLstOnAction(ActionEvent actionEvent) throws IOException {
        setUi("VehicleWorkListForm");
    }

    public void btnEmpShftOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeShiftForm");
    }

    private void setUi(String URI) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

}
