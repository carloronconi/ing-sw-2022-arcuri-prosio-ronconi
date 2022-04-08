package it.polimi.ingsw.model;

import java.util.EnumMap;

public class ProfessorManager {
    private final EnumMap<PawnColor, Player> map;

    public ProfessorManager(){
        map = new EnumMap<>(PawnColor.class);
    }

    public void setProfessorOwner(PawnColor color, Player owner){
        map.put(color, owner);
    }

    public Player getProfessorOwner(PawnColor color){
        return map.get(color);
    }
}
