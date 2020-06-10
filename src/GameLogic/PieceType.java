package GameLogic;

public enum PieceType {
    ROOK("R"),
    KNIGHT("K"),
    BISHOP("B"),
    KING("K"),
    QUEEN("Q"),
    PAWN("P");

    String Stringformat;

    PieceType(String Stringformat) {
        this.Stringformat = Stringformat;
    }
}