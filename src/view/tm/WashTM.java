package view.tm;

public class WashTM {
    private String dateTime;
    private String washID;
    private String vehicleNumber;
    private int vehicleYOM;
    private String customerNIC;
    private String customerName;
    private String customerMobile;
    private String customerAddress;
    private String washType;
    private double fee;

    public WashTM(String date, String time, String washID, String vehicleNumber, int vehicleYOM, String customerNIC, String customerName, String customerMobile, String customerAddress, String washType, double fee) {
        this.dateTime = date+"| "+time;
        this.washID = washID;
        this.vehicleNumber = vehicleNumber;
        this.vehicleYOM = vehicleYOM;
        this.customerNIC = customerNIC;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
        this.washType = washType;
        this.fee = fee;
    }

    public WashTM() {
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

    public String getWashType() {
        return washType;
    }

    public void setWashType(String washType) {
        this.washType = washType;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "WashTM{" +
                "dateTime='" + dateTime + '\'' +
                ", washID='" + washID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleYOM=" + vehicleYOM +
                ", customerNIC='" + customerNIC + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerMobile='" + customerMobile + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", washType='" + washType + '\'' +
                ", fee=" + fee +
                '}';
    }
}
