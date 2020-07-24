package GameLogic;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayPgnFile {

    GameManager manager = null;


    public void playGame(String file, int step, ServletContext context) {


        try {
            System.out.println("Step = "+step);
            String gamePlay = getGamePlay(file, context);
            manager = new GameManager(gamePlay);
            for (int i = 1; i <= step; i++) {
                manager.conductGameForPgnFile(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getResponseStep(int step) throws IOException {

        int totalsteps;
        int add;
        String responseStatus = this.manager.getLastMovementAsStringForJSON();
        try {
            totalsteps = Integer.parseInt(manager.gamePlayAsArray[manager.gamePlayAsArray.length - 3]);
            add = 0;
        } catch (NumberFormatException e) {
            totalsteps = Integer.parseInt(manager.gamePlayAsArray[manager.gamePlayAsArray.length - 4]);
            add = 1;
        }

        if (step == (totalsteps * 2) + 1) {
            if (manager.gamePlayAsArray[((totalsteps * 3) - 1) + add].equals("1/2-1/2")) {
                responseStatus = "{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Draw\"}";

            } else if (manager.gamePlayAsArray[((totalsteps * 3) - 1) + add].equals("0-1")) {
                responseStatus = "{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Player2\"}";

            } else if (manager.gamePlayAsArray[((totalsteps * 3) - 1) + add].equals("1-0")) {
                responseStatus = "{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Player1\"}";
            }
        }
        return responseStatus;
    }


    public String getGamePlay(String file, ServletContext context) throws Exception {
        BufferedReader br;
        if (context == null) {
            br = new BufferedReader(new FileReader(file));

        } else {
            InputStream is = context.getResourceAsStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
        }
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String string = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            string = sb.toString();
        }
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
}
