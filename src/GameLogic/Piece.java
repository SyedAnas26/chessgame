package GameLogic;

import java.util.List;

public abstract class Piece {
  Color color = null;
  PieceType pieceType = null;
    abstract List<Position> getPossiblePositions(Position toPosition);
}
