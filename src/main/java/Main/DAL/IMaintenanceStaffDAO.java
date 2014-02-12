package Main.DAL;

import Main.Entities.maintenance.MaintenanceStaff;

import java.sql.SQLException;

/**
 * Created by Jackson on 2/8/14.
 */
public interface IMaintenanceStaffDAO {
    MaintenanceStaff create(MaintenanceStaff newStaffMember);

    void delete(int staffMemberId);

    MaintenanceStaff get(int id) throws SQLException;

    MaintenanceStaff update(MaintenanceStaff updatedStaffMember);
}
