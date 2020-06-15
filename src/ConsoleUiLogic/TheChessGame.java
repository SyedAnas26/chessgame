package ConsoleUiLogic;

import GameLogic.Board;

import GameLogic.Color;
import GameLogic.Position;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TheChessGame {

    public static void main(String[] args) throws Exception {

        File file = new File("C:\\Users\\User\\Desktop\\test.txt");
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

