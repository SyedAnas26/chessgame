package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class AiManager {
    PgnGenerator pg = null;
    GameManager gameManager = null;
    String userMove;
    int gameStatus =0;
    int moveNo;
    long gameId;
    int time = 0;
    String responseStep;


    public String getAiMove(String difficulty) throws Exception {

        List<String> arr = getMovesArr(gameId);
        KongAiConnector kongAI = new KongAiConnector();
        String move = kongAI.getAIMove(Integer.parseInt(difficulty), gameId, moveNo, arr);
        System.out.println("AIMove : " + move);
        gameManager.conductGame(move);
        responseStep = gameManager.getLastMovementAsStringForJSON();
        pg.convertToPgn(gameManager.getLastFromPosAsString(), gameManager.getLastToPosAsString());
        moveNo++;
        return responseStep;

    }


    public void addMove(String fromPos, String toPos, String status) throws Exception {
        try {
            if (status!=null) {
                gameStatus =Integer.parseInt(status);
                System.out.println(gameStatus);
                userMove = "Game Ended";
            } else {
                userMove = pg.convertToPgn(fromPos, toPos);
                System.out.println("UserMove : " + userMove);
                gameManager.conductGame(userMove);
            }
            DbConnector.update("insert into gamemoves (GameID,MoveNo,Moves,TimeTaken,DrawClaimedStatus) values('" + gameId + "','" + moveNo + "','" + userMove + "','" + time + "','" + gameStatus + "')");
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
        pg = new PgnGenerator();
        gameManager = new GameManager();
        gameStatus = 0;
        moveNo = 1;
        try {
            gameId = System.currentTimeMillis();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
