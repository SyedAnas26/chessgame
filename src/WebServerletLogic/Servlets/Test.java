package WebServerletLogic.Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test
{
	private static Connection con = null;

	private Test()
	{
	}

	private static void makeConnection() throws Exception {
		try {

			if(con != null)
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame_database", "root", "admin123");
			}
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








	




	public static ResultSet get(String query) throws Exception
	{
		makeConnection();
		Statement stmt = con.createStatement();
		return stmt.executeQuery(query);
	}

	public static int update(String query) throws Exception
	{
		makeConnection();
		Statement stmt = con.createStatement();
		return stmt.executeUpdate(query);
	}
}