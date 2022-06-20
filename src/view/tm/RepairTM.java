package view.tm;

public class RepairTM {
    private String dateTime;
    private String repairID;
    private String vehicleNumber;
    private int vehicleYOM;
    private String customerNIC;
    private String customerName;
    private String customerMobile;
    private String customerAddress;
    private String faultDescription;
    private String repairDone;
    private double materialCost;
    private double labourCharge;
    private double totalCost;

    public RepairTM(String date, String time, String repairID, String vehicleNumber, int vehicleYOM, String customerNIC, String customerName, String customerMobile, String customerAddress, String faultDescription, String repairDone, double materialCost, double labourCharge, double totalCost) {
        this.dateTime = date+"| "+time;
        this.repairID = repairID;
        this.vehicleNumber = vehicleNumber;
        this.vehicleYOM = vehicleYOM;
        this.customerNIC = customerNIC;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
        this.faultDescription = faultDescription;
        this.repairDone = repairDone;
        this.materialCost = materialCost;
        this.labourCharge = labourCharge;
        this.totalCost = totalCost;
    }

    public RepairTM() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public int getVehicleYOM() {
        return vehicleYOM;
    }

    public void setVehicleYOM(int vehicleYOM) {
        this.vehicleYOM = vehicleYOM;
    }

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    @Override
    public String toString() {
        return "RepairTM{" +
                "dateTime='" + dateTime + '\'' +
                ", repairID='" + repairID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleYOM=" + vehicleYOM +
                ", customerNIC='" + customerNIC + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerMobile='" + customerMobile + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", faultDescription='" + faultDescription + '\'' +
                ", repairDone='" + repairDone + '\'' +
                ", materialCost=" + materialCost +
                ", labourCharge=" + labourCharge +
                ", totalCost=" + totalCost +
                '}';
    }
}
