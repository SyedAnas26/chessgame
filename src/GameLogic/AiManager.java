package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class AiManager {

    public String getAiMove(String FEN,int skill/*long gameId,int moveNo,String difficulty,String gamePgn*/) throws Exception {

        String responseStep;
        //List<String> arr = getMovesArr(gameId);
        StockfishConnector stockfishAI = new StockfishConnector();
        String aiMove = stockfishAI.getAiMove(FEN,skill);
        System.out.println("AIMove : " + aiMove.trim());
        responseStep = "{\"aiMove\":\""+aiMove.trim()+"\"}";
        return responseStep;

    }


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
                DbConnector.update("insert into gamelog (GameType,UserID1,GameFormat,MatchResult,GameId,GameinPgn) values('" + 1 + "','" + uniqueId + "','" + 1 + "','" + gameStatus +"','" + gameId +"','" + gamePgn + "')");
            } else {
                 time=Integer.parseInt(userTime);
                System.out.println("UserMove : " + userMove);
            }
            DbConnector.update("insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,GameStatus,GameTillNow) values('" + gameId + "','" + moveNo + "','" + userMove + "','" + time + "','" + gameStatus + "','" + gamePgn + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getMovesArr(long gameID) throws Exception {

        String sql = "SELECT * FROM gamemoves WHERE GameID='" + gameID + "'";
        List<String> moves = new ArrayList<>();

        DbConnector.get(sql, rs -> {
            while (rs.next())
            {
                moves.add(rs.getString("moves"));
            }
            return moves;
        });

        return moves;
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
}
