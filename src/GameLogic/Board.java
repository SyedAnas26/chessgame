package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    static final int dimension = 8;
    Cell cell[][];
    List<Player> players;
    Cell temp;
   Force blackforce;
   Force whiteforce;
   Piece piececolor;
   Piece piecetype;
   Position position;



    Board() {

        players = new ArrayList<>();
        cell = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Position pos=new Position(i,j);
                temp = new Cell(null,null,new Position(i,j));
                cell[i][j] = temp;
            }
        }
    }

    public void printboard() {


        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                String piece=cell[i][j].getPieceType();
                if(piece==null){
                    System.out.print("\t|  |");
                }
                else {
                    System.out.printf("\t|%2s|", piece);
                }
                if (j == 7) {
                    System.out.println("\n");
                }
            }
        }
    }

    String getCurrentCellColor(Position position) {

        if (IsbothOddorEven(position))
            return "W";
        else
            return "B";

    }

    private boolean IsbothOddorEven(Position position) {
        if (position.x % 2 == 0 && position.y % 2 == 0 || position.x % 2 != 0 && position.y % 2 != 0) {
            return true;
        } else
            return false;


    }


    public void setBoard() {
        setWhiteForce();
        setBlackForce();
                }


    private void setBlackForce() {
        {
            int i = 6;
            for (int j = 0; j < 8; j++) {

                cell[i][j] = new Cell(Color.Black,"P"+j,new Position(i,j));

            }
            cell[7][0] = new Cell(Color.Black,"LR", new Position(7,0));
            cell[7][1] = new Cell(Color.Black,"LN", new Position(7,1));
            cell[7][2] = new Cell(Color.Black,"LB", new Position(7,2));
            cell[7][3] = new Cell(Color.Black,"K", new Position(7,3));
            cell[7][4] = new Cell(Color.Black,"Q", new Position(7,4));
            cell[7][5] = new Cell(Color.Black,"RB", new Position(7,5));
            cell[7][6] = new Cell(Color.Black,"RN", new Position(7,6));
            cell[7][7] = new Cell(Color.Black,"RR", new Position(7,7));

        }
    }

    private void setWhiteForce() {
        int i = 1;

        for (int j = 0; j < 8; j++) {
            cell[i][j] = new Cell(Color.White,"P"+ j,new Position(i,j));

        }
        cell[0][0] = new Cell(Color.White,"LR", new Position(0,0));
        cell[0][1] = new Cell(Color.White,"LN", new Position(0,1));
        cell[0][2] = new Cell(Color.White,"LB", new Position(0,2));
        cell[0][3] = new Cell(Color.White,"K", new Position(0,3));
        cell[0][4] = new Cell(Color.White,"Q", new Position(0,4));
        cell[0][5] = new Cell(Color.White,"RB", new Position(0,5));
        cell[0][6] = new Cell(Color.White,"RN", new Position(0,6));
        cell[0][7] = new Cell(Color.White,"RR", new Position(0,7));

    }
}
