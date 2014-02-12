package Main.Entities.usage;

import Main.Entities.Facility.Unit;
import org.joda.time.DateTime;

import java.sql.Date;


public class UnitUsage {

    private int id;

	private Date startTime;

	private Date endTime;

    private UnitUser unitUser;

    private Unit unit;

    public UnitUsage()
    {
    }








    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UnitUser getUnitUser() {
        return unitUser;
    }

    public void setUnitUser(UnitUser unitUser) {
        this.unitUser = unitUser;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
