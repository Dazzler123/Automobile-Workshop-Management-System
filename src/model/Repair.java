package model;

public class Repair {
    private String date;
    private String time;
    private String repairID;
    private String vehicleNumber;
    private String faultDescription;
    private String repairDone;
    private double materialCost;
    private double labourCharge;
    private double totalCost;
    private String customerNIC ;

    public Repair(String date, String time, String repairID, String vehicleNumber, String faultDescription, String repairDone, double materialCost, double labourCharge, double totalCost, String customerNIC) {
        this.date = date;
        this.time = time;
        this.repairID = repairID;
        this.vehicleNumber = vehicleNumber;
        this.faultDescription = faultDescription;
        this.repairDone = repairDone;
        this.materialCost = materialCost;
        this.labourCharge = labourCharge;
        this.totalCost = totalCost;
        this.customerNIC = customerNIC;
    }

    public Repair() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getRepairDone() {
        return repairDone;
    }

    public void setRepairDone(String repairDone) {
        this.repairDone = repairDone;
    }

    public double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(double materialCost) {
        this.materialCost = materialCost;
    }

    public double getLabourCharge() {
        return labourCharge;
    }

    public void setLabourCharge(double labourCharge) {
        this.labourCharge = labourCharge;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", repairID='" + repairID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", faultDescription='" + faultDescription + '\'' +
                ", repairDone='" + repairDone + '\'' +
                ", materialCost=" + materialCost +
                ", labourCharge=" + labourCharge +
                ", totalCost=" + totalCost +
                ", customerNIC='" + customerNIC + '\'' +
                '}';
    }
}
