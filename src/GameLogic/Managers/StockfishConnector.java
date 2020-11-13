package GameLogic.Managers;

import GameLogic.PureLogic.Stockfish;

public class StockfishConnector {


    public String getAiMove(String FEN, int skill) {
        Stockfish client = new Stockfish();
        String depth;
        // String FEN = "N7/P3pk1p/3p2p1/r4p2/8/4b2B/4P1KP/R7 b - - 1 34";
        boolean startingEngine = client.startEngine();
        if (!startingEngine) {
            System.out.println("Oops! Stockfish Engine not starting ! ..");
        }
        if (skill <= 0) {
            skill = 1;
        }
        if (skill > 20) {
            skill = 20;
        }
        if (skill < 5) {
            depth = "1";
        } else if (skill < 10) {
            depth = "2";
        } else if (skill < 15) {
            depth = "3";
        } else {
            depth = "";
        }
        client.sendCommand("uci");
        client.getOutput(0);
        String aiMove = client.getBestMove(FEN, depth, skill);
        client.stopEngine();
        return aiMove;
    }

    public String getEvalScore(String FEN) {
        Stockfish client = new Stockfish();
        System.out.println("fen " +FEN);
        if (client.startEngine()) {
            System.out.println("Engine has started..");
        } else {
            System.out.println("Oops! Something went wrong..");
        }
        client.sendCommand("uci");
        client.getOutput(0);
        String evalScore = "" + client.getEvalScore(FEN, 200);
        System.out.println("Score "+evalScore);
        client.stopEngine();
        return evalScore;

    }
}

