package GameLogic;

public class Cell extends Object{
    Position position;
    Piece piece;

        Cell(int xPosition, int yPosition){
            this.xPosition=xPosition;
            this.yPosition=yPosition;
        }

        Cell(Color pieceColor,String pieceType,int xPosition, int yPosition)
        {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.pieceType= pieceType;
            this.pieceColor=pieceColor;
        }

    public String getPieceType() {
        return pieceType;
    }
    public String getPieceColor(){
            return pieceColor.toString();
    }

    @Override
    public String toString() {
        return "Cell{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", pieceType='" + pieceType + '\'' +
                '}';
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
