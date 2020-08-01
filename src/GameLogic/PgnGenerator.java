package GameLogic;

import java.util.List;

public class PgnGenerator {
    Board board = new Board();
    Position fromPosition = new Position(-1, -1);
    Position toPosition = new Position(-1, -1);
    String kill = "";
    int castling = 0;
    String castlingColor = "";

    public PgnGenerator() {
        board.setBoard();
    }
//    public static void main(String[] args) throws Exception {
//        PgnGenerator pg = new PgnGenerator();
//        System.out.println(pg.convertToPgn("f2", "f4"));
//        System.out.println(pg.convertToPgn("e7", "e5"));
//        System.out.println(pg.convertToPgn("f4", "e5"));
//        System.out.println(pg.convertToPgn("h1", "h3"));
//        System.out.println(pg.convertToPgn("g8","f6"));
//        System.out.println(pg.convertToPgn("h3", "b3"));
//        System.out.println(pg.convertToPgn("c6", "d4"));
//        System.out.println(pg.convertToPgn("g1", "f3"));
//        System.out.println(pg.convertToPgn("d4", "f5"));
//        System.out.println(pg.convertToPgn("b1", "c3"));
//        System.out.println(pg.convertToPgn("f5", "h4"));
//        System.out.println(pg.convertToPgn("c3", "d5"));
//        System.out.println(pg.convertToPgn("f6", "e4"));
//        System.out.println(pg.convertToPgn("f3", "g5"));
//        System.out.println(pg.convertToPgn("e4", "c3"));
//        System.out.println(pg.convertToPgn("d5", "e3"));
//        System.out.println(pg.convertToPgn("h4", "f5"));
//        System.out.println(pg.convertToPgn("e3", "g4"));
//        System.out.println(pg.convertToPgn("f5", "d6"));
//        System.out.println(pg.convertToPgn("g4", "h6"));
//        System.out.println(pg.convertToPgn("f5", "d6"));
//        System.out.println(pg.convertToPgn("c3", "e4"));

//        pg.convertToPgn("f1", "c4");
//        pg.convertToPgn("h8", "h6");
//        pg.convertToPgn("e1", "f2");
//        pg.convertToPgn("f6", "g4");
//        pg.convertToPgn("f2", "f3");
//        pg.convertToPgn("h6", "b6");
//        pg.convertToPgn("c4", "f7");
//        Nh3
//                e5
//        Nc3
//                Nf6
//        Nf4
//                exf4

    //  }
    public String convertToPgn(String fromPos, String toPos) throws Exception {
        String piece = "";
        String pgnMove = "";
        toPosition.y = getNumOf(toPos.charAt(0));
        fromPosition.y = getNumOf(fromPos.charAt(0));
        toPosition.x = Integer.parseInt(toPos.substring(1, 2)) - 1;
        fromPosition.x = Integer.parseInt(fromPos.substring(1, 2)) - 1;
        if (board.cell[toPosition.x][toPosition.y].getPieceType().equals(" ")) {
            kill = "";
        } else {
            kill = "x";
        }
        String pieceType = board.cell[fromPosition.x][fromPosition.y].getPieceType();
        Color pieceColor = board.cell[fromPosition.x][fromPosition.y].getPieceColor();
        String pieceColorString = board.cell[fromPosition.x][fromPosition.y].getPieceColorStringFormat();
        if (pieceType.equals("WRR") || pieceType.equals("WLR") || pieceType.equals("BRR") || pieceType.equals("BLR")) {
            piece = "R";
        } else if (pieceType.equals("WRN") || pieceType.equals("WLN") || pieceType.equals("BRN") || pieceType.equals("BLN")) {
            piece = "N";
        } else if (pieceType.equals("WRB") || pieceType.equals("WLB") || pieceType.equals("BRB") || pieceType.equals("BLB")) {
            piece = "B";
        } else if (pieceType.equals("WQ") || pieceType.equals("BQ")) {
            piece = "Q";
        } else if (pieceType.equals("WK") || pieceType.equals("BK")) {
            if (isCastling()) {
                if (pieceType.equals("WK")) {
                    castlingColor = "W";
                } else {
                    castlingColor = "B";
                }
            } else {
                piece = "K";
            }
        } else if (pieceType.equals("WP") || pieceType.equals("BP")) {
            piece = "";
        }


        if (piece.equals("") && kill.equals("x")) {
            pgnMove = "" + fromPos.charAt(0) + kill + toPos;
        } else {
            pgnMove = "" + piece + kill + toPos;
        }
        if (piece.equals("R") || piece.equals("N")) {
            if (canTheOtherPieceMove(piece, pieceColor, pieceColorString)) {
                pgnMove = "" + piece + fromPos.charAt(0) + kill + toPos;
            }
        }
        if (castling != 0) {
            if (castling == 1) {
                pgnMove = "O-O";
            } else if (castling == 2) {
                pgnMove = "O-O-O";
            }
        }
        board.cell[toPosition.x][toPosition.y] = board.cell[fromPosition.x][fromPosition.y];
        board.cell[fromPosition.x][fromPosition.y] = new Cell(Color.noColor, " ", fromPosition);
        return pgnMove;

    }

    private boolean isCastling() throws Exception {
        if (fromPosition.x -2 == toPosition.x && fromPosition.y == toPosition.y) {
            castling = 2;
            Position rookPos = getPiecePosition(castlingColor + "LR");
            board.cell[rookPos.x + 3][rookPos.y] = board.cell[rookPos.x][rookPos.y];
            board.cell[rookPos.x][rookPos.y] = new Cell(Color.noColor, " ", fromPosition);
            return true;
        } else if (fromPosition.x + 2== toPosition.x  && fromPosition.y == toPosition.y) {
            castling = 1;
            Position rookPos = getPiecePosition(castlingColor + "RR");
            board.cell[rookPos.x - 2][rookPos.y] = board.cell[rookPos.x][rookPos.y];
            board.cell[rookPos.x][rookPos.y] = new Cell(Color.noColor, " ", fromPosition);
            return true;
        }
        return false;
    }

    private boolean canTheOtherPieceMove(String piece, Color pieceColor, String pieceColorString) throws Exception {
        Position leftPiecePosition = getPiecePosition(pieceColorString + "L" + piece);
        Position rightPiecePosition = getPiecePosition(pieceColorString + "R" + piece);
        if (piece.equals("R")) {
            Rook rook = new Rook(pieceColor);
            if (rook.isMovePossible(leftPiecePosition, toPosition, board.cell)) {
                if (rook.isMovePossible(rightPiecePosition, toPosition, board.cell)) {
                    return true;
                }
            }
        } else if (piece.equals("N")) {
            Knight knight = new Knight(pieceColor);
            List<Position> leftPossiblePositions = knight.getPossiblePositions(leftPiecePosition);
            List<Position> rightPossiblePositions = knight.getPossiblePositions(rightPiecePosition);
            for (int i = 0; i < 8; i++) {
                if (toPosition.x == leftPossiblePositions.get(i).x && toPosition.y == leftPossiblePositions.get(i).y) {
                    for (int j = 0; j < 8; j++) {
                        if (toPosition.x == rightPossiblePositions.get(j).x && toPosition.y == rightPossiblePositions.get(j).y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    int getNumOf(char character) {
        String alphabet = "abcdefgh";
        return alphabet.indexOf(character);
    }

    void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = board.cell[i][j].getPieceType();
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

    private Position getPiecePosition(String piece) throws Exception {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.cell[i][j].getPieceType().equals(piece)) {
                    return new Position(i, j);
                }
            }

        }
        return new Position(9, 9);
    }

}
