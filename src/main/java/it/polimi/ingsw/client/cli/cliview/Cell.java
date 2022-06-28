package it.polimi.ingsw.client.cli.cliview;

import java.io.Serializable;

public class Cell implements Serializable {

    private Bullet bullet;

    public Cell() {
        bullet = new Bullet();
    }


    public Bullet getBullet(){
        return bullet;
    }

    @Override
    public String toString(){
        return getBullet().toString();
    }

}
