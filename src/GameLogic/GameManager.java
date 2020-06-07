package GameLogic;



import javax.swing.*;
import java.util.Scanner;

public class GameManager {
    int currentplayer = 0;
    GameStatus gameStatus;
    String CurrentPlayerColor;
    Board b = new Board();



    public GameManager() throws Exception {

        System.out.println(" Welcome To The Chess Game");
        getUserdetails();
        b.Setboard();
        b.printboard();
        Conductgame();


    }


    private void Conductgame() {

        gameStatus=GameStatus.NotBegan;
        while (Isgamecancontinue()) {

            currentplayer += 1;

            Scanner scan = new Scanner(System.in);

            System.out.println("Enter the Piece you want to select Player " + currentplayer);
            UpdateCurrentPlayerColor(currentplayer);


            System.out.println("Enter Piece(LR,RR,LN,RN,LB,RB,K,Q,P)");
            //String color = scan.nextLine();
            String piece = scan.nextLine();
            System.out.println("Enter Position of the Piece X,Y:");
            int x = scan.nextInt();
            int y = scan.nextInt();


            System.out.println("Enter the  cell Position u want to move the piece X,Y : ");
            int X = scan.nextInt();
            int Y = scan.nextInt();
            Movepiece(CurrentPlayerColor,piece,x,y,X, Y);
            b.printboard();
            UpdateGameStatus();


            if (currentplayer==2){
                currentplayer=0;



        }
        }
        PrintResult(currentplayer);

    }

    private void UpdateCurrentPlayerColor(int currentplayer) {
        if(currentplayer==1){
            CurrentPlayerColor="W";

        }
        else
            CurrentPlayerColor="B";
    }

    private void PrintResult(int currentplayer) {

       Player currentplayername = b.players.get(currentplayer);

        if(gameStatus==GameStatus.Draw)
        {
            System.out.printf(" \n \n               No one Wins ..... The Match has Draw !!        \n \n     ");
        }
        else if(gameStatus==GameStatus.PlayerWon)
        {
            System.out.printf( "\n \n                    Congrats!!" + currentplayername + " wins !!!!!!             \n \n    ");
        }
    }

        private void UpdateGameStatus() {

        if(IsCheckmate())
            gameStatus=GameStatus.PlayerWon;
        if(IsGameDraw())
            gameStatus=GameStatus.Draw;
        else
            gameStatus=GameStatus.Inprogress;


    }

    private boolean IsGameDraw() {
        //TODO:validation
        return false;

    }

    private boolean IsCheckmate() {
        //TODO: Validation
        return false;
    }


    private void Movepiece(String CurrentPlayerColor,String Piece,int x, int y, int X,int Y) {
    if(Isvalidmove(CurrentPlayerColor,Piece,x,y))
    {b.cell[X][Y] = b.cell[x][y];
      b.cell[x][y]=new Cell(" "," ",x,y);


    }}

    private boolean Isvalidmove(String currentPlayerColor,String Piece,int x, int y) {
        if(IsOccupiedByOwnPiece(currentPlayerColor,x,y))
            return true;
        else
            return true;



    }

    private boolean IsOccupiedByOwnPiece(String currentPlayerColor,int x, int y) {
        String xyz = b.cell[x][y].getPieceColor();
        if (xyz != null && xyz.equals(currentPlayerColor))
            return false;
        else
            return true;

           }

   /* private Cell Selectpiece(String piece, int x, int y) {
        Cell tempcell;
        tempcell = new Cell(piece, x, y);
        return tempcell;
    }*/

    void addplayers(String name) throws Exception {
        b.players.add(new Player(name));

    }


    void getUserdetails() throws Exception {
        for (int i = 1; i <= 2; i++) {
            System.out.println("Enter Player" + i + " Name : ");
            Scanner scan = new Scanner(System.in);
            String name = scan.nextLine();
            addplayers(name);

        }

    }

     boolean Isgamecancontinue() {
        if (gameStatus ==GameStatus.Inprogress) {
            return true;
        }
        if (gameStatus == GameStatus.NotBegan) {
            return true;
        }
        return false;
    }
}

