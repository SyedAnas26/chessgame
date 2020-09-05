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

//        Stockfish client = new Stockfish();
//        String FEN ="N7/P3pk1p/3p2p1/r4p2/8/4b2B/4P1KP/R7 b - - 1 34";
//
//// initialize and connect to engine
//        if (client.startEngine()) {
//            System.out.println("Engine has started..");
//        } else {
//            System.out.println("Oops! Something went wrong..");
//        }
//
//// send commands manually
//        client.sendCommand("uci");
//
//// receive output dump
//     //   System.out.println(client.getOutput(0));
//
//// get the best move for a position with a given think time
//        System.out.println("Best move :" + client.getBestMove(FEN,"3",10));
//
//// get all the legal moves from a given position
//       // System.out.println("Legal moves : " + client.getLegalMoves(FEN));
//
//// draw board from a given position
//       // System.out.println("Board state :");
//        //client.drawBoard(FEN);
//
//// get the evaluation score of current position
//   //    System.out.println("Eval score : " + client.getEvalScore(FEN, 2000));
//
//// stop the engine
//        System.out.println("Stopping engine..");
//        client.stopEngine();
//    }

//        Stockfish client = new Stockfish();
//        String FEN = "8/6pk/8/1R5p/3K3P/8/6r1/8 b - - 0 42";
//
//// initialize and connect to engine
//        if (client.startEngine()) {
//            System.out.println("Engine has started..");
//        } else {
//            System.out.println("Oops! Something went wrong..");
//        }
//
//// send commands manually
//        client.sendCommand("uci");
//
//// receive output dump
//        System.out.println(client.getOutput(0));
//
//// get the best move for a position with a given think time
//       // System.out.println("Best move : " + client.getBestMove(FEN, "3",10));
//
//// get all the legal moves from a given position
//   //     System.out.println("Legal moves : " + client.getLegalMoves(FEN));
//
//// draw board from a given position
////        System.out.println("Board state :");
////        client.drawBoard(FEN);
//
//// get the evaluation score of current position
//        System.out.println("Eval score : " + client.getEvalScore(FEN, 100));
//
//// stop the engine
//        System.out.println("Stopping engine..");
//        client.stopEngine();
//
//        //System.out.println("num=  "+ randomString(10));
    }

}



