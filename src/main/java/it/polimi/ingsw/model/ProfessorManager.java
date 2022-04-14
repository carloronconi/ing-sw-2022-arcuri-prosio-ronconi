package it.polimi.ingsw.model;

import java.util.EnumMap;

public class ProfessorManager {
    private final EnumMap<PawnColor, Player> map;
    private boolean cheeseMerchantEffect;

    public ProfessorManager(){
        map = new EnumMap<>(PawnColor.class);
        cheeseMerchantEffect = false;
    }



    public void assertCheeseMerchantEffect() {
        cheeseMerchantEffect = true;
    }
}
