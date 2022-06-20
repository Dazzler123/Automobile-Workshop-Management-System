package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDataController {
    public static ArrayList<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT e_Id FROM Employee");
        ArrayList<String> ids = new ArrayList<>();
        while(result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
}
