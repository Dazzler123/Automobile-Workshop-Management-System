package model;

public class Sales {
    private int salesID; // db table set as auto increment
    private String dateTime;
    private String washID;
    private String repairID;
    private String serviceID;
    private String description;
    private double subTotal;

    public Sales(int salesID, String dateTime, String washID, String repairID, String serviceID, String description, double subTotal) {
        this.salesID = salesID;
        this.dateTime = dateTime;
        this.washID = washID;
        this.repairID = repairID;
        this.serviceID = serviceID;
        this.description = description;
        this.subTotal = subTotal;
    }

    public Sales() {
    }

    public int getSalesID() {
        return salesID;
    }

    public void setSalesID(int salesID) {
        this.salesID = salesID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWashID() {
        return washID;
    }

    public void setWashID(String washID) {
        this.washID = washID;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "salesID='" + salesID + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", washID='" + washID + '\'' +
                ", repairID='" + repairID + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", description='" + description + '\'' +
                ", subTotal='" + subTotal + '\'' +
                '}';
    }
}
