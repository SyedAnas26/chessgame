package GameLogic;

import java.util.List;

public class Bishop extends Piece{
    boolean isMovePossible(Position fromPos,Position toPosition) {
        return fromPos.x - toPosition.x == fromPos.y - toPosition.y || fromPos.x - toPosition.x == -fromPos.y + toPosition.y;
    }
    public List<Position> getPossiblePositions(Position toPosition) {
return null;
    }
}

