package model;

public class Service {
    private String date;
    private String time;
    private String serviceID;
    private String vehicleNumber;
    private String customerNIC;
    private long odometer;
    private String serviceType;
    private double materialCost;
    private double labourCharge;
    private double totalCost;
    private int nextService;

    public Service(String date,String time, String serviceID,long odometer , String serviceType, double materialCost, double labourCharge, double totalCost, int nextService, String customerNIC,  String vehicleNumber) {
        this.date = date;
        this.time = time;
        this.serviceID = serviceID;
        this.vehicleNumber = vehicleNumber;
        this.customerNIC = customerNIC;
        this.odometer = odometer;
        this.serviceType = serviceType;
        this.materialCost = materialCost;
        this.labourCharge = labourCharge;
        this.totalCost = totalCost;
        this.nextService = nextService;
    }

    public Service() {
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

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    public long getOdometer() {
        return odometer;
    }

    public void setOdometer(long odometer) {
        this.odometer = odometer;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public int getNextService() {
        return nextService;
    }

    public void setNextService(int nextService) {
        this.nextService = nextService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", customerNIC='" + customerNIC + '\'' +
                ", odometer=" + odometer +
                ", serviceType='" + serviceType + '\'' +
                ", materialCost=" + materialCost +
                ", labourCharge=" + labourCharge +
                ", totalCost=" + totalCost +
                ", nextService=" + nextService +
                '}';
    }
}
