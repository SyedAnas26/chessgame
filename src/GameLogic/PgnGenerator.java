package GameLogic;

public class PgnGenerator {
    Board board = new Board();
    Position fromPosition = new Position(-1, -1);
    Position toPosition = new Position(-1, -1);
    String kill="";

    public PgnGenerator() {
        board.setBoard();
    }

 /*   public static void main(String[] args) throws Exception {
        PgnGenerator pg = new PgnGenerator();
        pg.convertToPgn("f2", "f4");
        pg.convertToPgn("h7", "h5");
        pg.convertToPgn("e2", "e4");
        pg.convertToPgn("g8", "f6");
        pg.convertToPgn("d1", "g4");
        pg.convertToPgn("b8", "a6");
        pg.convertToPgn("f1", "c4");
        pg.convertToPgn("h8", "h6");
        pg.convertToPgn("e1", "f2");
        pg.convertToPgn("f6", "g4");
        pg.convertToPgn("f2", "f3");
        pg.convertToPgn("h6", "b6");
        pg.convertToPgn("c4", "f7");
    }
*/
    public String convertToPgn(String fromPos, String toPos) throws Exception {
        String piece = "";
        toPosition.y = getNumOf(toPos.charAt(0));
        fromPosition.y = getNumOf(fromPos.charAt(0));
        toPosition.x = Integer.parseInt(toPos.substring(1, 2)) - 1;
        fromPosition.x = Integer.parseInt(fromPos.substring(1, 2)) - 1;
        if(board.cell[toPosition.x][toPosition.y].getPieceType().equals(" ")){
            kill="";
        }
        else {
            kill="x";
        }
        String pieceType = board.cell[fromPosition.x][fromPosition.y].getPieceType();
        board.cell[toPosition.x][toPosition.y] = board.cell[fromPosition.x][fromPosition.y];
        board.cell[fromPosition.x][fromPosition.y] = new Cell(Color.noColor, " ", fromPosition);
        if (pieceType.equals("WRR") || pieceType.equals("WLR") || pieceType.equals("BRR") || pieceType.equals("BLR")) {
            piece = "R";
        } else if (pieceType.equals("WRN") || pieceType.equals("WLN") || pieceType.equals("BRN") || pieceType.equals("BLN")) {
            piece = "N";
        } else if (pieceType.equals("WRB") || pieceType.equals("WLB") || pieceType.equals("BRB") || pieceType.equals("BLB")) {
            piece = "B";
        } else if (pieceType.equals("WQ") || pieceType.equals("BQ")) {
            piece = "Q";
        } else if (pieceType.equals("WK") || pieceType.equals("BK")) {
            piece = "K";
        } else if (pieceType.equals("WP") || pieceType.equals("BP")) {
            piece = "";
        }
        System.out.println(piece + kill + toPos);
        return "" + piece + kill + toPos;
    }

    int getNumOf(char character) {
        String alphabet = "abcdefgh";
        return alphabet.indexOf(character);
    }

    void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = board.cell[i][j].getPieceType();
                if (piece == null) {
                    System.out.print("\t|   |");
                } else {
                    System.out.printf("\t|%3s|", piece);
                }
                if (j == 7) {
                    System.out.print("\n \n");
                }
            }
        }
    }
}
