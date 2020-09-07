package GameLogic.PureLogic.PgnLogic;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

      Knight(Color color)
      {
        this.color = color;
        this.pieceType = PieceType.KNIGHT;
      }

      public List<Position> getPossiblePositions(Position fromPosition){
          List<Position> possiblePositions = new ArrayList<>();
          possiblePositions.add(new Position(fromPosition.x+2,fromPosition.y-1));
          possiblePositions.add(new Position(fromPosition.x+2,fromPosition.y+1));
          possiblePositions.add(new Position(fromPosition.x+1,fromPosition.y-2));
          possiblePositions.add(new Position(fromPosition.x+1,fromPosition.y+2));
          possiblePositions.add(new Position(fromPosition.x-1,fromPosition.y-2));
          possiblePositions.add(new Position(fromPosition.x-1,fromPosition.y+2));
          possiblePositions.add(new Position(fromPosition.x-2,fromPosition.y-1));
          possiblePositions.add(new Position(fromPosition.x-2,fromPosition.y+1));
          return possiblePositions;
    }
}
