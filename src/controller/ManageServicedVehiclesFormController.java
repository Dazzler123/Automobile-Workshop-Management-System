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
import view.tm.ServiceTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageServicedVehiclesFormController {
    public JFXComboBox cbxServiceType;
    public TableView<ServiceTM> tblService;
    public TableColumn colDateTIme;
    public TableColumn colServiceID;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleModel;
    public TableColumn colVehicleMake;
    public TableColumn colVehicleYOM;
    public TableColumn colCustomerNIC;
    public TableColumn colCustomerName;
    public TableColumn colCustomerMobile;
    public TableColumn colCustomerAddress;
    public TableColumn colOdometer;
    public TableColumn colServiceType;
    public TableColumn colMaterialCost;
    public TableColumn colLabourCharge;
    public TableColumn colTotalCost;
    public TableColumn colNextService;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleModel;
    public JFXTextField txtVehicleMake;
    public JFXTextField txtVehicleYOM;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
    public JFXTextField txtMaterialCost;
    public JFXTextField txtLabourCharge;
    public JFXTextField txtNextService;
    public JFXTextField txtOdometer;
    public Label lblServiceID;
    public JFXButton btnUpdateOnAction;
    public JFXButton btnDeleteOnAction;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        // service table
        colDateTIme.setCellValueFactory(new PropertyValueFactory("dateTime"));
        colServiceID.setCellValueFactory(new PropertyValueFactory("serviceID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory("vehicleModel"));
        colVehicleMake.setCellValueFactory(new PropertyValueFactory("vehicleMake"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        colOdometer.setCellValueFactory(new PropertyValueFactory("odometer"));
        colServiceType.setCellValueFactory(new PropertyValueFactory("serviceType"));
        colMaterialCost.setCellValueFactory(new PropertyValueFactory("materialCost"));
        colLabourCharge.setCellValueFactory(new PropertyValueFactory("labourCharge"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));
        colNextService.setCellValueFactory(new PropertyValueFactory("nextService"));
        loadAllServicedVehicles();

        // add vehicle types to the combo box
        ObservableList<String> vehicleTypes = FXCollections.observableArrayList("Full Service","Half Service");
        cbxServiceType.setItems(vehicleTypes);

        //get the selected row from the table to textfields
        tblService.getSelectionModel().selectedItemProperty()
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
        Pattern VehicleModelPattern = Pattern.compile("^[A-Z0-9-./ ]{3,25}$");
        Pattern VehicleMakePattern = Pattern.compile("^[A-Z0-9-./ ]{3,20}$");
        // Pattern CusNICPattern = Pattern.compile("^[0-9]{9,12}[V]$");
        Pattern CusNamePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern CusMobilePattern = Pattern.compile("^(077|076|078|071|072)[0-9]{7}$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z0-9.,/ ]{4,35}$");
        Pattern odometerPattern = Pattern.compile("^[0-9]{4,6}$");
        Pattern MaterialCostPattern = Pattern.compile("^[0-9]{3,10}(.[0-9]{1})?$");
        Pattern LabourChargePattern = Pattern.compile("^[0-9]{3,10}(.[0-9]{1})?$");
        Pattern nextServicePattern = Pattern.compile("^[0-9]{4,6}$");

        //map.put(txtVehicleNumber,VehicleNumberPattern);
        map.put(txtVehicleModel,VehicleModelPattern);
        map.put(txtVehicleMake,VehicleMakePattern);
        map.put(txtVehicleYOM,VehicleYOMPattern);
        //  map.put(txtCustomerNIC,CusNICPattern);
        map.put(txtCustomerName,CusNamePattern);
        map.put(txtCustomerMobile,CusMobilePattern);
        map.put(txtCustomerAddress,CusAddressPattern);
        map.put(txtOdometer,odometerPattern);
        map.put(txtMaterialCost,MaterialCostPattern);
        map.put(txtLabourCharge,LabourChargePattern);
        map.put(txtNextService,nextServicePattern);

    }

    public void loadAllServicedVehicles() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT s.date,s.time, s.s_id, v.vehicle_no, v.vehicle_name, v.vehicle_make, v.vehicle_yom, c.customer_nic, c.customer_name, c.customer_mobile, c.customer_address, s.odometer, s.service_type, s.material_cost, s.labour_charge, s.t_cost, s.next_service\n" +
                "FROM service AS s, customer AS c, vehicle AS v\n" +
                "WHERE c.customer_nic=v.customer_nic AND v.vehicle_no=s.vehicle_no ORDER BY s_Id DESC;");
        ObservableList<ServiceTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new ServiceTM(
                            result.getString("date"),
                            result.getString("time"),
                            result.getString("s_Id"),
                            result.getString("vehicle_no"),
                            result.getString("vehicle_name"),
                            result.getString("vehicle_make"),
                            result.getInt("vehicle_yom"),
                            result.getString("customer_nic"),
                            result.getString("customer_name"),
                            result.getString("customer_mobile"),
                            result.getString("customer_address"),
                            result.getLong("odometer"),
                            result.getString("service_type"),
                            result.getDouble("material_cost"),
                            result.getDouble("labour_charge"),
                            result.getDouble("t_cost"),
                            result.getInt("next_service")
                    )
            );
        }
        tblService.setItems(obList);
    }

    private void selectedData(ServiceTM newValue) {
        lblServiceID.setText(newValue.getServiceID());
        txtVehicleNumber.setText(newValue.getVehicleNumber());
        txtVehicleModel.setText(newValue.getVehicleModel());
        txtVehicleMake.setText(newValue.getVehicleMake());
        txtVehicleYOM.setText(String.valueOf(newValue.getVehicleYOM()));
        txtCustomerNIC.setText(newValue.getCustomerNIC());
        txtCustomerName.setText(newValue.getCustomerName());
        txtCustomerMobile.setText(newValue.getCustomerMobile());
        txtCustomerAddress.setText(newValue.getCustomerAddress());
        txtOdometer.setText(String.valueOf(newValue.getOdometer()));
        cbxServiceType.setValue(String.valueOf(newValue.getServiceType()));
        txtMaterialCost.setText(String.valueOf(newValue.getMaterialCost()));
        txtLabourCharge.setText(String.valueOf(newValue.getLabourCharge()));
        txtNextService.setText(String.valueOf(newValue.getNextService()));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double totalCost = Double.parseDouble(txtMaterialCost.getText())+Double.parseDouble(txtLabourCharge.getText());

        try{
            CrudUtil.execute("UPDATE Customer SET customer_nic=?, customer_name=?, customer_mobile=?, customer_address=? WHERE customer_nic=?",
                    txtCustomerNIC.getText(),txtCustomerName.getText(),txtCustomerMobile.getText(),txtCustomerAddress.getText(),txtCustomerNIC.getText());

            // vehicle number and customer NIC cannot be updated.
            CrudUtil.execute("UPDATE Vehicle SET vehicle_no=?, vehicle_name=?, vehicle_make=?, vehicle_yom=? WHERE vehicle_no=?",
                    txtVehicleNumber.getText(),txtVehicleModel.getText(),txtVehicleMake.getText(), txtVehicleYOM.getText(),txtVehicleNumber.getText());

            CrudUtil.execute("UPDATE Service SET odometer=?, service_type=?, material_cost=?, labour_charge=?, t_cost=?, next_service=? WHERE s_Id=?",txtOdometer.getText(),
                    String.valueOf(cbxServiceType.getSelectionModel().getSelectedItem()),txtMaterialCost.getText(),txtLabourCharge.getText(),totalCost,txtNextService.getText(),lblServiceID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Updated..").show();
        }catch (ClassNotFoundException | SQLException E){
            E.printStackTrace();
            new Alert(Alert.AlertType.ERROR, E.getMessage()).show();
        }
        // clear the user input areas after deletion is done
        txtVehicleNumber.clear();
        txtVehicleYOM.clear();
        txtVehicleModel.clear();
        txtVehicleMake.clear();
        txtCustomerNIC.clear();
        txtCustomerName.clear();
        txtCustomerMobile.clear();
        txtCustomerAddress.clear();
        cbxServiceType.getSelectionModel().clearSelection();
        txtOdometer.clear();
        txtNextService.clear();
        txtMaterialCost.clear();
        txtLabourCharge.clear();

        loadAllServicedVehicles();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            CrudUtil.execute("DELETE FROM Service WHERE s_Id=?",lblServiceID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Deleted..").show();

            // clear the user input areas after deletion is done
            txtVehicleNumber.clear();
            txtVehicleYOM.clear();
            txtVehicleModel.clear();
            txtVehicleMake.clear();
            txtCustomerNIC.clear();
            txtCustomerName.clear();
            txtCustomerMobile.clear();
            txtCustomerAddress.clear();
            cbxServiceType.getSelectionModel().clearSelection();
            txtOdometer.clear();
            txtNextService.clear();
            txtMaterialCost.clear();
            txtLabourCharge.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllServicedVehicles();
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
