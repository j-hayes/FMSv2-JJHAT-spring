package Main.BL;

import Main.Entities.maintenance.Inspection;

import java.sql.Date;
import java.util.List;

/**
 * User: alexthornburg
 * Date: 2/9/14
 * Time: 11:14 PM
 */
public interface IInspectionService {

    List<Inspection> listInspections(int facilityID);

    Inspection getInspectionInformation(int id);

    Inspection addInspection(int facilityID, int staffMemberId, Date inspectionDate);

    void removeInspection(int inspectionID);

    List<Inspection> getInspectionForFacility(int id);
}
