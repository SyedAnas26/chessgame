package GameLogic.PureLogic.PgnLogic;

public enum Color {

    White("W"),
    Black("B"),
    noColor(" ");

    public String stringFormat;

    Color(String stringFormat) {
        this.stringFormat = stringFormat;
    }

}

