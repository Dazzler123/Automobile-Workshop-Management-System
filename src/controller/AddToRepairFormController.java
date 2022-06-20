package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Customer;
import model.Repair;
import model.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.RepairTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static util.DateAndTime.loadDateAndTime;

public class AddToRepairFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtMaterialCost;
    public JFXTextField txtLabourCharge;
    public TextArea txtFaultDescription;
    public TextArea txtRepairDone;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleYOM;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
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
    public JFXButton btnAddToRepair;
    public JFXButton btnPrintInvoice;
    public Label lblRepairID;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {
        loadDateAndTime(lblDate, lblTime);

        //set columns for data
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colRepairID.setCellValueFactory(new PropertyValueFactory<>("repairID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory<>("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colFaultDescription.setCellValueFactory(new PropertyValueFactory<>("faultDescription"));
        colRepairDone.setCellValueFactory(new PropertyValueFactory<>("repairDone"));
        colMaterialCost.setCellValueFactory(new PropertyValueFactory<>("materialCost"));
        colLabourCharge.setCellValueFactory(new PropertyValueFactory<>("labourCharge"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        generateID();
        /////////////VALIDATION PART///////////////
        btnAddToRepair.setDisable(true);
        btnPrintInvoice.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        Pattern VehicleNumberPattern = Pattern.compile("^((WP)|(NW)|(SP)|(SG)|(NP))[A-Z-]{3}[0-9]{4,4}$");
        //)[A-z/-]{3}[0-9]{4,4}

        Pattern VehicleYOMPattern = Pattern.compile("^[0-9]{4}$");
        //Pattern RepairIDPattern = Pattern.compile("^(R)[0-9]{4,5}$");  // not necessary
        // Pattern CusNICPattern = Pattern.compile("^[0-9]{9,12}[V]$");
        Pattern CusNamePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern CusMobilePattern = Pattern.compile("^(077|076|078|071|072)[0-9]{7}$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z0-9.,/ ]{4,35}$");
        Pattern FaultDescPattern = Pattern.compile("^[A-z]$"); // not used due to type problem (not a textfield)
        Pattern RepairDonePattern = Pattern.compile("^[A-z]$"); // not used due to type problem (not a textfield)
        Pattern MaterialCostPattern = Pattern.compile("^[0-9]{3,10}$");
        Pattern LabourChargePattern = Pattern.compile("^[0-9]{3,10}$");

        //map.put(txtVehicleNumber,VehicleNumberPattern);
        map.put(txtVehicleYOM,VehicleYOMPattern);
      //  map.put(txtCustomerNIC,CusNICPattern);
        map.put(txtCustomerName,CusNamePattern);
        map.put(txtCustomerMobile,CusMobilePattern);
        map.put(txtCustomerAddress,CusAddressPattern);
        map.put(txtMaterialCost,MaterialCostPattern);
        map.put(txtLabourCharge,LabourChargePattern);
    }

    private void generateID() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(r_Id) FROM Repair");
            if(rs.next()){
                if(rs.getString("MAX(r_Id)")==null){
                    lblRepairID.setText("R0001");
                }else{
                    Long r_id = Long.parseLong(rs.getString("MAX(r_Id)").substring(2,rs.getString("MAX(r_Id)").length()));
                    r_id++;
                    lblRepairID.setText("R0"+String.format("%03d",r_id));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtSearchVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Vehicle WHERE vehicle_no=?",txtVehicleNumber.getText());
        if(result.next()) {
            txtVehicleYOM.setText(result.getString(4));
            new Alert(Alert.AlertType.CONFIRMATION,"Details Already Exists..").show();
        }
    }

    public void txtSearchCusOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE customer_nic=?", txtCustomerNIC.getText());
        if (result.next()) {
            txtCustomerName.setText(result.getString(2));
            txtCustomerMobile.setText(result.getString(3));
            txtCustomerAddress.setText(result.getString(4));
            new Alert(Alert.AlertType.CONFIRMATION, "Details Already Exists..").show();
        }
    }

    public void btnAddToRepairOnAction(ActionEvent actionEvent) {
        //gather information
         String date = lblDate.getText();
         String time = lblTime.getText();
         String dateTime = lblDate.getText() + "|" + lblTime.getText();
         String repairID = lblRepairID.getText();
         double materialCost = Double.parseDouble(txtMaterialCost.getText());
         double labourCharge = Double.parseDouble(txtLabourCharge.getText());
         double totalCost = materialCost+labourCharge;

         RepairTM repairTM = new RepairTM(
                 date,time,repairID,txtVehicleNumber.getText(),
                 Integer.parseInt(txtVehicleYOM.getText()),
                 txtCustomerNIC.getText(), txtCustomerName.getText(),
                 txtCustomerMobile.getText(), txtCustomerAddress.getText(),
                 txtFaultDescription.getText(),txtRepairDone.getText(),
                 materialCost,labourCharge,totalCost);
         tblRepair.getItems().add(repairTM);

    /////////////////////////////INSERTING DATA INTO THE DATABASE////////////////////////////////////////////////////////////
         Repair r = new Repair(date,time,repairID,txtVehicleNumber.getText()
                 ,txtFaultDescription.getText(),txtRepairDone.getText(),
                 materialCost,labourCharge,totalCost,txtCustomerNIC.getText());

         Customer c = new Customer(txtCustomerNIC.getText(),txtCustomerName.getText(),
                txtCustomerMobile.getText(),txtCustomerAddress.getText());

         Vehicle v = new Vehicle(txtVehicleNumber.getText(),"","",
                 Integer.parseInt(txtVehicleYOM.getText()),txtCustomerNIC.getText());

        try {

            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE customer_nic=?",txtCustomerNIC.getText());
            if(result.next()){
                // not adding the customer details to the customer table since its already exists
            }else{
                CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?)",
                        c.getCustomerNIC(),c.getCustomerName(),
                        c.getCustomerMobile(),c.getCustomerAddress());
            }
            ResultSet result2 = CrudUtil.execute("SELECT * FROM Vehicle WHERE vehicle_no=?",txtVehicleNumber.getText());
            if(result2.next()){
                // not adding the vehicle details to the vehicle table since its already exists
            }else{
                CrudUtil.execute("INSERT INTO Vehicle VALUES (?,?,?,?,?)",
                        v.getVehicleNo(),v.getVehicleName(),v.getVehicleMake(),
                        v.getVehicleYOM(),v.getCustomerNIC());
            }

            CrudUtil.execute("INSERT INTO Repair VALUES (?,?,?,?,?,?,?,?,?,?)",
                    r.getDate(),r.getTime(),r.getRepairID(),
                    r.getVehicleNumber(), r.getFaultDescription(),
                    r.getRepairDone(),r.getMaterialCost(),r.getLabourCharge(),
                    r.getTotalCost(),r.getCustomerNIC());

            new Alert(Alert.AlertType.CONFIRMATION, "Details Saved Successfully!..").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPrintInvoiceOnAction(ActionEvent actionEvent) {
        //get details from textfields
        String dateTime = lblDate.getText() +" |"+ lblTime.getText();
        String repairID = lblRepairID.getText();
        String vehicleNumber = txtVehicleNumber.getText();
        String faultDesc = txtFaultDescription.getText();
        String repairDone = txtRepairDone.getText();
        double materialCost = Double.parseDouble(txtMaterialCost.getText());
        double labourCharge = Double.parseDouble(txtLabourCharge.getText());
        double totalCost = materialCost+labourCharge;

        // add the key on the report and value from the user input to a hashmap
        HashMap paramMap2 = new HashMap();
        paramMap2.put("Date & Time",dateTime);
        paramMap2.put("Repair ID",repairID);
        paramMap2.put("Vehicle Number",vehicleNumber);
        paramMap2.put("Fault Desc",faultDesc);
        paramMap2.put("Repair Done",repairDone);
        paramMap2.put("Material Cost",materialCost);
        paramMap2.put("Labour Charge",labourCharge);
        paramMap2.put("Total Cost",totalCost);

        try {
            // catch the report
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/RepairInvoice.jasper"));

            //fill the information which report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap2, new JREmptyDataSource(1));

            //then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint,false);

            // clear the user input areas after addition is done
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
        } catch (JRException e) {
            e.printStackTrace();
        }
        // generate new id
        generateID();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void txtValidateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAddToRepair,btnPrintInvoice);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnAddToRepair,btnPrintInvoice);
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