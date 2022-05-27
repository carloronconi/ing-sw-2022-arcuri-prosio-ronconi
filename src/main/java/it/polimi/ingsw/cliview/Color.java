package it.polimi.ingsw.cliview;

public enum Color {
    ANSI_BLACK("\u001B[30m"),
    ANSI_GREY("\u001B[90m"),
    ANSI_WHITE("\u001B[97m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[93m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    RESET("\u001B[0m");

    //static final String RESET = "\u001B[0m";


    private String escape;


    Color(String escape)
    {
        this.escape = escape;
    }


    public String getEscape()
    {
        return escape;
    }


    @Override
    public String toString()
    {
        return escape;
    }
}
