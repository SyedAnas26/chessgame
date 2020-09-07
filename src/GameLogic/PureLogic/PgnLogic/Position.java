package GameLogic.PureLogic.PgnLogic;

public class Position {
     int x;
     int y;
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    String getPositionInOriginalFormat()
    {

        String alphabet = "abcdefgh";
        return  alphabet.charAt(this.y) + Integer.toString(this.x+1);
    }
    }

