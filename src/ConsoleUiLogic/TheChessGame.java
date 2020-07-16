package ConsoleUiLogic;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheChessGame {

    public static void main(String[] args) throws Exception {

        File file = new File("src/ConsoleUiLogic/ChessPgn.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String string = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            string =sb.toString();
        }
        String gamePlay=regexElimination(string);
        gamePlay = gamePlay.replace("\n", " ").replace("\r", "");
        new ConsoleUi(gamePlay);
    }

    static String regexElimination(String string) {

        String regex = "\\[.*]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String tmp = matcher.replaceAll(" ");
        tmp.trim ();
        String regex2 = "\\{.*?}";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(tmp);
        String tmp2 = matcher2.replaceAll(" ");
        tmp2.trim ();
        String regex3 = "\\((.*?)\\)";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(tmp2);
        String tmp3 = matcher3.replaceAll(" ");
        tmp3.trim ();
        return tmp3;
    }
}



