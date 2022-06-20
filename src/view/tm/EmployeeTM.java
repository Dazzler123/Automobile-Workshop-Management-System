package view.tm;

public class EmployeeTM {
    String empID;
    String empNIC;
    String empName;
    String empMobile;
    String empAddress;
    String empJobRole;
    double empSalary;

    public EmployeeTM(String empID, String empNIC, String empName, String empMobile, String empAddress, String empJobRole, double empSalary) {
        this.empID = empID;
        this.empNIC = empNIC;
        this.empName = empName;
        this.empMobile = empMobile;
        this.empAddress = empAddress;
        this.empJobRole = empJobRole;
        this.empSalary = empSalary;
    }

    public EmployeeTM() {
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpNIC() {
        return empNIC;
    }

    public void setEmpNIC(String empNIC) {
        this.empNIC = empNIC;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpJobRole() {
        return empJobRole;
    }

    public void setEmpJobRole(String empJobRole) {
        this.empJobRole = empJobRole;
    }

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "empID='" + empID + '\'' +
                ", empNIC='" + empNIC + '\'' +
                ", empName='" + empName + '\'' +
                ", empMobile='" + empMobile + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empJobRole='" + empJobRole + '\'' +
                ", empSalary=" + empSalary +
                '}';
    }
}
