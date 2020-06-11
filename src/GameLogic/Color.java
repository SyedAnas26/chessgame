package GameLogic;

import javax.swing.plaf.IconUIResource;

enum Color {

    White("W"),
    Black("B"),
    Empty(" ");

    String Stringformat;

    Color(String Stringformat) {
        this.Stringformat = Stringformat;
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
