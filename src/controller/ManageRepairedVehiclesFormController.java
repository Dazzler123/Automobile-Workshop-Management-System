package controller;

import com.jfoenix.controls.JFXButton;
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
import view.tm.RepairTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageRepairedVehiclesFormController {
    public TableView<RepairTM> tblRepair;
    public TableColumn colDateTime;
    public TableColumn colRepairID;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleYOM;
    public TableColumn colCustomerNIC;
    public TableColumn colCustomerName;
    public TableColumn colCustomerMobile;
    public TableColumn colCustomerAddress;
    public TableColumn colFaultDescription;
    public TableColumn colRepairDone;
    public TableColumn colMaterialCost;
    public TableColumn colLabourCharge;
    public TableColumn colTotalCost;
    public TextArea txtFaultDescription;
    public TextArea txtRepairDone;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
    public JFXTextField txtMaterialCost;
    public JFXTextField txtLabourCharge;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleYOM;
    public Label lblRepairID;
    public JFXButton btnUpdateOnAction;
    public JFXButton btnDeleteOnAction;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        // repair table
        colDateTime.setCellValueFactory(new PropertyValueFactory("dateTime"));
        colRepairID.setCellValueFactory(new PropertyValueFactory("repairID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        colFaultDescription.setCellValueFactory(new PropertyValueFactory("faultDescription"));
        colRepairDone.setCellValueFactory(new PropertyValueFactory("repairDone"));
        colMaterialCost.setCellValueFactory(new PropertyValueFactory("materialCost"));
        colLabourCharge.setCellValueFactory(new PropertyValueFactory("labourCharge"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));
        loadAllRepairedVehicles();

        //get the selected row from the table to textfields
        tblRepair.getSelectionModel().selectedItemProperty()
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
        Pattern FaultDescPattern = Pattern.compile("^[A-z]$"); // not used due to type problem (not a textfield)
        Pattern RepairDonePattern = Pattern.compile("^[A-z]$"); // not used due to type problem (not a textfield)
        Pattern MaterialCostPattern = Pattern.compile("^[0-9]{3,10}(.[0-9]{1})?$");
        Pattern LabourChargePattern = Pattern.compile("^[0-9]{3,10}(.[0-9]{1})?$");

        //map.put(txtVehicleNumber,VehicleNumberPattern);
        map.put(txtVehicleYOM,VehicleYOMPattern);
        //  map.put(txtCustomerNIC,CusNICPattern);
        map.put(txtCustomerName,CusNamePattern);
        map.put(txtCustomerMobile,CusMobilePattern);
        map.put(txtCustomerAddress,CusAddressPattern);
        map.put(txtMaterialCost,MaterialCostPattern);
        map.put(txtLabourCharge,LabourChargePattern);

    }

    public void loadAllRepairedVehicles() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT r.date, r.time, r.r_id, v.vehicle_no, v.vehicle_yom, c.customer_nic, c.customer_name, c.customer_mobile, c.customer_address, r.fault_description, r.repair_done, r.material_cost, r.labour_charge, r.t_cost\n" +
                "FROM repair AS r, customer AS c, vehicle AS v\n" +
                "WHERE c.customer_nic=v.customer_nic AND v.vehicle_no=r.vehicle_no ORDER BY r_Id DESC;");
        ObservableList<RepairTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new RepairTM(
                            result.getString("date"),
                            result.getString("time"),
                            result.getString("r_Id"),
                            result.getString("vehicle_no"),
                            result.getInt("vehicle_yom"),
                            result.getString("customer_nic"),
                            result.getString("customer_name"),
                            result.getString("customer_mobile"),
                            result.getString("customer_address"),
                            result.getString("fault_description"),
                            result.getString("repair_done"),
                            result.getDouble("material_cost"),
                            result.getDouble("labour_charge"),
                            result.getDouble("t_cost")
                    )
            );
        }
        tblRepair.setItems(obList);
    }

    private void selectedData(RepairTM newValue) {
        lblRepairID.setText(newValue.getRepairID());
        txtVehicleNumber.setText(newValue.getVehicleNumber());
        txtVehicleYOM.setText(String.valueOf(newValue.getVehicleYOM()));
        txtCustomerNIC.setText(newValue.getCustomerNIC());
        txtCustomerName.setText(newValue.getCustomerName());
        txtCustomerMobile.setText(newValue.getCustomerMobile());
        txtCustomerAddress.setText(newValue.getCustomerAddress());
        txtFaultDescription.setText(newValue.getFaultDescription());
        txtRepairDone.setText(newValue.getRepairDone());
        txtMaterialCost.setText(String.valueOf(newValue.getMaterialCost()));
        txtLabourCharge.setText(String.valueOf(newValue.getLabourCharge()));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double totalCost = Double.parseDouble(txtMaterialCost.getText())+Double.parseDouble(txtLabourCharge.getText());
        try{

            CrudUtil.execute("UPDATE Customer SET customer_nic=?, customer_name=?, customer_mobile=?, customer_address=? WHERE customer_nic=?",
                    txtCustomerNIC.getText(),txtCustomerName.getText(),txtCustomerMobile.getText(),txtCustomerAddress.getText(),txtCustomerNIC.getText());

            // vehicle number and customer NIC cannot be updated.
            CrudUtil.execute("UPDATE Vehicle SET vehicle_no=?, vehicle_yom=? WHERE vehicle_no=?",
                    txtVehicleNumber.getText(),txtVehicleYOM.getText(),txtVehicleNumber.getText());

            CrudUtil.execute("UPDATE Repair SET fault_description=?, repair_done=?, material_cost=?, labour_charge=?, t_cost=? WHERE r_Id=?",txtFaultDescription.getText(),
                    txtRepairDone.getText(),txtMaterialCost.getText(),txtLabourCharge.getText(),totalCost,lblRepairID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Updated..").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        // clear the user input areas after updating is done
        txtVehicleNumber.clear();
        txtVehicleYOM.clear();
        txtCustomerNIC.clear();
        txtCustomerName.clear();
        txtCustomerMobile.clear();
        txtCustomerAddress.clear();
        txtFaultDescription.clear();
        txtRepairDone.clear();
        txtMaterialCost.clear();
        txtLabourCharge.clear();

        loadAllRepairedVehicles();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            CrudUtil.execute("DELETE FROM Repair WHERE r_Id=?",lblRepairID.getText());
            //CrudUtil.execute("DELETE FROM Vehicle WHERE vehicle_no=?",txtVehicleNumber.getText());
           // CrudUtil.execute("DELETE FROM Customer WHERE customer_nic=?",txtCustomerNIC.getText());
            new Alert(Alert.AlertType.CONFIRMATION,"Details Deleted..").show();

            // clear the user input areas after deletion is done
            txtVehicleNumber.clear();
            txtVehicleYOM.clear();
            txtCustomerNIC.clear();
            txtCustomerName.clear();
            txtCustomerMobile.clear();
            txtCustomerAddress.clear();
            txtFaultDescription.clear();
            txtRepairDone.clear();
            txtMaterialCost.clear();
            txtLabourCharge.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllRepairedVehicles();
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
