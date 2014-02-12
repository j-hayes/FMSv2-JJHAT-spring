package Main.DAL;

/**
 * User: alexthornburg
 * Date: 2/4/14
 * Time: 7:07 PM
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector implements IDatabaseConnector {
    private static Connection Connection = null;

    public DatabaseConnector(){
    try {

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://ec2-54-204-27-119.compute-1.amazonaws.com:5432/d8bdfjiqldja8v?user=kzprhsuuhaqamu&password=CtkuwQTL2G3Mpv_cajVqzH_tNh&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        Connection = DriverManager.getConnection(url);

    } catch (ClassNotFoundException e) {

        System.out.println("No Driver");
        e.printStackTrace();


    }catch (SQLException e) {

        System.out.println("Sql problem");
        e.printStackTrace();
    }


    }


    @Override
    public Connection connect(){

     return Connection;

    }

    @Override
    public void diconnect() {
        try {
            this.Connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
