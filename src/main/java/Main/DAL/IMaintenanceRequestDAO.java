package Main.DAL;

import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.MaintenanceRequest;

import java.util.List;

/**
 * Created by Jackson on 2/8/14.
 */
public interface IMaintenanceRequestDAO {
    MaintenanceRequest create(MaintenanceRequest request);

    MaintenanceRequest update(MaintenanceRequest request) throws Exception;

    void delete(int ID);

    MaintenanceRequest get(int ID);

    List<MaintenanceRequest> getAllForUnit(Unit unit);
}
