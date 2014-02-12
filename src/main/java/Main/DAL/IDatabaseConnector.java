package Main.DAL;

import java.sql.Connection;

/**
 * Created by Jackson on 2/8/14.
 */
public interface IDatabaseConnector {
    Connection connect();

    void diconnect();
}
