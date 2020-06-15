package GameLogic;

public enum Color {

    White("W"),
    Black("B"),
    noColor(" ");

    String stringFormat;

    Color(String stringFormat) {
        this.stringFormat = stringFormat;
    }
};

/*String getplayercolor(currentplayer)
{
    if(currentplayer=1)
        return White;
    if (currentplayer=2)
        return Black;
    else
        return Emptyspace;
}

}*/
