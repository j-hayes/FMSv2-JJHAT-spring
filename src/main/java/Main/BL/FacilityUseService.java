package Main.BL;


import Main.DAL.*;
import Main.Entities.Facility.Facility;
import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.Inspection;
import Main.Entities.usage.UnitUsage;
import Main.Entities.usage.UnitUser;
import org.joda.time.DateTime;


import java.sql.Date;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityUseService implements IFacilityUseService {


    private IFacilityDAO facilityFactory;
    private  IUnitDAO unitFactory;
    private  IInspectionDAO inspectionDcAO;
    private  IUsageDAO usageDAO;

    public FacilityUseService(IFacilityDAO facilityFactory, IUnitDAO unitFactory, IInspectionDAO inspectionDcAO, IUsageDAO usageDAO){
        this.facilityFactory = facilityFactory;
        this.unitFactory = unitFactory;
        this.inspectionDcAO = inspectionDcAO;
        this.usageDAO = usageDAO;
    }

    @Override
    public boolean IsInUseDuringInterval(int unitID, java.util.Date startTime, java.util.Date endTime) {
        Unit unit = new Unit();
        try {
            unit = unitFactory.GetUnit(unitID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<UnitUsage> usages = unit.getUsages();
        if(usages!=null){
        for(UnitUsage usage: usages)
        {
            if(usage.getStartTime().compareTo(startTime) > 0 && usage.getStartTime().compareTo(endTime) < 0 )
            {
                return true;
            }

            if(usage.getEndTime().compareTo(startTime) > 0 && usage.getEndTime().compareTo(endTime) < 0 )
            {
                return true;
            }
        }

        }
        return false;
    }

    @Override
    public UnitUsage assignFacilityToUse(Date sartTime, Date entTime, UnitUser unitUser, Unit unit) {
        UnitUsage usage = new UnitUsage();
        usage.setEndTime(entTime);
        usage.setStartTime(sartTime);
        usage.setUnit(unit);
        usage.setUnitUser(unitUser);

        try {
            return usageDAO.CreateUsage(usage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void vacateFacility(int usageID)
    {
        usageDAO.DeleteUsage(usageID);
    }

    @Override
    public List<Inspection> listInspections(int facilityID) {
        return inspectionDcAO.listAllInspections(facilityID);
    }

    @Override
    public List<UnitUsage> listActualUsage(int unitID) {
        Unit unit = new Unit();

        List<UnitUsage> usages = new ArrayList<UnitUsage>();
        try {
            unit = unitFactory.GetUnit(unitID);

            usages = unitFactory.GetUsagesForUnit(unit);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usages;

    }

    @Override
    public List<UnitUsage> listUsages() {
        return usageDAO.GetAll();
    }
}



