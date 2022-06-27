package it.polimi.ingsw.client.cli.cliview;

import java.io.Serializable;

public class Cell implements Serializable {

    private Bullet bullet;

    public Cell() {
        bullet = new Bullet();
        //this.stato="libera";
        //this.oggetto=null;
    }

    /*public Cell(Color color){
        bullet = new Bullet(color);
    }*/

    /*public void setOccupata(Object ogg) {
        this.oggetto=ogg;
        this.stato="occupata";
    }*/

    /*public String getStato() {
        return stato; }*/

    /*public void setLibera() {
        this.stato="libera"; }*/

    /*public Object getOccupante() {
        return this.oggetto; }*/

    //private String stato;
    //private Object oggetto;

    public Bullet getBullet(){
        return bullet;
    }

    @Override
    public String toString(){
        return getBullet().toString();
    }

}
