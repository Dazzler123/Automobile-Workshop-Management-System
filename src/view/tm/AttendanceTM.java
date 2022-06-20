package view.tm;

public class AttendanceTM {
    private String date;
    private String empID;
    private String timeOfArrival;
    private String timeOfDeparture;

    public AttendanceTM(String date, String empID, String timeOfArrival, String timeOfDeparture) {
        this.date = date;
        this.empID = empID;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
    }

    public AttendanceTM() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    @Override
    public String toString() {
        return "AttendanceTM{" +
                "date='" + date + '\'' +
                ", empID='" + empID + '\'' +
                ", timeOfArrival='" + timeOfArrival + '\'' +
                ", timeOfDeparture='" + timeOfDeparture + '\'' +
                '}';
    }
}
