package WebServerletLogic.Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBclass {
    public Connection con = null;
    public Statement stmt = null;
    public void callDB() throws Exception {
       try {
           Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame_database", "root", "admin123");
           stmt = con.createStatement();
       }
       catch (SQLException e)
       {
           throw new Exception("Servlet Could not display records.", e);
       }

       catch (ClassNotFoundException e)
       {
           throw new Exception("JDBC Driver not found.", e);
       }

    }
}
