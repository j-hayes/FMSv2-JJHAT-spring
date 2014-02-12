package Main.BL;

import Main.Entities.maintenance.MaintenanceRequest;
import Main.Entities.maintenance.MaintenanceStaff;

import java.sql.Date;
import java.util.List;

public interface IFacilityMaintenanceService {

	public MaintenanceRequest makeFacilityMaintRequest(int unitId, String request);

    public MaintenanceRequest scheduleMaintenance(int MaintainenceRequestID, int staffMemberId, int estimatedTime, Date completeionDate);

    public double calcMaintenanceCostForFacility(int facilityId);

	public float calcDownTimeForFacility(int FacilityID, Date startDate, Date endDate);

    float calcProblemRateForFacility(int facilityID);

    public List<MaintenanceRequest> listMaintenanceRequests(int facilityID);


}
