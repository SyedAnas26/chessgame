package GameLogic.PureLogic;


import util.OSHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish {

        private Process engineProcess;
        private BufferedReader processReader;
        private OutputStreamWriter processWriter;

        private static final String PATH = "../engine/stockfish";

        /**
         * Starts Stockfish engine as a process and initializes it
         *
         * @return True on success. False otherwise
         */
        public boolean startEngine() {
            try {
                switch (OSHelper.getOSType())
                {
                    case MAC:
                        engineProcess = Runtime.getRuntime().exec("/Users/muthu-1987/git/chessgame/engine/MacStockFish");
                        break;
                    case UNIX:
                        engineProcess = Runtime.getRuntime().exec("/root/apache-tomcat-9.0.37/webapps/ROOT/WEB-INF/engine/stockfish");
                        break;
                    case WINDOWS:
                        engineProcess = Runtime.getRuntime().exec("C:\\Users\\User\\Desktop\\Saddique\\GitHub\\chessgame\\engine\\WindowsStockFish.exe");
                        break;
                }
                processReader = new BufferedReader(new InputStreamReader(
                        engineProcess.getInputStream()));
                processWriter = new OutputStreamWriter(
                        engineProcess.getOutputStream());
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**
         * Takes in any valid UCI command and executes it
         *
         * @param command
         */
        public void sendCommand(String command) {
            try {
                processWriter.write(command + "\n");
                processWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * This is generally called right after 'sendCommand' for getting the raw
         * output from Stockfish
         *
         * @param waitTime
         *            Time in milliseconds for which the function waits before
         *            reading the output. Useful when a long running command is
         *            executed
         * @return Raw output from Stockfish
         */
        public String getOutput(int waitTime) {
            StringBuffer buffer = new StringBuffer();
            try {
                Thread.sleep(waitTime);
                sendCommand("isready");
                while (true) {
                    String text = processReader.readLine();
                    if (text.equals("readyok"))
                        break;
                    else
                        buffer.append(text + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        /**
         * This function returns the best move for a given position after
         * calculating for 'waitTime' ms
         *
         * @param fen
         *            Position string
         *
         *            in milliseconds
         * @return Best Move in PGN format
         */
        public String getBestMove(String fen, String depth, int skill) {
             sendCommand("position fen " + fen);
            this.sendCommand("setoption name Skill Level value "+ skill);
            this.sendCommand("go depth " + depth);
           // sendCommand("go movetime " + waitTime);
            return getOutput( 100 + 20).split("bestmove ")[1].split(" ")[0];
        }

        /**
         * Stops Stockfish and cleans up before closing it
         */
        public void stopEngine() {
            try {
                sendCommand("quit");
                processReader.close();
                processWriter.close();
            } catch (IOException e) {
            }
        }

        /**
         * Get a list of all legal moves from the given position
         *
         * @param fen
         *            Position string
         * @return String of moves
         */
        public String getLegalMoves(String fen) {
            sendCommand("position fen " + fen);
            sendCommand("d");
            System.out.println(getOutput(100));
            return getOutput(100).split("Legal moves: ")[0];
        }

        /**
         * Draws the current state of the chess board
         *
         * @param fen
         *            Position string
         */
        public void drawBoard(String fen) {
            sendCommand("position fen " + fen);
            sendCommand("d");

            String[] rows = getOutput(100).split("\n");

            for (int i = 1; i < 18; i++) {
                System.out.println(rows[i]);
            }
        }

        /**
         * Get the evaluation score of a given board position
         * @param fen Position string
         * @param waitTime in milliseconds
         * @return evalScore
         */
        public float getEvalScore(String fen, int waitTime) {
            sendCommand("position fen " + fen);
            sendCommand("go movetime " + waitTime);

            float evalScore = 0.0f;
            String[] dump = getOutput(waitTime + 20).split("\n");
            for (int i = dump.length - 1; i >= 0; i--) {
                if (dump[i].startsWith("info depth ")) {
                    try {
                        evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
                                .split(" nodes")[0]);
                    } catch(Exception e) {
                        evalScore = Float.parseFloat(dump[i].split("score cp ")[1]
                                .split(" upperbound nodes")[0]);
                    }
                }
            }
            return evalScore/100;
        }
    }

