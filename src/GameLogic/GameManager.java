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
    String gamePlay = "1.e4 c5 2.Nf3 Nc6 3.d4 cxd4 4.Nxd4 Nf6 5.Nc3 d6 6.Be3 Ng4 7.Bg5 Qb6 8.Bb5 Bd7 " +
            "9.O-O Qxd4 10.Bxc6 Qxd1 11.Bxd7+ Kxd7 12.Raxd1 g6 13.h3 Ne5 14.Nd5 Nc6 15.b4 h6 " +
            "16.Bh4 f5 17.f4 Rg8 18.b5 Na5 19.e5 Nc4 20.Rd4 Rc8 21.e6+ Ke8 22.b6 axb6 " +
            "23.Rb1 g5 24.Rb4 b5 25.Rxb5 Bg7 26.Rxc4 Rxc4 27.Rxb7 Bd4+ 28.Bf2 Bxf2+ 29.Kxf2 Kf8 " +
            "30.Rxe7 Rg7 31.Rd7 Rxc2+ 32.Kf3 Rg6 33.Rd8+ Kg7 34.e7 g4+ 35.hxg4 fxg4+ 36.Kg3 ";


    public GameManager() throws Exception {

        System.out.println(" Welcome To The Chess Game");
        b.setBoard();
        makeGivenStringIntoArray();
        conductGame();

    }

    private void makeGivenStringIntoArray() {
        gamePlayAsArray = gamePlay.trim().split(" |\\.");

    }


    private void conductGame() throws Exception {

        gameStatus = GameStatus.NotBegan;

        while (isGameCanContinue()) {
            for (String arrayElement : gamePlayAsArray)
            {
                if (!checkIfNum(arrayElement))
                {
                    updateCurrentPlayerColor(currentPlayer);
                    b.printboard();
                    playGame(arrayElement);
                    updateGameStatus();
                    getGesture();
                    currentPlayer = updateCurrentPlayer(currentPlayer);
                    currentPlayer += 1;
                }
            }
            printResult(currentPlayer);

        }
    }

    private boolean checkIfNum(String arrayElement) {
        for (int i = 1; i < 100; i++) {
            if (String.valueOf(i).equals(arrayElement)) {
                return true;
            }
        }
        return false;
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
        if (currentPlayer == 1) {
            currentPlayerColor = Color.White;

        } else
            currentPlayerColor = Color.Black;
        ;
    }

    private void printResult(int currentPlayer) {

        Player currentPlayerName = b.players.get(currentPlayer);

        if (gameStatus == GameStatus.Draw) {
            System.out.println(" \n \n               No one Wins ..... The Match has Draw !!        \n \n     ");
        } else if (gameStatus == GameStatus.PlayerWon) {
            System.out.println("\n \n                    Congrats!!" + currentPlayerName + " wins !!!!!!             \n \n    ");
        }
    }

    private void updateGameStatus() {

        if (isCheckmate())
            gameStatus = GameStatus.PlayerWon;
        if (isGameDraw())
            gameStatus = GameStatus.Draw;
        else
            gameStatus = GameStatus.InProgress;


    }

    private boolean isGameDraw() {
        //TODO:validation
        return false;

    }

    private boolean isCheckmate() {
        //TODO: Validation
        return false;
    }


    private void movePiece(Color currentPlayerColor, String piece, Position fromPosition, Position toPosition) {
        if (isValidMove(currentPlayerColor, toPosition)) {
            b.cell[toPosition.x][toPosition.y] = b.cell[fromPosition.x][fromPosition.y];
            b.cell[fromPosition.x][fromPosition.y] = new Cell(Color.noColor, "   ", fromPosition);
        }
    }

    private boolean isValidMove(Color currentPlayerColor, Position toPosition) {
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
        if (gameStatus == GameStatus.InProgress) {
            return true;
        }
        if (gameStatus == GameStatus.NotBegan) {
            return true;
        }
        return false;
    }

    void playGame(String arrayElement) throws Exception {

        if (arrayElement.length() == 2) {
            for2elements(arrayElement);
        } else if (arrayElement.length() == 3) {
            if (arrayElement.charAt(2) == '+') {
                arrayElement = arrayElement.substring(0, 2);
                for2elements(arrayElement);
                System.out.println("\n \tIt is a Check\t \n ");

            } else {
                for3elements(arrayElement);
            }
        } else if (arrayElement.length() == 4) {
            if (arrayElement.charAt(3) == '+') {
                arrayElement = arrayElement.substring(0, 3);

                for3elements(arrayElement);
                System.out.println("\n \tIt is a Check\t \n");
            } else {
                for4elements(arrayElement);
            }
        } else if (arrayElement.length() == 5) {
            if (arrayElement.charAt(4) == '+') {
                arrayElement = arrayElement.substring(0, 4);

                for4elements(arrayElement);
                System.out.println("\n \tIt is a Check\t \n");
            } else {
                for5elements(arrayElement);
            }
        }


    }

    private void for5elements(String arrayElement) {
        char thePiece = arrayElement.charAt(0);
        String piece = "";
        Position toPosition = new Position(0, 0);
        Position fromPosition = new Position(0, 0);
        toPosition.y = getNumOf(arrayElement.charAt(3));
        toPosition.x = Character.getNumericValue((arrayElement.charAt(4))) - 1;
        if (arrayElement.charAt(2) != 'x') {
            fromPosition.y = getNumOf((arrayElement.charAt(1)));
            fromPosition.x = Character.getNumericValue(arrayElement.charAt(2));
        } else {
            fromPosition.y = getNumOf(arrayElement.charAt(1));
            for (int i = 0; i < 8; i++) {
                if (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece))) {
                    fromPosition.x = i;
                }
            }
        }
     movePiece(currentPlayerColor,piece, fromPosition, toPosition);
    }

    private void for4elements(String arrayElement) throws Exception {
        Position toPosition=new Position(0,0);
        Position fromPosition=new Position(0,0);
        if (isPawn(arrayElement)){
            fromPosition.y = getNumOf(arrayElement.charAt(0));
            String piece =currentPlayerColor.stringFormat +"P"+ fromPosition.y;
            if (arrayElement.charAt(1) != 'x') {
                fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;

            }
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3)))-1;
            fromPosition=getPiecePosition(piece);
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        } else {
            char thePiece = arrayElement.charAt(0);
            String piece="";
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3)))-1;
            if (arrayElement.charAt(1) != 'x') {
                fromPosition.y= getNumOf((arrayElement.charAt(1)));
                for(int i=0;i<8;i++){
                    if(b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat+"L"+thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat+"R"+thePiece)) ||(b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat+thePiece) )){
                        fromPosition.x=i;
                    }
                }
            }
            else {
                piece = whichPiece(arrayElement,thePiece,toPosition);
                fromPosition=getPiecePosition(currentPlayerColor.stringFormat+piece);
            }
             movePiece(currentPlayerColor,piece, fromPosition, toPosition);
        }

    }

    private void for3elements(String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        Position fromPosition;

        if (arrayElement.charAt(0) == 'O') {
            KingOrQueenCastling(arrayElement);
        } else {
            char thePiece = arrayElement.charAt(0);
            toPosition.x = Character.getNumericValue(arrayElement.charAt(2)) - 1;
            toPosition.y = getNumOf(arrayElement.charAt(1));
            String piece = whichPiece(arrayElement, thePiece, toPosition);
            fromPosition = getPiecePosition(currentPlayerColor.stringFormat+piece);
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        }
    }

    private void for2elements(String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        toPosition.x = Character.getNumericValue(arrayElement.charAt(1))-1;
        toPosition.y = getNumOf(arrayElement.charAt(0));
        String piece = currentPlayerColor.stringFormat+"P"+getNumOf(arrayElement.charAt(0));
        Position fromPosition = getPiecePosition(piece);
        movePiece(currentPlayerColor, piece, fromPosition, toPosition);
    }

    private void KingOrQueenCastling(String arrayElement) throws Exception {
         Position toPosition =new Position(0,0);
         Position toPosition2= new Position(0,0);
        String thePiece;
         if(isKingCastle()) {  thePiece="K";
         }
         else {thePiece="Q";}
         Position pos=getPiecePosition(currentPlayerColor.stringFormat+thePiece);
             String piece=leftOrRight(arrayElement,'R',new Position(pos.x,pos.y+minusOrPlus));
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

    private String whichPiece(String arrayElement,char thePiece,Position toPosition) throws Exception {
        if(thePiece=='R'){
          return leftOrRight(arrayElement,thePiece,toPosition);
        }
       else if(thePiece=='N'){
            return leftOrRight(arrayElement,thePiece,toPosition);
        }
       else if(thePiece=='B'){
            return leftOrRight(arrayElement,thePiece,toPosition);
        }
       else if(thePiece=='K'){
            return leftOrRight(arrayElement,thePiece,toPosition);
        }
       else if(thePiece=='Q'){
            return leftOrRight(arrayElement,thePiece,toPosition);
        }
        throw new Exception("Invalid Piece");
    }

    private String leftOrRight(String arrayElement,char thePiece,Position toPosition) throws Exception {
        if(thePiece=='R')
        {
            if(isRightRookCanMove(toPosition)){
                return "RR";
            }
            else
                return "LR";
        }
       else if(thePiece=='N'){
            if(isRightKnightCanMove(arrayElement,toPosition)){
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
            if(canTheKingMove(arrayElement,toPosition)){
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
        //Todo:
        return true;
    }

    private boolean canTheKingMove(String arrayElement,Position toPosition) throws Exception {
        Position fromPos=getPiecePosition(currentPlayerColor.stringFormat +"K");
        List<Position> possiblePositions;
        King king=new King(currentPlayerColor);
        possiblePositions=king.getPossiblePositions(fromPos);
        for(int i=0;i<=8;i++){
            if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x'){
                if(toPosition.x==possiblePositions.get(i).x && toPosition.y==possiblePositions.get(i).y){
                    return true;
                }
            }
                else if(b.cell[toPosition.x][toPosition.y].getPieceType().equals("   ") && toPosition==possiblePositions.get(i))
                {
                                                return true;
                       }
               }
        return false;
            }




    private boolean isWhiteOrBlackBishop() throws Exception {
        String piece = "RB";
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            return b.getCurrentCellColor(fromPos).equals("W");
        }
    }


    private boolean isRightKnightCanMove(String arrayElement,Position toPosition) throws Exception {
        String piece = "RN";
        List<Position> possiblePositions;
        Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if(fromPosition.x == 9 && fromPosition.y == 9) {
            return false;
        } else {
            Knight knight = new Knight(currentPlayerColor);
            possiblePositions = knight.getPossiblePositions(fromPosition);
            for (int i = 0; i < 8; i++) {
                if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
                    if (toPosition.x == possiblePositions.get(i).x && toPosition.y == possiblePositions.get(i).y) {
                        return true;

                    }
                } else {
                    if (b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.noColor)) {
                        if (toPosition.x == possiblePositions.get(i).x && toPosition.y == possiblePositions.get(i).y) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }



    private boolean isRightRookCanMove(Position toPosition) throws Exception {

            String piece = "RR";
            Position fromPos = getPiecePosition(currentPlayerColor.stringFormat +piece);
            if(fromPos.x==9 && fromPos.y==9)
            {
                return false;
            }
            else {Rook rook=new Rook(currentPlayerColor);
            return rook.isMovePossible(fromPos, toPosition,b.cell);}
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
        return new Position(9,9);

    }
}


