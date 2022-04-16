package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class ProfessorManager {
    private final EnumMap<PawnColor, Player> map;
    private boolean cheeseMerchantEffect;

    public ProfessorManager(){
        map = new EnumMap<>(PawnColor.class);
        cheeseMerchantEffect = false;
    }

    public void setProfessorOwner(PawnColor color, Player owner){
        map.put(color, owner);
    }

    public Player getProfessorOwner(PawnColor color){
        return map.get(color);
    } //forse da eliminare


    /**
     * Receives in input a player who will be compared with the players contained in the map
     * and will return the colors associated with it
     * @param player who needs to be compared
     * @return returns an ArrayList of colors
     */
    public ArrayList<PawnColor> colorsAssociateToPlayer(Player player){

        ArrayList<PawnColor> color = new ArrayList<>();
        for(PawnColor pawnColor : map.keySet()){
            if(player.equals(map.get(pawnColor))){
                color.add(pawnColor);
            }
        }

        return color;
    }

    public void assertCheeseMerchantEffect() {
        cheeseMerchantEffect = true;
    }


    /**
     * this method creates an ArrayList containing all the players that are present within ProfessorManager
     * without repetition
     * @return ArrayList with the different players contained
     */
    public ArrayList<Player> playersContained(){

        ArrayList<Player> player = new ArrayList<>();
        for(PawnColor pawnColor : map.keySet()){
            if(!player.contains(map.get(pawnColor)) && map.get(pawnColor)!=null ){
                player.add(map.get(pawnColor));
            }

        }

        return player;
    }



}
