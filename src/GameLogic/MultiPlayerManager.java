package GameLogic;

import java.security.SecureRandom;
import java.sql.ResultSet;

public class MultiPlayerManager {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public String createChallenge(long gameId,int uniqueId,String createdByPlayAs,String challengeType) throws Exception {

        String token=randomString(10);
        String sql="insert into challengeTable (ChallengeToken,CreatedByUserID,CreatedByPlayAs,ChallengeType,status,gameID) values ('"+token+"','"+uniqueId+"','"+createdByPlayAs+"','"+challengeType+"','0','"+gameId+"')";
        DbConnector.update(sql);
        return token;
    }
    public String acceptChallenge(String token) throws Exception {
        String resp="invalid";
         String sql="SELECT *  FROM challengeTable WHERE ChallengeToken='"+token+"'";
             resp= (String)DbConnector.get(sql, rs->{
                long gameId;
                String challengeType;
                String status;
                String createdPlayAs;
                String createdUser;
               if(rs.next()) {
                   status = rs.getString("Status");
                   if (status.equals("0") || status.equals("1")) {
                       gameId = rs.getLong("gameID");
                       challengeType = rs.getString("ChallengeType");
                       createdPlayAs = rs.getString("CreatedByPlayAs");
                       createdUser=rs.getString("CreatedByUserID");
                       return "{\"gameId\":\"" + gameId + "\",\"createdByPlayAs\":\"" + createdPlayAs + "\",\"challengeType\":\"" + challengeType + "\",\"createdUser\":\""+createdUser+"\",\"invalid\":\"false\"}>"+status;
                   }
                   else return "{\"invalid\":\"true\"}";
               }
               return "{\"invalid\":\"true\"}";
           });
        System.out.println("Db resp "+resp);
if(!resp.equals("{\"invalid\":\"true\"}")) {
    if (resp.split(">")[1].equals("1")) {
        DbConnector.update("UPDATE challengeTable SET Status='2' WHERE ChallengeToken='" + token + "'");
    } else {
        DbConnector.update("UPDATE challengeTable SET Status='1' WHERE ChallengeToken='" + token + "'");
    }
}
        return resp.split(">")[0];
}
    private String randomString(int len) throws Exception {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        String rand= sb.toString();
        String sql1 = "SELECT *  FROM challengeTable WHERE ChallengeToken='" + rand + "'";
        Boolean alreadyExist=(Boolean)DbConnector.get(sql1, ResultSet::next);
        if(alreadyExist){
            return randomString(10);
        }
        return rand;
    }

    public long checkStatus(String token) throws Exception {
        String status="0";
        String sql = "SELECT *  FROM challengeTable WHERE ChallengeToken='" + token + "'";
        while (status.equals("1")) {
            status = (String) DbConnector.get(sql, rs -> {
                if (rs.next()) {
                    return rs.getString("Status");
                }
                return null;
            });
        }

        DbConnector.update("UPDATE challengeTable SET Status='1' WHERE ChallengeToken='"+token+"'");
        return (long)DbConnector.get(sql, rs->{
            if(rs.next())
            {
                return rs.getLong("gameID");
            }
            return null;
        });
    }
}
