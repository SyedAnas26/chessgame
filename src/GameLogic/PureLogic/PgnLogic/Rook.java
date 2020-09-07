package GameLogic.PureLogic.PgnLogic;

import java.util.List;

public class Rook extends Piece{
    Rook(Color color) {
        this.color = color;
        this.pieceType = PieceType.ROOK;
    }
    boolean isMovePossible(Position fromPos,Position toPosition,Cell cell[][]){
        if(toPosition.x==fromPos.x && toPosition.y==fromPos.y)
        {
            return false;
        }

        if (fromPos.x == toPosition.x) {
            if (fromPos.y < toPosition.y) {
                for (int i = fromPos.y + 1; i < toPosition.y; ++i)
                    if (!Color.noColor.equals(cell[fromPos.x][i].getPieceColor()))
                        return false;
            } else {
                for (int i = fromPos.y - 1; i > toPosition.y; --i)
                    if (!Color.noColor.equals(cell[fromPos.x][i].getPieceColor()))
                        return false;
            }



        } else if (fromPos.y == toPosition.y) {
            if (fromPos.x < toPosition.x) {
                for (int i = fromPos.x + 1; i < toPosition.x; ++i)
                    if (!Color.noColor.equals(cell[i][fromPos.y].getPieceColor()))
                        return false;
            } else {
                for (int i = fromPos.x - 1; i > toPosition.x; --i)
                    if (!Color.noColor.equals(cell[i][fromPos.y].getPieceColor()))
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
