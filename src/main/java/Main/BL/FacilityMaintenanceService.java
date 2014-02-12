package Main.BL;

import Main.DAL.*;

import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.MaintenanceRequest;
import Main.Entities.maintenance.MaintenanceStaff;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FacilityMaintenanceService implements IFacilityMaintenanceService {

    private MaintenanceRequest request;
    private IFacilityDAO facilityDAO;
    private IUnitDAO unitDAO;
    private IMaintenanceRequestDAO maintenanceRequestDAO;
    private IMaintenanceStaffDAO maintenanceStaffDAO;

    public FacilityMaintenanceService(IFacilityDAO facilityDAO, IUnitDAO unitDAO, IMaintenanceRequestDAO maintenanceRequestDAO, IMaintenanceStaffDAO maintenanceStaffDAO){
        this.unitDAO = unitDAO;
        this.maintenanceRequestDAO = maintenanceRequestDAO;
        this.maintenanceStaffDAO = maintenanceStaffDAO;
    }

    @Override
    public MaintenanceRequest makeFacilityMaintRequest(int unitID, String request) {
        MaintenanceRequest newRequest = new MaintenanceRequest();
        newRequest.setRequest(request);

        try {
            newRequest.setUnit(unitDAO.GetUnit(unitID));
            newRequest = maintenanceRequestDAO.create(newRequest);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newRequest;
    }

    @Override
    public MaintenanceRequest scheduleMaintenance(int MaintainenceRequestID, int staffMemberId, int estimatedTime, java.sql.Date completionDate) {

        MaintenanceRequest request = new MaintenanceRequest();

        try {
            request = maintenanceRequestDAO.get(MaintainenceRequestID);

            MaintenanceStaff staff = maintenanceStaffDAO.get(staffMemberId);
            System.out.println("StaffMemberID in scheduleMaintenceService" + staff.getID());

            request.setHoursToComplete(estimatedTime);
            request.setStaffMemberAssigned(staff);
            request.setCompletionDate(completionDate);

            return maintenanceRequestDAO.update(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }


    @Override
    public double calcMaintenanceCostForFacility(int facilityID) {
        try {
            List<Unit> units = unitDAO.GetUnitForFacility(facilityID);
            List<MaintenanceRequest> requests = new ArrayList<MaintenanceRequest>();


            for(Unit unit:units)
            {
              requests.addAll(maintenanceRequestDAO.getAllForUnit(unit));
            }
            double cost = 0.0;
            for(MaintenanceRequest maintenanceRequest:requests)
            {
               cost += maintenanceRequest.getStaffMemberAssigned().getHoursPerWeek() * maintenanceRequest.getHoursToComplete();
            }
            return cost;

            } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public float calcDownTimeForFacility(int FacilityID, Date startDate, Date endDate) {
        return 0;
    }

    @Override
    public float calcProblemRateForFacility(int facilityID) {
        try {
            List<Unit> units = unitDAO.GetUnitForFacility(facilityID);
            List<MaintenanceRequest> requests = new ArrayList<MaintenanceRequest>();

            int numberOfunits = 0;
            for(Unit unit:units)
            {
                requests.addAll(maintenanceRequestDAO.getAllForUnit(unit));
                numberOfunits++;
            }
            int numberOfProblems=0;
            for(MaintenanceRequest maintenanceRequest:requests)
            {
                numberOfProblems++;
            }
            return (float) numberOfProblems/numberOfunits;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public List<MaintenanceRequest> listMaintenanceRequests(int facilityID) {

        List<MaintenanceRequest> requests = new ArrayList<MaintenanceRequest>();
        try {

            List<Unit> units = unitDAO.GetUnitForFacility(facilityID);
            for(Unit unit:units)
            {
                requests.addAll(maintenanceRequestDAO.getAllForUnit(unit));
            }
            return requests;

        } catch (SQLException e) {
            e.printStackTrace();
            return requests;
        }
    }

}