package GameLogic.PureLogic.PgnLogic;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public List<Position> getPossiblePositions(Position fromPosition) {
        List<Position> possiblePositions = new ArrayList<>();
        possiblePositions.add(new Position(fromPosition.x+1,fromPosition.y));
        possiblePositions.add(new Position(fromPosition.x+2,fromPosition.y));
        possiblePositions.add(new Position(fromPosition.x-1,fromPosition.y));
        possiblePositions.add(new Position(fromPosition.x-2,fromPosition.y));
        return possiblePositions;
    }
}
