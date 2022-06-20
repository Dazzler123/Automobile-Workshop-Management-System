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
import model.Customer;
import model.Service;
import model.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.ServiceTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static util.DateAndTime.loadDateAndTime;

public class AddToServiceFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleModel;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtMaterialCost;
    public JFXTextField txtLabourCharge;
    public JFXTextField txtNextService;
    public JFXTextField txtVehicleMake;
    public JFXTextField txtVehicleYOM;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
    public JFXTextField txtOdometer;
    public TableView<ServiceTM> tblService;
    public TableColumn colServiceID;
    public TableColumn colDateTime;
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
    public JFXComboBox cbxServiceType;
    public JFXButton btnPrintInvoice;
    public JFXButton btnAddToService;
    public Label lblServiceID;
    LinkedHashMap<TextField, Pattern> map2 = new LinkedHashMap<>();

    public void initialize(){
        loadDateAndTime(lblDate,lblTime);

        // add vehicle types to the combo box
        ObservableList<String> vehicleTypes = FXCollections.observableArrayList("Full Service","Half Service");
        cbxServiceType.setItems(vehicleTypes);

        //set columns for data
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colServiceID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colVehicleMake.setCellValueFactory(new PropertyValueFactory<>("vehicleMake"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory<>("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colOdometer.setCellValueFactory(new PropertyValueFactory<>("odometer"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colMaterialCost.setCellValueFactory(new PropertyValueFactory<>("materialCost"));
        colLabourCharge.setCellValueFactory(new PropertyValueFactory<>("labourCharge"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colNextService.setCellValueFactory(new PropertyValueFactory<>("nextService"));

        generateID();
        /////////////VALIDATION PART///////////////
        btnAddToService.setDisable(true);
        btnPrintInvoice.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        Pattern VehicleNumberPattern = Pattern.compile("^((WP)|(NW)|(SP)|(SG)|(NP))[A-Z-]{3}[0-9]{4,4}$");
        //)[A-z/-]{3}[0-9]{4,4}

        Pattern VehicleYOMPattern = Pattern.compile("^[0-9]{4}$");
        Pattern VehicleModelPattern = Pattern.compile("^[A-Z0-9-./ ]{3,25}$");
        Pattern VehicleMakePattern = Pattern.compile("^[A-Z0-9-./ ]{3,20}$");
        //Pattern ServiceIDPattern = Pattern.compile("^(S)[0-9]{3,3}$");
        // Pattern CusNICPattern = Pattern.compile("^[0-9]{9,12}[V]$");
        Pattern CusNamePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern CusMobilePattern = Pattern.compile("^(077|076|078|071|072)[0-9]{7}$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z0-9.,/ ]{4,35}$");
        Pattern odometerPattern = Pattern.compile("^[0-9]{4,6}$");
        Pattern MaterialCostPattern = Pattern.compile("^[0-9]{3,10}$");
        Pattern LabourChargePattern = Pattern.compile("^[0-9]{3,10}$");
        Pattern nextServicePattern = Pattern.compile("^[0-9]{4,6}$");

        //map.put(txtVehicleNumber,VehicleNumberPattern);
        map2.put(txtVehicleModel,VehicleModelPattern);
        map2.put(txtVehicleMake,VehicleMakePattern);
        map2.put(txtVehicleYOM,VehicleYOMPattern);
        //  map.put(txtCustomerNIC,CusNICPattern);
        map2.put(txtCustomerName,CusNamePattern);
        map2.put(txtCustomerMobile,CusMobilePattern);
        map2.put(txtCustomerAddress,CusAddressPattern);
        map2.put(txtOdometer,odometerPattern);
        map2.put(txtMaterialCost,MaterialCostPattern);
        map2.put(txtLabourCharge,LabourChargePattern);
        map2.put(txtNextService,nextServicePattern);

    }
    private void generateID() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(s_Id) FROM Service");
            if(rs.next()){
                if(rs.getString("MAX(s_Id)")==null){
                    lblServiceID.setText("S0001");
                }else{
                    Long s_id = Long.parseLong(rs.getString("MAX(s_Id)").substring(2,rs.getString("MAX(s_Id)").length()));
                    s_id++;
                    lblServiceID.setText("S0"+String.format("%03d",s_id));
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
            txtVehicleModel.setText(result.getString(2));
            txtVehicleMake.setText(result.getString(3));
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

    public void btnAddToServiceOnAction(ActionEvent actionEvent) {
        // gather information
        String date = lblDate.getText();
        String time = lblTime.getText();
        String dateTime = lblDate.getText() + "|" + lblTime.getText();
        String serviceID = lblServiceID.getText();
        String vehicleNumber = txtVehicleNumber.getText();
        String vehicleModel = txtVehicleModel.getText();
        String vehicleMake = txtVehicleMake.getText();
        int vehicleYOM = Integer.parseInt(txtVehicleYOM.getText());
        String customerNIC = txtCustomerNIC.getText();
        String customerName = txtCustomerName.getText();
        String customerMobile = txtCustomerMobile.getText();
        String customerAddress = txtCustomerAddress.getText();
        long odometer = Long.parseLong(txtOdometer.getText());
        String serviceType = cbxServiceType.getSelectionModel().getSelectedItem().toString() ;
        double materialCost = Double.parseDouble(txtMaterialCost.getText());
        double labourCharge = Double.parseDouble(txtLabourCharge.getText());
        double totalCost = materialCost+labourCharge;
        int nextService = Integer.parseInt(txtNextService.getText());

        ServiceTM serviceTM = new ServiceTM(date,time,serviceID,vehicleNumber,vehicleModel,
                vehicleMake,vehicleYOM,customerNIC,customerName,customerMobile,
                customerAddress,odometer,serviceType,materialCost,labourCharge,totalCost,nextService);
        tblService.getItems().add(serviceTM);


     /////////////////////////////INSERTING DATA INTO THE DATABASE/////////////////////////////////////////////////////////////////////////
        Service s = new Service(date,time,serviceID,odometer,serviceType,materialCost,
                labourCharge,totalCost,nextService,customerNIC,vehicleNumber);

        Customer c = new Customer(customerNIC,customerName,customerMobile,customerAddress);

        Vehicle v = new Vehicle(vehicleNumber,vehicleModel,vehicleMake,vehicleYOM,customerNIC);

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

           CrudUtil.execute("INSERT INTO Service VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                  s.getDate(),s.getTime(),s.getServiceID(),
                  s.getOdometer(), s.getServiceType(),
                  s.getMaterialCost(),s.getLabourCharge(),s.getTotalCost(),
                  s.getNextService(),s.getCustomerNIC(),s.getVehicleNumber());

             new Alert(Alert.AlertType.CONFIRMATION, "Details Saved Successfully!..").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPrintInvoiceOnAction(ActionEvent actionEvent) {
        //get details from textfields
        String dateTime = lblDate.getText() + "|" + lblTime.getText();
        String serviceID = lblServiceID.getText();
        String vehicleNumber = txtVehicleNumber.getText();
        long odometer = Long.parseLong(txtOdometer.getText());
        String serviceType = cbxServiceType.getSelectionModel().getSelectedItem().toString() ;
        double materialCost = Double.parseDouble(txtMaterialCost.getText());
        double labourCharge = Double.parseDouble(txtLabourCharge.getText());
        double totalCost = materialCost+labourCharge;
        int nextService = Integer.parseInt(txtNextService.getText());

        // add key on the report and value from the user input to a hashmap
        HashMap paramMap = new HashMap();
        paramMap.put("Date & Time",dateTime);
        paramMap.put("Service ID",serviceID);
        paramMap.put("Vehicle Number",vehicleNumber);
        paramMap.put("Odometer",odometer);
        paramMap.put("Service Type",serviceType);
        paramMap.put("Material Cost",materialCost);
        paramMap.put("Labour Charge",labourCharge);
        paramMap.put("Total Cost",totalCost);
        paramMap.put("Next Service",nextService);

        try {
            // catch the report
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/ServiceInvoice.jasper"));

            //fill the information which report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));

            //then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint,false);

            // clear the user input areas after addition is done
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
        } catch (JRException e) {
            e.printStackTrace();
        }
        // generate new id
        generateID();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void txtValidateOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(map2,btnAddToService,btnPrintInvoice);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map2,btnAddToService,btnPrintInvoice);
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
