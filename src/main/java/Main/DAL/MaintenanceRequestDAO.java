package Main.DAL;


import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.MaintenanceRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MaintenanceRequestDAO implements IMaintenanceRequestDAO {

    //TODO:This is where we execute our sql statements.

    private IDatabaseConnector connector;
    private Connection connection;
    private IFacilityDAO facilityDAO;
    private IMaintenanceStaffDAO maintenanceStaffDAO;

    public MaintenanceRequestDAO(IDatabaseConnector connector, IFacilityDAO facilityDAO, IMaintenanceStaffDAO maintenanceStaffDAO) {
        this.connector = connector;
        this.facilityDAO = facilityDAO;
        this.maintenanceStaffDAO = maintenanceStaffDAO;

        connection = connector.connect();
    }

    @Override
    public MaintenanceRequest create(MaintenanceRequest request)
    {

        String createQuery = " INSERT INTO maintenance_request(request, date_requested, completion_date," +
                         "unit_id, hours_to_complete) VALUES (?, ?, ?, ?, ?);";


        try {
            PreparedStatement createStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            createStatement.setString(1, request.getRequest());
            createStatement.setDate(2, request.getDateRequested());
            createStatement.setDate(3, null);
           // createStatement.setInt(4, null);//no one assigned on create
            createStatement.setInt(4, request.getUnit().getId());
            createStatement.setInt(5, request.getHoursToComplete());



           int affectedRows = createStatement.executeUpdate();//

            ResultSet rs = createStatement.getGeneratedKeys();
           if(rs.next())
           {
              request.setID(rs.getInt("id"));
                rs.close();
               createStatement.close();
              return request;
           }
        } catch (SQLException e) {
           e.printStackTrace();

        }
        return request;
    }

	@Override
    public MaintenanceRequest update(MaintenanceRequest request) throws Exception {
        String updateQuery = "UPDATE maintenance_request SET request=?, date_requested=?," +
                " completion_date=?, staff_member_assigned_id=?, unit_id=?, hours_to_complete = ? WHERE id = ?";

        System.out.println("StaffMember Assigned ID in DAO"+ request.getStaffMemberAssigned().getID());


        try {
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, request.getRequest());
            updateStatement.setDate(2, request.getDateRequested());
            updateStatement.setDate(3, request.getCompletionDate());
            updateStatement.setInt(4, request.getStaffMemberAssigned().getID());
            updateStatement.setInt(5, request.getUnit().getId());
            updateStatement.setInt(6, request.getHoursToComplete());
            updateStatement.setInt(7, request.getID());


            int affectedRows = updateStatement.executeUpdate();

           if(affectedRows == 1)
           {
               request = get(request.getID());
               return request;
           }
           
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return request;
    }

	@Override
    public void delete(int ID) {
        String deleteQuery = "DELETE FROM maintenance_request WHERE id = ?";



        MaintenanceRequest request = new MaintenanceRequest();
        try {
            PreparedStatement getStatement = connection.prepareStatement(deleteQuery);
            getStatement.setInt(1,ID);

            int rowsAffected = getStatement.executeUpdate();

            getStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();

            //todo add throw??
        }



	}
    /*
    * @return maintenance request
    * null if no records are found
    * */
	@Override
    public MaintenanceRequest get(int ID)
    {
        String getQuery = "SELECT * FROM maintenance_request where id =? ";

        MaintenanceRequest request = new MaintenanceRequest();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1,ID);


            ResultSet rs = getStatement.executeQuery();
            if(rs.next())
            {
                request.setDateRequested(rs.getDate("date_requested"));
                request.setID(rs.getInt("id"));
                request.setRequest(rs.getString("request"));
                request.setUnit(facilityDAO.getUnit(rs.getInt("unit_id")));
                request.setStaffMemberAssigned(maintenanceStaffDAO.get(rs.getInt("staff_member_assigned_id")));
                request.setCompletionDate(rs.getDate("completion_date"));
                request.setHoursToComplete(rs.getInt("hours_to_complete"));
                rs.close();
                getStatement.close();
                return request;
            }
            else{return request;}//no item found

        } catch (SQLException e) {
            e.printStackTrace();
            return request;
        }

	}

    @Override
    public List<MaintenanceRequest> getAllForUnit(Unit unit)
    {
        String getQuery = "SELECT * FROM maintenance_request where unit_id =? ";

        List<MaintenanceRequest> maintenanceRequests = new ArrayList<MaintenanceRequest>();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1,unit.getId());


            ResultSet rs = getStatement.executeQuery();
            while(rs.next())
            {

                MaintenanceRequest request = new MaintenanceRequest();

                request.setDateRequested(rs.getDate("date_requested"));
                request.setID(rs.getInt("id"));
                request.setRequest(rs.getString("request"));
                request.setUnit(facilityDAO.getUnit(rs.getInt("unit_id")));
                request.setStaffMemberAssigned(maintenanceStaffDAO.get(rs.getInt("staff_member_assigned_id")));
                request.setCompletionDate(rs.getDate("completion_date"));
                request.setHoursToComplete(rs.getInt("hours_to_complete"));


                maintenanceRequests.add(request);

            }
            rs.close();
            getStatement.close();
            return maintenanceRequests;

        }
            catch (SQLException e) {
            e.printStackTrace();
            }

            return maintenanceRequests;
    }

}
