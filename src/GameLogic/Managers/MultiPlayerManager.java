package GameLogic.Managers;

import org.json.JSONObject;

import java.security.SecureRandom;
import java.sql.ResultSet;

public class MultiPlayerManager extends ChessManager {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String createChallenge(int uniqueId, String createdByPlayAs, String challengeType) throws Exception {

        long gameId=createGameId();
        String token = randomString(10);
        DbConnector.update("insert into challengetable (ChallengeToken,CreatedByUserID,CreatedByPlayAs,ChallengeType,status,gameID) values ('" + token + "','" + uniqueId + "','" + createdByPlayAs + "','" + challengeType + "','0','" + gameId + "')");
        DbConnector.update("insert into gamelog (GameType,UserID1,GameFormat,MatchResult,GameId,GameinPgn) values('" + 2 + "','" + uniqueId + "','" + challengeType + "','" + 0 + "','" + gameId + "','" + "null" + "')");
        return "{\"gameId\":\""+gameId+"\",\"token\":\""+token+"\"}";

    }

    public String acceptChallenge(String token, int uniqueId) throws Exception {
        String resp = "invalid";
        String sql = "SELECT *  FROM challengetable WHERE ChallengeToken='" + token + "'";
        resp = (String) DbConnector.get(sql, rs -> {
            long gameId;
            String challengeType;
            String status;
            String createdPlayAs;
            String createdUser;
            if (rs.next()) {
                status = rs.getString("Status");
                if (status.equals("0") || status.equals("1")) {
                    gameId = rs.getLong("gameID");
                    challengeType = rs.getString("ChallengeType");
                    createdPlayAs = rs.getString("CreatedByPlayAs");
                    createdUser = rs.getString("CreatedByUserID");
                    return "{\"gameId\":\"" + gameId + "\",\"createdByPlayAs\":\"" + createdPlayAs + "\",\"challengeType\":\"" + challengeType + "\",\"createdUser\":\"" + createdUser + "\",\"invalid\":\"false\",\"status\":\""+status+"\"}";
                } else return "{\"invalid\":\"true\"}";
            }
            return "{\"invalid\":\"true\"}";
        });
        JSONObject obj = new JSONObject(resp);
        if (obj.getString("invalid").equals("false")) {
            if (uniqueId!=(obj.getInt("createdUser"))) {
                DbConnector.update("UPDATE gamelog SET UserID2='" + uniqueId + "' WHERE GameId='" + obj.getString("gameId") + "'");
            }
            DbConnector.update("UPDATE challengetable SET Status='"+(obj.getInt("status")+1)+"' WHERE ChallengeToken='" + token + "'");
        }
        return resp;
    }

    private String randomString(int len) throws Exception {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        String rand = sb.toString();
        String sql1 = "SELECT *  FROM challengetable WHERE ChallengeToken='" + rand + "'";
        Boolean alreadyExist = (Boolean) DbConnector.get(sql1, ResultSet::next);
        if (alreadyExist) {
            return randomString(10);
        }
        return rand;
    }

}
