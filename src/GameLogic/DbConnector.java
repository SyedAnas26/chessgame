package GameLogic;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;

public class DbConnector
{
	private DbConnector()
	{
	}

	private static Connection makeConnection() throws Exception {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame_database", "root", "");
			return con;
		}
		catch (SQLException e)
		{
			throw new ServletException("Servlet Could not display records.", e);
		}
		catch (ClassNotFoundException e)
		{
			throw new ServletException("JDBC Driver not found.", e);
		}
	}

	public static Object get(String query, DBQueryRunner runner) throws Exception
	{
		Connection connection = makeConnection();
		try
		{
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery(query);
			return runner.execute(rs);
		}
		finally
		{
			connection.close();
		}
	}

	public static void update(String query) throws Exception
	{
		Connection connection = makeConnection();
		try
		{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		}
		finally
		{
			connection.close();
		}
	}

}