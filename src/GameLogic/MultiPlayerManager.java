package GameLogic;

import java.security.SecureRandom;

public class MultiPlayerManager {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public String createChallenge(long gameId,int uniqueId,String createdByPlayAs,String challengeType) throws Exception {

        String token=randomString(10);
        String sql="insert into challengeTable (ChallengeToken,CreatedByUserID,CreatedByPlayAs,ChallengeType,status,gameID) values ('"+token+"','"+uniqueId+"','"+createdByPlayAs+"','"+challengeType+"','0','"+gameId+"')";
        DbConnector.update(sql);
        return token;
    }
public long acceptChallenge(String token) throws Exception {
        String sql="SELECT *  FROM challengeTable WHERE ChallengeToken='"+token+"'";
        String status=(String)DbConnector.get(sql,rs->{
            if(rs.next())
            {
                return rs.getString("Status");
            }
            return null;
        });
        if(status.equals("0")){
            DbConnector.update("UPDATE challengeTable SET Status='1' WHERE ChallengeToken='"+token+"'");
            return (long)DbConnector.get(sql, rs->{
               if(rs.next())
               {
                   return rs.getLong("gameID");
               }
               return null;
           });
        }
        else {
            return 0;
        }

}
    private String randomString(int len){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
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
