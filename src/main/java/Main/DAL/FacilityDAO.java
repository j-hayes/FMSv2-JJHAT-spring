package Main.DAL;

import Main.Entities.Facility.Facility;
import Main.Entities.Facility.Unit;
import Main.Entities.usage.UnitUsage;
import Main.Entities.usage.UnitUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityDAO implements IFacilityDAO {

    private IDatabaseConnector Connector;
    private Connection connection;
    private IUnitDAO unitDAO;

    public FacilityDAO(IDatabaseConnector connector, IUnitDAO unitDAO) {
       Connector = connector;
        this.unitDAO = unitDAO;
        connection = connector.connect();
    }

    @Override
    public Facility create(Facility facility) {
        String createQuery = "INSERT INTO facility (name,capacity,building_number) VALUES (?,?,?)";

        try {

            PreparedStatement insertStatement = connection.prepareStatement(createQuery, java.sql.Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, facility.getName());
            insertStatement.setInt(2, facility.getCapacity());
            insertStatement.setInt(3, facility.getBuildingNumber());


            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet result = insertStatement.getGeneratedKeys();

            if(result.next())
            {
                facility.setID(result.getInt("id"));


                List<Unit> units  = facility.getUnits();

                for(Unit u:units){
                    u.setFacilityId(facility.getID());
                }

                unitDAO.CreateUnit(units);
            }
            else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }

            insertStatement.close();
            result.close();


            return facility;

        }

        catch (Exception e) {
            e.printStackTrace();
            return facility;
        }

    }

    @Override
    public Facility update(Facility facility) {
        for(Unit unit:facility.getUnits()){
            Unit check = getUnit(unit.getId());
            if(check.getId()==0){
                try{

                    try {
                        unitDAO.CreateUnit(unit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for(UnitUsage usage:unit.getUsages()){
                        usage = unitDAO.CreateUsage(usage);//also creates any new users
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }
        try {
            connection.createStatement().executeUpdate("UPDATE facility" +
                    " SET (name,capacity,building_number)"+
                    "= ('"+facility.getName()+"','"+facility.getCapacity()+"','"+facility.getBuildingNumber()+"')" +
                    "WHERE id = "+facility.getID());

            for(Unit unit:facility.getUnits()){
                unit = unitDAO.UpdateUnit(unit);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return facility;
    }

    @Override
    public void delete(Facility facility) {


        String deleteQuery = "DELETE FROM facility WHERE id = ?";

        try {
            PreparedStatement getStatement = connection.prepareStatement(deleteQuery);
            getStatement.setInt(1,facility.getID());

            getStatement.executeUpdate();

            getStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();

            //todo add throw??
        }


	}

	@Override
    public Facility get(int id) {
        Facility facility = new Facility();
        try {
            ResultSet rs = connection.createStatement().executeQuery("Select*FROM facility where id = '"+id+"'");
            while (rs.next()) {
                facility.setID(rs.getInt("id"));
                facility.setName(rs.getString("name"));
                facility.setBuildingNumber(rs.getInt("building_number"));
                facility.setCapacity(rs.getInt("capacity"));
                facility.setUnits(getUnitsForFacility(rs.getInt("id")));
            }

            facility.setUnits(unitDAO.GetUnitForFacility(facility.getID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facility;
    }

    private List<Unit> getUnitsForFacility(int facilityId)
    {
        List<Unit> units = new ArrayList<Unit>();


        try {
            return unitDAO.GetUnitForFacility(facilityId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;


    }

    /*
    public List<UnitUsage> getUsages(int unitId){
        List<UnitUsage> usages = new ArrayList<UnitUsage>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("Select*FROM unit_usage where unit_id = '"+unitId+"'");
            while (rs.next()) {
                UnitUsage usage = new UnitUsage();
                usage.setId(rs.getInt("id"));
                //usage.setUnit(getUnit(unitId));
                usage.setStartTime(rs.getDate("start_time"));
                usage.setEndTime(rs.getDate("end_time"));
                usage.setUnit(unitDAO.GetUnit(rs.getInt("unit_id")));
                usage.setUnitUser(usrs.getInt("unit_user_id"));
                usages.add(usage);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usages;
    }*/



    @Override
    public List<Facility> getAll() {
        List<Facility> facilities = new ArrayList<Facility>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("Select * FROM facility");
            while (rs.next()) {
                Facility facility = new Facility();
                facility.setID(rs.getInt("id"));
                facility.setName(rs.getString("name"));
                facility.setBuildingNumber(rs.getInt("building_number"));
                facility.setCapacity(rs.getInt("capacity"));
                facility.setUnits(getUnitsForFacility(rs.getInt("id")));

                facility.setUnits(unitDAO.GetUnitForFacility(facility.getID()));

                facilities.add(facility);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilities;
    }

    @Override
    public Unit getUnit(int unitId){
        try {
            return unitDAO.GetUnit(unitId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Unit();
    }
}
