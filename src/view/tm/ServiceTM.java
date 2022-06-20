package view.tm;

public class ServiceTM {
    private String dateTime;
    private String serviceID;
    private String vehicleNumber;
    private String vehicleModel;
    private String vehicleMake;
    private int vehicleYOM;
    private String customerNIC;
    private String customerName;
    private String customerMobile;
    private String customerAddress;
    private long odometer;
    private String serviceType;
    private double materialCost;
    private double labourCharge;
    private double totalCost;
    private int nextService;

    public ServiceTM(String date, String time, String serviceID, String vehicleNumber, String vehicleModel, String vehicleMake, int vehicleYOM, String customerNIC, String customerName, String customerMobile, String customerAddress, long odometer, String serviceType, double materialCost, double labourCharge, double totalCost, int nextService) {
        this.dateTime = date + "| " + time;
        this.serviceID = serviceID;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleMake = vehicleMake;
        this.vehicleYOM = vehicleYOM;
        this.customerNIC = customerNIC;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
        this.odometer = odometer;
        this.serviceType = serviceType;
        this.materialCost = materialCost;
        this.labourCharge = labourCharge;
        this.totalCost = totalCost;
        this.nextService = nextService;
    }

    public ServiceTM() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
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
        return "ServiceTM{" +
                "dateTime='" + dateTime + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleYOM=" + vehicleYOM +
                ", customerNIC='" + customerNIC + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerMobile='" + customerMobile + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", odometer=" + odometer +
                ", serviceType='" + serviceType + '\'' +
                ", materialCost=" + materialCost +
                ", labourCharge=" + labourCharge +
                ", totalCost=" + totalCost +
                ", nextService=" + nextService +
                '}';
    }
}
