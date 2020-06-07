package GameLogic;

import java.util.List;

public class Knight extends Piece{

      Knight(Color color)
      {
        this.color = color;
        this.pieceType = PieceType.KNIGHT;
      }

      public List<Position> getPossiblePositions() {
        return null;
    }
}
