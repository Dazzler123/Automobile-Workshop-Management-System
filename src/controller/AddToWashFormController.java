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
import model.Vehicle;
import model.Wash;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.WashTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static util.DateAndTime.loadDateAndTime;

public class AddToWashFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cbxWashType;
    public JFXTextField txtVehicleNumber;
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
    public JFXTextField txtVehicleYOM;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobile;
    public JFXButton btnPrintInvoice;
    public JFXButton btnAddToWash;
    public Label lblWashID;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        loadDateAndTime(lblDate,lblTime);

        // add wash types to the combo box
        ObservableList<String> washTypes = FXCollections.observableArrayList("Body Wash","Body Wash with Cut & Polish",
                "Body Wash and Interior Clean","Body Wash with Cut & Polish and Interior Clean");
        cbxWashType.setItems(washTypes);

        // set columns for data
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colWashID.setCellValueFactory(new PropertyValueFactory<>("washID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleYOM.setCellValueFactory(new PropertyValueFactory<>("vehicleYOM"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerMobile.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colWashType.setCellValueFactory(new PropertyValueFactory<>("washType"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        generateID();
        /////////////VALIDATION PART///////////////
        btnAddToWash.setDisable(true);
        btnPrintInvoice.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        Pattern VehicleNumberPattern = Pattern.compile("^((WP)|(NW)|(SP)|(SG)|(NP))[A-Z-]{3}[0-9]{4,4}$");
        //)[A-z/-]{3}[0-9]{4,4}

        Pattern VehicleYOMPattern = Pattern.compile("^[0-9]{4}$");
        //Pattern WashIDPattern = Pattern.compile("^(W)[0-9]{3,3}$");
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
    private void generateID() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(w_Id) FROM Wash");
            if(rs.next()){
                if(rs.getString("MAX(w_Id)")==null){
                    lblWashID.setText("W0001");
                }else{
                    Long w_id = Long.parseLong(rs.getString("MAX(w_Id)").substring(2,rs.getString("MAX(w_Id)").length()));
                    w_id++;
                    lblWashID.setText("W0"+String.format("%03d",w_id));
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
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE customer_nic=?",txtCustomerNIC.getText());
        if(result.next()) {
            txtCustomerName.setText(result.getString(2));
            txtCustomerMobile.setText(result.getString(3));
            txtCustomerAddress.setText(result.getString(4));
            new Alert(Alert.AlertType.CONFIRMATION,"Details Already Exists..").show();
        }
    }

    public void btnAddToWashOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        // gather information
        String date = lblDate.getText();
        String time = lblTime.getText();
        String washID = lblWashID.getText();
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

        WashTM washTM = new WashTM(date,time,washID,txtVehicleNumber.getText(),
                Integer.parseInt(txtVehicleYOM.getText()),txtCustomerNIC.getText(),
                txtCustomerName.getText(),txtCustomerMobile.getText(),
                txtCustomerAddress.getText(),washType,fee);
        tblWash.getItems().add(washTM);

    /////////////////////////////INSERTING DATA INTO THE DATABASE////////////////////////////////////////////////////////////
        Wash w = new Wash(date,time,washID,txtVehicleNumber.getText(),
                washType,fee,txtCustomerNIC.getText());

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
            CrudUtil.execute("INSERT INTO Wash VALUES (?,?,?,?,?,?,?)",
                    w.getDate(), w.getTime(), w.getWashID(),
                    w.getVehicleNumber(), w.getWashType(),
                    w.getFee(), w.getCustomerNIC());
            new Alert(Alert.AlertType.CONFIRMATION, "Details Saved Successfully!..").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnPrintInvoiceOnAction(ActionEvent actionEvent) {
        //get details from textfields
        String dateTime = lblDate.getText() +" |"+ lblTime.getText();
        String washID = lblWashID.getText();
        String vehicleNumber = txtVehicleNumber.getText();
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

        // add the key on the report and value from the user input to a hashmap
        HashMap paramMap = new HashMap();
        paramMap.put("Date & Time",dateTime);
        paramMap.put("Wash ID",washID);
        paramMap.put("Vehicle Number",vehicleNumber);
        paramMap.put("Wash Type",washType);
        paramMap.put("Fee",fee);

        try {
            // catch the report
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/WashInvoice.jasper"));

            //fill the information which report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));

            //then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint,false);

            // clear the user input areas after saving is done
            txtVehicleNumber.clear();
            txtVehicleYOM.clear();
            txtCustomerNIC.clear();
            txtCustomerName.clear();
            txtCustomerMobile.clear();
            txtCustomerAddress.clear();
            cbxWashType.getSelectionModel().clearSelection();
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
        ValidationUtil.validate(map,btnAddToWash,btnPrintInvoice);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnAddToWash,btnPrintInvoice);
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
