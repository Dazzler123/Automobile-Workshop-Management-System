package model;

public class Wash {
    private String date;
    private String time;
    private String washID;
    private String vehicleNumber;
    private String washType;
    private double fee;
    private String customerNIC ;

    public Wash(String date, String time, String washID, String vehicleNumber, String washType, double fee, String customerNIC) {
        this.date = date;
        this.time = time;
        this.washID = washID;
        this.vehicleNumber = vehicleNumber;
        this.washType = washType;
        this.fee = fee;
        this.customerNIC = customerNIC;
    }

    public Wash() {
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

    public String getCustomerNIC() {
        return customerNIC;
    }

    public void setCustomerNIC(String customerNIC) {
        this.customerNIC = customerNIC;
    }

    @Override
    public String toString() {
        return "Wash{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", washID='" + washID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", washType='" + washType + '\'' +
                ", fee=" + fee +
                ", customerNIC='" + customerNIC + '\'' +
                '}';
    }
}
