package Main.DAL;

import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.MaintenanceRequest;
import Main.Entities.usage.UnitUsage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2/10/14.
 */
public class UsageDAO implements IUsageDAO {


    private IDatabaseConnector connector;
    private Connection connection;
    private IUnitDAO unitDAO;
    private IUserDAO userDAO;

    public UsageDAO(IDatabaseConnector connector, IUnitDAO unitDAO, IUserDAO userDAO) {
        this.connector = connector;
        this.unitDAO = unitDAO;
        this.userDAO = userDAO;
        connection = connector.connect();
    }

    public UnitUsage CreateUsage(UnitUsage usage) throws SQLException {
        String createQuery = "INSERT INTO unit_usage (unit_id,start_time,end_time,unit_user_id)" +
                "VALUES (?,?,?,?)";


        try {

            PreparedStatement insertStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1,usage.getUnit().getId());
            insertStatement.setDate(2,usage.getStartTime());
            insertStatement.setDate(3,usage.getStartTime());
            insertStatement.setInt(4,usage.getUnitUser().getID());


            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet result = insertStatement.getGeneratedKeys();

            if(result.next())
            {
                usage.setId(result.getInt("id"));

            }
            else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }

            insertStatement.close();
            result.close();

            if(usage.getUnitUser().getID()==0)
            {
                 usage.setUnitUser(userDAO.Create(usage.getUnitUser()));
            }
            else
            {
                usage.setUnitUser(userDAO.Update(usage.getUnitUser()));
            }
            return usage;

        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void DeleteUsage(int id){

        String deleteQuery = "DELETE FROM unit_usage WHERE id = ?";

        MaintenanceRequest request = null;
        try {
            PreparedStatement getStatement = connection.prepareStatement(deleteQuery);
            getStatement.setInt(1,id);

            ResultSet rs = getStatement.executeQuery();
            rs.close();
            getStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();

            //todo add throw??
        }

    }

    @Override
    public List<UnitUsage> GetAll() {
        String getQuery = "SELECT id, unit_id, start_time, end_time, unit_user_id FROM unit_usage";

        List<UnitUsage> usages = new ArrayList<UnitUsage>();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);

            ResultSet rs = getStatement.executeQuery();

            while(rs.next())
            {
                UnitUsage usage = new UnitUsage();
                usage.setUnit(unitDAO.GetUnit(rs.getInt("unit_id")));
                usage.setUnitUser(userDAO.Get(rs.getInt("unit_user_id")));
                usage.setId(rs.getInt("id"));
                usage.setStartTime(rs.getDate("start_time"));
                usage.setEndTime(rs.getDate("end_time"));

                usages.add(usage);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return usages;

    }

    public UnitUsage GetUsage(int id)
    {
        String getQuery = "SELECT id, unit_id, start_time, end_time, unit_user_id FROM unit_usage where id =?";

       UnitUsage usage = new UnitUsage();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1,id);


            ResultSet rs = getStatement.executeQuery();
            if(rs.next())
            {
                usage.setUnit(unitDAO.GetUnit(rs.getInt("unit_id")));
                usage.setUnitUser(userDAO.Get(rs.getInt("user_id")));
                usage.setId(rs.getInt("id"));
                usage.setStartTime(rs.getDate("start_time"));
                usage.setEndTime(rs.getDate("end_time"));
                return usage;
            }
            else{return usage;}//no item found

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public List<UnitUsage> GetUsagesForUnit(Unit unit)
    {
        String getQuery = "SELECT id, unit_id, start_time, end_time, unit_user_id FROM unit_usage where unit_id =?";

        UnitUsage usage = new UnitUsage();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1,unit.getId());


            ResultSet rs = getStatement.executeQuery();
            List<UnitUsage> usages = new ArrayList<UnitUsage>();

            if(rs.isBeforeFirst()){//TODO this could be suspect
            while(rs.next())
            {
                //usage.setUnit(unitDAO.GetUnit(rs.getInt("unit_id")));
                usage.setUnit(unit);//this enables not to call back and forth between the DAOs for unit and usage --jjh
                usage.setUnitUser(userDAO.Get(rs.getInt("user_id")));
                usage.setId(rs.getInt("id"));
                usage.setStartTime(rs.getDate("start_time"));
                usage.setEndTime(rs.getDate("end_time"));
                usages.add(usage);
            }
                return usages;
            }
            else{return usages;}

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UnitUsage UpdateUsage(UnitUsage unitUsage) throws Exception {

        String updateQuery = "UPDATE unit_usage unit_id=?, start_time=?, end_time=?, unit_user_id=? where id = ?";
        try {
            PreparedStatement createStatement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);

            createStatement.setInt(1, unitUsage.getUnit().getId());
            createStatement.setDate(2, unitUsage.getStartTime());
            createStatement.setDate(3, unitUsage.getEndTime());
            createStatement.setInt(4, unitUsage.getUnitUser().getID());
            createStatement.setInt(5, unitUsage.getId());


            int affectedRows = createStatement.executeUpdate();

            if(affectedRows == 1)
            {
                unitUsage = GetUsage(unitUsage.getId());

            }
            else
            {
                throw new Exception("UpdateUnit Failed");
            }

            if(unitUsage.getUnitUser().getID()==0)
            {
                unitUsage.setUnitUser(userDAO.Create(unitUsage.getUnitUser()));
            }
            else
            {
                unitUsage.setUnitUser(userDAO.Update(unitUsage.getUnitUser()));
            }

            return unitUsage;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return unitUsage;
    }
}
