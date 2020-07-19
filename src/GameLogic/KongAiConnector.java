package GameLogic;

import WebServerletLogic.Servlets.DBclass;
import io.apigee.trireme.core.NodeEnvironment;
import io.apigee.trireme.core.NodeScript;
import io.apigee.trireme.core.ScriptStatus;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KongAiConnector
{

    public String getAIMove(int difficulty, long gameId, int moveNo, List<String> moveArr)
    {
        NodeEnvironment env = new NodeEnvironment();

        try
        {
            // Pass in the script file name, a File pointing to the actual script, and an Object[] containg "argv"
            NodeScript script = null;

            List<String> args = new ArrayList<>();
            args.addAll(Arrays.asList(String.valueOf(difficulty), String.valueOf(gameId), String.valueOf(moveNo)));
            args.addAll(moveArr);

            script = env.createScript("KongAIConnector.js",
                new File("KongAIConnector.js"), args.toArray(new String[0]));
            //new File("src\\WebServletLogic\\html_files\\KongAIConnector.js"), moveArr.toArray(new String[0]));


            // Wait for the script to complete
            ScriptStatus status = script.execute().get();

            return getLatestMove(gameId, moveNo);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private String getLatestMove(long gameID, int moveNo) throws Exception
    {
        String sql = "SELECT * FROM gamemoves WHERE GameID='" + gameID + "' and MoveNo = '" + moveNo + "'";
        System.out.println(sql);

        DBclass dbConnector = new DBclass();
        dbConnector.callDB();
        PreparedStatement pst = dbConnector.con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println(rs.getString("Moves"));
            return rs.getString("Moves");
        }

        System.out.println("Move not found");
        throw new Exception("Move not found!!");
    }

}