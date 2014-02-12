package Main.Entities.maintenance;

import Main.Entities.Person;

public class MaintenanceStaff extends Person {

    private double payPerHour;

	private boolean isFullTime;

	private double hoursPerWeek;

	public MaintenanceStaff() {

	}

    public double getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(double payPerHour) {
        this.payPerHour = payPerHour;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

}
