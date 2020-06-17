package GameLogic;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class GameManager {
    int currentPlayer = 1;
    GameStatus gameStatus;
    Color currentPlayerColor;
    public Board b = new Board();
    public String[] gamePlayAsArray;

    public GameManager(String gamePlay) throws Exception {

        System.out.println(" Welcome To The Chess Game");
        b.setBoard();
        makeGivenStringIntoArray(gamePlay);
    }

    private void makeGivenStringIntoArray(String gamePlay) {
        String[] clean = gamePlay.trim().split("\\s+|\\.");
        gamePlayAsArray = Arrays.stream(clean)
                .map(String::trim)
                .filter(Predicate.isEqual("").negate())
                .toArray(String[]::new);

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
        for (int i = 1; i < 200; i++) {
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

    void playGame(String arrayElement) throws Exception {

        if (arrayElement.length() == 2) {
            for2elements(arrayElement);
        } else if (arrayElement.length() == 3) {
            if (arrayElement.charAt(2) == '+') {
                arrayElement = arrayElement.substring(0, 2);
                for2elements(arrayElement);
                System.out.println("\n \n \t \t \tIt is a Check\t \t \n \n");

            } else {
                for3elements(arrayElement);
            }
        } else if (arrayElement.length() == 4) {
            if (arrayElement.charAt(2) == '=') {
                pawnPromotion(arrayElement);
            } else if (arrayElement.charAt(3) == '+') {
                arrayElement = arrayElement.substring(0, 3);

                for3elements(arrayElement);
                System.out.println("\n \n \t \t \tIt is a Check\t \t \n \n");
            } else {
                for4elements(arrayElement);
            }
        } else if (arrayElement.length() == 5) {

            if (arrayElement.charAt(4) == '+') {
                arrayElement = arrayElement.substring(0, 4);
                if (arrayElement.charAt(2) == '=') {
                    pawnPromotion(arrayElement);
                } else {
                    for4elements(arrayElement);
                }
                System.out.println("\n \n \t \t \tIt is a Check\t \t \n \n");
            } else {
                for5elements(arrayElement);
            }
        } else if (arrayElement.length() == 6) {
            if (arrayElement.charAt(5) == '+') {
                arrayElement = arrayElement.substring(0, 5);
                for5elements(arrayElement);
            }
        }

    }

    private void pawnPromotion(String arrayElement) throws Exception {
        char thePiece = arrayElement.charAt(3);
        String newPiece = null;
        Position toPosition = new Position(0, 0);
        toPosition.x = Character.getNumericValue(arrayElement.charAt(1)) - 1;
        toPosition.y = getNumOf(arrayElement.charAt(0));
        String piece = currentPlayerColor.stringFormat + whichPawn(arrayElement, 'P', toPosition);
        Position fromPosition = getPiecePosition(piece);
        if (thePiece == 'Q') {
            newPiece = "NQ";
        } else if (thePiece == 'N') {
            newPiece = "NN";
        } else if (thePiece == 'R') {
            newPiece = "NR";
        } else if (thePiece == 'B') {
            newPiece = "NR";
        }
        b.cell[toPosition.x][toPosition.y] = new Cell(currentPlayerColor, currentPlayerColor.stringFormat + newPiece, toPosition);
        b.cell[fromPosition.x][fromPosition.y] = new Cell(Color.noColor, " ", fromPosition);
    }

    private void for5elements(String arrayElement) throws Exception {
        if (arrayElement.charAt(0) == 'O') {
            KingCastling("K", arrayElement);
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
                if (arrayElement.charAt(1) != '1' & arrayElement.charAt(1) != '2' & arrayElement.charAt(1) != '3' & arrayElement.charAt(1) != '4' & arrayElement.charAt(1) != '5' & arrayElement.charAt(1) != '6' & arrayElement.charAt(1) != '7' & arrayElement.charAt(1) != '8') {
                    fromPosition.y = getNumOf((arrayElement.charAt(1)));
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.x = i;
                        }
                    }
                } else {
                    fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;
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

            if (arrayElement.charAt(1) != 'x') {
                fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;
            }
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3))) - 1;
            String piece = whichPiece(arrayElement, 'P', toPosition);
            fromPosition = getPiecePosition(piece);
            movePiece(currentPlayerColor, piece, fromPosition, toPosition);
        } else {
            char thePiece = arrayElement.charAt(0);
            String piece = " ";
            toPosition.y = getNumOf(arrayElement.charAt(2));
            toPosition.x = Character.getNumericValue((arrayElement.charAt(3))) - 1;
            if (arrayElement.charAt(1) != 'x') {
                if (arrayElement.charAt(1) != '1' & arrayElement.charAt(1) != '2' & arrayElement.charAt(1) != '3' & arrayElement.charAt(1) != '4' & arrayElement.charAt(1) != '5' & arrayElement.charAt(1) != '6' & arrayElement.charAt(1) != '7' & arrayElement.charAt(1) != '8') {
                    fromPosition.y = getNumOf((arrayElement.charAt(1)));
                    for (int i = 0; i < 8; i++) {
                        if (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "L" + thePiece) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + "R" + thePiece)) || (b.cell[i][fromPosition.y].getPieceType().equals(currentPlayerColor.stringFormat + thePiece))) {
                            fromPosition.x = i;
                        }
                    }
                } else {
                    fromPosition.x = Character.getNumericValue((arrayElement.charAt(1))) - 1;
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
            KingCastling("K", arrayElement);
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
        String piece = whichPiece(arrayElement, 'P', toPosition);
        Position fromPosition = getPiecePosition(piece);
        movePiece(currentPlayerColor, piece, fromPosition, toPosition);
    }

    private void KingCastling(String thePiece, String arrayElement) throws Exception {
        Position toPosition = new Position(0, 0);
        Position toPosition2 = new Position(0, 0);
        int minusOrPlus = whichSideCastle(arrayElement,thePiece);
        Position pos = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
        String piece = leftOrRightOrNew(arrayElement, 'R', new Position(pos.x, pos.y + minusOrPlus));
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

    private int whichSideCastle(String arrayElement,String thePiece) throws Exception {
        Position kingOrQueenPosition = getPiecePosition(currentPlayerColor.stringFormat + thePiece);
        if(arrayElement.length()==3)
        {if (b.cell[kingOrQueenPosition.x][kingOrQueenPosition.y + 1].getPieceColor().equals(Color.noColor) && b.cell[kingOrQueenPosition.x][kingOrQueenPosition.y + 2].getPieceColor().equals(Color.noColor)) {
            return 1;}
        } else if(arrayElement.length()==5) {
            if (b.cell[kingOrQueenPosition.x][kingOrQueenPosition.y - 1].getPieceColor().equals(Color.noColor) && b.cell[kingOrQueenPosition.x][kingOrQueenPosition.y - 2].getPieceColor().equals(Color.noColor)) {
                return -1;

            }
        }
        return 0;
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
        if (thePiece == 'P') {
            if (arrayElement.length() == 4 || arrayElement.length() == 5) {
                if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
                    String piece;
                    if (currentPlayerColor == Color.White) {
                        piece = b.cell[toPosition.x - 1][getNumOf(arrayElement.charAt(0))].getPieceType();
                        if (piece != null && !piece.equals(" ") && piece.length() != 2 && piece.charAt(0) != 'B') {
                            if (!b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.White) && (checkIfNum(piece.substring(2, 3)) || piece.charAt(2) == '0')) {
                                return piece;
                            }
                        }
                    } else if (currentPlayerColor == Color.Black) {
                        piece = b.cell[toPosition.x + 1][getNumOf(arrayElement.charAt(0))].getPieceType();
                        if (piece != null && !piece.equals(" ") && piece.length() != 2 && piece.charAt(0) != 'W') {
                            if (!b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.Black) && (checkIfNum(piece.substring(2, 3)) || piece.charAt(2) == '0')) {
                                return piece;
                            }
                        }
                    }
                }
            } else {
                return currentPlayerColor.stringFormat + whichPawn(arrayElement, thePiece, toPosition);
            }
        } else if (thePiece == 'R') {
            return leftOrRightOrNew(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'N') {
            return leftOrRightOrNew(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'B') {
            return leftOrRightOrNew(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'K') {
            return leftOrRightOrNew(arrayElement, thePiece, toPosition);
        } else if (thePiece == 'Q') {
            return leftOrRightOrNew(arrayElement, thePiece, toPosition);
        }
        throw new Exception("Invalid Piece");
    }

    private String whichPawn(String arrayElement, char thePiece, Position toPosition) throws Exception {
        List<Position> possiblePositions;
        for (int j = 0; j < 8; j++) {
            String piece = Character.toString(thePiece) + j;
            Position fromPosition = getPiecePosition(currentPlayerColor.stringFormat + piece);
            Pawn pawn = new Pawn();
            possiblePositions = pawn.getPossiblePositions(fromPosition);
            if (currentPlayerColor == Color.White) {
                if (toPosition.x == possiblePositions.get(0).x && toPosition.y == possiblePositions.get(0).y && b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.noColor)) {
                    return piece;
                } else if (toPosition.x == possiblePositions.get(1).x && toPosition.y == possiblePositions.get(1).y && b.cell[toPosition.x - 1][toPosition.y].getPieceColor().equals(Color.noColor)) {
                    return piece;
                }

            } else if (currentPlayerColor == Color.Black) {
                if (toPosition.x == possiblePositions.get(2).x && toPosition.y == possiblePositions.get(2).y && b.cell[toPosition.x][toPosition.y].getPieceColor().equals(Color.noColor)) {
                    return piece;
                } else if (toPosition.x == possiblePositions.get(3).x && toPosition.y == possiblePositions.get(3).y && b.cell[toPosition.x + 1][toPosition.y].getPieceColor().equals(Color.noColor)) {
                    return piece;
                }
            }
        }
        throw new Exception("pawn not found");
    }

    private String leftOrRightOrNew(String arrayElement, char thePiece, Position toPosition) throws Exception {
        if (thePiece == 'R') {
            if (isRookCanMove(arrayElement, toPosition, "RR")) {
                return "RR";
            } else if (isRookCanMove(arrayElement, toPosition, "LR")) {
                return "LR";
            } else if (isRookCanMove(arrayElement, toPosition, "NR")) {
                return "NR";
            }
        } else if (thePiece == 'N') {
            if (isKnightCanMove(arrayElement, toPosition, "RN")) {
                return "RN";
            } else if (isKnightCanMove(arrayElement, toPosition, "LN")) {
                return "LN";
            } else if (isKnightCanMove(arrayElement, toPosition, "NN")) {
                return "NN";
            }
        } else if (thePiece == 'B') {
            if (isBishopCanMove(arrayElement, toPosition, "RB")) {
                return "RB";
            } else if (isBishopCanMove(arrayElement, toPosition, "LB")) {
                return "LB";
            } else if (isBishopCanMove(arrayElement, toPosition, "NN")) {
                return "NN";
            }
        } else if (thePiece == 'K') {
            if (canTheKingMove(arrayElement, toPosition)) {
                return "K";
            }
        } else if (thePiece == 'Q') {
            if (canTheQueenMove(arrayElement, toPosition, "Q")) {
                return "Q";
            } else if (canTheQueenMove(arrayElement, toPosition, "NQ")) {
                return "NQ";
            }
        }

        throw new Exception("Piece not Found");

    }

    private boolean isBishopCanMove(String arrayElement, Position toPosition, String piece) throws Exception {

        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
            b.cell[toPosition.x][toPosition.y].pieceColor = Color.noColor;
        }
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            Bishop bishop = new Bishop();
            return bishop.isMovePossible(fromPos, toPosition);
        }
    }

    private boolean canTheQueenMove(String arrayElement, Position toPosition, String piece) throws Exception {
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
        if (arrayElement.charAt(1) == 'x' || arrayElement.charAt(2) == 'x') {
            b.cell[toPosition.x][toPosition.y].pieceColor = Color.noColor;
        }
        if (fromPos.x == 9 && fromPos.y == 9) {
            return false;
        } else {
            Bishop bishop = new Bishop();
            Rook rook = new Rook(currentPlayerColor);
            if (bishop.isMovePossible(fromPos, toPosition) || rook.isMovePossible(fromPos, toPosition, b.cell)) {
                return true;
            }
        }
        return false;
    }

    private boolean canTheKingMove(String arrayElement, Position toPosition) throws Exception {
        String piece = "K";
        List<Position> possiblePositions;
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
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


    private boolean isKnightCanMove(String arrayElement, Position toPosition, String piece) throws Exception {
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


    private boolean isRookCanMove(String arrayElement, Position toPosition, String piece) throws Exception {
        Position fromPos = getPiecePosition(currentPlayerColor.stringFormat + piece);
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


