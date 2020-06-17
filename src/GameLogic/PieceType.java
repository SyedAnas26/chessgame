package GameLogic;

public enum PieceType {
    ROOK("R"),
    KNIGHT("K"),
    BISHOP("B"),
    KING("K"),
    QUEEN("Q"),
    PAWN("P");

    String StringFormat;
    PieceType(String StringFormat) {
        this.StringFormat = StringFormat;
    }
}