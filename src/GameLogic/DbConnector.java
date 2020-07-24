package GameLogic;

import java.sql.*;

public class DbConnector
{
	private static Connection con = null;

	private DbConnector()
	{
	}

	private static void makeConnection() throws Exception {
		try {

			if(con == null)
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
		PreparedStatement pst = con.prepareStatement(query);
		return pst.executeQuery(query);
	}

	public static void update(String query) throws Exception
	{
		makeConnection();
		Statement stmt = con.createStatement();
		int i=stmt.executeUpdate(query);
		//System.out.println(i+" record(s) updated (User Move)");
	}
}