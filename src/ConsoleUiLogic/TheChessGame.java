package ConsoleUiLogic;

import GameLogic.PlayPgnFile;

public class TheChessGame {

    public static void main(String[] args) throws Exception {
        String file = "src/ConsoleUiLogic/ChessPgn.txt";
        PlayPgnFile playPgnFile = new PlayPgnFile();
        String gamePlay = playPgnFile.getGamePlay(file, null);
        new ConsoleUi(gamePlay);
    }
}



