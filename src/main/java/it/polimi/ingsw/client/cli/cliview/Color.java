package it.polimi.ingsw.client.cli.cliview;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.TowerColor;

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



    private String escape;


    Color(String escape)
    {
        this.escape = escape;
    }


    public String getEscape()
    {
        return escape;
    }

    public static Color pawnColorConverter(PawnColor color){
        switch (color){
            case RED:
                return Color.ANSI_RED;
            case GREEN:
                return Color.ANSI_GREEN;
            case BLUE:
                return Color.ANSI_BLUE;
            case YELLOW:
                return Color.ANSI_YELLOW;
            case PURPLE:
                return Color.ANSI_PURPLE;
        }
        return Color.RESET;
    }

    public static Color towerColorConverter(TowerColor color){
        switch (color){
            case WHITE:
                return Color.ANSI_WHITE;
            case BLACK:
                return Color.ANSI_BLACK;
            case GREY:
                return Color.ANSI_GREY;
        }
        return Color.RESET;
    }


    @Override
    public String toString()
    {
        return escape;
    }
}
