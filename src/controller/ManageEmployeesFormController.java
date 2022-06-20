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
import model.Employee;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageEmployeesFormController {
    public TableView<Employee> tblEmployee;
    public TableColumn colEmpID;
    public TableColumn colEmpNIC;
    public TableColumn colEmpName;
    public TableColumn colEmpMobile;
    public TableColumn colEmpAddress;
    public TableColumn colEmpJobRole;
    public TableColumn colEmpSalary;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpNIC;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpMobile;
    public JFXTextField txtEmpJobRole;
    public JFXTextField txtEmpSalary;
    public JFXButton btnAddEmployeeOnAction;
    public JFXButton btnUpdateEmployeeOnAction;
    public JFXButton btnDeleteEmployeeOnAction;
    public Label lblEmpID;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
      colEmpID.setCellValueFactory(new PropertyValueFactory("empID"));
      colEmpNIC.setCellValueFactory(new PropertyValueFactory("empNIC"));
      colEmpName.setCellValueFactory(new PropertyValueFactory("empName"));
      colEmpMobile.setCellValueFactory(new PropertyValueFactory("empMobile"));
      colEmpAddress.setCellValueFactory(new PropertyValueFactory("empAddress"));
      colEmpJobRole.setCellValueFactory(new PropertyValueFactory("empJobRole"));
      colEmpJobRole.setCellValueFactory(new PropertyValueFactory("empJobRole"));
      colEmpSalary.setCellValueFactory(new PropertyValueFactory("empSalary"));

      loadAllEmployees();

      //get the selected row from the table to textfields
      tblEmployee.getSelectionModel().selectedItemProperty()
              .addListener(((observable, oldValue, newValue) -> {
                  selectedData(newValue);
              }));

      generateID();

        /////////////VALIDATION PART///////////////
        btnAddEmployeeOnAction.setDisable(true);
        btnUpdateEmployeeOnAction.setDisable(true);
        btnDeleteEmployeeOnAction.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        //Pattern EmpIDPattern = Pattern.compile("^(E)[0-9]{3,3}$");
        //Pattern EmpNICPattern = Pattern.compile("^[0-9]{9,12}[V]$");
        Pattern EmpNamePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern EmpMobilePattern = Pattern.compile("^(077|076|078|071|072)[0-9]{7}$");
        Pattern EmpAddressPattern = Pattern.compile("^[A-z0-9.,/ ]{4,35}$");
        Pattern EmpJobRolePattern = Pattern.compile("^[A-z+,/ ]{4,25}$");
        Pattern EmpSalaryPattern = Pattern.compile("^[0-9]{3,10}(.[0-9]{1})?$");

        //map.put(txtEmpNIC,EmpNICPattern);
        map.put(txtEmpName,EmpNamePattern);
        map.put(txtEmpMobile,EmpMobilePattern);
        map.put(txtEmpAddress,EmpAddressPattern);
        map.put(txtEmpJobRole,EmpJobRolePattern);
        map.put(txtEmpSalary,EmpSalaryPattern);
    }

    private void generateID() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(e_Id) FROM Employee");
            if(rs.next()){
                //rs.getString("MAX(e_Id)");

                if(rs.getString("MAX(e_Id)")==null){
                    lblEmpID.setText("E001");
                }else{
                    Long e_id = Long.parseLong(rs.getString("MAX(e_Id)").substring(2,rs.getString("MAX(e_id)").length()));
                    e_id++;
                    lblEmpID.setText("E0"+String.format("%02d",e_id));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddEmpOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    /////////////////////////////INSERTING DATA INTO THE DATABASE////////////////////////////////////////////////////////////
        Employee e = new Employee(
                lblEmpID.getText(),txtEmpNIC.getText(),
                txtEmpName.getText(),txtEmpAddress.getText(),
                txtEmpMobile.getText(),txtEmpJobRole.getText(),
                Double.parseDouble(txtEmpSalary.getText()));

        try {
            CrudUtil.execute("INSERT INTO Employee VALUES(?,?,?,?,?,?,?)",
                    e.getEmpID(),e.getEmpNIC(),e.getEmpName(),
                    e.getEmpAddress(),e.getEmpMobile(),e.getEmpJobRole(),e.getEmpSalary());

            new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully!..").show();

            // clear the user input areas after employee addition is done
            //lblEmpID.clear();
            txtEmpNIC.clear();
            txtEmpName.clear();
            txtEmpMobile.clear();
            txtEmpAddress.clear();
            txtEmpJobRole.clear();
            txtEmpSalary.clear();
        } catch (ClassNotFoundException | SQLException exc) {
            exc.printStackTrace();
            new Alert(Alert.AlertType.ERROR, exc.getMessage()).show();
        }
        loadAllEmployees();
        //generate new id
        generateID();
    }

    public void btnUpdateEmpOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try{
            CrudUtil.execute("UPDATE Employee SET employee_nic=?, employee_name=?, employee_address=?, employee_mobile=?, job_role=?, salary=? WHERE e_Id=?",
                    txtEmpNIC.getText(),txtEmpName.getText(),txtEmpAddress.getText(),
                    txtEmpMobile.getText(),txtEmpJobRole.getText(),txtEmpSalary.getText(),lblEmpID.getText());

            new Alert(Alert.AlertType.CONFIRMATION,"Details Updated..").show();

            // clear the user input areas after employee updating is done
            //lblEmpID.clear();
            txtEmpNIC.clear();
            txtEmpName.clear();
            txtEmpMobile.clear();
            txtEmpAddress.clear();
            txtEmpJobRole.clear();
            txtEmpSalary.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployees();
        generateID();
    }

    public void btnDeleteEmpOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("DELETE FROM Employee WHERE e_Id=?",lblEmpID.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted..").show();

            // clear the user input areas after employee deletion is done
            //lblEmpID.clear();
            txtEmpNIC.clear();
            txtEmpName.clear();
            txtEmpMobile.clear();
            txtEmpAddress.clear();
            txtEmpJobRole.clear();
            txtEmpSalary.clear();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
        loadAllEmployees();
        generateID();
    }

    private void loadAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Employee(
                            result.getString("e_Id"),
                            result.getString("employee_nic"),
                            result.getString("employee_name"),
                            result.getString("employee_address"),
                            result.getString("employee_mobile"),
                            result.getString("job_role"),
                            result.getDouble("salary")
                    )
            );
        }
        tblEmployee.setItems(obList);
    }

    //get the selected data row from the table to available textfields
    private void selectedData(Employee newValue) {
        lblEmpID.setText(newValue.getEmpID());
        txtEmpNIC.setText(newValue.getEmpNIC());
        txtEmpName.setText(newValue.getEmpName());
        txtEmpAddress.setText(newValue.getEmpAddress());
        txtEmpMobile.setText(newValue.getEmpMobile());
        txtEmpJobRole.setText(newValue.getEmpJobRole());
        txtEmpSalary.setText(String.valueOf(newValue.getEmpSalary()));
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void txtValidateOnAction(KeyEvent keyEvent) {
        validate(map,btnAddEmployeeOnAction,btnUpdateEmployeeOnAction,btnDeleteEmployeeOnAction);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  validate(map,btnAddEmployeeOnAction,btnUpdateEmployeeOnAction,btnDeleteEmployeeOnAction);
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {

            }
        }
    }

    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton addBtn, JFXButton updateBtn,JFXButton deleteBtn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                //if the input is not matching
                addError(key,addBtn,updateBtn,deleteBtn);
                return key;
            }
            removeError(key,addBtn,updateBtn,deleteBtn);
        }
        return true;
    }

    private static void removeError(TextField txtField,JFXButton addBtn,JFXButton updateBtn,JFXButton deleteBtn) {
        txtField.setStyle("-fx-border-color: green");
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private static void addError(TextField txtField,JFXButton addBtn,JFXButton updateBtn,JFXButton deleteBtn) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-fx-border-color: red");
        }
        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }
}
