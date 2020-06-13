package GameLogic;



import java.util.List;
import java.util.Scanner;

public class GameManager {
    int currentPlayer = 1;
    GameStatus gameStatus;
    int minusOrPlus;
    Color currentPlayerColor;
    Board b = new Board();
    String[] gamePlayAsArray;
    String gamePlay="e4 e5 Nf3 Nc6 Bb5 a6 Ba4 Nf6 0-0 Be7 Re1 b5 Bb3 d6 c3 0-0 h3 Nb8 d4 Nbd7 Nbd2 Bb7 Bc2 "+
 "Re8 Nf1 Bf8 Ng3 c5 d5 c4 Bg5 Qc7 Nf5 Kh8 g4 Ng8 Qd2 Nc5 Be3 Bc8 Ng3 Rb8 Kg2 a5 a3 Ne7 Rh1 Ng6 g5 b4 axb4 "+
 "cxb4 Na6 Ra4 Nf4 Bxf4 exf4 Nh5 Qb6 Qxf4 Nxb4 Bb1 Rb7 Ra3 Rc7 Rd1 Na6 Nd4 Qxb2 Rg3 c3 Nf6 Re5 "+
  "g6 fxg6 Nd7 Be7 Nxe5 dxe5 Qf7 h6 Qe8";



    public GameManager() throws Exception {

        System.out.println(" Welcome To The Chess Game");
        b.setBoard();
        makeGivenStringIntoArray();
        conductGame();

    }

    private void makeGivenStringIntoArray() {
        gamePlayAsArray=gamePlay.trim().split(" ");

    }


    private void conductGame() throws Exception {

        gameStatus = GameStatus.NotBegan;

            while (isGameCanContinue()) {
                for (int i = 0; i < gamePlayAsArray.length; i++) {
                    String arrayElement = gamePlayAsArray[i];
               updateCurrentPlayerColor(currentPlayer);
                b.printboard();
                playGame(arrayElement);
                updateGameStatus();
                getGesture();
                currentPlayer = updateCurrentPlayer(currentPlayer);
                currentPlayer += 1;

            }
            printResult(currentPlayer);

        }
    }

    private int updateCurrentPlayer(int currentPlayer) {
        if (currentPlayer == 2) {
             return 0;
        }
        return 1;
    }

    private void getGesture() {
        Scanner scanner = new Scanner(System.in);
        String readString = scanner.nextLine();
    }


    private void updateCurrentPlayerColor(int currentPlayer) {
        if(currentPlayer==1){
            currentPlayerColor =Color.White;

        }
        else
            currentPlayerColor =Color.Black;                    ;
    }

    private void printResult(int currentPlayer) {

       Player currentPlayerName = b.players.get(currentPlayer);

        if(gameStatus==GameStatus.Draw)
        {
            System.out.println(" \n \n               No one Wins ..... The Match has Draw !!        \n \n     ");
        }
        else if(gameStatus==GameStatus.PlayerWon)
        {
            System.out.println( "\n \n                    Congrats!!" + currentPlayerName + " wins !!!!!!             \n \n    ");
        }
    }

        private void updateGameStatus() {

        if(isCheckmate())
            gameStatus=GameStatus.PlayerWon;
        if(isGameDraw())
            gameStatus=GameStatus.Draw;
        else
            gameStatus=GameStatus.InProgress;


    }

    private boolean isGameDraw() {
        //TODO:validation
        return false;

    }

    private boolean isCheckmate() {
        //TODO: Validation
        return false;
    }


    private void movePiece(Color currentPlayerColor, String piece, Position fromPosition,Position toPosition) {
    if(isValidMove(currentPlayerColor,toPosition))
   {
      b.cell[toPosition.x][toPosition.y] = b.cell[fromPosition.x][fromPosition.y];
      b.cell[fromPosition.x][fromPosition.y]=new Cell(Color.noColor,"   ",fromPosition);
   }
     }

    private boolean isValidMove(Color currentPlayerColor,Position toPosition) {
        return !isOccupiedByOwnColorPiece(currentPlayerColor, toPosition);
            }

    private boolean isOccupiedByOwnColorPiece(Color currentPlayerColor, Position toPosition) {
        return b.cell[toPosition.x][toPosition.y].getPieceColor().stringFormat.equals(currentPlayerColor.stringFormat);
           }

    /*void addplayers(String name) throws Exception {
        b.players.add(new Player(name));

    }


    void getUserdetails() throws Exception {
        for (int i = 1; i <= 2; i++) {
            System.out.println("Enter Player" + i + " Name : ");
            Scanner scan = new Scanner(System.in);
            String name = scan.nextLine();
            addplayers(name);

        }

    }*/

     boolean isGameCanContinue() {
        if (gameStatus ==GameStatus.InProgress) {
            return true;
        }
        if (gameStatus == GameStatus.NotBegan) {
            return true;
        }
        return false;
    }

    void playGame(String arrayElement) throws Exception {

            if (arrayElement.length()==2) {
                Position toPosition = new Position(0, 0);
                toPosition.x = Character.getNumericValue(arrayElement.charAt(1))-1;
                toPosition.y = getNumOf(arrayElement.charAt(0));
                String piece = currentPlayerColor.stringFormat+"P"+getNumOf(arrayElement.charAt(0));
                Position fromPosition = getPiecePosition(piece);
                movePiece(currentPlayerColor, piece, fromPosition, toPosition);

            } else if (arrayElement.length()==3) {
                Position toPosition = new Position(0, 0);

                if(arrayElement.charAt(0)=='0'){
                  KingOrQueencastling(arrayElement);
                                  }
             else {
                    char thePiece = arrayElement.charAt(0);
                    toPosition.x = Character.getNumericValue(arrayElement.charAt(2)) - 1;
                    toPosition.y = getNumOf(arrayElement.charAt(1));
                    String piece = whichPiece(thePiece, toPosition);
                    Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
                    movePiece(currentPlayerColor, piece, fromPosition, toPosition);
                }
            } else if (arrayElement.length() == 4) {
                Position toPosition=new Position(0,0);
                Position fromPosition=new Position(0,0);
                if (isPawn(arrayElement)) {
                    fromPosition.y = getNumOf(arrayElement.charAt(0));
                    String piece =currentPlayerColor.stringFormat +"P"+ fromPosition.x;
                    if (arrayElement.charAt(1) != 'x') {
                        fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;

                    }
                    toPosition.y = getNumOf(arrayElement.charAt(2));
                    toPosition.x = Character.getNumericValue((arrayElement.charAt(3)))-1;
                    fromPosition=getPiecePosition(piece);
                    movePiece(currentPlayerColor, piece, fromPosition, toPosition);
                } else {
                    char thePiece = arrayElement.charAt(0);
                    String piece;
                    if (arrayElement.charAt(1) != 'x') {
                        fromPosition.y= Character.getNumericValue((arrayElement.charAt(1))) - 1;


                    }
                    toPosition.y = getNumOf(arrayElement.charAt(2));
                    toPosition.x = Character.getNumericValue((arrayElement.charAt(3)))-1;
                    piece = whichPiece(thePiece,toPosition);
                    fromPosition=getPiecePosition(currentPlayerColor.stringFormat+piece);
                    movePiece(currentPlayerColor,piece, fromPosition, toPosition);
                }
            }


    }

    private void KingOrQueencastling(String arrayElement) throws Exception {
         Position toPosition =new Position(0,0);
         Position toPosition2= new Position(0,0);
        String thePiece;
         if(isKingCastle()) {  thePiece="K";
         }
         else {thePiece="Q";}
         Position pos=getPiecePosition(currentPlayerColor.stringFormat+thePiece);
             String piece=leftOrRight('R',new Position(pos.x,pos.y+minusOrPlus));
              Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
              toPosition.x = fromPosition.x;
             if (piece.equals("RR")) {
                 toPosition.y = fromPosition.y + 2;
                 toPosition2.y = fromPosition.y + 1;
             }
               else {  toPosition.y = fromPosition.y-2;
                 toPosition2.y = fromPosition.y - 1;}
                 movePiece(currentPlayerColor, thePiece, fromPosition, toPosition);
                 Position fromPosition2 = getPiecePosition(currentPlayerColor.stringFormat + piece);
                 toPosition2.x = fromPosition2.x;
                 movePiece(currentPlayerColor, piece, fromPosition2, toPosition2);
             }

    private boolean isKingCastle() throws Exception {
         Position kingPosition=getPiecePosition(currentPlayerColor.stringFormat+"K");
 if (b.cell[kingPosition.x][kingPosition.y+1].getPieceColor().equals(Color.noColor) && b.cell[kingPosition.x][kingPosition.y+2].getPieceColor().equals(Color.noColor)){

     minusOrPlus=1;
     return true;

     }
       if(b.cell[kingPosition.x][kingPosition.y-1].getPieceColor().equals(Color.noColor) && b.cell[kingPosition.x][kingPosition.y-2].getPieceColor().equals(Color.noColor)) {
            {
                minusOrPlus=-1;
                return true;
            }
        }
       return false;
    }


    private boolean isPawn(String arrayElement) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 26; i++) {
            if (arrayElement.charAt(0) ==alphabet.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private String whichPiece(char thePiece,Position toPosition) throws Exception {
        if(thePiece=='R'){
          return leftOrRight(thePiece,toPosition);
        }
       else if(thePiece=='N'){
            return leftOrRight(thePiece,toPosition);
        }
       else if(thePiece=='B'){
            return leftOrRight(thePiece,toPosition);
        }
       else if(thePiece=='K'){
            return leftOrRight(thePiece,toPosition);
        }
       else if(thePiece=='Q'){
            return leftOrRight(thePiece,toPosition);
        }
        throw new Exception("Invalid Piece");
    }

    private String leftOrRight(char thePiece,Position toPosition) throws Exception {
        if(thePiece=='R')
        {
            if(isRightRookCanMove(toPosition)){
                return "RR";
            }
            else
                return "LR";
        }
       else if(thePiece=='N'){
            if(isRightKnightCanMove(toPosition)){
                return "RN";
            }
            else
                return "LN";
        }
        else if(thePiece=='B'){


            if(isWhiteOrBlackBishop()){
                if(currentPlayerColor.stringFormat.equals("W")) {
                    return "LB";
                }else return "RB";
            }
            else if(currentPlayerColor.stringFormat.equals("W"))
            {return "RB";}
            else return "LB";
        }
       else if(thePiece=='K'){
            if(canTheKingMove(toPosition)){
                return "K";
            }
        }
       else if(thePiece=='Q'){
            if (canTheQueenMove()){
                  return "Q";
            }
        }

        throw new Exception("Piece not Found");

    }

    private boolean canTheQueenMove() {
        return true;
    }

    private boolean canTheKingMove(Position toPosition) throws Exception {
        Position fromPos=getPiecePosition(currentPlayerColor.stringFormat +"K");
        List<Position> possiblePositions;
        King king=new King(currentPlayerColor);
        possiblePositions=king.getPossiblePositions(fromPos);
        for(int i=0;i<=8;i++){
                if(b.cell[toPosition.x][toPosition.y].getPieceType().equals("   ") && toPosition==possiblePositions.get(i))
                {
                                                return true;
                       }
               }
        return false;
            }




    private boolean isWhiteOrBlackBishop() throws Exception {
        String piece= "RB";
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat+piece);
        return b.getCurrentCellColor(fromPos).equals("W");
    }


    private boolean isRightKnightCanMove(Position toPosition) throws Exception {
        String piece ="RN";
        List<Position> possiblePositions;
        Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat+piece);
        Knight knight = new Knight(currentPlayerColor);
        possiblePositions=knight.getPossiblePositions(fromPosition);
        for(int i=0;i<8;i++){
            if(b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.noColor)) {
                if (toPosition.x == possiblePositions.get(i).x && toPosition.y == possiblePositions.get(i).y) {
                    return true;
                }
            }

        }
        return false;
     }



    private boolean isRightRookCanMove(Position toPosition) throws Exception {

            String piece = "RR";
            Position fromPos = getPiecePosition(currentPlayerColor.stringFormat +piece);
            Rook rook=new Rook(currentPlayerColor);
            return rook.isMovePossible(fromPos, toPosition,b.cell);


    }


    private int getNumOf(char character) {
        String alphabet ="abcdefghijklmnopqrstuvwxyz";
        return alphabet.indexOf(character);
    }


    public Position getPiecePosition(String piece) throws Exception {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(b.cell[i][j].getPieceType().equals(piece)){
                    return new Position(i,j);
                }
            }

        }
        throw new Exception("Element not found");

    }
}


