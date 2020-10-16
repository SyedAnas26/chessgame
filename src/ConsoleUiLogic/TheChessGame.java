package ConsoleUiLogic;

import GameLogic.Managers.StockfishConnector;

public class TheChessGame {
    public static void main(String[] args) throws Exception {
//        String file = "src/ConsoleUiLogic/ChessPgn.txt";
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String line = br.readLine();
//        StringBuilder sb = new StringBuilder();
//        String gamePlay = null;
//        while (line != null) {
//            sb.append(line).append("\n");
//            line = br.readLine();
//            gamePlay = sb.toString();
//        }
//        new ConsoleUi(gamePlay);
        StockfishConnector st=new StockfishConnector();
        System.out.println(st.getEvalScore("rnbqkbnr/ppp1pppp/8/3p4/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 2"));

    }
}


