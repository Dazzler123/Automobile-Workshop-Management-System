package model;

public class Attendance {
    private String date;
    private String e_Id;
    private String timeOfArrival;
    private String timeOfDeparture;

    public Attendance(String date, String e_Id, String timeOfArrival, String timeOfDeparture) {
        this.date = date;
        this.e_Id = e_Id;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
    }

    public Attendance() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getE_Id() {
        return e_Id;
    }

    public void setE_Id(String e_Id) {
        this.e_Id = e_Id;
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
        return "Attendance{" +
                "date='" + date + '\'' +
                ", e_Id='" + e_Id + '\'' +
                ", timeOfArrival='" + timeOfArrival + '\'' +
                ", timeOfDeparture='" + timeOfDeparture + '\'' +
                '}';
    }
}
