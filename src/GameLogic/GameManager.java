package GameLogic;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    int currentPlayer = 0;
    GameStatus gameStatus;
    Color currentPlayerColor;
    Board b = new Board();
    String[] gamePlayAsArray;
    String gamePlay="e4 e5 Nf3 Nc6 Bb5 a6 Ba4 Nf6 Be7 Re1 b5";
/*"7. Bb3 d6 8. c3 0-0 9. h3 Nb8 10. d4 Nbd7 11. Nbd2 Bb7 12. Bc2 Re8 13. Nf1 Bf8 " +
"14. Ng3 c5 15. d5 c4 16. Bg5 Qc7 17. Nf5 Kh8 18. g4 Ng8 19. Qd2 Nc5 20. Be3 Bc8" +
"21. Ng3 Rb8 22. Kg2 a5 23. a3 Ne7 24. Rh1 Ng6 25. g5! b4!? Anand has a strong kingside attack, so Bologan seeks counterplay with the sacrifice of a pawn." +
"26. axb4 axb4 27. cxb4 Na6 28. Ra4 Nf4+ 29. Bxf4 exf4 30. Nh5 Qb6 31. Qxf4 Nxb4 32. Bb1 Rb7" +
"33. Ra3 Rc7 34. Rd1 Na6 35. Nd4 Qxb2 36. Rg3 c3 (see diagram) 37. Nf6!! Re5 If 37...gxf6, 38.gxf6 h6 39.Rg1! Qd2! 40.Qh4 leaves White with an irresistible initiative. 38. g6! fxg6 39. Nd7 Be7 " +
"40. Nxe5 dxe5 41. Qf7 h6 42. Qe8+ 1–0[167] White forces mate in 12 moves if the game were to continue, with 42...Bf8 43.Rf3 Qa3 44.Rxf8+ Qxf8 45.Qxf8+ Kh7 46.d6 exd4 47.Ba2 h5 48.dxc7 Nb4 49.Qg8+ Kh6 50.f4 g5 51.f5 g4 52.h4 Bxf5 53.exf5 Nxa2 54.Qh8#."
*/


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

        gameStatus=GameStatus.NotBegan;
        while (isGameCanContinue()) {
           currentPlayer += 1;
            updateCurrentPlayerColor(currentPlayer);
            b.printboard();
            playGame();
            updateGameStatus();
            getGesture();


            if (currentPlayer ==2){
                currentPlayer =0;  }
        }
        printResult(currentPlayer);

    }

    private void getGesture() {
        Scanner scanner = new Scanner(System.in);
        String readString = scanner.nextLine();
    }


    private void updateCurrentPlayerColor(int currentplayer) {
        if(currentplayer==1){
            currentPlayerColor =Color.White;

        }
        else
            currentPlayerColor =Color.Black;                    ;
    }

    private void printResult(int currentplayer) {

       Player currentplayername = b.players.get(currentplayer);

        if(gameStatus==GameStatus.Draw)
        {
            System.out.println(" \n \n               No one Wins ..... The Match has Draw !!        \n \n     ");
        }
        else if(gameStatus==GameStatus.PlayerWon)
        {
            System.out.println( "\n \n                    Congrats!!" + currentplayername + " wins !!!!!!             \n \n    ");
        }
    }

        private void updateGameStatus() {

        if(isCheckmate())
            gameStatus=GameStatus.PlayerWon;
        if(isGameDraw())
            gameStatus=GameStatus.Draw;
        else
            gameStatus=GameStatus.Inprogress;


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
      b.cell[fromPosition.x][fromPosition.y]=new Cell(Color.Empty," ",fromPosition);
   }
     }

    private boolean isValidMove(Color currentPlayerColor,Position toPosition) {
        return !isOccupiedByOwnColorPiece(currentPlayerColor, toPosition);
            }

    private boolean isOccupiedByOwnColorPiece(Color currentPlayerColor, Position toPosition) {
        Color color = b.cell[toPosition.x][toPosition.y].getPieceColor();
        return !color.equals(currentPlayerColor);
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
        if (gameStatus ==GameStatus.Inprogress) {
            return true;
        }
        if (gameStatus == GameStatus.NotBegan) {
            return true;
        }
        return false;
    }

    void playGame() throws Exception {
        for (int i = 0; i < gamePlayAsArray.length; i++) {
            if (gamePlayAsArray[i].length() == 2) {
                String arrayElement = gamePlayAsArray[i];
                Position toPosition = new Position(0, 0);
                toPosition.x = getNumOf(arrayElement.charAt(0));
                toPosition.y = Character.getNumericValue(arrayElement.charAt(1)) - 1;
                String piece = "P" + toPosition.x;
                Position fromPosition = getPiecePosition(currentPlayerColor.Stringformat + piece);
                movePiece(currentPlayerColor, piece, fromPosition, toPosition);

            } else if (gamePlayAsArray[i].length() == 3) {
                String arrayElement = gamePlayAsArray[i];
                Position toPosition = new Position(0, 0);
                char thePiece = arrayElement.charAt(0);
                toPosition.x = getNumOf(arrayElement.charAt(1));
                toPosition.y = Character.getNumericValue(arrayElement.charAt(2)) - 1;
                String piece = whichPiece(thePiece, toPosition);
                Position fromPosition = getPiecePosition(currentPlayerColor.Stringformat + piece);
                movePiece(currentPlayerColor, piece, fromPosition, toPosition);

            } else if (gamePlayAsArray[i].length() == 4) {
                String arrayElement = gamePlayAsArray[i];
                Position toPosition = new Position(0, 0);
                Position fromPosition = new Position(0, 0);
                if (isPawn(arrayElement)) {
                    fromPosition.x = getNumOf(arrayElement.charAt(0));
                    String piece = "P" + fromPosition.x;
                    if (arrayElement.charAt(1) == 'x') {
                        toPosition.x = getNumOf(arrayElement.charAt(2));
                        toPosition.y = Character.getNumericValue((arrayElement.charAt(3))) - 1;

                    } else {
                        fromPosition.y = Character.getNumericValue((arrayElement.charAt(1))) - 1;
                        toPosition.x = getNumOf(arrayElement.charAt(2));
                        toPosition.y = Character.getNumericValue((arrayElement.charAt(3))) - 1;

                    }
                    movePiece(currentPlayerColor, piece, fromPosition, toPosition);
                } else {
                    char thePiece = arrayElement.charAt(0);
                    String piece;
                    if (arrayElement.charAt(1) == 'x') {
                        toPosition.x = getNumOf(arrayElement.charAt(2));
                        toPosition.y = Character.getNumericValue((arrayElement.charAt(3))) - 1;
                        piece = whichPiece(thePiece, toPosition);
                    } else {
                        fromPosition.y = Character.getNumericValue((arrayElement.charAt(1))) - 1;
                        toPosition.x = getNumOf(arrayElement.charAt(2));
                        toPosition.y = Character.getNumericValue((arrayElement.charAt(3))) - 1;
                        piece = whichPiece(thePiece, toPosition);

                    }
                    movePiece(currentPlayerColor,piece, fromPosition, toPosition);
                }
            }

        }
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
        throw new Exception();
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
                return "RB";
            }
            else
                return "LB";
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

        throw new Exception();

    }

    private boolean canTheQueenMove() {
        return true;
    }

    private boolean canTheKingMove(Position toPosition) throws Exception {
        Position fromPos=getPiecePosition(currentPlayerColor.Stringformat+"K");
        List<Position> possiblePositions;
        King king=new King(currentPlayerColor);
        possiblePositions=king.getPossiblePositions(fromPos);
        for(int i=0;i<=8;i++){
                if(b.cell[toPosition.x][toPosition.y].getPieceType().equals("  ") && toPosition==possiblePositions.get(i))
                {
                                                return true;
                       }
               }
        return false;
            }




    private boolean isWhiteOrBlackBishop() throws Exception {
        String piece= "RB";
        Position fromPos = getPiecePosition(currentPlayerColor.Stringformat+piece);
        return b.getCurrentCellColor(fromPos).equals("W");
    }


    private boolean isRightKnightCanMove(Position toPosition) throws Exception {
        String piece = "RN";
        List<Position> possiblePositions;
        Position fromPosition = getPiecePosition(currentPlayerColor.Stringformat+piece);
        Knight knight = new Knight(currentPlayerColor);
        possiblePositions=knight.getPossiblePositions(fromPosition);
        for(int i=0;i<8;i++){
            if(b.cell[toPosition.x][toPosition.y].getPieceType().equals("  ") && toPosition==possiblePositions.get(i)){
                return true;
            }

        }
        return false;
     }



    private boolean isRightRookCanMove(Position toPosition) throws Exception {

            String piece = "RR";
            Position fromPos = getPiecePosition(currentPlayerColor.Stringformat+piece);
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
        throw new Exception();
    }
}


