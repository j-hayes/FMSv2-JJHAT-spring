package Main.BL;

import Main.DAL.IFacilityDAO;
import Main.DAL.IInspectionDAO;
import Main.DAL.IMaintenanceStaffDAO;
import Main.Entities.maintenance.Inspection;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: alexthornburg
 * Date: 2/9/14
 * Time: 11:13 PM
 */

public class InspectionService implements IInspectionService{


    private IInspectionDAO inspectionDAO;
    private IFacilityDAO facilityDAO;
    private IMaintenanceStaffDAO maintenanceStaffDAO;

    public InspectionService(IInspectionDAO inspectionDAO, IFacilityDAO facilityDAO, IMaintenanceStaffDAO maintenanceStaffDAO) {
        this.inspectionDAO = inspectionDAO;
        this.facilityDAO = facilityDAO;
        this.maintenanceStaffDAO = maintenanceStaffDAO;
    }

    @Override
    public List<Inspection> listInspections(int facilityID) {
        return inspectionDAO.listAllInspections(facilityID);
    }

    @Override
    public Inspection getInspectionInformation(int id) {
        return inspectionDAO.get(id);
    }

    @Override
    public Inspection addInspection(int facilityID, int staffMemberId, Date inspectionDate) {

        Inspection inspection = new Inspection();
        try {

            inspection.setFacility(facilityDAO.get(facilityID));
            inspection.setInspectingStaff(maintenanceStaffDAO.get(staffMemberId));
            inspection.setInspectionDate(inspectionDate);
            return inspectionDAO.create(inspection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inspection;

    }

    @Override
    public void removeInspection(int inspectionID) {
        inspectionDAO.delete(inspectionID);
    }

    @Override
    public List<Inspection> getInspectionForFacility(int id) {
      return inspectionDAO.listAllInspections(id);
    }
}
