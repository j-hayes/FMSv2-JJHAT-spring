package Main.Entities.maintenance;


import Main.Entities.Facility.Facility;

import java.sql.Date;

public class Inspection {

	private int id;

	private Facility facility;

	private MaintenanceStaff inspectingStaff;

	private Date inspectionDate;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public MaintenanceStaff getInspectingStaff() {
        return inspectingStaff;
    }

    public void setInspectingStaff(MaintenanceStaff inspectingStaffID) {
        this.inspectingStaff = inspectingStaffID;
    }
}
