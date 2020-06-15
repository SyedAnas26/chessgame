package GameLogic;

import java.util.List;

public class Bishop extends Piece{
    boolean isMovePossible(Position fromPos,Position toPosition,Cell cell[][]) {

        if (toPosition.x == fromPos.x && toPosition.y == fromPos.y) {
            return false;
        }
        if(toPosition.x>fromPos.x && toPosition.y<fromPos.y){
            for(int i=fromPos.x+1;i<=toPosition.x;){
                for(int j=fromPos.y-1;j>=toPosition.y;--j)
                {
                    if (!Color.noColor.equals(cell[i][j].getPieceColor()))
                        return false;
                    else
                        i++;
                }

            }
        }
        else if (toPosition.x>fromPos.x && toPosition.y>fromPos.y)
        {
            for(int i=fromPos.x+1;i<=toPosition.x;){
                for(int j=fromPos.y+1;j<=toPosition.y;++j)
                {
                    if (!Color.noColor.equals(cell[i][j].getPieceColor()))
                        return false;
                    else
                        i++;
                }
            }
        }
       else if(toPosition.x<fromPos.x && toPosition.y<fromPos.y){
            for(int i=fromPos.x-1;i>=toPosition.x;){
                for(int j=fromPos.y-1;j>=toPosition.y;--j)
                {
                    if (!Color.noColor.equals(cell[i][j].getPieceColor()))
                        return false;
                    else
                        i--;
                }
            }
        }
        else if (toPosition.x<fromPos.x && toPosition.y>fromPos.y)
        {
            for(int i=fromPos.x-1;i>=toPosition.x;){
                for(int j=fromPos.y+1;j<=toPosition.y;++j)
                {
                    if (!Color.noColor.equals(cell[i][j].getPieceColor()))
                        return false;
                    else
                        i--;
                }
            }
        }
        return true;
           }

    public List<Position> getPossiblePositions(Position toPosition) {
        return null;
    }
}
