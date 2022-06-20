package controller;

import com.jfoenix.controls.JFXDatePicker;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportsFormController {
    public JFXDatePicker txtServiceDateFrom;
    public JFXDatePicker txtServiceDateTo;
    public JFXDatePicker txtRepairDateFrom;
    public JFXDatePicker txtRepairDateTo;
    public JFXDatePicker txtWashDateFrom;
    public JFXDatePicker txtWashDateTo;
    public JFXDatePicker txtEmpAtdDateFrom;
    public JFXDatePicker txtEmpAtdDateTo;
    public JFXDatePicker txtTotSalDateFrom;
    public JFXDatePicker txtTotSalDateTo;

    public void btnGenServiceReport(ActionEvent actionEvent) {
        // get selection date from date pickers
        LocalDate dateFrom = txtServiceDateFrom.getValue();
        LocalDate dateTo = txtServiceDateTo.getValue();

        try {
            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/ServiceReport.jrxml"));
            String sql = "SELECT * FROM Service WHERE date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //create connection
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,null,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnGenRepairReport(ActionEvent actionEvent) {
        // get selection date from date pickers
        LocalDate dateFrom = txtRepairDateFrom.getValue();
        LocalDate dateTo = txtRepairDateTo.getValue();

        try {
            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/RepairReport.jrxml"));
            String sql = "SELECT * FROM Repair WHERE date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //create connection
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,null,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnGenWashReport(ActionEvent actionEvent) {
        // get selection date from date pickers
        LocalDate dateFrom = txtWashDateFrom.getValue();
        LocalDate dateTo = txtWashDateTo.getValue();

        try {
            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/WashReport.jrxml"));
            String sql = "SELECT * FROM Wash WHERE date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //create connection
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,null,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnGenEmpAtdReport(ActionEvent actionEvent) {
        // get selection date from date pickers
        LocalDate dateFrom = txtEmpAtdDateFrom.getValue();
        LocalDate dateTo = txtEmpAtdDateTo.getValue();

        try {
            //JasperReport compileReport = (JasperReport) JRLoader.loadObject);
            //JasperDesign jd = JRXmlLoader.load(getClass().getResource("/view/reports/EmpAttendance.jasper"));
            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/EmpAttendance.jrxml"));
            String sql = "SELECT * FROM Attendance WHERE date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //create connection
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,null,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnGenTotSalReport(ActionEvent actionEvent) {
        // get selection date from date pickers
        LocalDate dateFrom = txtTotSalDateFrom.getValue();
        LocalDate dateTo = txtTotSalDateTo.getValue();

        try {
            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/TotalSales.jrxml"));
            String sql = "SELECT SUM(s.labour_charge), SUM(s.material_cost), SUM(s.t_cost), SUM(r.labour_charge), SUM(r.material_cost), SUM(r.t_cost), SUM(w.fee)\n" +
                    "FROM service AS s, repair AS r, wash AS w WHERE s.date BETWEEN '"+dateFrom+"' AND '"+dateTo+"' AND r.date BETWEEN '"+dateFrom+"' AND '"+dateTo+"' AND w.date BETWEEN '"+dateFrom+"' AND '"+dateTo+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //create connection
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr,null,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
