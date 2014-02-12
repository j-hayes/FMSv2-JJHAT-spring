package Main.DAL;

import Main.Entities.maintenance.MaintenanceRequest;
import Main.Entities.usage.UnitUser;

import java.sql.*;


/**
 * Created by Jackson on 2/10/14.
 */


public class UserDAO implements IUserDAO {
    private IDatabaseConnector connector;
    private Connection connection;

    public UserDAO(IDatabaseConnector connector) {
        this.connector = connector;
        connection = connector.connect();

    }

    @Override
    public UnitUser Create(UnitUser newUnitUser) {
        String createQuery = "INSERT INTO unit_user(first_name, last_name, phone_number, email_address," +
                " credit_card, company_name) VALUES (?, ?, ?, ?, ?, ?)";
        try {

                PreparedStatement insertStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);

                insertStatement.setString(1,newUnitUser.getFirstName());
                insertStatement.setString(2,newUnitUser.getLastName());
                insertStatement.setInt(3, newUnitUser.getPhoneNumber());
                insertStatement.setString(4,newUnitUser.getEmailAddress());
                insertStatement.setString(5, newUnitUser.getCreditCard());
                insertStatement.setString(6, newUnitUser.getCompanyName());

                int affectedRows = insertStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                ResultSet result = insertStatement.getGeneratedKeys();

                if(result.next())
                {
                    newUnitUser.setID(result.getInt("id"));

                }
                else {
                    throw new SQLException("Creating user failed, no generated key obtained.");
                }

                insertStatement.close();
                result.close();
                return newUnitUser;

            }

            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }



    }

    @Override
    public void Delete(int unitUserId) {

        String deleteQuery = "DELETE FROM unit_user WHERE id = ?";



        UnitUser user = new UnitUser();
        try {
            PreparedStatement getStatement = connection.prepareStatement(deleteQuery);
            getStatement.setInt(1,unitUserId);

            getStatement.executeUpdate();

            getStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();

            //todo add throw??
        }
    }

    @Override
    public UnitUser Get(int user_id) {
        String getQuery = "SELECT first_name, last_name, phone_number, id," +
                " email_address, credit_card, company_name FROM unit_user where id =?";

        UnitUser user = new UnitUser();
        try {
            PreparedStatement getStatement = connection.prepareStatement(getQuery);
            getStatement.setInt(1,user_id);

            ResultSet rs = getStatement.executeQuery();
            if(rs.next())
            {
                user.setID(rs.getInt("id"));
                user.setLastName(rs.getString("last_name"));
                user.setFirstName(rs.getString("first_name"));
                user.setPhoneNumber(rs.getInt("phone_number"));
                user.setEmailAddress(rs.getString("email_address"));
                user.setCompanyName(rs.getString("company_name"));
                user.setCreditCard(rs.getString("credit_card"));
                return user;
            }
            else{return null;}//no item found

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public UnitUser Update(UnitUser unitUser) throws Exception {
        String updateQuery = "UPDATE unit_user SET first_name=?, last_name=?," +
                " phone_number=?, email_address=?, credit_card=?, company_name=? WHERE id = ?";

        try {
            PreparedStatement createStatement = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);

            createStatement.setString(1, unitUser.getFirstName());
            createStatement.setString(2, unitUser.getLastName());
            createStatement.setInt(3, unitUser.getPhoneNumber());
            createStatement.setString(4, unitUser.getEmailAddress());
            createStatement.setString(5, unitUser.getCreditCard());
            createStatement.setString(6, unitUser.getCompanyName());
            createStatement.setInt(7, unitUser.getID());


            int affectedRows = createStatement.executeUpdate();

            if(affectedRows == 1)
            {
                unitUser = Get(unitUser.getID());
                return unitUser;
            }
            else
            {
                throw new Exception("UpdateUnit Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return unitUser;
    }
}
