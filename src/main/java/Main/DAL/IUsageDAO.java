package Main.DAL;

import Main.Entities.Facility.Unit;
import Main.Entities.usage.UnitUsage;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jackson on 2/10/14.
 */
public interface IUsageDAO {
    UnitUsage CreateUsage(UnitUsage usage) throws SQLException;

    List<UnitUsage> GetUsagesForUnit(Unit unit);

    UnitUsage UpdateUsage(UnitUsage unit) throws Exception;

    void DeleteUsage(int id);


    List<UnitUsage> GetAll();
}
