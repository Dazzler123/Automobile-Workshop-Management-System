package model;

public class Vehicle {
    String vehicleNo;
    String vehicleName; //vehicle model
    String vehicleMake;
    int vehicleYOM;
    String customerNIC;

    public Vehicle(String vehicleNo, String vehicleName, String vehicleMake, int vehicleYOM, String customerNIC) {
        this.vehicleNo = vehicleNo;
        this.vehicleName = vehicleName;
        this.vehicleMake = vehicleMake;
        this.vehicleYOM = vehicleYOM;
        this.customerNIC = customerNIC;
    }

    public Vehicle() {
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleYOM='" + vehicleYOM + '\'' +
                ", customerNIC='" + customerNIC + '\'' +
                '}';
    }
}
