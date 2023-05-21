
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DOUVIC
 */
public class MyConnection {
    private static final String username = "root";
    private static final String password = "vealeto";
    private static final String dataConn = "jdbc:mysql://localhost:3306/Gestion_emploi_du_temps";
    private static Connection con =null;
    
    public static Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dataConn,username,password);
 
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
        return con;
    }
    
}
