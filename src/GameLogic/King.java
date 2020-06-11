package GameLogic;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {


    King(Color color) {
        this.color = color;
        this.pieceType = PieceType.KNIGHT;
    }

    public List<Position> getPossiblePositions(Position fromPosition) {
        List<Position> possiblePositions = new ArrayList<>();
        for (int i = fromPosition.x - 1; i <= fromPosition.x + 1; i++) {
            for (int j = fromPosition.y - 1; j <= fromPosition.y + 1; j++) {
                possiblePositions.add(new Position(i, j));
            }
        }
        return possiblePositions;
    }
}
