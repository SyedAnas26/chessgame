package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class AiManager {

    int gameStatus =0;
    int moveNo;
    long gameId;
    long time;
    String responseStep;


    public String getAiMove(String difficulty) throws Exception {

        List<String> arr = getMovesArr(gameId);
        KongAiConnector kongAI = new KongAiConnector();
        String aiMove = kongAI.getAIMove(Integer.parseInt(difficulty), gameId, moveNo, arr);
        System.out.println("AIMove : " + aiMove);
        responseStep = "{\"aiMove\":\""+aiMove+"\"}";
        moveNo++;
        return responseStep;

    }


    public void addMove(String userMove, String status,int uniqueId,String userTime,String gamePgn) throws Exception {
        try {
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
            DbConnector.update("insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,GameStatus) values('" + gameId + "','" + moveNo + "','" + userMove + "','" + time + "','" + gameStatus + "')");
            moveNo++;
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

    public void newGame() {
        gameStatus = 0;
        moveNo = 1;
        try {
            gameId = System.currentTimeMillis();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
