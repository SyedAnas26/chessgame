package GameLogic.Managers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class  ChessManager {

    public void addMove(long gameId,int moveNo,String userMove, String status,int uniqueId,String userTime,String gamePgn) throws Exception {

        try {
            long time=0;
            int gameStatus =0;
            if (status!=null) {
                gameStatus =Integer.parseInt(status);
                if(gameStatus==1){
                    gamePgn=gamePgn+" 1-0";
                }
                else if(gameStatus==2){
                    gamePgn=gamePgn+" 0-1";
                }
                else if(gameStatus==3){
                    gamePgn=gamePgn+ " 1/2-1/2";
                }
                userMove = "Game Ended";
                DbConnector.update("UPDATE gamelog SET MatchResult='"+gameStatus+"',GameinPgn='" + gamePgn + "' WHERE GameId='"+gameId+"'");
            } else {
                time=Integer.parseInt(userTime);
                System.out.println("UserMove : " + userMove);
            }
            DbConnector.update("insert into gamemoves (GameID,MoveNo,Moves,MovedBy,TimeTaken,GameStatus,GameTillNow) values('" + gameId + "','" + moveNo + "','" + userMove + "','" + uniqueId + "','"+ time + "','" + gameStatus + "','" + gamePgn + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGamePosition(long gameId,int uniqueId) throws Exception {
        String  sql="SELECT MAX(MoveNo) AS moveNo FROM gamemoves WHERE GameID='"+gameId+"'" ;
        int moveno= (int)DbConnector.get(sql , rs->{
            if(rs.next()){
                return rs.getInt("moveNo");
            }
            return null;
        });

        String  sql2="SELECT * FROM gamemoves WHERE GameID='"+gameId+"' AND MoveNo='"+moveno+"'";
        return  (String) DbConnector.get(sql2, rs->{
            if(rs.next()){
                return rs.getString("GameTillNow");
            }
            return null;
        });
    }

    public String getTotalTime(int uniqueId, long gameId) throws Exception {
        String sql="SELECT * FROM gamemoves WHERE NOT MovedBy='"+uniqueId+"' AND GameID='"+gameId+"'";
        List<Integer> timeTaken=new ArrayList<>();
        int totalTime=0;
        int totalMoves=0;
        DbConnector.get(sql,rs->{
            while (rs.next()){
                timeTaken.add(rs.getInt("TimeTaken"));
            }
            return timeTaken;
        });
        totalMoves=timeTaken.size();
        return "{\"totalMoves\":\""+totalMoves+"\",\"totalTime\":\""+totalTime+"\"}";
    }

    public long createGameId() throws Exception {
        long gameId=0;
        try {
            gameId = System.currentTimeMillis();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        String sql= "Select GameId from gamelog where GameId ='"+gameId+"'";
       boolean alreadyExists=(boolean)DbConnector.get(sql, ResultSet::next);
       if(alreadyExists){
           return createGameId();
       }
       return gameId;
    }
}
