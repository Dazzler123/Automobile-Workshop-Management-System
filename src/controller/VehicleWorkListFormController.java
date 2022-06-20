package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.CrudUtil;
import view.tm.RepairTM;
import view.tm.ServiceTM;
import view.tm.WashTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleWorkListFormController {
    public TableView<ServiceTM> tblService;
    public TableColumn colDateTime;
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
    ////////// repair table fields ///////////////
    public TableView<RepairTM> tblRepair;
    public TableColumn colDateTime1;
    public TableColumn colRepairID;
    public TableColumn colVehicleNumber1;
    public TableColumn colVehicleYOM1;
    public TableColumn colCustomerNIC1;
    public TableColumn colCustomerName1;
    public TableColumn colCustomerMobile1;
    public TableColumn colCustomerAddress1;
    public TableColumn colFaultDescription;
    public TableColumn colRepairDone;
    public TableColumn colMaterialCost1;
    public TableColumn colLabourCharge1;
    public TableColumn colTotalCost1;
    //// wash table fields ///////////////////////
    public TableView<WashTM> tblWash;
    public TableColumn colDateTime2;
    public TableColumn colWashID;
    public TableColumn colVehicleNumber2;
    public TableColumn colVehicleYOM2;
    public TableColumn colCustomerNIC2;
    public TableColumn colCustomerName2;
    public TableColumn colCustomerMobile2;
    public TableColumn colCustomerAddress2;
    public TableColumn colWashType;
    public TableColumn colFee;

    public void initialize() throws SQLException, ClassNotFoundException {
        // service table
        colDateTime.setCellValueFactory(new PropertyValueFactory("dateTime"));
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

        // repair table
        colDateTime1.setCellValueFactory(new PropertyValueFactory("dateTime"));
        colRepairID.setCellValueFactory(new PropertyValueFactory("repairID"));
        colVehicleNumber1.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehicleYOM1.setCellValueFactory(new PropertyValueFactory("vehicleYOM"));
        colCustomerNIC1.setCellValueFactory(new PropertyValueFactory("customerNIC"));
        colCustomerName1.setCellValueFactory(new PropertyValueFactory("customerName"));
        colCustomerMobile1.setCellValueFactory(new PropertyValueFactory("customerMobile"));
        colCustomerAddress1.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        colFaultDescription.setCellValueFactory(new PropertyValueFactory("faultDescription"));
        colRepairDone.setCellValueFactory(new PropertyValueFactory("repairDone"));
        colMaterialCost1.setCellValueFactory(new PropertyValueFactory("materialCost"));
        colLabourCharge1.setCellValueFactory(new PropertyValueFactory("labourCharge"));
        colTotalCost1.setCellValueFactory(new PropertyValueFactory("totalCost"));

        // wash table
        colDateTime2.setCellValueFactory(new PropertyValueFactory("dateTime"));
        colWashID.setCellValueFactory(new PropertyValueFactory("washID"));
        colVehicleNumber2.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehicleYOM2.setCellValueFactory(new PropertyValueFactory("vehicleYOM"));
        colCustomerNIC2.setCellValueFactory(new PropertyValueFactory("customerNIC"));
        colCustomerName2.setCellValueFactory(new PropertyValueFactory("customerName"));
        colCustomerMobile2.setCellValueFactory(new PropertyValueFactory("customerMobile"));
        colCustomerAddress2.setCellValueFactory(new PropertyValueFactory("customerAddress"));
        colWashType.setCellValueFactory(new PropertyValueFactory("washType"));
        colFee.setCellValueFactory(new PropertyValueFactory("fee"));

        loadAllServicedVehicles();
        loadAllRepairedVehicles();
        loadAllWashedVehicles();
    }

    public void loadAllServicedVehicles() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT s.date, s.time, s.s_id, v.vehicle_no, v.vehicle_name, v.vehicle_make, v.vehicle_yom, c.customer_nic, c.customer_name, c.customer_mobile, c.customer_address, s.odometer, s.service_type, s.material_cost, s.labour_charge, s.t_cost, s.next_service\n" +
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

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
