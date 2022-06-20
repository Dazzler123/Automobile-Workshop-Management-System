package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class OwnerFormController {

    public void btnLogoutOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void btnMngSrvcdVhclsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManageServicedVehiclesForm");
    }
    public void btnMngRprdVhclsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManageRepairedVehiclesForm");
    }
    public void btnMngWshdVhclsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManageWashedVehiclesForm");
    }
    public void btnMngEmplysOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManageEmployeesForm");
    }
    public void btnLoadReportsOnAction(ActionEvent actionEvent) throws IOException {
       setUi("ReportsForm");
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
