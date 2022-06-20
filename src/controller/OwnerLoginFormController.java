package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class OwnerLoginFormController {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String tempUserName = txtUsername.getText();
        String tempPassword = txtPassword.getText();

        if(tempUserName.equals("owner") && tempPassword.equals("1234")){
            Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            URL resource = getClass().getResource("../view/OwnerForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.initStyle(StageStyle.UNDECORATED);
            stage1.show();

        }else{
            new Alert(Alert.AlertType.WARNING, "Incorrect Username or Password").show();
        }

    }
    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


}
