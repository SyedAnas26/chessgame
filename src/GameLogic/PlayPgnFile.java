package GameLogic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayPgnFile {

    GameManager manager = null;


    public String playGame(String log, int logId, int step,int uniqueId) throws Exception {


        try {
            System.out.println("Step = "+step);
            String gamePlay = getGamePlay(log, logId,uniqueId);
            manager = new GameManager(gamePlay);
            for (int i = 1; i <= step; i++) {
                manager.conductGameForPgnFile(i);
            }
            String responseStatus = this.manager.getLastMovementAsStringForJSON();
            int checkStep=(step + step/2 - (step%2 == 0? 1: 0))+1;

            if (manager.gamePlayAsArray[checkStep].equals("1/2-1/2")) {
                responseStatus =  responseStatus + ",\"checkStatus\":\"Draw\"}";

            } else if (manager.gamePlayAsArray[checkStep].equals("0-1")) {
                responseStatus =  responseStatus + ",\"checkStatus\":\"Player2\"}";

            } else if (manager.gamePlayAsArray[checkStep].equals("1-0")) {
                responseStatus =  responseStatus + ",\"checkStatus\":\"Player1\"}";
            }
            else {
                responseStatus =  responseStatus + ",\"checkStatus\":\"0\"}";
            }
            // }
            return responseStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
       throw new Exception("response Status error");
    }

    //public String getResponseStep(int step) throws IOException {

//        int totalsteps;
//        int add;
//        try {
//
//            totalsteps = Integer.parseInt(manager.gamePlayAsArray[manager.gamePlayAsArray.length - 3]);
//            add = 0;
//        } catch (NumberFormatException e) {
//
//            totalsteps = Integer.parseInt(manager.gamePlayAsArray[manager.gamePlayAsArray.length - 4]);
//            add = 1;
//        }

        //if (step == (totalsteps * 2) + 1) {

   // }


    public String getGamePlay(String log, int logId,int uniqueId) throws Exception {
//        BufferedReader br;
//        if (context == null) {
//            br = new BufferedReader(new FileReader(file));
//
//        } else {
//            InputStream is = context.getResourceAsStream(file);
//            InputStreamReader isr = new InputStreamReader(is);
//            br = new BufferedReader(isr);
//        }
//        String line = br.readLine();
//        StringBuilder sb = new StringBuilder();
//        String string = null;
//        while (line != null) {
//            sb.append(line).append("\n");
//            line = br.readLine();
//            string = sb.toString();
//        }
        String column;
        String userColumn;
        if(log.equals("gamelog")){
            column="idGameLog";
            userColumn="(UserID1='"+uniqueId+"' OR UserID2='"+uniqueId+"')";
        }
        else
        {
            column="idPgnLog";
            userColumn="createdBy='"+uniqueId+"'";
        }
        String sql="select * from "+log+" WHERE "+userColumn+" AND "+column+"='"+logId+"'";
        String string= (String) DbConnector.get(sql,rs->{
            if(rs.next()){
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
        tmp.trim();
        String regex2 = "\\{.*?}";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(tmp);
        String tmp2 = matcher2.replaceAll(" ");
        tmp2.trim();
        String regex3 = " \"\\\\(.*?)\"";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(tmp2);
        String tmp3 = matcher3.replaceAll(" ");
        tmp3.trim();
        return tmp3;

    }
    public List<String> getHistoryOfGames(int uniqueId){
        String sql = "SELECT * FROM gamelog WHERE UserID1='" + uniqueId + "' OR UserID2='"+uniqueId+"'";
        List<String> games = new ArrayList<>();
        try {
            DbConnector.get(sql, rs -> {
                while (rs.next())
                {
                    long millis=rs.getLong("GameId");
                    Date matchDate = new Date(millis);
                    DateFormat est = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    TimeZone estTime = TimeZone.getTimeZone("IST");
                    DateFormat gmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    TimeZone gmtTime = TimeZone.getTimeZone("GMT");
                    est.setTimeZone(gmtTime);
                    gmt.setTimeZone(estTime);
                    String indFormat= gmt.format(matchDate);
                    games.add(indFormat);
                    String gameLogid=rs.getString("idGameLog");
                    games.add(gameLogid);
                }
                return games;
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return games;
    }
    public void setWatchHistoryFile(String tomPath,String idGameLog) throws IOException {
        String sql = "SELECT * FROM gamelog WHERE idGameLog='" + idGameLog + "'";
        String gamePlayHistory=null;
        try {
            gamePlayHistory = (String) DbConnector.get(sql, rs -> {
                if (rs.next()) {
                    return rs.getString("GameinPgn");
                }
                System.out.println("pgn not found");
                throw new Exception("pgn not found!!");
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        File folder = new File(tomPath + "/FileUploads");
//        FileUtils.deleteDirectory(folder);
//        createFolder(folder);
        File file =new File(tomPath + "/FileUploads/watchHistory"+idGameLog+".txt");
        PrintWriter myWriter = new PrintWriter(file);
        myWriter.write(""+gamePlayHistory);
        myWriter.close();
    }

    public int storePgn(String gamPlay,int uniqueId,String fileName) throws Exception {
        long createdTime=System.currentTimeMillis();
        String sql="select * from pgnlog WHERE createdBy='"+uniqueId+"' AND createdTime='"+createdTime+"'";
        DbConnector.update("insert into pgnlog (createdBy,createdTime,fileName,GameinPgn) values('"+uniqueId+"','"+createdTime+"','"+fileName+"','"+gamPlay+"')");
        return (int)DbConnector.get(sql,rs->{
            if(rs.next()){
                int i=rs.getInt("idPgnLog");
                System.out.println("logId "+i);
                return i;
            }
            return null;
        });
    }
    private void createFolder(File folder) {
        boolean bool = folder.mkdir();
        if (bool) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }
    }

}
