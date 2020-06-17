package ConsoleUiLogic;

import java.io.*;

public class TheChessGame {

    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\User\\Desktop\\ChessPgn.txt");
      BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String gamePlay = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            gamePlay =sb.toString();
        }

        gamePlay = gamePlay.replace("\n", " ").replace("\r", "");
        new ConsoleUi(gamePlay);
    }
}


