package ConsoleUiLogic;

import GameLogic.GameManager;

import java.util.Scanner;

public class ConsoleUi {
    int step;
    int totalSteps;
    int add;


    public ConsoleUi(String gamePlay) throws Exception {
        System.out.println("Welcome to Chess Game");
        GameManager gameManager = new GameManager(gamePlay);
        try {
            totalSteps = Integer.parseInt(gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 3]);
            add = 0;
        } catch (NumberFormatException e) {
            totalSteps = Integer.parseInt(gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 4]);
            add = 1;
        }
        for (int i = 0; i <= totalSteps * 2 + 1; i++) {
            getGesture();
            if (step == (totalSteps * 2) + 1) {
                if (gameManager.gamePlayAsArray[(totalSteps * 3) - 1 + add].equals("1/2-1/2")) {
                    System.out.println("\n \t \t \t Match Draw !!!\t \t \t \n");
                } else if (gameManager.gamePlayAsArray[(totalSteps * 3) - 1 + add].equals("0-1")) {
                    System.out.println("\n \t \t \t Player 2 Won the Match (Black) !!!\t \t \t \n");
                } else if (gameManager.gamePlayAsArray[(totalSteps * 3) - 1 + add].equals("1-0"))
                    System.out.println("\n \t \t \t Player 1 Won the Match (White) !!!\t \t \t \n");
                else {
                    System.out.println("error");
                }
                break;
            }
            gameManager.conductGameForPgnFile(step);
            printBoard(gameManager);

        }
    }


    private void getGesture() {
        Scanner scan = new Scanner(System.in);
        String gesture = scan.nextLine();
        step += 1;

    }

    void printBoard(GameManager manager) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = manager.b.cell[i][j].getPieceType();
                if (piece == null) {
                    System.out.print("\t|   |");
                } else {
                    System.out.printf("\t|%3s|", piece);
                }
                if (j == 7) {
                    System.out.print("\n \n");
                }
            }
        }

    }

}
