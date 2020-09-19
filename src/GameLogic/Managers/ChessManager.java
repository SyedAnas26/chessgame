package GameLogic.Managers;

public class ChessManager {

    public void addMove(long gameId, int moveNo, String userMove, String status, int uniqueId, String userTime, String gamePgn) throws Exception {

        try {
            long time = 0;
            int gameStatus = 0;
            if (status != null) {
                gameStatus = Integer.parseInt(status);
                if (gameStatus == 1) {
                    gamePgn = gamePgn + " 1-0";
                } else if (gameStatus == 2) {
                    gamePgn = gamePgn + " 0-1";
                } else if (gameStatus == 3) {
                    gamePgn = gamePgn + " 1/2-1/2";
                }
                DbConnector.update("UPDATE gamelog SET MatchResult='" + gameStatus + "',GameinPgn='" + gamePgn + "' WHERE GameId='" + gameId + "'");
                DbConnector.update("UPDATE gamemoves SET GameStatus='" + gameStatus + "',GameTillNow='" + gamePgn + "' WHERE GameID='" + gameId + "'");

            } else {
                time = Integer.parseInt(userTime);
                System.out.println("UserMove : " + userMove);
                DbConnector.update("insert into gamemoves (GameID,MoveNo,Moves,MovedBy,TimeTaken,GameStatus,GameTillNow) values('" + gameId + "','" + moveNo + "','" + userMove + "','" + uniqueId + "','" + time + "','" + gameStatus + "','" + gamePgn + "')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMaxMoveOf(long gameId, String user, String addQuery) throws Exception {
        String sql;
        if (user.equals("*")) {
            sql = "SELECT MAX(MoveNo) AS moveNo FROM gamemoves WHERE GameID='" + gameId + "'";

        } else {
            sql = "SELECT MAX(MoveNo) AS moveNo FROM gamemoves WHERE GameID='" + gameId + "' AND " + addQuery + " MovedBy='" + user + "'";
        }
        return (int) DbConnector.get(sql, rs -> {
            if (rs.next()) {
                return rs.getInt("moveNo");
            }
            return 0;
        });
    }

    public Object getValue(long gameId, int moveNo, String columnLabel) throws Exception {
        String sql2 = "SELECT * FROM gamemoves WHERE GameID='" + gameId + "' AND MoveNo='" + moveNo + "'";
        return DbConnector.get(sql2, rs -> {
            if (rs.next()) {
                return rs.getString(columnLabel);
            }
            return null;
        });
    }

    public String getGamePosition(long gameId, int uniqueId) throws Exception {

        int lastMoveNo = getMaxMoveOf(gameId, "*", "");
        System.out.println("lastmove No: " + lastMoveNo);
        String gameTillNow = (String) getValue(gameId, lastMoveNo, "GameTillNow");
        System.out.println("gameTillnow " + gameTillNow);
        if (gameTillNow != null) {
            int player1MoveNo = getMaxMoveOf(gameId, "" + uniqueId, "");
            int player2MoveNo = getMaxMoveOf(gameId, "" + uniqueId, "NOT");
            String player1RemainingTime = (String) getValue(gameId, player1MoveNo, "TimeTaken");
            String player2RemainingTime = (String) getValue(gameId, player2MoveNo, "TimeTaken");
            return "{\"gamePosition\":\"" + gameTillNow + "\",\"player1Time\":\"" + player1RemainingTime + "\",\"player2Time\":\"" + player2RemainingTime + "\"}";
        }
        return null;
    }

    public String opponentRemainingTime(int uniqueId, long gameId, int moveNo) throws Exception {
        int oppMoveNo = moveNo - 1;
        System.out.println("moveNo " + moveNo);
        String sql = "SELECT * FROM gamemoves WHERE MoveNo='" + oppMoveNo + "' AND GameID='" + gameId + "'";
        int remainingTime = (int) DbConnector.get(sql, rs -> {
            if (rs.next()) {
                return rs.getInt("TimeTaken");
            }
            return 0;
        });
        System.out.println("remTime " + remainingTime);
        return "{\"opponentRemainingTime\":\"" + remainingTime + "\"";
    }

    public long createGameId() throws Exception {
        long gameId = 0;
        try {
            gameId = System.currentTimeMillis();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
//        String sql = "Select GameId from gamelog where GameId ='" + gameId + "'";
//        boolean alreadyExists = (boolean) DbConnector.get(sql, ResultSet::next);
//        if (alreadyExists) {
//            return createGameId();
//        }
        return gameId;
    }

    public String getNextMove(String gameId, String moveNo) throws Exception {

        String sql = "SELECT Moves FROM gamemoves WHERE GameID='" + gameId + "' AND MoveNo='" + moveNo + "'";
        String move = null;
        while (true) {
            assert false;
            if (move!=null) break;
            move = (String) DbConnector.get(sql, rs -> {
                if (rs.next()) {
                    return rs.getString("Moves");
                }
                return null;
            });
        }
        System.out.println("Move"+move);
        return move;
    }
}

