package GameLogic.PureLogic.PgnLogic;

public class Cell extends Object {
    Color pieceColor;
    String pieceType;
    Position position;


    Cell(Color pieceColor, String pieceType, Position position) {

        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.position = position;
    }

    public String getPieceType() {
        return pieceType;
    }

    public Color getPieceColor() {

        return pieceColor;
    }
    public String getPieceColorStringFormat() {
        return pieceColor.stringFormat;
    }


    @Override
    public String toString() {
        return "Cell{" +
                "Position=" + position +
                ", Color=" + pieceColor +
                "  " + pieceType + '}';
    }

}
