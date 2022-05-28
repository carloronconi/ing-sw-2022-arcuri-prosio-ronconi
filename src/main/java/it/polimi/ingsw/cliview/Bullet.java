package it.polimi.ingsw.cliview;

import java.io.Serializable;

public class Bullet implements Serializable {
    private static final String BULLET ="\u2022";

    private Color color;
    private String symbol;

    public Bullet(){
        color=Color.RESET;
        symbol=" ";
    }

    public Bullet(Color color){
        this.color=color;
        this.symbol=BULLET;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public void setSymbol(){
        symbol=BULLET;
    }

    @Override
    public String toString(){
        return color + symbol + Color.RESET;
    }

    public void dumb(){
        System.out.println(this);
    }


}
