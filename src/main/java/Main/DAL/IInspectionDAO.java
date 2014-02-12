package Main.DAL;

import Main.Entities.maintenance.Inspection;

import java.util.List;

/**
 * Created by Jackson on 2/10/14.
 */
public interface IInspectionDAO {
    Inspection create(Inspection inspection);

    Inspection update(Inspection inspection);

    void delete(int id);

    Inspection get(int id);

    List<Inspection> listAllInspections(int facilityID);
}
