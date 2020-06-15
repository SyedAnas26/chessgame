package GameLogic;


import java.util.List;

public class GameManager {
    int currentPlayer = 1;
    GameStatus gameStatus;
    int minusOrPlus;
    Color currentPlayerColor;
    public Board b = new Board();
   public String[] gamePlayAsArray;

    public GameManager(String gamePlay) throws Exception {

        System.out.println(" Welcome To The Chess Game");
        b.setBoard();
        makeGivenStringIntoArray(gamePlay);
    }

    private void makeGivenStringIntoArray(String gamePlay) {
        gamePlayAsArray = gamePlay.trim().split(" |\\.");

    }


    public void conductGame(int step) throws Exception {

        gameStatus = GameStatus.NotBegan;


        {

            updateCurrentPlayerColor(currentPlayer);
            if (!checkIfNum(gamePlayAsArray[step])) {
                playGame(gamePlayAsArray[step]);
                updateGameStatus();
                currentPlayer = updateCurrentPlayer(currentPlayer);
                currentPlayer += 1;
            }
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

    private void updateCurrentPlayerColor(int currentPlayer) {
        if (currentPlayer == 1) {
            currentPlayerColor = Color.White;

        } else
            currentPlayerColor = Color.Black;
        ;
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
            b.cell[fromPosition.x][fromPosition.y] = new Cell(Color.noColor, " ", fromPosition);
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

 /*   boolean isGameCanContinue() {
        if (gameStatus == GameStatus.InProgress) {
            return true;
        }
        if (gameStatus == GameStatus.NotBegan) {
            return true;
        }
        return false;
    }*/

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

    private void for5elements(String arrayElement) throws Exception {
        if (arrayElement.charAt(0) == 'O') {
            KingOrQueenCastling("Q", arrayElement);
        } else {
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
                if(arrayElement.charAt(1) != '1' & arrayElement.charAt(1)!='2' & arrayElement.charAt(1)!='3' & arrayElement.charAt(1)!='4' & arrayElement.charAt(1)!='5' & arrayElement.charAt(1)!='6' & arrayElement.charAt(1)!='7' & arrayElement.charAt(1)!='8') {
                    fromPosition.y = getNumOf((arrayElement.charAt(1)));
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.x = i;
                        }
                    }
                }
                else
                {
                    fromPosition.x = Character.getNumericValue((arrayElement.charAt(1)))-1;
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.y = i;
                        }
                    }

                }
            }
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        }
    }


    private void for4elements(String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        Position fromPosition = new Position(0, 0);
        if (isPawn(arrayElement)) {
            fromPosition.y = getNumOf(arrayElement.charAt(0));
            String piece = currentPlayerColor.stringFormat + "P" + fromPosition.y;
            if (arrayElement.charAt(1) != 'x') {
                fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;

            }
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3))) - 1;
            fromPosition = getPiecePosition(piece);
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        } else {
            char thePiece = arrayElement.charAt(0);
            String piece = " ";
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3))) - 1;
            if (arrayElement.charAt(1) != 'x') {
                if(arrayElement.charAt(1) != '1' & arrayElement.charAt(1)!='2' & arrayElement.charAt(1)!='3' & arrayElement.charAt(1)!='4' & arrayElement.charAt(1)!='5' & arrayElement.charAt(1)!='6' & arrayElement.charAt(1)!='7' & arrayElement.charAt(1)!='8') {
                    fromPosition.y = getNumOf((arrayElement.charAt(1)));
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.x = i;
                        }
                    }
                }
                else
                {
                    fromPosition.x = Character.getNumericValue((arrayElement.charAt(1)))-1;
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[fromPosition.x][i].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.y = i;
                        }
                    }

                }
            } else {
                piece = whichPiece(arrayElement, thePiece, toPosition);
                fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
            }
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        }

    }

    private void for3elements(String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        Position fromPosition;

        if (arrayElement.charAt(0) == 'O') {
            KingOrQueenCastling("K", arrayElement);
        } else {
            char thePiece = arrayElement.charAt(0);
            toPosition.x = Character.getNumericValue(arrayElement.charAt(2)) - 1;
            toPosition.y = getNumOf(arrayElement.charAt(1));
            String piece = whichPiece(arrayElement, thePiece, toPosition);
            fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        }
    }

    private void for2elements(String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        toPosition.x = Character.getNumericValue(arrayElement.charAt(1)) - 1;
        toPosition.y = getNumOf(arrayElement.charAt(0));
        String piece = currentPlayerColor.stringFormat + "P" + getNumOf(arrayElement.charAt(0));
        Position fromPosition = getPiecePosition(piece);
        movePiece(currentPlayerColor, piece, fromPosition, toPosition);
    }

    private void KingOrQueenCastling(String thePiece, String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        Position toPosition2 = new Position(0, 0);
        whichSideCastle(thePiece);
        Position pos = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
        String piece = leftOrRight(arrayElement, 'R', new Position(pos.x, pos.y + minusOrPlus));
        Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
        toPosition.x = fromPosition.x;
        if (piece.equals("RR")) {
            toPosition.y = fromPosition.y + 2;
            toPosition2.y = fromPosition.y + 1;
        } else {
            toPosition.y = fromPosition.y - 2;
            toPosition2.y = fromPosition.y - 1;
        }
        movePiece(currentPlayerColor, thePiece, fromPosition, toPosition);
        Position fromPosition2 = getPiecePosition(currentPlayerColor.stringFormat + piece);
        toPosition2.x = fromPosition2.x;
        movePiece(currentPlayerColor, piece, fromPosition2, toPosition2);
    }

    private void whichSideCastle(String thePiece) throws Exception {
        Position kingPosition = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
        if (b.cell[kingPosition.x][kingPosition.y + 1].getPieceColor().equals(Color.noColor) && b.cell[kingPosition.x][kingPosition.y + 2].getPieceColor().equals(Color.noColor)) {
            minusOrPlus = 1;
        }
        if (b.cell[kingPosition.x][kingPosition.y - 1].getPieceColor().equals(Color.noColor) && b.cell[kingPosition.x][kingPosition.y - 2].getPieceColor().equals(Color.noColor)) {
            {
                minusOrPlus = -1;
            }
        }
    }


    private boolean isPawn(String arrayElement) {
        String alphabet = "abcdefgh";
        for (int i = 0; i < alphabet.length(); i++) {
            if (arrayElement.charAt(0) == alphabet.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private String whichPiece(String arrayElement, char thePiece, Position toPosition) throws Exception {
        if (thePiece == 'R') {
            return leftOrRight(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'N') {
            return leftOrRight(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'B') {
            return leftOrRight(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'K') {
            return leftOrRight(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'Q') {
            return leftOrRight(arrayElement, thePiece, toPosition);
        }
        throw new Exception("Invalid Piece");
    }

    private String leftOrRight(String arrayElement, char thePiece, Position toPosition) throws Exception {
        if (thePiece == 'R') {
            if (isRightRookCanMove(arrayElement, toPosition)) {
                return "RR";
            } else
                return "LR";
        } else if (thePiece == 'N') {
            if (isRightKnightCanMove(arrayElement, toPosition)) {
                return "RN";
            } else
                return "LN";
        } else if (thePiece == 'B') {
            if (isRightBishopCanMove(arrayElement, toPosition)) {
                return "RB";
            } else
                return "LB";
        } else if (thePiece == 'K') {
            if (canTheKingMove(arrayElement, toPosition)) {
                return "K";
            }
        } else if (thePiece == 'Q') {
            if (canTheQueenMove(arrayElement, toPosition)) {
                return "Q";
            }
        }

        throw new Exception("Piece not Found");

    }

    private boolean isRightBishopCanMove(String arrayElement, Position toPosition) throws Exception {
        String piece = "RB";
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
            b.cell[toPosition.x][toPosition.y].pieceColor = Color.noColor;
        }
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            Bishop bishop = new Bishop();
            if (bishop.isMovePossible(fromPos, toPosition, b.cell)) {
                return true;
            } else
                return false;
        }

    }

    private boolean canTheQueenMove(String arrayElement, Position toPosition) throws Exception {
        String piece = "Q";
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
            b.cell[toPosition.x][toPosition.y].pieceColor = Color.noColor;
        }
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            Bishop bishop = new Bishop();
            Rook rook = new Rook(currentPlayerColor);
            if (bishop.isMovePossible(fromPos, toPosition, b.cell) || rook.isMovePossible(fromPos, toPosition, b.cell)) {
                return true;
            }
        }
        return false;
    }

    private boolean canTheKingMove(String arrayElement, Position toPosition) throws Exception {
        String piece = "K";
        List<Position> possiblePositions;
        Position fromPos=getPiecePosition(currentPlayerColor.stringFormat+piece);
        King king = new King(currentPlayerColor);
        possiblePositions = king.getPossiblePositions(fromPos);
        for (int i = 0; i <= 8; i++) {
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



    private boolean isRightKnightCanMove(String arrayElement, Position toPosition) throws Exception {
        String piece = "RN";
        List<Position> possiblePositions;
        Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (fromPosition.x == 9 && fromPosition.y == 9) {
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


    private boolean isRightRookCanMove(String arrayElement, Position toPosition) throws Exception {

        String piece = "RR";
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
            b.cell[toPosition.x][toPosition.y].pieceColor = Color.noColor;
        }
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            Rook rook = new Rook(currentPlayerColor);
            return rook.isMovePossible(fromPos, toPosition, b.cell);
        }
    }


    private int getNumOf(char character) {
        String alphabet = "abcdefgh";
        return alphabet.indexOf(character);
    }


    public Position getPiecePosition(String piece) throws Exception {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.cell[i][j].getPieceType().equals(piece)) {
                    return new Position(i, j);
                }
            }

        }
        return new Position(9, 9);

    }
}


