package ConsoleUiLogic;

import GameLogic.PlayPgnFile;

import java.io.BufferedReader;
import java.io.FileReader;

public class TheChessGame {

    public static void main(String[] args) throws Exception {
        String file = "src/ConsoleUiLogic/ChessPgn.txt";
        PlayPgnFile playPgnFile = new PlayPgnFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String gamePlay = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            gamePlay = sb.toString();
        }
        new ConsoleUi(gamePlay);
    }
}



