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

    public String createAiGame(String uniqueId, String challengeType) throws Exception {
        long gameId=createGameId();
        String id;
        if(uniqueId.equals("0")) {
            long value = gameId;
            int sum = 0;

            while (value != 0) {
                sum += value % 10;
                value = Math.abs(value / 10);
            }
            id = "g" + sum;
        }else {
            id=""+uniqueId;
        }
        DbConnector.update("insert into gamelog (GameType,UserID1,GameFormat,MatchResult,GameId,GameinPgn) values('" + 1 + "','" + id + "','" + challengeType + "','" + 0 +"','" + gameId +"','" + "null" + "')");
        return "{\"gameId\":\""+gameId+"\"}";
    }
}
