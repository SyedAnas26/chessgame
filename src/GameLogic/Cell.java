package GameLogic;

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

    public String getPieceColor() {
        return pieceColor.toString();
    }




    @Override
    public String toString() {
        return "Cell{" +
                "Position=" + position +
                ", Color=" + pieceColor+
                "  " + pieceType +                '}';
    }


        /*       String getPieceRepresentation()
        {
            return  getCharByColor() + pieceType + xPosition + yPosition;
        }


        String getCharByColor()
        {
            return  color == true? "B" : "W";
        }

       String getCellColor()
        {
            return  color == true? "B" : "W";
        }
*/






}
