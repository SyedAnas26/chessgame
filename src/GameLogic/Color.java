package GameLogic;

public enum Color {

    White("W"),
    Black("B"),
    noColor(" ");

    String stringFormat;

    Color(String stringFormat) {
        this.stringFormat = stringFormat;
    }
}

