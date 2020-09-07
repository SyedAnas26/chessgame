package GameLogic.Managers;

public class AiManager extends ChessManager {

    public String getAiMove(String FEN,int skill){

        String responseStep;
        StockfishConnector stockfishAI = new StockfishConnector();
        String aiMove = stockfishAI.getAiMove(FEN,skill);
        System.out.println("AIMove : " + aiMove.trim());
        responseStep = "{\"aiMove\":\""+aiMove.trim()+"\"}";
        return responseStep;

    }

    public String createAiGame(int uniqueId, String challengeType) throws Exception {
        long gameId=createGameId();
        DbConnector.update("insert into gamelog (GameType,UserID1,GameFormat,MatchResult,GameId,GameinPgn) values('" + 1 + "','" + uniqueId + "','" + challengeType + "','" + 0 +"','" + gameId +"','" + "null" + "')");
        return "{\"gameId\":\""+gameId+"\"}";
    }
}
