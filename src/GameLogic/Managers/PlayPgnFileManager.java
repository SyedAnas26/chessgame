package GameLogic.Managers;

import GameLogic.PureLogic.PgnLogic.PgnConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayPgnFileManager {

    PgnConverter pgnConverter = null;

    public String playGame(String log, int logId, int step, int uniqueId) throws Exception {

        try {
            System.out.println("Step = " + step);
            String gamePlay = getGamePlay(log, logId, uniqueId);
            pgnConverter = new PgnConverter(gamePlay);
            for (int i = 1; i <= step; i++) {
                pgnConverter.conductGameForPgnFile(i);
            }
            String responseStatus = this.pgnConverter.getLastMovementAsStringForJSON();
            int checkStep = (step + step / 2 - (step % 2 == 0 ? 1 : 0)) + 1;

            switch (pgnConverter.gamePlayAsArray[checkStep]) {
                case "1/2-1/2":
                    responseStatus = responseStatus + ",\"checkStatus\":\"Draw\"}";
                    break;

                case "0-1":
                    responseStatus = responseStatus + ",\"checkStatus\":\"Player2\"}";
                    break;

                case "1-0":
                    responseStatus = responseStatus + ",\"checkStatus\":\"Player1\"}";
                    break;

                default:
                    responseStatus = responseStatus + ",\"checkStatus\":\"0\"}";
                    break;

            }
            return responseStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("response Status error");
    }

    public String getGamePlay(String log, int logId, int uniqueId) throws Exception {

        String column;
        String userColumn;
        if (log.equals("gamelog")) {
            column = "idGameLog";
            userColumn = "(UserID1='" + uniqueId + "' OR UserID2='" + uniqueId + "')";
        } else {
            column = "idPgnLog";
            userColumn = "createdBy='" + uniqueId + "'";
        }
        String sql = "select * from " + log + " WHERE " + userColumn + " AND " + column + "='" + logId + "'";
        String string = (String) DbConnector.get(sql, rs -> {
            if (rs.next()) {
                return rs.getString("GameinPgn");
            }
            return null;
        });
        String gamePlay = regexElimination(string);
        gamePlay = gamePlay.replace("\n", " ").replace("\r", "");
        return gamePlay;
    }

    private String regexElimination(String string) {

        String regex = "\\[.*]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String tmp = matcher.replaceAll(" ");
        String regex2 = "\\{.*?}";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(tmp.trim());
        String tmp2 = matcher2.replaceAll(" ");
        String regex3 = " \"\\\\(.*?)\"";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(tmp2.trim());
        String tmp3 = matcher3.replaceAll(" ");

        return tmp3.trim();

    }

    public List<String> getHistoryOfGames(int uniqueId) {
        String sql = "SELECT * FROM gamelog WHERE UserID1='" + uniqueId + "' OR UserID2='" + uniqueId + "'";
        List<String> games = new ArrayList<>();
        try {
            DbConnector.get(sql, rs -> {
                while (rs.next()) {
                    String gameInPgn = rs.getString("GameinPgn");
                    if (!gameInPgn.equals("null")){
                        long millis = rs.getLong("GameId");
                        Date matchDate = new Date(millis);
                        DateFormat est = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        TimeZone estTime = TimeZone.getTimeZone("IST");
                        DateFormat gmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
                        est.setTimeZone(gmtTime);
                        gmt.setTimeZone(estTime);
                        String indFormat = gmt.format(matchDate);
                        games.add(indFormat);
                        String gameLogId = rs.getString("idGameLog");
                        games.add(gameLogId);
                        String userId1=rs.getString("UserID1");
                        String userId2=rs.getString("UserID2");
                        for(int i=0;i<2;i++) {
                            if(i>=1){
                                userId1=userId2;
                            }
                            if (userId1 == null) {
                                games.add("AI");
                            } else {
                                DbConnector.get(
                                        "SELECT * FROM login WHERE UserId='" + userId1 + "'", res -> {
                                            if (res.next()) {
                                                String name = res.getString("fullname");
                                                games.add(name);
                                            }
                                            return null;
                                        });
                            }
                        }
                    }
                }
                return games;
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return games;
    }

    public int storePgn(String gamPlay, int uniqueId, String fileName) throws Exception {
        long createdTime = System.currentTimeMillis();
        String sql = "select * from pgnlog WHERE createdBy='" + uniqueId + "' AND createdTime='" + createdTime + "'";
        DbConnector.update("insert into pgnlog (createdBy,createdTime,fileName,GameinPgn) values('" + uniqueId + "','" + createdTime + "','" + fileName + "','" + gamPlay + "')");
        return (int) DbConnector.get(sql, rs -> {
            if (rs.next()) {
                int i = rs.getInt("idPgnLog");
                System.out.println("logId " + i);
                return i;
            }
            return null;
        });
    }
}
