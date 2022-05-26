package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.cliview.Color;
import it.polimi.ingsw.cliview.Matrix;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.studentmanagers.*;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.PawnColor.*;

public class GameState implements Serializable, ModelEvent {
    private final int bag;
    private final EnumMap<PawnColor, UUID> professorOwners;
    private final ArrayList<HashMap<UUID, ArrayList<PawnColor>>> clouds;
    private final ArrayList<HashMap<UUID, ArrayList<PawnColor>>> islands;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> entrances;
    private final HashMap<UUID, EnumMap<PawnColor, Integer>> diningRooms;
    private final HashMap<UUID, ArrayList<Integer>> assistantDecks;
    private final HashMap<UUID, Integer> coinsMap;
    private final HashMap<AvailableCharacter, Boolean> characterCards;
    private final HashMap<UUID, Integer> playedAssistantCards;
    private final UUID motherNaturePosition;
    private final HashMap<UUID, UUID> islandOwners;
    private final ArrayList<String> nicknames;

    //private ArrayList<String> [][] e;

    private ArrayList<Matrix> matrix;

    //private Matrix matrix;

    public GameState(GameModel gameModel) {
        bag = gameModel.countStudentsInBag();
        professorOwners = gameModel.getProfessorOwners();
        clouds = gameModel.getClouds();
        islands = gameModel.getIslands();
        entrances = gameModel.getEntrances();
        diningRooms = gameModel.getDiningRooms();
        assistantDecks = gameModel.getDecks();
        coinsMap = gameModel.getCoinsMap();
        characterCards = gameModel.getAvailableCharacterCards();
        playedAssistantCards = gameModel.getPlayedAssistantCards();
        motherNaturePosition = gameModel.getMotherNaturePosition();
        islandOwners = gameModel.getIslandOwners();
        nicknames = gameModel.getPlayerNicknames();

        //ArrayList<Player> players = gameModel.getPlayers();


        matrix = new ArrayList<>();
        for (Player p : gameModel.getPlayers()){
            matrixCreation(5, max(p.getEntrance()), p.getEntrance(), p.getDiningRoom());
        }

        //gMatrix(5, max(gameModel.getPlayers().get(0).getEntrance()), gameModel.getPlayers().get(0).getEntrance(), gameModel.getPlayers().get(0).getDiningRoom());

        //matrixCreation(players.get(0).getEntrance());
    }

    public ArrayList<Matrix> getMatrix(){
        return matrix;
    }

    private int max(StudentCounter studentCounter){
        int max=-1;
        for (PawnColor c : PawnColor.values()){
            if (studentCounter.count(c)>max){
                max=studentCounter.count(c);
            }
        }
        return max;
    }


    public void matrixCreation(int n, int m, StudentCounter studentCounter, StudentCounter studentCounter2){
        matrix.add(new Matrix(n, m, studentCounter, studentCounter2));
        //matrix = new Matrix(n, m, studentCounter, studentCounter2);
    }

    /*public ArrayList<String> [][] getE(){
        return e;
    }*/

    /*public ArrayList<Player> getPlayers(){
        return players;
    }*/

    public int getBag() {
        return bag;
    }

    public EnumMap<PawnColor, UUID> getProfessorOwners() {
        return professorOwners;
    }

    public ArrayList<HashMap<UUID, ArrayList<PawnColor>>> getClouds() {
        return clouds;
    }

    public ArrayList<HashMap<UUID, ArrayList<PawnColor>>> getIslands() {
        return islands;
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getEntrances() {
        return entrances;
    }

    public HashMap<UUID, EnumMap<PawnColor, Integer>> getDiningRooms() {
        return diningRooms;
    }

    public HashMap<UUID, ArrayList<Integer>> getAssistantDecks() {
        return assistantDecks;
    }

    public HashMap<UUID, Integer> getCoinsMap() {
        return coinsMap;
    }

    public HashMap<AvailableCharacter, Boolean> getCharacterCards() {
        return characterCards;
    }

    public HashMap<UUID, Integer> getPlayedAssistantCards() {
        return playedAssistantCards;
    }

    public UUID getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public HashMap<UUID, UUID> getIslandOwners() {
        return islandOwners;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        eventManager.notify(this);
    }

    /*@Override
    public String toString(){
        String s = ;

        return s;
    }*/

    /*@Override
    public String toString() {
        return "GameState{" +
                "\nbag=" + bag +
                ", \nprofessorOwners=" + professorOwners +
                ", \nclouds=" + clouds +
                ", \nislands=" + islands +
                ", \nentrances=" + entrances +
                ", \ndiningRooms=" + diningRooms +
                ", \nassistantDecks=" + assistantDecks +
                ", \ncoinsMap=" + coinsMap +
                ", \ncharacterCards=" + characterCards +
                ", \nplayedAssistantCards=" + playedAssistantCards +
                ", \nmotherNaturePosition=" + motherNaturePosition +
                ", \nislandOwners=" + islandOwners +
                ", \nnicknames=" + nicknames +
                "}";
    }*/

    /*public void matrixCreation(Entrance entrance){

        //e = new ArrayList[][]




        e = new ArrayList[10][10];

        /*e[0][0].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
        e[0][1].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
        e[0][2].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
        e[0][0].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
        e[1][0].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
        e[2][0].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);*/


        /*for (int j=0; j<10; j++){
            for (int i=0; i<10; i++){
                e[j][i] = new ArrayList<>();
                e[j][i].add(Color.ANSI_BLUE+ "\u2022" + Color.RESET);
            }
        }

        /*for (PawnColor color : values()){
            //e.add(new ArrayList<String>());
            int numColor = entrance.count(color);
            //e = new ArrayList[numColor][];
            for (int i = 0; i<numColor; i++){
                e[j][i].add(sameColor(color) + "\u2022" + Color.RESET);
                //e.get(j).add(sameColor(color) + "\u2022" + Color.RESET);
            }
            j++;
        }*/




    //}

    private Color sameColor(PawnColor color){
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






}
