package Main.DAL;


import Main.Entities.maintenance.MaintenanceStaff;

import java.sql.*;


/**
 * Created by Jackson on 2/8/14.
 */
public class MaintenanceStaffDAO implements IMaintenanceStaffDAO {


    private IDatabaseConnector connector;
    private Connection connection;

    public MaintenanceStaffDAO(IDatabaseConnector connector){
        this.connector = connector;

        connection = connector.connect();
    }

    @Override
    public MaintenanceStaff create(MaintenanceStaff newStaffMember) {
            String createQuery = "INSERT INTO maintenance_staff(" +
                    "first_name, last_name, phone_number, email_address, pay_per_hour," +
                    "is_full_time, hours_per_week)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement insertStatement = connection.prepareStatement(createQuery, java.sql.Statement.RETURN_GENERATED_KEYS);
                insertStatement.setString(1,newStaffMember.getFirstName());
                insertStatement.setString(2,newStaffMember.getLastName());
                insertStatement.setInt(3, newStaffMember.getPhoneNumber());
                insertStatement.setString(4,newStaffMember.getEmailAddress());
                insertStatement.setDouble(5, newStaffMember.getPayPerHour());
                insertStatement.setBoolean(6, newStaffMember.isFullTime());
                insertStatement.setDouble(7, newStaffMember.getHoursPerWeek());

                int affectedRows = insertStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                 ResultSet result = insertStatement.getGeneratedKeys();

                if(result.next())
                {
                    newStaffMember.setID(result.getInt("id"));//todo if we ever rebuild the database reorder tables so id is the first column

                }
                else {
                    throw new SQLException("Creating user failed, no generated key obtained.");
                }

                insertStatement.close();
                result.close();

            }

            catch (SQLException e) {
                e.printStackTrace();

            }

        return newStaffMember;


    }

    @Override
    public void delete(int staffMemberId) {
        String deleteQuery = "DELETE FROM maintenance_staff WHERE id =?";

        try {
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1,staffMemberId);
            deleteStatement.executeQuery();
            deleteStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public MaintenanceStaff get(int id) throws SQLException {
        String getQuery = "Select * FROM maintenance_staff where id = ?";

        PreparedStatement getStatement = connection.prepareStatement(getQuery);
        getStatement.setInt(1, id);


        MaintenanceStaff maintenanceStaff = new MaintenanceStaff();
        try {
            ResultSet rs = getStatement.executeQuery();
            if (rs.next()) {

                maintenanceStaff.setID(rs.getInt("id"));
                maintenanceStaff.setFirstName(rs.getString("first_name"));
                maintenanceStaff.setLastName(rs.getString("last_name"));
                maintenanceStaff.setFullTime(rs.getBoolean("is_full_time"));
                maintenanceStaff.setPayPerHour(rs.getDouble("pay_per_hour"));
                maintenanceStaff.setPhoneNumber(rs.getInt("phone_number"));
                maintenanceStaff.setEmailAddress(rs.getString("email_address"));
                maintenanceStaff.setHoursPerWeek(rs.getInt("hours_per_week"));


            rs.close();
            getStatement.close();}
            else {
                rs.close();
                getStatement.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return maintenanceStaff;

    }

    @Override
    public MaintenanceStaff update(MaintenanceStaff updatedStaffMember) {
        String updateQuery = "UPDATE maintenance_staff SET first_name=?, last_name=?, phone_number=?," +
                " email_address=?,  hours_per_week=?, pay_per_hour=? where id = ?";

        MaintenanceStaff staff = new MaintenanceStaff();

        try {
            PreparedStatement createStatement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);

            createStatement.setString(1, updatedStaffMember.getFirstName());
            createStatement.setString(2, updatedStaffMember.getLastName());
            createStatement.setInt(3, updatedStaffMember.getPhoneNumber());
            createStatement.setString(4, updatedStaffMember.getEmailAddress());
            createStatement.setDouble(5, updatedStaffMember.getHoursPerWeek());
            createStatement.setDouble(6, updatedStaffMember.getPayPerHour());
            createStatement.setInt(7,updatedStaffMember.getID());

            int affectedRows = createStatement.executeUpdate();

            if(affectedRows == 1)
            {

                return get(updatedStaffMember.getID());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedStaffMember;
    }

}
