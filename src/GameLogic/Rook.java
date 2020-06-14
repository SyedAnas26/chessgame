package GameLogic;

import java.util.List;

public class Rook extends Piece{
    Rook(Color color) {
        this.color = color;
        this.pieceType = PieceType.KNIGHT;
    }
    boolean isMovePossible(Position fromPos,Position toPosition,Cell cell[][]){
        if(toPosition.x==fromPos.x && toPosition.y==fromPos.y)
        {
            return false;
        }

        if (fromPos.x == toPosition.x) {
            if (fromPos.y < toPosition.y) {
                for (int i = fromPos.y + 1; i <= toPosition.y; ++i)
                    if (!"   ".equals(cell[fromPos.x][i].getPieceType()))
                        return false;
            } else {
                for (int i = fromPos.y - 1; i >= toPosition.y; --i)
                    if (!"   ".equals(cell[fromPos.x][i].getPieceType()))
                        return false;
            }



        } else if (fromPos.y == toPosition.y) {
            if (fromPos.x < toPosition.x) {
                for (int i = fromPos.x + 1; i <= toPosition.y; ++i)
                    if (!"   ".equals(cell[i][fromPos.y].getPieceType()))
                        return false;
            } else {
                for (int i = fromPos.x - 1; i >= toPosition.y; --i)
                    if (!"   ".equals(cell[i][fromPos.y].getPieceType()))
                        return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public List<Position> getPossiblePositions(Position toPosition) {
        return null;
    }
}