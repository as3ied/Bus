package net.senior.busreservations.model;

public class StudentSubscribes {
    private String studentCode;
    private String BusCode;
    private String studentName;
    private String driverName;
    private String period;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getBusCode() {
        return BusCode;
    }

    public void setBusCode(String busCode) {
        BusCode = busCode;
    }
}
