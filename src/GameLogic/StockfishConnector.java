package GameLogic;

public class StockfishConnector {


    public String getAiMove(String FEN,int skill) {
        Stockfish client = new Stockfish();
        String depth;
       // String FEN = "N7/P3pk1p/3p2p1/r4p2/8/4b2B/4P1KP/R7 b - - 1 34";

// initialize and connect to engine
        if (client.startEngine()) {
            System.out.println("Engine has started..");
        } else {
            System.out.println("Oops! Something went wrong..");
        }

        if (skill < 0) {
            skill = 0;
        }
        if (skill > 20) {
            skill = 20;
        }

        /// Change thinking depth allowance.
        if (skill < 5) {
            depth = "1";
        } else if (skill < 10) {
            depth = "2";
        } else if (skill < 15) {
            depth = "3";
        } else {
            /// Let the engine decide.
            depth = "";
        }

// send commands manually
        client.sendCommand("uci");

// receive output dump
      //  System.out.println(client.getOutput(0));

// get the best move for a position with a given think time
        String aiMove= client.getBestMove(FEN,depth,skill);
        System.out.println("Depth "+depth +"Skill "+skill + "Best Move "+ aiMove);
// get all the legal moves from a given position
      //  System.out.println("Legal moves : " + client.getLegalMoves(FEN));

// draw board from a given position
        //System.out.println("Board state :");
        //client.drawBoard(FEN);

// get the evaluation score of current position
        //System.out.println("Eval score : " + client.getEvalScore(FEN, 2000));

// stop the engine
        System.out.println("Stopping engine..");
        client.stopEngine();
        return aiMove;
    }
}

