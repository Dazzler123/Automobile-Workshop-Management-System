package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.WashTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageWashedVehiclesFormController {
    public JFXComboBox cbxWashType;
    public TableView<WashTM> tblWash;
    public TableColumn colDateTime;
    public TableColumn colWashID;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleYOM;
    public TableColumn colCustomerNIC;
    public TableColumn colCustomerName;
    public TableColumn colCustomerMobile;
    public TableColumn colCustomerAddress;
    public TableColumn colWashType;
    public TableColumn colFee;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleYOM;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
    public Label lblWashID;
    public JFXButton btnUpdateOnAction;
    public JFXButton btnDeleteOnAction;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        // wash table
        colDateTime.setCellValueFactory(new PropertyValueFactory("dateTime"));
        colWashID.setCellValueFactory(new PropertyValueFactory("washID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        colWashType.setCellValueFactory(new PropertyValueFactory("washType"));
        colFee.setCellValueFactory(new PropertyValueFactory("fee"));
        loadAllWashedVehicles();

        // add wash types to the combo box
        ObservableList<String> washTypes = FXCollections.observableArrayList("Body Wash","Body Wash with Cut & Polish",
                "Body Wash and Interior Clean","Body Wash with Cut & Polish and Interior Clean");
        cbxWashType.setItems(washTypes);

        //get the selected row from the table to textfields
        tblWash.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    selectedData(newValue);
                }));

        /////////////VALIDATION PART///////////////
        btnUpdateOnAction.setDisable(true);
        btnDeleteOnAction.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        Pattern VehicleNumberPattern = Pattern.compile("^((WP)|(NW)|(SP)|(SG)|(NP))[A-Z-]{3}[0-9]{4,4}$");
        //)[A-z/-]{3}[0-9]{4,4}

        Pattern VehicleYOMPattern = Pattern.compile("^[0-9]{4}$");
        // Pattern CusNICPattern = Pattern.compile("^[0-9]{9,12}[V]$");
        Pattern CusNamePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern CusMobilePattern = Pattern.compile("^(077|076|078|071|072)[0-9]{7}$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z0-9.,/ ]{4,35}$");

        //map.put(txtVehicleNumber,VehicleNumberPattern);
        map.put(txtVehicleYOM,VehicleYOMPattern);
        //  map.put(txtCustomerNIC,CusNICPattern);
        map.put(txtCustomerName,CusNamePattern);
        map.put(txtCustomerMobile,CusMobilePattern);
        map.put(txtCustomerAddress,CusAddressPattern);

    }

    public void loadAllWashedVehicles() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT w.date, w.time, w.w_id, v.vehicle_no, v.vehicle_yom, c.customer_nic, c.customer_name, c.customer_mobile, c.customer_address, w.wash_type, w.fee\n" +
                "FROM wash AS w, customer AS c, vehicle AS v\n" +
                "WHERE c.customer_nic=v.customer_nic AND v.vehicle_no=w.vehicle_no ORDER BY w_Id DESC;\n");
        ObservableList<WashTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new WashTM(
                            result.getString("date"),
                            result.getString("time"),
                            result.getString("w_Id"),
                            result.getString("vehicle_no"),
                            result.getInt("vehicle_yom"),
                            result.getString("customer_nic"),
                            result.getString("customer_name"),
                            result.getString("customer_mobile"),
                            result.getString("customer_address"),
                            result.getString("wash_type"),
                            result.getDouble("fee")
                    )
            );
        }
        tblWash.setItems(obList);
    }

    private void selectedData(WashTM newValue) {
        lblWashID.setText(newValue.getWashID());
        txtVehicleNumber.setText(newValue.getVehicleNumber());
        txtVehicleYOM.setText(String.valueOf(newValue.getVehicleYOM()));
        txtCustomerNIC.setText(newValue.getCustomerNIC());
        txtCustomerName.setText(newValue.getCustomerName());
        txtCustomerMobile.setText(newValue.getCustomerMobile());
        txtCustomerAddress.setText(newValue.getCustomerAddress());
        cbxWashType.setValue(String.valueOf(newValue.getWashType()));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String washType = cbxWashType.getSelectionModel().getSelectedItem().toString();

        // set fixed fees for wash types available
        Double fee = 0.0;
        if(washType.equals("Body Wash")) {
            fee = 1500.0;
        }else if(washType.equals("Body Wash with Cut & Polish")){
            fee = 2500.0;
        }else if(washType.equals("Body Wash and Interior Clean")){
            fee = 6000.0;
        }else if(washType.equals("Body Wash with Cut & Polish and Interior Clean")){
            fee = 7000.0;
        }

        try{
            CrudUtil.execute("UPDATE Customer SET customer_nic=?, customer_name=?, customer_mobile=?, customer_address=? WHERE customer_nic=?",
                    txtCustomerNIC.getText(),txtCustomerName.getText(),txtCustomerMobile.getText(),txtCustomerAddress.getText(),txtCustomerNIC.getText());

            // vehicle number and customer NIC cannot be updated.
            CrudUtil.execute("UPDATE Vehicle SET vehicle_no=?, vehicle_yom=? WHERE vehicle_no=?",
                    txtVehicleNumber.getText(),txtVehicleYOM.getText(),txtVehicleNumber.getText());

            CrudUtil.execute("UPDATE Wash SET wash_type=?, fee=? WHERE w_Id=?",washType,fee,lblWashID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Updated..").show();

            // clear the user input areas after updating is done
            lblWashID.getText();
            txtVehicleNumber.clear();
            txtVehicleYOM.clear();
            txtCustomerNIC.clear();
            txtCustomerName.clear();
            txtCustomerMobile.clear();
            txtCustomerAddress.clear();
            cbxWashType.getSelectionModel().clearSelection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllWashedVehicles();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            CrudUtil.execute("DELETE FROM Wash WHERE w_Id=?",lblWashID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Deleted..").show();

            // clear the user input areas after deletion is done
            txtVehicleNumber.clear();
            txtVehicleYOM.clear();
            txtCustomerNIC.clear();
            txtCustomerName.clear();
            txtCustomerMobile.clear();
            txtCustomerAddress.clear();
            cbxWashType.getSelectionModel().clearSelection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllWashedVehicles();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void txtValidateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnUpdateOnAction,btnDeleteOnAction);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnUpdateOnAction,btnDeleteOnAction);
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {

            }
        }
    }
}
