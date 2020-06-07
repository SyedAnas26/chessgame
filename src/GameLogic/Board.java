package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    static final int dimension = 8;
    Cell cell[][];
    List<Player> players;
    Cell temp;
   Force Blackforce;
   // Force Whiteforce;
    String Playercolor;

    Piece p;
    Board() {
        p.pieceType.toString();

        if(Blackforce.LeftKnight == p)
        {
            System.out.print("K");
        }
        else if(Blackforce.LeftBishop == p)
        {
            System.out.print("B");
        }
        else if(Blackforce.LeftRook== p)
        {
            System.out.print("R");
        }



        players = new ArrayList<>();
        cell = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                temp = new Cell(null,null, i, j);
                cell[i][j] = temp;
            }
        }
    }

    public void printboard() {


        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                System.out.printf("\t|%2c|", cell[i][j].getPieceType());
                if (j == 7) {
                    System.out.println("\n");
                }
            }
        }
    }

    String GetCurrentCellColor(int x, int y) {

        if (IsbothOddorEven(x, y))
            return "W";
        else
            return "B";

    }

    private boolean IsbothOddorEven(int x, int y) {
        if (x % 2 == 0 && y % 2 == 0 || x % 2 != 0 && y % 2 != 0) {
            return true;
        } else
            return false;


    }


    public void Setboard() {
        setWhiteForce();
        setBlackForce();
                }


    private void setBlackForce() {
        {
            int i = 6;
            for (int j = 0; j < 8; j++) {
                cell[i][j] = new Cell("B","P" + j, i, j);

            }
            cell[7][0] = new Cell("B","LR", 7, 0);
            cell[7][1] = new Cell("B","LN", 7, 1);
            cell[7][2] = new Cell("B","LB", 7, 2);
            cell[7][3] = new Cell("B","K", 7, 3);
            cell[7][4] = new Cell("B","Q", 7, 4);
            cell[7][5] = new Cell("B","RB", 7, 5);
            cell[7][6] = new Cell("B","RN", 7, 6);
            cell[7][7] = new Cell("B","RR", 7, 7);

        }
    }

    private void setWhiteForce() {
        int i = 1;
        for (int j = 0; j < 8; j++) {
            cell[i][j] = new Cell(Color.White,"P" + j, i, j);

        }
        cell[0][0] = new Cell(Color.White,"LR", 0, 0);
        cell[0][1] = new Cell(Color.White,"LN", 0, 1);
        cell[0][2] = new Cell(Color.White,"LB", 0, 2);
        cell[0][3] = new Cell(Color.White,"K", 0, 3);
        cell[0][4] = new Cell(Color.White,"Q", 0, 4);
        cell[0][5] = new Cell(Color.White,"RB", 0, 5);
        cell[0][6] = new Cell(Color.White,"RN", 0, 6);
        cell[0][7] = new Cell(Color.White,"RR", 0, 7);

    }
}
