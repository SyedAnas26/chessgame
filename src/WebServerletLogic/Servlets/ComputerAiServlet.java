package WebServerletLogic.Servlets;

import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeException;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class ComputerAiServlet {
    public static void main(String[] args) throws NodeException, ExecutionException, InterruptedException, SQLException {

// The NodeEnvironment controls the environment for many scripts
        DBclass db=new DBclass();
        String sql = "SELECT Moves FROM chessgame_database.gamemoves";
        PreparedStatement pst = db.con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmetadata = rs.getMetaData();
        int columns = rsmetadata.getColumnCount();
        String[] Moves = new String[columns];
        int j=0;
        while (rs.next()) {
            Moves[j] = rs.getString("Moves");
            System.out.println("moves"+Moves[j]);
            j++;
        }

        NodeEnvironment env = new NodeEnvironment();
// Pass in the script file name, a File pointing to the actual script, and an Object[] containg "argv"
        NodeScript script = env.createScript("myfirst.js",
                new File("src\\WebServerletLogic\\OpenChess\\html_files\\ComputerAi.js"),Moves);

// Wait for the script to complete
        ScriptStatus status = script.execute().get();

// Check the exit code
        System.exit(status.getExitCode());
    }
}