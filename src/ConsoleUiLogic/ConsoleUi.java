package ConsoleUiLogic;

import GameLogic.GameManager;


import java.util.Scanner;

public class ConsoleUi {
  int step;


    public ConsoleUi(String gamePlay) throws Exception {
        GameManager gameManager = new GameManager(gamePlay);
        for (int i = 0; i < gameManager.gamePlayAsArray.length - 2; i++) {
            getGesture();
            gameManager.conductGame(step);
            if (!isMultipleOf3(step)) {
                printBoard(gameManager);
            }
            if(i==gameManager.gamePlayAsArray.length-3)
            {
                if (gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 1].charAt(1) == '/') {
                    System.out.println("\n \t \t \t Player 2 Won the Match (Black)\t \t \t \n");
                } else if (gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 1].charAt(0) == '0') {
                    System.out.println("\n \t \t \t Player 2 Won the Match (Black)\t \t \t \n");
                } else
                    System.out.println("\n \t \t \t Player 1 Won the Match (White)\t \t \t \n");

            }


        }
    }



    private boolean isMultipleOf3(int step1) {
        for(int i=0;i<300;i++) {
            if (step1 == i * 3) {
                return true;
            }
        }
        return false;
    }


    private void getGesture() {
        Scanner scan=new Scanner(System.in);
        String gesture = scan.nextLine();
        step+=1;

    }
    void printBoard(GameManager manager){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece=manager.b.cell[i][j].getPieceType();
                if(piece==null){
                    System.out.print("\t|   |");
                }
                else {
                    System.out.printf("\t|%3s|",piece);
                }
                if (j == 7) {
                    System.out.print("\n \n");
                }
            }

            
        }

    }

}
