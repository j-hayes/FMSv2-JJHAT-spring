package Main.Entities.maintenance;

import Main.Entities.Facility.Facility;
import Main.Entities.Facility.Unit;

import java.sql.Date;

public class MaintenanceRequest {

	private int id;

	private Unit unit;

    private String request;

	private Date dateRequested;

	private Date completionDate;

	private MaintenanceStaff staffMemberAssigned;

    private int hoursToComplete;

	public MaintenanceRequest() {

	}

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }


    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public MaintenanceStaff getStaffMemberAssigned() {
        return staffMemberAssigned;
    }

    public void setStaffMemberAssigned(MaintenanceStaff staffMemberAssigned) {
        this.staffMemberAssigned = staffMemberAssigned;
    }

    public int getHoursToComplete() {
        return hoursToComplete;
    }

    public void setHoursToComplete(int hoursToComplete) {
        this.hoursToComplete = hoursToComplete;
    }
}
